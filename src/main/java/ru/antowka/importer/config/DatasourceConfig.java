package ru.antowka.importer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


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

    @Bean
    public JdbcTemplate notificationJdbcTemplate(@Qualifier("notificationDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate djJdbcTemplate(@Qualifier("bjDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
