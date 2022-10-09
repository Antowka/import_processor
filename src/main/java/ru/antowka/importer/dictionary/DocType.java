package ru.antowka.importer.dictionary;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Типы документов
 */
public enum DocType {
    RESOLUTION("Резолюция", "lecm-resolutions:document"),
    ERRANDS("Поручение", "lecm-errands:document"),
    INCOMING("Входящий документ", "lecm-incoming:document"),
    OUTGOING("Исходящий документ", "lecm-outgoing:document"),
    APPROVAL_CARD("Карточка согласования", "rn-document-approval:document"),
    INTERNAL("Служебная записка","lecm-internal:document"),
    PROTOCOL("Протокол","lecm-protocol:document"),
    ORD("Распорядительный документ","lecm-ord:document"),
    FAIL("Не определён", "fail");

    private final String keyword;

    private final String value;

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
