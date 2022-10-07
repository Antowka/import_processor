package ru.antowka.importer.model;

import lombok.Data;

import java.util.Set;

@Data
public class NodeModel {

    private String nodeRef;

    private String name;

    private Set<PropModel> props;

    public NodeModel() {
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
    }
}
