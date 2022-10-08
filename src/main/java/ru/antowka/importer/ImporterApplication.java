package ru.antowka.importer;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.*;

@SpringBootApplication
public class ImporterApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(ImporterApplication.class, args);
    }
}
