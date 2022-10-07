package ru.antowka.importer.entitiy;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NotificationRecord {

    private Date date;
    private String recipient;
}
