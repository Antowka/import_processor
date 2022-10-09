package ru.antowka.importer.processing;

import ru.antowka.importer.utils.FileUtils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class HtmlFileVisitor extends SimpleFileVisitor<Path> {

    private Set<Path> foundFiles = new HashSet<>();
    private String subString;
    private long fileSizeInKb;

    public HtmlFileVisitor(String subString, long fileSizeInKb) {
        this.fileSizeInKb = fileSizeInKb;
        this.subString = subString;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if(!file.toString().endsWith(".bin")) {
            return FileVisitResult.CONTINUE;
        }

        //Ограничиваем 20кб
        if (Files.size(file) > fileSizeInKb * 1024) {
            return FileVisitResult.CONTINUE;
        }


        final String fiveSymbolOfFile = FileUtils.readFileByBytes(file, 150);
        if (!fiveSymbolOfFile.startsWith("<htm")) {
            return FileVisitResult.CONTINUE;
        }

        if (!fiveSymbolOfFile.contains(subString)) {
            return FileVisitResult.CONTINUE;
        }

        System.out.println("HTML-file found: " + file);
        foundFiles.add(file);

        return FileVisitResult.CONTINUE;
    }

    public Set<Path> getFoundFiles() {
        return foundFiles;
    }
}
