package com.puredroid.hk.model;

import java.time.LocalDateTime;

public class MasterPassword {
    private int id;
    private String passwordHash;
    private String salt;
    private int failedAttempts;
    private LocalDateTime lockedUntil;
    private LocalDateTime createdAt;

    public MasterPassword(int id, String passwordHash, String salt, int failedAttempts, LocalDateTime lockedUntil, LocalDateTime createdAt) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.failedAttempts = failedAttempts;
        this.lockedUntil = lockedUntil;
        this.createdAt = createdAt;
    }

    public MasterPassword(String passwordHash, String salt) {
        this.id = 1;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.failedAttempts = 0;
        this.lockedUntil = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MasterPassword{" +
                "id=" + id +
                ", failedAttempts=" + failedAttempts +
                ", lockedUntil=" + lockedUntil +
                ", createdAt=" + createdAt +
                '}';
    }
}
