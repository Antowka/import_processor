package ru.antowka.importer.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class AttachmentPathDto {
    private String category;
    private String initiator;
    private String nameAttachment;
    private List<Map<String, String>> paths = new ArrayList<>();
}
