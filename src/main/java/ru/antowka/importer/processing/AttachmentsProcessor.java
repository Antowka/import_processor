package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemProcessor;
import ru.antowka.importer.model.NodeModel;

public class AttachmentsProcessor implements ItemProcessor<NodeModel, NodeModel> {

    @Override
    public NodeModel process(NodeModel o) throws Exception {
        return null;
    }
}
