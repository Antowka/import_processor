package ru.antowka.importer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.antowka.importer.entitiy.NotificationRecord;
import ru.antowka.importer.mapper.NotificationRowMapper;
import ru.antowka.importer.model.ApprovalModel;

import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String TEMPLATE_APPROVAL_DIRECT = "APPROVAL_DIRECT";

    @Autowired
    @Qualifier("notificationJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ApprovalModel> getApproversForDocument(String docRef) {
        List<NotificationRecord> records = jdbcTemplate.query("SELECT * FROM \"NOTIFICATIONSTORERECORD\" WHERE \"OBJECT\" = '" + docRef + "' AND \"TEMPLATE\" = '" + TEMPLATE_APPROVAL_DIRECT + "'",
                new NotificationRowMapper());
        Set<ApprovalModel> approvalData = new HashSet<>();
        for (NotificationRecord record : records) {
            ApprovalModel approvalModel = new ApprovalModel();
            approvalModel.setApproverRef(record.getRecipient());
            approvalData.add(approvalModel);
        }
        return new ArrayList<ApprovalModel>(approvalData);
    }
}
