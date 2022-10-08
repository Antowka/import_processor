package ru.antowka.importer.model;

import java.util.ArrayList;
import java.util.List;

public class Attachments {
    public String getNodeRefDocuments() {
        return nodeRefDocuments;
    }

    public void setNodeRefDocuments(String nodeRefDocuments) {
        this.nodeRefDocuments = nodeRefDocuments;
    }

    public List<Attachment> getAttachment() {
        return Attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        Attachment = attachment;
    }

    private String nodeRefDocuments;
    List <Attachment> Attachment = new ArrayList<>();
}

