package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.service.NotificationService;

public class NotificationProcessor implements ItemProcessor<NodeModel, NodeModel> {

    @Autowired
    NotificationService notificationService;

    /**
     * Дополняем модельку из уведомлений
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public NodeModel process(NodeModel model) throws Exception {
        String docRef = model.getNodeRef();
        model.setApprovalData(notificationService.getApproversForDocument(docRef));
        return model;
    }
}
