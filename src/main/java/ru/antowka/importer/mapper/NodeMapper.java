package ru.antowka.importer.mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.item.file.LineMapper;
import ru.antowka.importer.dictionary.DocType;
import ru.antowka.importer.dictionary.PropsNames;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
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

        String nodeRef = linkForDocument
                .get(0)
                .attributes()
                .get("href")
                .replaceAll("(htt.*=)", "");

        final NodeModel nodeModel = new NodeModel();
        nodeModel.setNodeRef(nodeRef);
        nodeModel.setName(linkForDocument.text());



        final Elements liTags = htmlDocument.getElementsByTag("li");
        final Set<String> collect = liTags
                .parallelStream()
                .map(e -> e.text().trim())
                .collect(Collectors.toSet());

        final Set<PropModel> propModels = mapProps(collect);
        nodeModel.setProps(propModels);

        nodeModel.setType(getDocType(propModels).getValue());

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
                    //System.out.println("Map line: " + line);
                    Pattern pattern = Pattern.compile("(:\\s)");
                    final List<String> arrLine = Arrays.asList(pattern.split(line));

                    if (arrLine.isEmpty()) {
                        return null;
                    }

                    String key = arrLine.get(0).trim();
                    String value;
                    if (arrLine.size() == 2) {
                        value = arrLine.get(1);
                    } else {
                        value = arrLine
                                .stream()
                                .filter(it -> !it.equals(key))
                                .collect(Collectors.joining(": "));
                    }

                    final PropModel propNameByPresentString = PropsNames.getPropNameByPresentString(key);
                    propNameByPresentString.setValue(value);
                    return propNameByPresentString;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Определяем тип документа
     *
     * @param props
     * @return
     */
    private DocType getDocType(Set<PropModel> props) {
        final PropModel propModel = props.stream()
                .filter(prop -> "lecm-document:doc-type".equals(prop.getName()))
                .findFirst().orElse(null);

        if (Objects.isNull(propModel)) {
            //Если не удалось определить тип по полю Тип, пробуем по первому слову из cm:name
            final String propModelName = props.stream()
                    .filter(prop -> "cm:name".equals(prop.getName()))
                    .map(propValue -> {
                        final String[] words = propValue.getValue().split(" ");
                        if (words.length == 0) {
                            return "Не определён";
                        }
                        return words[0];
                    })
                    .findFirst()
                    .orElse("Не определён");

            return DocType.getTypeByKeyword(propModelName);
        }

        return DocType.getTypeByKeyword(propModel.getValue());
    }
}
