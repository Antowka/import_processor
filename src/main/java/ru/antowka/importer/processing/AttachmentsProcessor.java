package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.service.SearchAttachmentService;

import java.util.List;

public class AttachmentsProcessor implements ItemProcessor<NodeModel, NodeModel> {

    @Autowired
    private SearchAttachmentService searchAttachmentService;

    /**
     * Дополняем модельку вложениями
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public NodeModel process(NodeModel model) throws Exception {
        List<Attachment> attachments = searchAttachmentService.searchAttachmentsDocument(model.getNodeRef());
        model.setAttachmentsData(attachments);
        return model;
    }
}
