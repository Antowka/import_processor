package ru.antowka.importer.mapper;

import org.springframework.stereotype.Component;
import ru.antowka.importer.dto.AttachmentPathDto;
import ru.antowka.importer.dto.NodeDto;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

import java.util.*;
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

        final List<AttachmentPathDto> attachmentData = mapAttachmentsToDto(nodeModel.getAttachmentsData());
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

    private List<AttachmentPathDto> mapAttachmentsToDto(List<Attachment> attachmentsData) {

        if (attachmentsData == null) return new ArrayList<>();

        return attachmentsData
                .stream()
                .map(att -> {
                    AttachmentPathDto dto = new AttachmentPathDto();
                    dto.setNameAttachment(att.getNameAttachment());
                    dto.setCategory(att.getCategory());
                    dto.setInitiator(att.getInitiator());

                    List<Map<String, String>> paths = att
                            .getPaths()
                            .stream()
                            .filter(Objects::nonNull)
                            .map(path -> {
                                Map<String, String> dtoPath = new HashMap<>();
                                dtoPath.put("path", path.getPath());
                                dtoPath.put("type", path.getType());
                                return dtoPath;
                            })
                            .collect(Collectors.toList());


                    dto.setPaths(paths);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
