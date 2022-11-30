package ru.antowka.importer.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.antowka.importer.entitiy.BjRecord;
import ru.antowka.importer.mapper.AttachmentRowMapper;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.model.Path;
import ru.antowka.importer.utils.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Value("${time.max.diff.dj.and.file}")
    private Long maxDiffTimeMsBjAndFile;

    private Date startDateProcessing;

    @Autowired
    public SearchAttachmentServiceImpl(@Value("${date.processing.date}") String startDateProcessingString) {
        startDateProcessing = new DateFolderModel(startDateProcessingString).getDate();
    }

    public List<Attachment> searchAttachmentsDocument(String nodeRef) {

        List<BjRecord> bjList = getBJRecordsFromDBSortByDate(nodeRef);
        List<Attachment> attachmentList = new ArrayList<>();

        if (bjList.size() > 0) {
            for (BjRecord bj : bjList) {
                final Attachment attachment = processAttachments(nodeRef, bj);
                if (Objects.nonNull(attachment)) {
                    attachmentList.add(attachment);
                }
            }
        } else {
            System.out.println("ATTACHMENTS: Нет записей в bj по данному документу " + nodeRef);
        }

        return attachmentList;
    }

    /**
     * Выбираем по nodeRef-документа все записи журнала из категории "Добавление вложения"
     *
     * @param nodeRef
     * @return
     */
    private List<BjRecord> getBJRecordsFromDBSortByDate(String nodeRef) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDateProcessing);
        String timeSuffix = "00:00:00.0 +03:00";

        String startYear = String.valueOf(calendar.get(Calendar.YEAR));
        String startMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String startDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String startDateString = startYear + "-" + startMonth + "-" + startDay + " " + timeSuffix;


        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String endtYear = String.valueOf(calendar.get(Calendar.YEAR));
        String endMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String endDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String endDateString = endtYear + "-" + endMonth + "-" + endDay + " " + timeSuffix;


        String query = "SELECT \"DATE\", \"INITIATOR\" , \"RECORDDESCRIPTION\" , \"OBJECT1\"   FROM \"BUSINESSJOURNALSTORERECORD\" where \"EVENTCATEGORYTEXT\" = 'Добавление вложения' AND \"MAINOBJECT\" ='" + nodeRef + "' AND \"DATE\" > '" + startDateString + "' AND \"DATE\" < '" + endDateString + "'  ORDER BY \"DATE\" ASC";
        List<BjRecord> bjList = jdbcTemplate.query(query, new AttachmentRowMapper());
        return bjList;
    }

    private Attachment processAttachments(String nodeRef, BjRecord bj) {
        List<Path> paths = new ArrayList<>();
        Attachment attachment = new Attachment();
        String record = bj.getRecord();

        String name = record.substring(record.indexOf(bj.getRefAttachment(), 1), record.indexOf("к документу"));

        String nameAttachment = getRealFileName(name);

        attachment.setNameAttachment(nameAttachment);
        if (record.indexOf("в категорию") > 0) {
            attachment.setCategory(record.substring(record.indexOf("в категорию") + 13, record.length() - 1));
        } else {
            System.out.println("Не удалось найти категорию вложения для записи: " + nameAttachment);
            return null;
        }
        attachment.setInitiator(bj.getInitiator());

        final java.nio.file.Path path1 = FileUtils.buildPathByDate(pathToContentStore, bj.getDate(), null);

        File dir1 = path1.toFile();
        File[] arr1Files = dir1.listFiles();

        if (arr1Files != null && arr1Files.length > 0) {
            System.out.println("Для документа " + nodeRef + " найдены папки с вложениями: ");
            System.out.println("  - " + path1);

            File[] arrFiles = new File[arr1Files.length];
            System.arraycopy(arr1Files, 0, arrFiles, 0, arr1Files.length);

            List<File> lst = Arrays.stream(arrFiles)
                    .sorted(Comparator.comparingLong(File::lastModified))
                    .collect(Collectors.toList());

            for (File file : lst) {
                String type = FileUtils.getFileMime(file);
                if (!Objects.equals(type, "-")) {
                    //Проверяем чтобы вложение было того типа который указан в записи БЖ
                    if (typeIsOk(attachment, type)) {
                        if (isDiffIsOk(bj, file)) {
                            Path path = new Path();
                            path.setPath(file.toPath().toString());
                            path.setType(type);
                            paths.add(path);
                        }
                    }
                }

                attachment.setPaths(paths);
                if (!CollectionUtils.isEmpty(attachment.getPaths())) {
                    System.out.println("Добавлен Attachment для  " + nodeRef + " c путём " + attachment.getPaths().get(0));
                }
            }

            if (CollectionUtils.isEmpty(attachment.getPaths())) {
                System.out.println("Для документа НЕ найдены вложения. " + nodeRef);
                return null;
            }

            return attachment;
        } else {
            System.out.println("По записи в bj не нашли ни одного вложения. NodeRef документа " + nodeRef);
        }

        return null;
    }

    /**
     * Получаем реальное имя файла
     *
     * @param name
     * @return
     */
    private String getRealFileName(String name) {
        String nameAttachment = "";
        Pattern pattern = Pattern.compile(">(.*)</a>");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            nameAttachment = matcher.group(1);
        }

        if (StringUtils.isEmpty(nameAttachment)) {
            nameAttachment = name.substring(name.indexOf(">") + 1, name.length() - 5);
        }
        return nameAttachment;
    }

    /**
     * Проверяем, что найденый файл того же типа, что и тот который упоминался в БЖ
     *
     * @param attachment
     * @param type
     * @return
     */
    private boolean typeIsOk(Attachment attachment, String type) {
        return attachment.getNameAttachment().contains(type);
    }

    /**
     * Проверяем разницу между датами создания записи в БЖ и датой последней модификации файлов
     *
     * @param bj
     * @param file
     * @return
     */
    private boolean isDiffIsOk(BjRecord bj, File file) {
        final long diffTime = bj.getDate().getTime() - file.lastModified();
        return diffTime <= maxDiffTimeMsBjAndFile;
    }
}
