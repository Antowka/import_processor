package ru.antowka.importer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.antowka.importer.entitiy.BjRecord;
import ru.antowka.importer.mapper.AttachmentRowMapper;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.Path;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private ExecutorService taskWorker;


    public String checkFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        char[] chars = new char[10];
        for (int i = 0; i < 10; i++) {
            chars[i] = (char) is.read();
        }
        if (new String(chars) != null && new String(chars).contains("PDF")) {
            is.close();
            return "pdf";
        }
        is.close();
        return "-";
    }

    public List<Attachment> searchAttachmentsDocument(String nodeRef) throws IOException {
        String query = "SELECT \"DATE\" , \"INITIATOR\" , \"RECORDDESCRIPTION\" , \"OBJECT1\"   FROM \"BUSINESSJOURNALSTORERECORD\" where \"EVENTCATEGORYTEXT\" = 'Добавление вложения' AND \"MAINOBJECT\" ='" + nodeRef + "'";
        List<BjRecord> bjList = jdbcTemplate.query(query, new AttachmentRowMapper());
        List<Attachment> attachmentList = Collections.synchronizedList(new ArrayList<>());
        List<Future<Boolean>> features = Collections.synchronizedList(new ArrayList<>());
        if (bjList.size() > 0) {
            for (BjRecord bj : bjList) {
                final Future<Boolean> submit = taskWorker.submit(() ->
                    processAttachments(nodeRef, attachmentList, bj));
                features.add(submit);
            }
        } else {
            System.out.println("Нет записей в bj по данному документу " + nodeRef);
        }

        if (!features.isEmpty()) {
            while (!features.stream().allMatch(Future::isDone)) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return attachmentList;
    }

    private Boolean processAttachments(String nodeRef, List<Attachment> attachmentList, BjRecord bj) {
        List<Path> paths = new ArrayList<>();
        Attachment attachment = new Attachment();
        String record = bj.getRecord();
        String name = record.substring(record.indexOf(bj.getRefAttachment(), 1), record.indexOf("к документу"));
        attachment.setNameAttachment(name.substring(name.indexOf(">") + 1, name.length() - 5));
        if (record.indexOf("в категорию") > 0) {
            attachment.setCategory(record.substring(record.indexOf("в категорию") + 13, record.length() - 1));
        }
        attachment.setInitiator(bj.getInitiator());
        String path1 = pathToContentStore + (bj.getDate().toString().substring(0, 4)) + File.separator +
                Integer.valueOf(bj.getDate().toString().substring(5, 7)) + File.separator
                + Integer.valueOf(bj.getDate().toString().substring(8, 10)) + File.separator
                + Integer.valueOf(bj.getDate().toString().substring(11, 13)) + File.separator
                + Integer.valueOf(bj.getDate().toString().substring(14, 16));
        String path2 = pathToContentStore + (bj.getDate().toString().substring(0, 4)) + File.separator +
                Integer.valueOf(bj.getDate().toString().substring(5, 7)) + File.separator
                + Integer.valueOf(bj.getDate().toString().substring(8, 10)) + File.separator
                + Integer.valueOf(bj.getDate().toString().substring(11, 13)) + File.separator
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

            List<File> lst = Arrays.asList(arrFiles);
            for (File file : lst) {
                String type = "-";
                try {
                    type = checkFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!Objects.equals(type, "-")) {
                    Path path = new Path();
                    path.setPath(file.toPath().toString());
                    path.setType(type);
                    paths.add(path);
                }
            }
            attachment.setPaths(paths);
            attachmentList.add(attachment);
        } else {
            System.out.println("По записи в bj не нашли ни одного вложения. NodeRef документа " + nodeRef);
        }

        return true;
    }
}
