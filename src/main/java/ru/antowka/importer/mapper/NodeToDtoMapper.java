package ru.antowka.importer.mapper;

import org.apache.poi.xslf.model.geom.Path;
import org.springframework.stereotype.Component;
import ru.antowka.importer.dto.NodeDto;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NodeToDtoMapper {

    public NodeDto nodeToDto(NodeModel nodeModel) {

        NodeDto dto = new NodeDto();
        dto.setNodeRef(nodeModel.getNodeRef());
        dto.setName(nodeModel.getName());
        dto.setType(nodeModel.getType());

        final Set<PropModel> props = nodeModel.getProps();
        final Set<PropModel> assocs = nodeModel.getAssocs();
        dto.setProps(mapPropsToDto(props));
        dto.setAssocs(mapPropsToDto(assocs));

        final List<Map<String, String>> attachmentData = mapAttachmentsToDto(nodeModel.getAttachmentsData());
        dto.setAttachmentData(attachmentData);
        dto.setApprovalData(nodeModel.getApprovalData());

        return dto;
    }

    private Map<String, String> mapPropsToDto(Set<PropModel> props) {
        try {
            return props
                    .stream()
                    .collect(Collectors.toMap(PropModel::getName, PropModel::getValue));
        } catch (Exception e) {
            return new HashMap<>();
        }

    }

    private List<Map<String, String>> mapAttachmentsToDto(List<Attachment> attachmentsData) {
        return attachmentsData
                .stream()
                .map(att -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("category", att.getCategory());
                    map.put("initiator", att.getInitiator());
                    map.put("nameAttachment", att.getNameAttachment());
                    map.put("paths", "[" + att
                            .getPaths()
                            .stream()
                            .map(path -> "{\"path\":\"" + path.getPath() + "\", \"type\": \"" + path.getType()+"\"}")
                            .collect(Collectors.joining( "," )) + "]");
                    return map;
                }).collect(Collectors.toList());
    }
}
