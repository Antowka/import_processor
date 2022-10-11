package ru.antowka.importer.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * JSON представление ноды
 */
@Data
public class NodeDto {

    private String nodeRef;

    private String name;

    private String type;

    private Map<String, String> props;

    private Map<String, String> assocs;

    private List<Map<String, String>> attachmentData;

    private Map<String, String> approvalData;
}
