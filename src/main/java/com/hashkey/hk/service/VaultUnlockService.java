package com.hashkey.hk.service;

import com.hashkey.hk.crypto.VaultKeyDeriver;
import com.hashkey.hk.database.dao.MasterPasswordDAO;
import com.hashkey.hk.database.dao.StoredMasterPassword;
import com.hashkey.hk.security.AuthResult;
import com.hashkey.hk.security.MasterPasswordVerificationService;
import com.hashkey.hk.security.MemoryWiper;
import com.hashkey.hk.vault.VaultSession;

public class VaultUnlockService {

    private final MasterPasswordVerificationService verifier;
    private final MasterPasswordDAO masterPasswordDAO;
    private final VaultSession vaultSession;

    public VaultUnlockService(
            MasterPasswordVerificationService verifier,
            MasterPasswordDAO masterPasswordDAO,
            VaultSession vaultSession) {

        this.verifier = verifier;
        this.masterPasswordDAO = masterPasswordDAO;
        this.vaultSession = vaultSession;
    }

    public AuthResult unlock(char[] masterPassword) throws Exception {

        // 1️⃣ Verify password + lockout
        AuthResult result = verifier.verify(masterPassword);

        if (!result.isSuccess()) {
            return result;
        }

        // 2️⃣ Load salt (safe only after auth success)
        StoredMasterPassword stored = masterPasswordDAO.load();
        byte[] salt = stored.getSalt();

        // 3️⃣ Derive vault key
        byte[] vaultKey =
            VaultKeyDeriver.deriveKey(masterPassword, salt);

        // 4️⃣ Unlock vault session
        vaultSession.unlock(vaultKey);

        // hygiene: wipe derived key copy
        MemoryWiper.wipe(vaultKey);


        return AuthResult.success();
    }
}
