package ru.antowka.importer.utils;

import org.apache.tika.detect.CompositeDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.microsoft.POIFSContainerDetector;
import org.apache.tika.parser.pkg.ZipContainerDetector;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.processing.HtmlFileVisitor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileUtils {

    private final static Detector detector = getDefaultDetector();


    private static Detector getDefaultDetector() {

        List<Detector> detectors = new ArrayList<>();

        // zip compressed container types
        detectors.add(new ZipContainerDetector());
        // Microsoft stuff
        detectors.add(new POIFSContainerDetector());
        // mime magic detection as fallback
        detectors.add(MimeTypes.getDefaultMimeTypes());

        return new CompositeDetector(detectors);
    }

    /**
     * Поиск всех файлов во всех поддиректориях (глубина)
     *
     * @param startPath    - путь верхней папки
     * @param subString    - файл должен содержать подстроку для попадения в выборку
     * @param fileSizeInKb - размер файла в Кб
     * @return
     */
    public static Set<Path> getAllFilesFromSubFolders(Path startPath, String subString, long fileSizeInKb) {

        HtmlFileVisitor fileVisitor = new HtmlFileVisitor(subString, fileSizeInKb);
        try {
            Files.walkFileTree(startPath, fileVisitor);
        } catch (NoSuchFileException e) {
            System.out.println("Folder doesn't exist: " + startPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileVisitor.getFoundFiles();
    }

    /**
     * Чтение данных указанного из файла без блокировок на файл
     *
     * @param path
     * @param amountBytes
     * @return
     * @throws IOException
     */
    public static String readFileByBytes(Path path, int amountBytes) throws IOException {

        StringBuilder sb = new StringBuilder();
        FileReader in = null;
        BufferedReader reader = null;
        try {
            in = new FileReader(path.toFile());
            reader = new BufferedReader(in);
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();
    }

    /**
     * Строим пути по часам внутри дня
     *
     * @param dateFolderModel
     * @return
     */
    public static Set<Path> buildPathsForHoursFolderByDay(String contentStorePath, DateFolderModel dateFolderModel) {
        final String path = dateFolderModel.buildPath();
        return IntStream
                .range(0, 23)
                .mapToObj(hour -> {
                    final Path pathForMap = Paths.get(contentStorePath, path, String.valueOf(hour));
                    System.out.println("Check path: " + pathForMap);
                    return pathForMap;
                })
                .filter(pathForCheck -> {
                    return Files.exists(pathForCheck);
                })
                .collect(Collectors.toSet());
    }

    public static Set<Path> buildPathsFolderByDay(String contentStorePath, DateFolderModel dateFolderModel) {
        final Path path = Paths.get(contentStorePath, dateFolderModel.getDateForOutputPath());
        return new HashSet<Path>() {{
            add(path);
        }};
    }

    /**
     * Формирует путь по Timestamp
     *
     * @param prePath
     * @param date
     * @param afterPath
     * @return
     */
    public static Path buildPathByDate(String prePath, Date date, String afterPath) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));

        Path datePath = Paths.get(year, month, day, hour, minute);

        if (Objects.nonNull(prePath) && Objects.nonNull(afterPath))
            return Paths.get(prePath, datePath.toString(), afterPath);
        if (Objects.nonNull(prePath)) return Paths.get(prePath, datePath.toString());
        return datePath;
    }

    /**
     * Проверка файла на тип pdf|docx|xlsx
     *
     * @param file
     * @return
     */
    public static String getFileMime(File file) {
        try {
            final InputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
            MediaType mediaType = detector.detect(fileInputStream, new Metadata());
            if (mediaType.getSubtype().equals("pdf")) {
                return "pdf";
            } else if (mediaType.getSubtype().equals("vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                return "xlsx";
            } else if (mediaType.getSubtype().equals("vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                return "docx";
            } else if (mediaType.getSubtype().equals("msword")) {
                return "doc";
            } else if (mediaType.getSubtype().equals("vnd.ms-excel")) {
                return "xls";
            } else if (mediaType.getSubtype().contains("vnd.ms-excel.sheet.macroEnabled")) {
                return "xlsm";
            } else {
                System.out.println("Не удалось подставить тип файла " + file + ". Тип " + mediaType.getSubtype());
                return "-";
            }

        } catch (IOException e) {
            System.out.println("Не удалось прочитать тип файла " + file);
        }
        return "-";
    }
}
