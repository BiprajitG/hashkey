package com.hashkey.hk.security;

import com.hashkey.hk.database.dao.MasterPasswordDAO;
import com.hashkey.hk.database.dao.StoredMasterPassword;

import java.security.MessageDigest;

public class MasterPasswordVerificationService {

    private final MasterPasswordDAO dao;

    public MasterPasswordVerificationService(MasterPasswordDAO dao) {
        this.dao = dao;
    }

    public AuthResult verify(char[] inputPassword) throws Exception {

        if (inputPassword == null || inputPassword.length == 0) {
            throw new IllegalArgumentException("Master password required");
        }

        SecurityMetadata metadata = new SecurityMetadata();

        // 1️⃣ Check lockout FIRST (fail fast)
        if (LockoutPolicy.isLocked(
                metadata.getFailedAttempts(),
                metadata.getLastFailedEpoch())) {

            long remainingMs =
                LockoutPolicy.remainingLockTimeMs(
                    metadata.getFailedAttempts(),
                    metadata.getLastFailedEpoch());

            return AuthResult.locked(remainingMs);
        }

        // 2️⃣ Load stored credentials
        StoredMasterPassword stored = dao.load();

        byte[] storedHash =
            CredentialEncoding.fromBase64(stored.getPasswordHash());

        byte[] salt = stored.getSalt();

        // 3️⃣ Hash input password using stored salt
        byte[] computedHash =
            PasswordHasher.hash(inputPassword, salt);

        // hygiene: wipe password from memory
        MemoryWiper.wipe(inputPassword);

        // 4️⃣ Constant-time comparison
        boolean match =
            MessageDigest.isEqual(storedHash, computedHash);

        if (match) {
            // 5️⃣ Success → reset lockout state
            metadata.reset();
            return AuthResult.success();
        } else {
            // 6️⃣ Failure → record failure
            metadata.recordFailure();
            return AuthResult.failure();
        }
    }
}
