package com.hashkey.hk.database.dao.impl;

import com.hashkey.hk.database.DatabaseManager;
import com.hashkey.hk.database.dao.AuditLogDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AuditLogDAOImpl implements AuditLogDAO {

    private static final String SQL_INSERT =
        "INSERT INTO audit_log " +
        "(account_id, org_id, action_type, old_values, new_values) " +
        "VALUES (?, ?, ?, ?, ?)";

    @Override
    public void log(
            Integer accountId,
            Integer orgId,
            String actionType,
            String oldValues,
            String newValues) {

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            ps.setObject(1, accountId);
            ps.setObject(2, orgId);
            ps.setString(3, actionType);
            ps.setString(4, oldValues);
            ps.setString(5, newValues);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to write audit log", e);
        }
    }
}
