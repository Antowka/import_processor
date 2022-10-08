package ru.antowka.importer.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.antowka.importer.entitiy.BjRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttachmentRowMapper  implements RowMapper<BjRecord> {
    @Override
    public BjRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        BjRecord bjRecord = new BjRecord();
        bjRecord.setDate(rs.getTimestamp("DATE"));
        bjRecord.setInitiator(rs.getString("INITIATOR"));
        bjRecord.setRecord(rs.getString("RECORDDESCRIPTION"));
        bjRecord.setRefAttachment(rs.getString("OBJECT1"));
        return bjRecord;
    }
}
