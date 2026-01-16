package com.hashkey.hk.database.dao;

public interface AuditLogDAO {

    void log(
        Integer accountId,
        Integer orgId,
        String actionType,
        String oldValues,
        String newValues
    );
}
