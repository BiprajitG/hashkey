package com.puredroid.hk.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SecurityMetadata {
    private static final String SECURITY_METADATA_PATH="security.meta";

    private static final String FAILED_ATTEMPTS_KEY = "failed_attempts";
    private static final String LAST_FAILED_EPOCH_KEY = "last_failed_attempt";
    private int failedAttempts;
    private long lastFailedEpoch;

    public SecurityMetadata(int failedAttempts, long lastFailedEpoch) {
        this.failedAttempts = failedAttempts;
        this.lastFailedEpoch = lastFailedEpoch;
    }

    public SecurityMetadata() throws IOException{
        Properties properties = getCurrentProperties();
        this.failedAttempts = Integer.parseInt(properties.getProperty(FAILED_ATTEMPTS_KEY, "0"));
        this.lastFailedEpoch = Long.parseLong(properties.getProperty(LAST_FAILED_EPOCH_KEY, "0"));
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public long getLastFailedEpoch() {
        return lastFailedEpoch;
    }

    private Properties getCurrentProperties() throws IOException{
        Properties properties = new Properties();
        File file = new File(SECURITY_METADATA_PATH);
        if (!file.exists()) {
            initializeEmptyFile(file);
        }
        try(FileInputStream in = new FileInputStream(file)){
            properties.load(in);
        }
        return properties;
    }

    private void persist() throws IOException{
        Properties properties = new Properties();
        File file = new File(SECURITY_METADATA_PATH);
        if (!file.exists()) {
            initializeEmptyFile(file);
        }
        properties.setProperty(FAILED_ATTEMPTS_KEY, String.valueOf(failedAttempts));
        properties.setProperty(LAST_FAILED_EPOCH_KEY, String.valueOf(lastFailedEpoch));
        try(FileOutputStream out = new FileOutputStream(file)){
            properties.store(out, "HashKey security metadata");
        }
    }    

    public void initializeEmptyFile(File file) throws IOException{
        Properties properties = new Properties();
        properties.setProperty(FAILED_ATTEMPTS_KEY, "0");
        properties.setProperty(LAST_FAILED_EPOCH_KEY, "0");

        try (FileOutputStream out = new FileOutputStream(file)) {
            properties.store(out, "HashKey security metadata");
        }
    }

    public void recordFailure() throws IOException {
        this.failedAttempts++;
        this.lastFailedEpoch = System.currentTimeMillis();
        persist();
    }

    public void reset() throws IOException{
        File file = new File(SECURITY_METADATA_PATH);
        if (!file.exists()) {
            initializeEmptyFile(file);
        }
        this.failedAttempts=0;
        this.lastFailedEpoch=0;
        persist();
    }
}
