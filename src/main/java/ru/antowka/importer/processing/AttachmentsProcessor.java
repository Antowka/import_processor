package ru.antowka.importer.processing;

import org.springframework.batch.item.ItemProcessor;
import ru.antowka.importer.model.Attachment;
import ru.antowka.importer.model.Attachments;
import ru.antowka.importer.model.NodeModel;
import ru.antowka.importer.service.SearchAttachmentServiceImpl;

import java.util.List;

public class AttachmentsProcessor implements ItemProcessor<NodeModel, NodeModel> {

    /**
     * Дополняем модельку вложениями
     *
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public NodeModel process(NodeModel model) throws Exception {
        SearchAttachmentServiceImpl service = new SearchAttachmentServiceImpl();
        List<Attachment> attachments = service.searchAttachmentsDocument(model.getNodeRef());
        model.setAttachmentsData(attachments);
        return model;
    }
}
