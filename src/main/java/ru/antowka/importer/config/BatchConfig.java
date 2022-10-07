package ru.antowka.importer.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.processing.FileProcessor;

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
     * Результатирующий файл
     */
    @Value("#{file.output}")
    private String outputFile;

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
    public FlatFileItemReader<NodeModel> reader() {
        //Create reader instance
        FlatFileItemReader<NodeModel> reader = new FlatFileItemReader<>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id", "firstName", "lastName" });
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
                    {
                        setTargetType(Employee.class);
                    }
                });
            }
        });
        return reader;
    }


    /**
     * Пулучение списка файлов для обхода
     *
     * @return
     */
    private Resource[] getFilesForProcessing() {
        final DateFolderModel startDate = new DateFolderModel(startDateProcessing);
        final DateFolderModel endDate = new DateFolderModel(endDateProcessing);

        //Проверяем на корректность даты
        if (startDate.moreThan(endDate)) {
            throw new IllegalArgumentException("Date arguments is wrong: " + startDateProcessing + " to " + endDateProcessing);
            return null;
        }


    }
}
