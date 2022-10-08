package ru.antowka.importer;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ImporterApplication {

    public static void main(String[] args) throws FileNotFoundException {
        File test = new File("C:/alfresco-rn/alf_data/contentstore/2022/10/7/14/13/77491292-f17b-4d9f-9309-6b8fbd24212f.bin");
        InputStream is = new FileInputStream(test);
        try {
            new XWPFDocument(is);
            System.out.println("good");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new HSSFWorkbook(is);
            System.out.println("nogood");
        } catch (EncryptedDocumentException | IOException e){
        }
        SpringApplication.run(ImporterApplication.class, args);
    }

}
