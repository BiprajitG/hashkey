package com.hashkey.hk.service;

import com.hashkey.hk.crypto.VaultEncryptor;
import com.hashkey.hk.database.dao.AccountDAO;
import com.hashkey.hk.database.dao.AuditLogDAO;
import com.hashkey.hk.model.Account;
import com.hashkey.hk.vault.VaultSession;

import java.nio.charset.StandardCharsets;

public class AccountService {

    private final VaultSession vaultSession;
    private final AccountDAO accountDAO;
    private final AuditLogDAO auditLogDAO;

    public AccountService(
            VaultSession vaultSession,
            AccountDAO accountDAO,
            AuditLogDAO auditLogDAO) {

        this.vaultSession = vaultSession;
        this.accountDAO = accountDAO;
        this.auditLogDAO = auditLogDAO;
    }

    public void createAccount(
            int orgId,
            String username,
            char[] plaintextPassword,
            String notes,
            String url) {

        if (!vaultSession.isUnlocked()) {
            throw new IllegalStateException("Vault is locked");
        }

        if (plaintextPassword == null || plaintextPassword.length == 0) {
            throw new IllegalArgumentException("Password required");
        }

        // 1️⃣ Convert password to bytes
        byte[] passwordBytes =
            new String(plaintextPassword).getBytes(StandardCharsets.UTF_8);

        // 2️⃣ Encrypt using vault key
        byte[] encryptedPassword =
            VaultEncryptor.encrypt(
                vaultSession.getVaultKey(),
                passwordBytes
            );

        // hygiene: wipe plaintext password
        for (int i = 0; i < plaintextPassword.length; i++) {
            plaintextPassword[i] = 0;
        }

                // 3️⃣ Build account model (encrypted only)
        Account account = new Account(
            orgId,
            username,
            null,
            null,
            encryptedPassword,
            notes,
            url
        );

        // 4️⃣ Persist account
        accountDAO.save(account);

        // 5️⃣ Audit log (AFTER successful save)
        auditLogDAO.log(
            null,               // accountId unknown here (autoincrement)
            orgId,
            "ACCOUNT_CREATE",
            null,
            "username=" + username
        );

    }

    public char[] getAccountPassword(int accountId) {

        if (!vaultSession.isUnlocked()) {
            throw new IllegalStateException("Vault is locked");
        }

        // 1️⃣ Load encrypted account
        Account account = accountDAO.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        byte[] encryptedPassword = account.getPasswordEncrypted();

        // 2️⃣ Decrypt
        byte[] decryptedBytes =
            VaultEncryptor.decrypt(
                vaultSession.getVaultKey(),
                encryptedPassword
            );

        // 3️⃣ Convert to char[] (temporary)
        char[] plaintext =
            new String(decryptedBytes, java.nio.charset.StandardCharsets.UTF_8)
                .toCharArray();

        // hygiene: wipe decrypted bytes
        for (int i = 0; i < decryptedBytes.length; i++) {
            decryptedBytes[i] = 0;
        }

        return plaintext;
    }

}
