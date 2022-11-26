package ru.antowka.importer.utils;

import org.javaync.io.AsyncFiles;
import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.processing.HtmlFileVisitor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileUtils {

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

    public static String readFileByBytes(Path path, int amountBytes) throws IOException {

        if (amountBytes > 0) {
            return AsyncFiles
                    .readAllBytes(path, amountBytes)
                    .thenApply(bytes -> new String(bytes, StandardCharsets.UTF_8))
                    .join();
        } else {
            return AsyncFiles
                    .readAllBytes(path)
                    .thenApply(bytes -> new String(bytes, StandardCharsets.UTF_8))
                    .join();
        }
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
        return new HashSet<Path>() {{add(path);}};
    }
}
