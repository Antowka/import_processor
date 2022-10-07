package ru.antowka.importer.mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.item.file.LineMapper;
import ru.antowka.importer.dictionary.PropsNames;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class NodeMapper implements LineMapper<NodeModel> {

    /**
     * Используется для Batch-a в reader-e
     *
     * @param stringOfHtmlFile
     * @param lineNumber
     * @return
     * @throws Exception
     */
    @Override
    public NodeModel mapLine(String stringOfHtmlFile, int lineNumber) throws Exception {

        final Document htmlDocument = Jsoup.parse(stringOfHtmlFile, "UTF-8");
        final Elements linkForDocument = htmlDocument.getElementsByAttribute("href");
        if (Objects.isNull(linkForDocument) || linkForDocument.isEmpty()) {
            System.out.println("Not found element for Link");
        }
        final NodeModel nodeModel = new NodeModel();
        return nodeModel;
    }

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
}
