package ru.antowka.importer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.antowka.importer.entitiy.NotificationRecord;
import ru.antowka.importer.mapper.NotificationRowMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String TEMPLATE_APPROVAL_DIRECT = "APPROVAL_DIRECT";
    private static final String TEMPLATE_APPROVAL_EMPLOYEE_RESOLUTION = "APPROVAL_EMPLOYEE_RESOLUTION";

    @Value("${dictionary.notificationtype.noderef}")
    private String notificationType;

    private static final String QUERY_APPROVAL_DIRECT = "SELECT * FROM \"NOTIFICATIONSTORERECORD\" WHERE \"OBJECT\" = '%s' AND \"NOTIFICATIONTYPE\" = '%s' AND \"TEMPLATE\" = '%s'";
    private static final String QUERY_APPROVAL_EMPLOYEE_RESOLUTION = "SELECT * FROM \"NOTIFICATIONSTORERECORD\" WHERE \"OBJECT\" = '%s' AND \"NOTIFICATIONTYPE\" = '%s' AND \"TEMPLATE\" = '%s' AND \"DESCRIPTION\" LIKE '%%%s%%'";

    @Autowired
    @Qualifier("notificationJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, String> getApproversForDocument(String docRef) {
        //ищем записи с отправкой на согласование
        List<NotificationRecord> directRecords = jdbcTemplate.query(String.format(QUERY_APPROVAL_DIRECT, docRef, notificationType, TEMPLATE_APPROVAL_DIRECT),
                new NotificationRowMapper());
        //берем получателей и отбросим дублирование
        Set<String> appoverRefs = new HashSet<>(directRecords.stream().map(record -> record.getRecipient()).collect(Collectors.toList()));

        Map<String, String> approvalData = new HashMap<>();

        for (String appoverRef : appoverRefs) {
            //ищем записи о вынесении решения от конкретного согласующего
            List<NotificationRecord> resolutionRecords = jdbcTemplate.query(String.format(QUERY_APPROVAL_EMPLOYEE_RESOLUTION, docRef, notificationType, TEMPLATE_APPROVAL_EMPLOYEE_RESOLUTION, appoverRef),
                    new NotificationRowMapper());
            //вычленяем название этапа в которм находится данный согласующий
            String stageName = "NOT_SPECIFIED"; //если еще не вынесено решение названия этапа не знаем
            if (!resolutionRecords.isEmpty()) {
                String content = resolutionRecords.get(resolutionRecords.size() - 1).getContent();
                String tempStageName = content.substring(content.indexOf("этапе") + 6);
                stageName = tempStageName.length() > 0 ? tempStageName : stageName;
            }
            //кладем в мапу учитывая остальных согласующих в данном этапе
            String refs = approvalData.get(stageName);
            if (refs != null) {
                refs += ", " + appoverRef;
            } else {
                refs = appoverRef;
            }
            approvalData.put(stageName, refs);
        }
        return approvalData;
    }
}
