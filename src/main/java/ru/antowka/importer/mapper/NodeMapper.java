package ru.antowka.importer.mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.util.StringUtils;
import ru.antowka.importer.dictionary.DocType;
import ru.antowka.importer.dictionary.PropsNames;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

import java.text.SimpleDateFormat;
import java.util.*;
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

        final NodeModel nodeModel = new NodeModel();
        if (StringUtils.isEmpty(stringOfHtmlFile)) {
            return nodeModel;
        }

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

        nodeModel.setNodeRef(nodeRef);
        nodeModel.setName(linkForDocument.text());

        DocType docType = getDocType(htmlDocument);
        nodeModel.setType(docType.getValue());

        final Elements liTags = htmlDocument.getElementsByTag("li");
        final Set<String> collect = liTags
                .parallelStream()
                .map(e -> e.text().trim())
                .collect(Collectors.toSet());

        final Set<PropModel> propModels = mapProps(docType, collect);
        final Set<PropModel> assocModels = new HashSet<>();
        propModels
                .stream()
                .filter(prop -> PropModel.PropType.ASSOC.equals(prop.getType()))
                .forEach(assocModels::add);
        propModels.removeAll(assocModels);

        nodeModel.setProps(propModels);
        nodeModel.setAssocs(assocModels);

        return nodeModel;
    }

    /**
     * Конвертируем строки с props-ами разделённые двоеточием в объект Node
     *
     * @param lineWithDataWithDelimiter
     * @return
     */
    private Set<PropModel> mapProps(DocType docType, Set<String> lineWithDataWithDelimiter) {

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

                    final PropModel propNameByPresentString = PropsNames.getPropNameByPresentString(docType, key);
                    if (propNameByPresentString == null) {
                        return null;
                    }

                    if (PropModel.PropType.DATE.equals(propNameByPresentString.getType())) {
                        value = dateFormatter(value);
                        propNameByPresentString.setValue(value);
                    } else {
                        propNameByPresentString.setValue(value);
                    }
                    return propNameByPresentString;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Определяем тип документа
     *
     * @param htmlDocument
     * @return
     */
    private DocType getDocType(Document htmlDocument) {

        String name = htmlDocument
                .getElementsByTag("li")
                .stream()
                .map(Element::text)
                .filter(text -> text.contains("Тип:"))
                .map(text -> text.replace("Тип:", "").trim())
                .findFirst()
                .orElse(null);

        if (Objects.isNull(name)) {
            //Определение по первому слову в заголовке
            final String[] split = htmlDocument
                    .getElementsByTag("a")
                    .text()
                    .split(" ");

            if (split.length > 0) {
                name = split[0];
            }
        }

        return DocType.getTypeByKeyword(name);
    }

    /**
     * Форматирует дату в формат для JSON (чтоб удобно было использовать при импорте в JS)
     *
     * @return
     */
    private String dateFormatter(String dateString) {

        SimpleDateFormat inF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        SimpleDateFormat outF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        try {
            final Date date = inF.parse(dateString);
            dateString = outF.format(date);
        } catch (Exception e) {
            System.out.println("Date string is wrong: " + dateString);
        }

        return dateString;
    }
}
