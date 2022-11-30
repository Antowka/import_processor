package ru.antowka.importer.service;

import ru.antowka.importer.model.Attachment;

import java.io.IOException;
import java.util.List;

public interface SearchAttachmentService {

    List<Attachment> searchAttachmentsDocument(String nodeRef) throws IOException;
}
