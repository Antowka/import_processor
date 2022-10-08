package ru.antowka.importer.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class NodeModel {

    private String nodeRef;

    private String name;

    private Set<PropModel> props;

    private Map<String, String> approvalData;

    private List<Attachment> attachmentsData;

    public NodeModel() {
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props, Map<String, String> approvalData) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
        this.approvalData = approvalData;
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props, Map<String, String> approvalData , List attachmentsData) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
        this.approvalData = approvalData;
        this.attachmentsData = attachmentsData;
    }
}
