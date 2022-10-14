package ru.antowka.importer.processing;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import ru.antowka.importer.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader<T>  extends FlatFileItemReader<T> {

    private LineMapper<T> lineMapper;

    private Resource resource;

    private Set<String> readFiles = new CopyOnWriteArraySet<>();
    private Set<String> readLines = new CopyOnWriteArraySet<>();


    @Override
    public void setResource(Resource resource) {
        synchronized (resource) {
            super.setResource(resource);
            this.resource = resource;
        }
    }

    @Override
    public void setLineMapper(LineMapper<T> lineMapper) {
        super.setLineMapper(lineMapper);
        this.lineMapper = lineMapper;

        super.setSkippedLinesCallback((a) -> {
            System.out.println("Skipped");
        });
    }

    @Override
    protected T doRead() throws Exception {
         String line = this.readLine();

         if (line == null) {
             return null;
         } else {
            try {
                return this.lineMapper.mapLine(line, 0); //0 - т.к читаем файл целиком в одну линию
            } catch (Exception var3) {
                System.out.println("Can't map file/line: " + line.substring(0, 300));
                var3.printStackTrace();
            }
         }
         return null;
    }

    @Nullable
    private String readLine() {
        try {
            final File file = resource.getFile();
            final String path = file.getPath();

            //Чтобы читать по файлам, а не по строкам
            if (readFiles.contains(path)) {
                return null;
            }
            readFiles.add(path);

            //Исключаем дубли по версиям
            final Path path1 = Paths.get(path);
            final String fileString = FileUtils.readFileByBytes(path1, 0);
            final Pattern compile = Pattern.compile("nodeRef=(.*)?\"");
            final Matcher matcher = compile.matcher(fileString);
            if(matcher.find()) {
                final String nodeRefOfHtml = matcher.group(1);
                if (readLines.contains(nodeRefOfHtml)) {
                    return null;
                }
                readLines.add(nodeRefOfHtml);
            }

            return fileString;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't read file with path: " + resource.toString());
        }
        return null;
    }
}
