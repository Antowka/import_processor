package ru.antowka.importer.dictionary;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Типы документов
 */
public enum DocType {
    RESOLUTION("Резолюция", "lecm-resolutions:document"),
    ERRANDS("Поручение", "lecm-errands:document"),
    INCOMING("Входящий", "lecm-incoming:document"),
    OUTGOING("Исходящий", "lecm-outgoing:document"),
    FAIL("Не определён", "fail");

    private String keyword;

    private String value;

    DocType(String keyword, String value) {
        this.keyword = keyword;
        this.value = value;
    }

    public static DocType getTypeByKeyword(String keyword) {

        if(StringUtils.isEmpty(keyword)) {
            return DocType.FAIL;
        }

        return Arrays
                .stream(DocType.values())
                .filter(type -> type.keyword.equals(keyword))
                .findFirst()
                .orElse(DocType.FAIL);
    }

    public String getValue() {
        return value;
    }
}
