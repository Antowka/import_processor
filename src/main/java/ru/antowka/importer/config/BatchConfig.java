package ru.antowka.importer.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.processing.FileProcessor;
import ru.antowka.importer.processing.FileReader;
import ru.antowka.importer.utils.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
    @Value("#{file.output}")
    private String outputFile;

    /**
     * Абсолютный путь к contentstore
     */
    @Value("#{path.to.contentstore}")
    private String pathToContentStore;

    @Bean
    public MultiResourceItemReader<NodeModel> multiResourceItemReader() {
        return new MultiResourceItemReaderBuilder<NodeModel>()
                .delegate(reader())
                .resources(getFilesForProcessing())
                .build();
    }

    @Bean
    public FileProcessor processor() {
        return new FileProcessor();
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
        return new FileReader<>();
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
            final List<Path> allFilesFromSubFolders = FileUtils.getAllFilesFromSubFolders(path, "<html>");

            //Если файлы не нашли
            if (CollectionUtils.isEmpty(allFilesFromSubFolders)) {
                System.out.println("Files not found in path: " + path);
                break;
            }

            final List<Resource> newResourcesOfFoundFiles = allFilesFromSubFolders
                    .stream()
                    .map(FileSystemResource::new)
                    .collect(Collectors.toList());
            resources.addAll(newResourcesOfFoundFiles);

            //переходим на следующий день для следующий итерации
            startDate.addDay();
        }

        return resources.toArray(new Resource[]{});
    }
}
