package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antowka.importer.dto.NodeDto;
import ru.antowka.importer.mapper.NodeToDtoMapper;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.service.SearchAttachmentService;

import java.util.List;

public class DtoProcessor implements ItemProcessor<NodeModel, NodeDto> {

    @Autowired
    private NodeToDtoMapper mapper;

    /**
     * Маппим модель на объект представления для json-а
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public NodeDto process(NodeModel model) throws Exception {
        return mapper.nodeToDto(model);
    }
}
