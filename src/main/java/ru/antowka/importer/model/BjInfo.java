package ru.antowka.importer.model;

import java.sql.Timestamp;

public class BjInfo {
    private Timestamp date;
    private  String record;
    private  String initiator;
    private  String refAttachment;

    public String getRefAttachment() {
        return refAttachment;
    }

    public void setRefAttachment(String refAttachment) {
        this.refAttachment = refAttachment;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }


}
