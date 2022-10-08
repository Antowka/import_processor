package ru.antowka.importer.model;

import java.util.ArrayList;
import java.util.List;
//Пока не используется
public class Attachments {
    private String nodeRefDocuments;
    List <Attachment> Attachment = new ArrayList<>();
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
}

