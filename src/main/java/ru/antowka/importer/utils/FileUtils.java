package ru.antowka.importer.utils;

import ru.antowka.importer.model.DateFolderModel;
import ru.antowka.importer.processing.HtmlFileVisitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static Set<Path> getAllFilesFromSubFolders(Path startPath, String subString, int fileSizeInKb) {

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

    public static String readFileByBytes(Path path, int amountBytes) {
        try (InputStream is = new FileInputStream(path.toFile())) {
            char[] chars = new char[amountBytes];
            for (int i = 0; i < amountBytes; i++) {
                chars[i] = (char) is.read();
            }
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
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
}
