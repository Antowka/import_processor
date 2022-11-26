package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.antowka.importer.dto.NodeDto;
import ru.antowka.importer.mapper.NodeToDtoMapper;
import ru.antowka.importer.model.NodeModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class NodeAggregator implements ItemWriter<NodeModel> {

    private List<NodeDto> aggregateNodes = new CopyOnWriteArrayList<>();

    @Autowired
    private NodeToDtoMapper mapper;

    @Override
    public void write(List<? extends NodeModel> list) throws Exception {
        list.forEach(node -> {
            NodeDto dto = mapper.nodeToDto(node);
            if (!aggregateNodes.contains(dto)) {
                aggregateNodes.add(dto);
            }
        });
    }

    public List<NodeDto> getAggregateNodes() {
        return aggregateNodes;
    }
}
