package ru.antowka.importer.entitiy;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class AttachmentSolrRecord {
    private String docRef;
    private String attRef;
    private Timestamp created;
    private Timestamp modified;
    private long size;
    private String mime;
}
