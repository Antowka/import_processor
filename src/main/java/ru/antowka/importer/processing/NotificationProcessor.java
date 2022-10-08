package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemProcessor;
import ru.antowka.importer.model.NodeModel;

public class NotificationProcessor implements ItemProcessor<NodeModel, NodeModel> {

    /**
     * Дополняем модельку из уведомлений
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public NodeModel process(NodeModel model) throws Exception {
        return model;
    }
}
