package com.puredroid.hk.model;

import java.time.LocalDateTime;

public class AuditLog {
    private int id;
    private Integer accountId;
    private Integer orgId;
    private String actionType;
    private String oldValues;
    private String newValues;
    private LocalDateTime timestamp;

    public AuditLog(int id, Integer accountId, Integer orgId, String actionType, String oldValues, String newValues,
            LocalDateTime timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.orgId = orgId;
        this.actionType = actionType;
        this.oldValues = oldValues;
        this.newValues = newValues;
        this.timestamp = timestamp;
    }

    public AuditLog(Integer accountId, Integer orgId, String actionType, String oldValues, String newValues) {
        this.id = 0;
        this.accountId = accountId;
        this.orgId = orgId;
        this.actionType = actionType;
        this.oldValues = oldValues;
        this.newValues = newValues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getOldValues() {
        return oldValues;
    }

    public void setOldValues(String oldValues) {
        this.oldValues = oldValues;
    }

    public String getNewValues() {
        return newValues;
    }

    public void setNewValues(String newValues) {
        this.newValues = newValues;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AuditLog [id=" + id + ", "+
                "accountId=" + accountId + ", " +
                "orgId=" + orgId + ", " +
                "actionType=" + actionType + ", " +
                "oldValues=" + oldValues + ", " +
                "newValues=" + newValues + ", " +
                "timestamp=" + timestamp + "]";
    }
}
