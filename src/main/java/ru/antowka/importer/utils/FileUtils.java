package ru.antowka.importer.utils;

import org.springframework.util.StringUtils;
import ru.antowka.importer.processing.HtmlFileVisitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtils {

    private static final int MAX_DEPTH = 999;

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
}
