package ru.antowka.importer.entitiy;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class BjRecord {
    private Timestamp date;
    private  String record;
    private  String initiator;
    private  String refAttachment;
}
