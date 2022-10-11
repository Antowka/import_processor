package ru.antowka.importer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadConfig {

    @Bean("taskWorker")
    public ExecutorService threadPoolExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
