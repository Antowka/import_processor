package ru.antowka.importer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.*;

@SpringBootApplication
public class ImporterApplication {

    public static void main(String[] args) throws FileNotFoundException {

        SpringApplication.run(ImporterApplication.class, args);
    }
}
