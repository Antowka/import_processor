package ru.antowka.importer.attachment;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import  ru.antowka.importer.model.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAttachment {
    public static String checkFile(File file) throws IOException {
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
            } catch (EncryptedDocumentException e){
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
    public static Attachments checkDocument (String url, String user, String password, String nodeRef) throws SQLException, IOException {
        Connection con = DriverManager.getConnection(url, user, password);
        String base_path = "C:/alfresco-rn/alf_data/contentstore/";
        List<Timestamp> dates = new ArrayList<Timestamp>();
        List<BjInfo> bjList = new ArrayList<>();
        Attachments attachments = new Attachments();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT \"DATE\" , \"INITIATOR\" , \"RECORDDESCRIPTION\" , \"OBJECT1\"   FROM \"BUSINESSJOURNALSTORERECORD\" where \"EVENTCATEGORYTEXT\" = 'Добавление вложения' AND \"MAINOBJECT\" ='" + nodeRef + "'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BjInfo bj = new BjInfo();
                bj.setInitiator(rs.getString(2));
                bj.setDate(rs.getTimestamp(1));
                bj.setRecord(rs.getString(3));
                bj.setRefAttachment(rs.getString(4));
                dates.add(rs.getTimestamp(1));
                bjList.add(bj);
            }
            List<Attachment> attachmentList = new ArrayList<>();
            if (bjList.size() > 0) {
                for (BjInfo bj : bjList) {
                    List<Path> paths = new ArrayList<>();
                    System.out.println("Дата полученная из bj " + bj.getDate().toString());
                    Attachment attachment = new Attachment();
                    String record = bj.getRecord();
                    String name = record.substring(record.indexOf(bj.getRefAttachment(), 1), record.indexOf("к документу"));
                    attachment.setNameAttachment(name.substring(name.indexOf(">") + 1, name.length() - 5));
                    attachment.setCategory(record.substring(record.indexOf("в категорию") + 13, record.length() - 1));
                    attachment.setInitiator(bj.getInitiator());
                    String path1 = base_path + (bj.getDate().toString().substring(0, 4)) + "/" +
                            Integer.valueOf(bj.getDate().toString().substring(5, 7)) + "/"
                            + Integer.valueOf(bj.getDate().toString().substring(8, 10)) + "/"
                            + Integer.valueOf(bj.getDate().toString().substring(11, 13)) + "/"
                            + Integer.valueOf(bj.getDate().toString().substring(14, 16));
                    String path2 = base_path + (bj.getDate().toString().substring(0, 4)) + "/" +
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
                        System.out.println("Ничего не нашли");
                        break;
                    }
                    List<File> lst = Arrays.asList(arrFiles);
                    for (File file : lst) {
                        System.out.println(name.substring(name.indexOf(">") + 1, name.length() - 5));
                        String type = checkFile(file);
                        if (type == "pdf" || type == "doc" || type == "docx") {
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
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            con.close();
        }
        System.out.println("322");
        return attachments;
    }
}
