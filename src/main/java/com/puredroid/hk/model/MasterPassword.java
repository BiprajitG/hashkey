package com.puredroid.hk.model;

import java.time.LocalDateTime;

public class MasterPassword {
    private int id;
    private String passwordHash;
    private String salt;
    private LocalDateTime createdAt;

    public MasterPassword(int id, String passwordHash, String salt, LocalDateTime createdAt) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.createdAt = createdAt;
    }

    public MasterPassword(String passwordHash, String salt) {
        this.id = 1;
        this.passwordHash = passwordHash;
        this.salt = salt;
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
                ", createdAt=" + createdAt +
                '}';
    }
}
