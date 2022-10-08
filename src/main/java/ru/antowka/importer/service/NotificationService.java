package ru.antowka.importer.service;

import ru.antowka.importer.model.ApprovalModel;

import java.util.List;
import java.util.Set;

public interface NotificationService {

    List<ApprovalModel> getApproversForDocument(String docRef);
}
