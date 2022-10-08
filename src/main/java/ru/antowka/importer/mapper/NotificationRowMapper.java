package ru.antowka.importer.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.antowka.importer.entitiy.NotificationRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<NotificationRecord> {

    @Override
    public NotificationRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord.setDate(rs.getDate("FORMINGDATE"));
        notificationRecord.setRecipient(rs.getString("RECIPIENT"));
        notificationRecord.setContent(rs.getString("DESCRIPTION"));
        return notificationRecord;
    }
}
