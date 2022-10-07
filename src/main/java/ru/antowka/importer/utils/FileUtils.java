package ru.antowka.importer.utils;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    private static final int MAX_DEPTH = 999;

    /**
     * Поиск всех файлов во всех поддиректориях (глубина)
     *
     * @param startPath - путь верхней папки
     * @param subString - файл должен содержать подстроку для попадения в выборку
     *
     * @return
     */
    public static List<Path> getAllFilesFromSubFolders(Path startPath, String subString) {

        try (Stream<Path> stream = Files.walk(startPath, MAX_DEPTH)) {

            if (!StringUtils.isEmpty(subString)) {
                return stream
                        .filter(p -> {
                            if (Files.isDirectory(p)) {
                                return false;
                            }
                            try {
                                return Files.lines(p).anyMatch(line -> line.contains(subString));
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }
                            return false;
                        }).collect(Collectors.toList());
            }

            return stream.collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Directory doesn't exist: " + startPath.toString());
        }

        return null;
    }
}
