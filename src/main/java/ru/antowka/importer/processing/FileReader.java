package ru.antowka.importer.processing;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader<T>  extends FlatFileItemReader<T> {

    private LineMapper<T> lineMapper;

    private Resource resource;

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
                throw new RuntimeException("Can't map file");
            }
         }
    }

    @Nullable
    private String readLine() {
        try {
            return new String(Files.readAllBytes(Paths.get(resource.getFile().getPath())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't read file with path: " + resource.toString());
        }
        return null;
    }
}
