package ru.antowka.importer.service;

import ru.antowka.importer.model.Attachments;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public interface SearchAttachmentService {

    String checkFile(File file) throws IOException;

    Attachments searchAttachmentsDocument  (String nodeRef) throws IOException;
}