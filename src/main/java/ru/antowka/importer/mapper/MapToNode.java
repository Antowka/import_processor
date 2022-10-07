package ru.antowka.importer.mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.antowka.importer.dictionary.PropsNames;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapToNode {

    /**
     * Конвертируем строки с props-ами разделённые двоеточием в объект Node
     *
     * @param lineWithDataWithDelimiter
     * @return
     */
    private Set<PropModel> mapProps(Set<String> lineWithDataWithDelimiter) {

        return lineWithDataWithDelimiter
                .stream()
                .map(line -> {
                    System.out.println("Map line: " + line);
                    final String[] arrLine = line.split(":");
                    if (arrLine.length != 2) {
                        System.out.println("Line is broken: " + line);
                    }
                    String key = arrLine[0];
                    String value = arrLine[1];
                    final PropModel propNameByPresentString = PropsNames.getPropNameByPresentString(key);
                    propNameByPresentString.setValue(value);
                    return propNameByPresentString;
                })
                .collect(Collectors.toSet());
    }

    public NodeModel mapModel(File file) {
        final Document htmlDocument;
        try {
            htmlDocument = Jsoup.parse(file,  "UTF-8");
        } catch (IOException e) {
            System.out.println("File is broken or empty: " + file.getPath());
            return null;
        }

        final Elements linkForDocument = htmlDocument.getElementsByAttribute("href");
        if (Objects.isNull(linkForDocument) || linkForDocument.isEmpty()) {
            System.out.println("Not found element for Link");
        }
        final NodeModel nodeModel = new NodeModel();
        return nodeModel;
    }

}
