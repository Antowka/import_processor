package ru.antowka.importer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.CollectionUtils;
import ru.antowka.importer.dto.NodeDto;
import ru.antowka.importer.mapper.NodeMapper;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.override.MultiResourceItemReaderBuilderExt;
import ru.antowka.importer.processing.AttachmentsProcessor;
import ru.antowka.importer.processing.FileReader;
import ru.antowka.importer.processing.NodeAggregator;
import ru.antowka.importer.processing.NotificationProcessor;
import ru.antowka.importer.utils.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private NodeAggregator nodeAggregator;

    /**
     * Дата обработки файлов
     * Формат: 01.09.2022 для properties
     */
    final DateFolderModel processingDate;


    /**
     * Абсолютный путь к результатирующему файлу
     */
    private Path outputFolder;

    /**
     * Абсолютный путь к contentstore
     */
    @Value("${path.to.contentstore}")
    private String pathToContentStore;

    /**
     * Ограничение размера файла, который читаем при поиске html-файла
     */
    @Value("${html.file.read.limit.kb}")
    private int htmlFileReadLimitKb;

    /**
     * Ограничение размера файла, который читаем при поиске html-файла
     */
    @Value("${threads.limit:5}")
    private int threadLimit;

    @Autowired
    private ExecutorService taskWorker;

    private long processedFileCounter = 0;


    public BatchConfig(@Value("${date.processing.date}") String startDateProcessing, @Value("${path.folder.output}") String outPathString) throws IOException {
        this.processingDate = new DateFolderModel(startDateProcessing);
        this.outputFolder = Paths.get(outPathString, processingDate.getDateForOutputPath());

        if (!Files.exists(this.outputFolder)) {
            Files.createDirectory(this.outputFolder);
        }
    }

    public MultiResourceItemReader<NodeModel> multiResourceItemReader(final Resource[] filesForProcessing) {
        return new MultiResourceItemReaderBuilderExt<NodeModel>()
                .delegate(reader())
                .resources(filesForProcessing)
                .name("multiReader")
                .build();
    }

    @Bean
    public AttachmentsProcessor attachmentProcessor() {
        return new AttachmentsProcessor();
    }

    @Bean
    public NotificationProcessor notificationProcessor() {
        return new NotificationProcessor();
    }

    @Bean
    public FileReader<NodeModel> reader() {
        final FileReader<NodeModel> nodeModelFileReader = new FileReader<>();
        nodeModelFileReader.setLineMapper(new NodeMapper());
        return nodeModelFileReader;
    }

    @Bean
    public Job readHtmlFilesJob() {

        //Если кривые даты
        if (Objects.isNull(processingDate)) {
            throw new IllegalArgumentException("Date is fail");
        }

        Path dateFolder = Paths.get(pathToContentStore, processingDate.buildPath());
        if (!Files.exists(dateFolder)) {
            return null;
        }

        final JobBuilder readHtmlFilesJob = jobBuilderFactory
                .get("readHtmlFilesJob_" + Math.random())
                .incrementer(new RunIdIncrementer());

        SimpleJobBuilder flow = null;

        final Set<Path> pathsOfHoursInDay = FileUtils.buildPathsForHoursFolderByDay(pathToContentStore, processingDate);
        int stepCounter = 0;
        for (Path hourPath : pathsOfHoursInDay) {
            final Resource[] filesForProcessing = getFilesForProcessing(hourPath);

            if (filesForProcessing == null) {
                continue;
            }
            processedFileCounter += filesForProcessing.length;
            if (Objects.isNull(flow)) {
                flow = readHtmlFilesJob.start(mainStep("STEP_" + stepCounter, filesForProcessing));
            } else {
                flow.next(mainStep("STEP_" + stepCounter, filesForProcessing));
            }
            stepCounter++;
        }

        if (Objects.isNull(flow)) {
            throw new IllegalArgumentException("Flow is empty, some problems with arguments (start date, end date) or path to contentStore");
        }
        System.out.println("ALL FILES FOUND FOR PROCESSING: " + processedFileCounter);

        return flow
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {

                        final List<NodeDto> aggregateNodes = nodeAggregator.getAggregateNodes();
                        System.out.println("DOCUMENTS FOR EXPORT: " + aggregateNodes.size());

                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            String jsonString = mapper.writeValueAsString(aggregateNodes);
                            Files.write(Paths.get(outputFolder.toString(), "result.json"),
                                    jsonString.getBytes(StandardCharsets.UTF_8),
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.TRUNCATE_EXISTING);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        taskWorker.shutdownNow();
                    }
                })
                .build();
    }


    /**
     * Получение списка файлов для обхода
     *
     * @return
     */
    private Resource[] getFilesForProcessing(Path path) {

        final Set<Path> allFoldersAndFilesFromSubFolders = FileUtils
                .getAllFilesFromSubFolders(path, "<h3><a href=\"http", htmlFileReadLimitKb);

        if (CollectionUtils.isEmpty(allFoldersAndFilesFromSubFolders)) {
            System.out.println("Folder doesn't exist or doesn't have html files with searchObj: " + path);
            return null;
        }

        final List<Path> allFilesFromSubFolders = allFoldersAndFilesFromSubFolders
                .stream()
                .sorted((a, b) -> { //Сортировка для того, чтоб сверху оказались наиболее свежие файлы (т.к. версии потом будут удалены как дубли)
                    try {
                        final Object lastModifiedTimeA = Files.getAttribute(a, "lastModifiedTime");
                        final Object lastModifiedTimeB = Files.getAttribute(b, "lastModifiedTime");
                        return ((FileTime) lastModifiedTimeB).compareTo((FileTime) lastModifiedTimeA);
                    } catch (IOException e) {
                        System.out.println("File doesn't have lastModifiedTime attr. Files: \n -" + a + "\n -" + b);
                    }
                    return 1;
                })
                .collect(Collectors.toList());

        List<Resource> resources = allFilesFromSubFolders
                .stream()
                .map(FileSystemResource::new)
                .collect(Collectors.toList());

        return resources.toArray(new Resource[]{});
    }


    //ШАГИ
    public Step mainStep(String nameStep, final Resource[] filesForProcessing) {
        return stepBuilderFactory
                .get(nameStep)
                //.listener(new StepResultListener())
                .<NodeModel, NodeModel>chunk(threadLimit * 10)
                .reader(multiResourceItemReader(filesForProcessing))
                //.faultTolerant()
                .processor(compositeItemProcessor())
                .writer(nodeAggregator)
                .taskExecutor(taskExecutor())
                //.throttleLimit(3)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        final SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring_batch_pool_");
        taskExecutor.setConcurrencyLimit(threadLimit);
        return taskExecutor;
    }

    @Bean
    public CompositeItemProcessor<NodeModel, NodeModel> compositeItemProcessor() {
        CompositeItemProcessor<NodeModel, NodeModel> compositeProcessor = new CompositeItemProcessor<>();
        List itemProcessors = new ArrayList();
        itemProcessors.add(attachmentProcessor());
        itemProcessors.add(notificationProcessor());
        compositeProcessor.setDelegates(itemProcessors);

        return compositeProcessor;
    }
}
