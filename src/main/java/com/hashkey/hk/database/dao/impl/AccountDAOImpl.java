package com.hashkey.hk.database.dao.impl;

import com.hashkey.hk.database.DatabaseManager;
import com.hashkey.hk.database.dao.AccountDAO;
import com.hashkey.hk.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAOImpl implements AccountDAO {

    private static final String SQL_INSERT =
        "INSERT INTO accounts (org_id, username, password_encrypted, notes, url) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM accounts WHERE id = ?";

    @Override
    public void save(Account account) {

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            ps.setInt(1, account.getOrgId());
            ps.setString(2, account.getUsername());
            ps.setBytes(3, account.getPasswordEncrypted());
            ps.setString(4, account.getNotes());
            ps.setString(5, account.getUrl());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to save account", e);
        }
    }

    @Override
    public Account findById(int accountId) {

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, accountId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                return new Account(
                    rs.getInt("id"),
                    rs.getInt("org_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getBytes("password_encrypted"),
                    rs.getString("notes"),
                    rs.getString("url"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getTimestamp("last_password_change").toLocalDateTime()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load account", e);
        }
    }
}
