package ru.antowka.importer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Configuration
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.notification")
    public DataSourceProperties notificationDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.bj")
    public DataSourceProperties bjDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource notificationDataSource() {
        return notificationDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSource bjDataSource() {
        return bjDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    /**
     * H2DB для хранения meta-данных для Spring-Batch (чтоб не лазил в Postgres)
     * @return
     */
    @Bean("h2db")
    @Primary
    public DataSource dataSource() throws Exception {
        final DataSource ds = dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();

        System.out.println("Загрузка доп данных в H2...");
        loadDataFromCSVToH2DB(ds);
        System.out.println("Загрузка доп данных в H2 - Завершено");

        return ds;
    }

    @Bean
    public JdbcTemplate notificationJdbcTemplate(@Qualifier("notificationDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate bjJdbcTemplate(@Qualifier("bjDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public JdbcTemplate h2JdbcTemplate(@Qualifier("h2db") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private void loadDataFromCSVToH2DB(DataSource ds) throws Exception {

        Connection connection = null;
        try {
            connection = ds.getConnection();
            final Statement statement = connection
                    .createStatement();
            final boolean execute = statement.execute("CREATE TABLE attachments (" +
                    "  doc_ref VARCHAR(77), " +
                    "  att_ref VARCHAR(77), " +
                    "  created TIMESTAMP, " +
                    "  modified TIMESTAMP, " +
                    "  size BIGINT, " +
                    "  mime VARCHAR(77)) ");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать БД attachments");
            if (connection != null) {
                connection.close();
            }

            throw new Exception();
        }

        if (!connection.isClosed()) {
            final ClassPathResource classPathResource = new ClassPathResource("attachments.csv");
            try (final InputStream inputStream = classPathResource.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder inserts = new StringBuilder();
                while (reader.ready()) {
                    String line = reader.readLine();
                    final String[] values = line.split(";");
                    inserts.append("INSERT INTO attachments (doc_ref, att_ref, created, modified, size, mime) VALUES ('").append(values[0]).append("', '").append(values[1]).append("', '").append(values[2]).append("', '").append(values[3]).append("', ").append(values[4]).append(", '").append(values[5]).append("');");
                }
                final Statement statement = connection.createStatement();
                statement.execute(inserts.toString());
            } catch (Exception e) {
                System.out.println("Не удалось импортировать данные в БД attachments");
            } finally {
                connection.close();
            }

            System.out.println("Импортированы данные в БД attachments");
        }
    }
}
