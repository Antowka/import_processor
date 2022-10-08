package ru.antowka.importer.service;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.antowka.importer.mapper.AttachmentRowMapper;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.Attachments;
import ru.antowka.importer.entitiy.BjRecord;
import ru.antowka.importer.model.Path;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchAttachmentServiceImpl implements SearchAttachmentService {

    @Autowired
    @Qualifier("bjJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * Абсолютный путь к contentstore
     */
    @Value("${path.to.contentstore}")
    private String pathToContentStore;
    private static final String TEMPLATE_CATEGORY_TEXT = "Добавление вложения";


    public String checkFile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        InputStream is = new FileInputStream(file);
        if (line.contains("PDF")) {
            return "pdf";
        } else {
            try {
                new HWPFDocument(is);
                return "doc";
            } catch (EncryptedDocumentException e) {
            }
            try {
                new HSSFWorkbook(is);
                return "xls";
            } catch (EncryptedDocumentException e) {
            }
            try {
                new XWPFDocument(is);
                return "docx";
            } catch (EncryptedDocumentException e) {
            }
            try {
                new XSSFWorkbook(is);
                return "xlsx";
            } catch (EncryptedDocumentException e) {
            }

        }
        return "-";
    }

    public Attachments searchAttachmentsDocument(String nodeRef) throws IOException {
        Attachments attachments = new Attachments();
        String query = "SELECT \"DATE\" , \"INITIATOR\" , \"RECORDDESCRIPTION\" , \"OBJECT1\"   FROM \"BUSINESSJOURNALSTORERECORD\" where \"EVENTCATEGORYTEXT\" = 'Добавление вложения' AND \"MAINOBJECT\" ='" + nodeRef + "'";
        List<BjRecord> bjList = jdbcTemplate.query(query, new AttachmentRowMapper());
        List<Attachment> attachmentList = new ArrayList<>();
        if (bjList.size() > 0) {
            for (BjRecord bj : bjList) {
                List<Path> paths = new ArrayList<>();
                Attachment attachment = new Attachment();
                String record = bj.getRecord();
                String name = record.substring(record.indexOf(bj.getRefAttachment(), 1), record.indexOf("к документу"));
                attachment.setNameAttachment(name.substring(name.indexOf(">") + 1, name.length() - 5));
                attachment.setCategory(record.substring(record.indexOf("в категорию") + 13, record.length() - 1));
                attachment.setInitiator(bj.getInitiator());
                String path1 = pathToContentStore + (bj.getDate().toString().substring(0, 4)) + "/" +
                        Integer.valueOf(bj.getDate().toString().substring(5, 7)) + "/"
                        + Integer.valueOf(bj.getDate().toString().substring(8, 10)) + "/"
                        + Integer.valueOf(bj.getDate().toString().substring(11, 13)) + "/"
                        + Integer.valueOf(bj.getDate().toString().substring(14, 16));
                String path2 = pathToContentStore + (bj.getDate().toString().substring(0, 4)) + "/" +
                        Integer.valueOf(bj.getDate().toString().substring(5, 7)) + "/"
                        + Integer.valueOf(bj.getDate().toString().substring(8, 10)) + "/"
                        + Integer.valueOf(bj.getDate().toString().substring(11, 13)) + "/"
                        + (Integer.valueOf(bj.getDate().toString().substring(14, 16)) + 1);
                File dir1 = new File(path1);
                File dir2 = new File(path2);
                File[] arr1Files = dir1.listFiles();
                File[] arr2Files = dir2.listFiles();
                File[] arrFiles;
                if (arr2Files != null) {
                    arrFiles = new File[arr2Files.length + arr1Files.length];
                    System.arraycopy(arr1Files, 0, arrFiles, 0, arr1Files.length);
                    System.arraycopy(arr2Files, 0, arrFiles, arr1Files.length, arr2Files.length);
                } else if (arr1Files != null) {
                    arrFiles = new File[arr1Files.length];
                    System.arraycopy(arr1Files, 0, arrFiles, 0, arr1Files.length);
                } else {
                    System.out.println("По записи в bj не нашли ни одного вложения. NodeRef документа" + nodeRef);
                    break;
                }
                List<File> lst = Arrays.asList(arrFiles);
                for (File file : lst) {
                    String type = checkFile(file);
                    if (type !="-") {
                        Path path = new Path();
                        path.setPath(file.toPath().toString());
                        path.setPath(type);
                        paths.add(path);
                    }
                }
                attachment.setPaths(paths);
                attachmentList.add(attachment);
            }
        } else {
            System.out.println("Нет записей в bj по данному документу " + nodeRef);
        }
        attachments.setAttachment(attachmentList);
        attachments.setNodeRefDocuments(nodeRef);
        return attachments;
    }
}
