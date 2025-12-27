package com.puredroid.hk.security;

public class LockoutPolicy {
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MS = 15*60*1000;

    public static boolean isLocked(int failedAttempts, long lastFailedEpoch){
        return failedAttempts>=MAX_ATTEMPTS&&(System.currentTimeMillis()-lastFailedEpoch)<LOCK_DURATION_MS;
    }

    public static long remainingLockTimeMs(int failedAttempts, long lastFailedEpoch){
        if (!isLocked(failedAttempts, lastFailedEpoch)) {
            return 0;
        }
        long remaining = LOCK_DURATION_MS - (System.currentTimeMillis() - lastFailedEpoch);
        return Math.max(0, remaining);
    }
}
