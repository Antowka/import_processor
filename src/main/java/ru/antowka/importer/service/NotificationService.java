package ru.antowka.importer.service;

import ru.antowka.importer.model.ApprovalModel;

import java.util.Set;

public interface NotificationService {

    Set<ApprovalModel> getApproversForDocument(String docRef);
}
