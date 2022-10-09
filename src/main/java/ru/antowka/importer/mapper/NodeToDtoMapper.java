package ru.antowka.importer.mapper;

import org.springframework.stereotype.Component;
import ru.antowka.importer.dto.NodeDto;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.model.PropModel;

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

        return dto;
    }

    private Map<String, String> mapPropsToDto(Set<PropModel> props) {
        return props
                .stream()
                .collect(Collectors.toMap(PropModel::getName, PropModel::getValue));
    }
}
