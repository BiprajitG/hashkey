# 🔐 HashKey

<p align="center">
  <img src="assets/social-preview.png" alt="HashKey – Secure Offline Password Vault Core" />
</p>

> **Secure Offline Password Vault Core (Java)**  
> *If the system can recover your secrets, so can an attacker.*

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)

---

## 🎯 What is HashKey?

**HashKey is a secure, offline-first password vault core written in Java.**

It is **not a UI**, **not a cloud service**, and **not a complete password manager**.

HashKey provides:
- Authentication
- Cryptographic protection
- Secure persistence
- Vault key lifecycle management

so that **other applications** (CLI, desktop app, mobile bridge, etc.) can safely build on top of it.

---

## 🛡️ The HashKey Promise

| NO CLOUD | NO SYNC | NO RECOVERY |
|:--------:|:-------:|:-----------:|
| Zero network dependency | Zero telemetry | Zero backdoors |
| All data stays local | No background services | Lost password = lost data |
| Offline by design | Deterministic behavior | This is intentional |

> **If HashKey can't unlock your vault, nobody can.**

---

## ✨ What HashKey Provides

### 🔒 Security & Authentication
- Single master password (never stored)
- Memory-hard password hashing (Argon2)
- Brute-force protection with lockout
- Authentication isolated from storage
- External lockout metadata (`security.meta`)

### 🔑 Cryptographic Protection
- Vault key derived in memory only
- Authenticated encryption (AES-GCM)
- No plaintext written to disk
- Explicit key lifecycle (unlock / lock / wipe)

### 💾 Local Persistence
- Embedded SQLite database
- Encrypted secrets only
- Audit trail for sensitive actions
- Zero external dependencies at runtime

### 🧱 Architecture
- Strict separation of concerns
- DAO isolation (no crypto in persistence)
- Services orchestrate, never store secrets
- Designed as a reusable **library module**

---

## 🧠 High-Level Flow

```
Master Password
       ↓
Authentication & Lockout
       ↓
Vault Key (in memory only)
       ↓
Encrypt / Decrypt Secrets
       ↓
Encrypted Persistence (SQLite)
```

Failed attempts are tracked **outside** the database to ensure lockout works even if the vault itself is inaccessible.

---

## 🏗️ Architecture Overview

| Layer | Responsibility |
|-------|----------------|
| 📦 Domain Models | Pure data structures |
| 💾 Persistence (DAO) | SQLite access only |
| 🔐 Security | Authentication & lockout |
| 🔑 Crypto | Hashing & encryption primitives |
| ⚙️ Services | Business orchestration |
| 🧠 Vault Session | In-memory key lifecycle |

No layer leaks responsibility into another.

---

## 📂 Project Structure

```
hashkey/
│
├── src/main/java/com/hashkey/hk/
│ │
│ ├── model/ # Domain models
│ │ ├── Organization.java
│ │ ├── Account.java
│ │ ├── AuditLog.java
│ │ └── MasterPassword.java
│ │
│ ├── database/ # Persistence layer
│ │ ├── DatabaseManager.java
│ │ └── dao/
│ │ ├── AccountDAO.java
│ │ ├── MasterPasswordDAO.java
│ │ ├── AuditLogDAO.java
│ │ └── impl/
│ │ ├── AccountDAOImpl.java
│ │ ├── MasterPasswordDAOImpl.java
│ │ └── AuditLogDAOImpl.java
│ │
│ ├── security/ # Authentication & lockout
│ │ ├── MasterPasswordSetupService.java
│ │ ├── MasterPasswordVerificationService.java
│ │ ├── LockoutPolicy.java
│ │ ├── AuthResult.java
│ │ └── (internal helpers)
│ │
│ ├── crypto/ # Cryptographic primitives
│ │ ├── VaultKeyDeriver.java
│ │ └── VaultEncryptor.java
│ │
│ ├── service/ # Business orchestration
│ │ ├── VaultUnlockService.java
│ │ └── AccountService.java
│ │
│ └── vault/ # In-memory vault session
│ └── VaultSession.java
│
├── src/main/resources/
│ └── schema.sql # Database schema
│
├── passwords.db # Generated at runtime
├── security.meta # Lockout metadata (generated)
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started (Developer)

### Requirements
- Java **17+**
- Maven **3.6+**

### Build
```bash
mvn clean compile
```

### Initialize Database
```bash
mvn exec:java -Dexec.mainClass="com.hashkey.hk.Main"
```

This creates:
- `passwords.db`
- `security.meta`

---

## 📊 Project Status

### ✅ Core Completed (Frozen)

- Authentication & lockout
- Master password lifecycle
- Vault key lifecycle
- Encryption & decryption
- DAO isolation
- Audit logging
- Memory hygiene
- End-to-end sanity verified

### 🔒 Core API Stability

The following are considered stable public APIs:

- `MasterPasswordSetupService`
- `MasterPasswordVerificationService`
- `VaultUnlockService`
- `AccountService`
- `VaultSession`
- `AuthResult`
- Crypto primitives

Internal implementations may change without notice.

---

## ⚠️ Critical Security Notice

### 🚨 THERE IS NO PASSWORD RECOVERY 🚨

**Reality:**
- Forget the master password → data is gone
- No backdoors
- No reset
- No exceptions

**This is by design.**

If you need recovery, cloud sync, or convenience features, HashKey is not the right tool.

---

## 💭 Design Philosophy

- **Simple** > clever
- **Explicit** > implicit
- **Deterministic** > magical
- **Security** > convenience

HashKey is intentionally boring.  
That's how secure systems survive.

---

## 📜 License

MIT License.  
Use it. Break it. Embed it. Audit it.

---

**🔐 HashKey — A quiet core for serious vaults.**