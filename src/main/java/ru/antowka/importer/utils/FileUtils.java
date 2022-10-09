package ru.antowka.importer.utils;

import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
     * @param fileSizeInKb - размер файла в Кб
     *
     * @return
     */
    public static List<Path> getAllFilesFromSubFolders(Path startPath, String subString, int fileSizeInKb) {

        try (Stream<Path> stream = Files.walk(startPath, MAX_DEPTH)) {

            if (!StringUtils.isEmpty(subString)) {
                final int countLine[] = new int[1];
                return stream
                        .filter(p -> {
                            if (Files.isDirectory(p)) {
                                return false;
                            }
                            try {

                                //Ограничиваем 20кб
                                if (Files.size(p) > fileSizeInKb*1024) {
                                    return false;
                                }



                                final String firstFiveSymbols = readFileByBytes(p, 5);
                                if (!firstFiveSymbols.startsWith("<htm")) {
                                    return false;
                                }

                                //ограничение на чтение кол-ва строк в файле
                                countLine[0] = 5;
                                return Files.lines(p)
                                        .anyMatch(line -> {
                                            if (countLine[0] > 0) {
                                                return line.contains(subString);
                                            }
                                            countLine[0]--;
                                            return false;
                                        });
                            } catch (Exception e) {
                                System.out.println("The file has problems:" + p);
                            }
                            return false;
                        }).collect(Collectors.toList());
            }

            final List<Path> collect = stream.collect(Collectors.toList());
            stream.close();
            return collect;
        } catch (Exception e) {
            System.out.println("Directory doesn't exist: " + startPath.toString() + "(It isn't error)");
        }

        return null;
    }

    public static String readFileByBytes(Path path, int amountBytes) {
        try(InputStream is = new FileInputStream(path.toFile())) {
            char[] chars = new char[amountBytes];
            for (int i = 0; i < amountBytes; i++){
                chars[i] =(char)is.read();
            }

           return new String(chars);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
