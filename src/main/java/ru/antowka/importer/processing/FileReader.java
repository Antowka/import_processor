package ru.antowka.importer.processing;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader<T>  extends FlatFileItemReader<T> {

    private LineMapper<T> lineMapper;

    private Resource resource;

    private Set<String> readFiles = new HashSet<>();
    private Set<String> readLines = new HashSet<>();


    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);
        this.resource = resource;
    }

    @Override
    public void setLineMapper(LineMapper<T> lineMapper) {
        super.setLineMapper(lineMapper);
        this.lineMapper = lineMapper;
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
                System.out.println("Can't map file/line: " + line);
            }
         }
         return null;
    }

    @Nullable
    private String readLine() {
        try {
            final String path = resource.getFile().getPath();

            //Чтобы читать по файлам, а не по строкам
            if (readFiles.contains(path)) {
                return null;
            }

            readFiles.add(path);
            final String fileString = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
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
