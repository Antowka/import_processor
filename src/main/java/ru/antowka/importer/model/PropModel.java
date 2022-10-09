package ru.antowka.importer.model;

import lombok.Data;

@Data
public class PropModel {

    private String name;

    private PropType type;

    private String value;

    public PropModel(String name, PropType type) {
        this.name = name;
        this.type = type;
    }

    public enum PropType {
        BOOLEAN, INT, LONG, STRING, DATE, FAIL, ASSOC
    }
}
