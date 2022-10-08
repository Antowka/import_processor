package ru.antowka.importer.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class NodeModel {

    private String nodeRef;

    private String name;

    private Set<PropModel> props;

    private Set<ApprovalModel> approvalData;

    private List<Attachment> attachmentsData;

    public NodeModel() {
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props, Set<ApprovalModel> approvalData) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
        this.approvalData = approvalData;
    }

    public NodeModel(String nodeRef, String name, Set<PropModel> props, Set<ApprovalModel> approvalData , List attachmentsData) {
        this.nodeRef = nodeRef;
        this.name = name;
        this.props = props;
        this.approvalData = approvalData;
        this.attachmentsData = attachmentsData;
    }
}
