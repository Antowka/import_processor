package ru.antowka.importer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
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
import ru.antowka.importer.mapper.NodeMapper;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.processing.AttachmentsProcessor;
import ru.antowka.importer.processing.DtoProcessor;
import ru.antowka.importer.processing.FileReader;
import ru.antowka.importer.processing.NotificationProcessor;
import ru.antowka.importer.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    /**
     * Начальная дата обработки файлов
     * Формат: 01.09.2022
     */
    @Value("${date.processing.start}")
    private String startDateProcessing;

    /**
     * Конечная дата обработки файлов
     * Формат: 30.09.2022
     */
    @Value("${date.processing.end}")
    private String endDateProcessing;

    /**
     * Абсолютный путь к результатирующему файлу
     */
    @Value("${path.file.output}")
    private String outputFile;

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

    @Bean
    public MultiResourceItemReader<NodeModel> multiResourceItemReader() {
        return new MultiResourceItemReaderBuilder<NodeModel>()
                .delegate(reader())
                .resources(getFilesForProcessing())
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
    public DtoProcessor dtoProcessor() {
        return new DtoProcessor();
    }

    @Bean
    public JsonFileItemWriter<NodeModel> writer() {
        JsonFileItemWriterBuilder<NodeModel> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<NodeModel> marshaller = new JacksonJsonObjectMarshaller<>();

        return builder
                .name("jsonNodeModelWriter")
                .jsonObjectMarshaller(marshaller)
                .resource(new FileSystemResource(outputFile))
                .build();
    }

    @Bean
    public FileReader<NodeModel> reader() {
        final FileReader<NodeModel> nodeModelFileReader = new FileReader<>();
        nodeModelFileReader.setLineMapper(new NodeMapper());
        return nodeModelFileReader;
    }

    @Bean
    public Job readHtmlFilesJob() {
        return jobBuilderFactory.get("readHtmlFilesJob")
                .incrementer(new RunIdIncrementer())
                //.listener(new JobResultListener())
                .flow(mainStep())
                .end()
                .build();
    }


    /**
     * Получение списка файлов для обхода
     *
     * @return
     */
    private Resource[] getFilesForProcessing() {

        final DateFolderModel startDate = new DateFolderModel(startDateProcessing);
        final DateFolderModel endDate = new DateFolderModel(endDateProcessing);

        //Проверяем на корректность даты
        if (startDate.moreThan(endDate)) {
            throw new IllegalArgumentException("Date arguments is wrong: " + startDateProcessing + " to " + endDateProcessing);
        }

        List<Resource> resources = new ArrayList<>();

        while (!startDate.equals(endDate)) {
            final Path path = Paths.get(pathToContentStore, startDate.buildPath());
            final Set<Path> allFoldersAndFilesFromSubFolders = FileUtils.getAllFilesFromSubFolders(path, "<h3><a href=\"http://", htmlFileReadLimitKb);

            //переходим на следующий день для следующий итерации
            startDate.addDay();

            if (CollectionUtils.isEmpty(allFoldersAndFilesFromSubFolders)) {
                continue;
            }

            final List<Path> allFilesFromSubFolders = allFoldersAndFilesFromSubFolders
                    .stream()
                    .sorted((a, b) -> { //Сортировка для того, чтоб сверху оказались наиболее свежие файлы (т.к. версии потом будут удалены как дубли)
                        try {
                            final Object lastModifiedTimeA = Files.getAttribute(a, "lastModifiedTime");
                            final Object lastModifiedTimeB = Files.getAttribute(b, "lastModifiedTime");
                            return ((FileTime) lastModifiedTimeB).compareTo((FileTime)lastModifiedTimeA);
                        } catch (IOException e) {
                            System.out.println("File doesn't have lastModifiedTime attr. Files: \n -" + a + "\n -" + b);
                        }
                        return 1;
                    })
                    .collect(Collectors.toList());

            //Если файлы не нашли
            if (CollectionUtils.isEmpty(allFilesFromSubFolders)) {
                continue;
            }

            final List<Resource> newResourcesOfFoundFiles = allFilesFromSubFolders
                    .stream()
                    .map(FileSystemResource::new)
                    .collect(Collectors.toList());
            resources.addAll(newResourcesOfFoundFiles);
        }

        return resources.toArray(new Resource[]{});
    }


    //ШАГИ
    @Bean
    public Step mainStep() {
        return stepBuilderFactory.get("mainStep")
                //.listener(new StepResultListener())
                .<NodeModel, NodeModel>chunk(2)
                .reader(multiResourceItemReader())
                .faultTolerant()
                .processor(compositeItemProcessor())
                .writer(writer())
                //.taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    @Bean
    public CompositeItemProcessor<NodeModel, NodeModel> compositeItemProcessor() {
        CompositeItemProcessor<NodeModel, NodeModel> compositeProcessor = new CompositeItemProcessor<>();
        List itemProcessors = new ArrayList();
        itemProcessors.add(attachmentProcessor());
        itemProcessors.add(notificationProcessor());
        itemProcessors.add(dtoProcessor());
        compositeProcessor.setDelegates(itemProcessors);

        return compositeProcessor;
    }
}
