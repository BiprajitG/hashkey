package com.hashkey.hk.security;

import com.hashkey.hk.database.dao.MasterPasswordDAO;

public class MasterPasswordSetupService {

    private final MasterPasswordDAO dao;

    public MasterPasswordSetupService(MasterPasswordDAO dao) {
        this.dao = dao;
    }

    public void setup(char[] masterPassword) {
        if (masterPassword == null || masterPassword.length == 0) {
            throw new IllegalArgumentException("Master password required");
        }

        if (dao.exists()) {
            throw new IllegalStateException("Master password already set");
        }

        byte[] salt = PasswordHasher.generateSalt();
        byte[] hashBytes = PasswordHasher.hash(masterPassword, salt);
        String hashBase64 = CredentialEncoding.toBase64(hashBytes);

        dao.save(hashBase64, salt);


        // hygiene
        MemoryWiper.wipe(masterPassword);

    }
}
