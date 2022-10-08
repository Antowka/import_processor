package ru.antowka.importer.service;

import ru.antowka.importer.model.Attachment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SearchAttachmentService {

    String checkFile(File file) throws IOException;

    List<Attachment> searchAttachmentsDocument  (String nodeRef) throws IOException;
}
