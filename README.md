# 🔐 HashKey - Secure Offline Password Manager

A robust, fully offline password manager built in Java with SQLite, designed for maximum security and privacy. All your passwords stay on your machine, encrypted with military-grade AES-256 encryption.

## ✨ Features

### 🔒 Security First
- **Master Password Protection** - Single password to access your entire vault
- **AES-256 Encryption** - Military-grade encryption for all stored passwords
- **Offline Only** - No internet connection required, no cloud sync, complete privacy
- **Failed Login Protection** - Account locks after multiple failed attempts
- **Clipboard Auto-Clear** - Automatically clears copied passwords after timeout
- **Comprehensive Audit Log** - Track every change made to your accounts

### 📋 Organization & Management
- **Organization-based Structure** - Group accounts by company, project, or category
- **Complete Account Information** - Store username, email, phone, URL, and notes
- **Full CRUD Operations** - Create, Read, Update, Delete accounts and organizations
- **Advanced Search & Filter** - Quickly find accounts across all organizations
- **Change History** - Track old passwords and account modifications

### 🛠️ Developer Features
- **Pure Java Implementation** - No external frameworks, clean code
- **SQLite Database** - Lightweight, embedded, zero-configuration
- **Maven Build System** - Easy dependency management
- **Modular Architecture** - Clean separation of concerns (Model, Database, Service, Security)

## 🏗️ Architecture

```
com.puredroid.hk/
├── model/          # Data models (Organization, Account, AuditLog)
├── database/       # Database connection and DAO layer
├── security/       # Encryption, master password, password generator
├── service/        # Business logic layer
├── util/           # Helper utilities
└── Main.java       # Application entry point
```

## 📊 Database Schema

### Tables
- **organizations** - Group your accounts by organization
- **accounts** - Store encrypted account credentials
- **audit_log** - Track all changes for security
- **master_password** - Secure access control
- **settings** - Application configuration

## 🚀 Getting Started

### Prerequisites
- Java JDK 17 or higher
- Maven 3.6+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/hashkey.git
cd hashkey
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn exec:java -Dexec.mainClass="com.puredroid.hk.Main"
```

### First Time Setup
On first launch, you'll be prompted to:
1. Create a master password (choose wisely - this cannot be recovered!)
2. Create your first organization
3. Start adding accounts

## 🔧 Configuration

Database location: `~/.hashkey/passwords.db`

Settings can be configured in the application:
- Clipboard timeout duration
- Auto-lock timer
- Password generator defaults

## 📦 Dependencies

```xml
<dependencies>
    <!-- SQLite JDBC Driver -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.44.1.0</version>
    </dependency>
    
    <!-- Bouncy Castle for Encryption -->
    <dependency>
        <groupId>org.bouncycastle</groupId>
        <artifactId>bcprov-jdk15on</artifactId>
        <version>1.70</version>
    </dependency>
    
    <!-- SLF4J for Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.9</version>
    </dependency>
</dependencies>
```

## 🔐 Security Features Explained

### Encryption
- All passwords encrypted with AES-256-GCM
- Unique salt per password
- Master password hashed with Argon2id

### Master Password
- Never stored in plain text
- Salted and hashed using Argon2id
- Failed attempt tracking with automatic lockout

### Audit Trail
- Every create, update, delete operation logged
- Old values preserved for rollback capability
- Timestamps for all changes

## 🎯 Roadmap

- [x] Core database schema
- [x] Model classes
- [ ] Database layer implementation
- [ ] Encryption module
- [ ] Master password authentication
- [ ] Password generator
- [ ] CLI interface
- [ ] GUI (JavaFX/Swing)
- [ ] Import/Export functionality
- [ ] Backup/Restore features

## 🤝 Contributing

This is a learning project, but contributions are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## ⚠️ Security Notice

**IMPORTANT:** 
- This is an educational project
- Always backup your database file
- If you forget your master password, your data is UNRECOVERABLE
- For production use, consider audited, battle-tested solutions like Bitwarden or KeePass

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Built as a learning project to understand encryption, database design, and secure software development
- Inspired by KeePass, Bitwarden, and other open-source password managers

## 📧 Contact

Project Link: [https://github.com/yourusername/hashkey](https://github.com/yourusername/hashkey)

---

**⚡ HashKey - Your passwords, your machine, your control.**