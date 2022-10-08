package ru.antowka.importer.service;

import java.util.Map;

public interface NotificationService {

    Map<String, String> getApproversForDocument(String docRef);
}
