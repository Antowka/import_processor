package ru.antowka.importer.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.antowka.importer.entitiy.AttachmentSolrRecord;
import ru.antowka.importer.entitiy.BjRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttachmentSolrMapper implements RowMapper<AttachmentSolrRecord> {
    @Override
    public AttachmentSolrRecord mapRow(ResultSet rs, int rowNum) throws SQLException {

        AttachmentSolrRecord rec = new AttachmentSolrRecord();
        rec.setDocRef(rs.getString("doc_ref"));
        rec.setAttRef(rs.getString("att_ref"));
        rec.setCreated(rs.getTimestamp("created"));
        rec.setModified(rs.getTimestamp("modified"));
        rec.setSize(rs.getLong("size"));
        rec.setMime(rs.getString("mime"));
        return rec;
    }
}