package com.hashkey.hk.security;

public final class MemoryWiper {

    private MemoryWiper() {}

    public static void wipe(char[] data) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                data[i] = 0;
            }
        }
    }

    public static void wipe(byte[] data) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                data[i] = 0;
            }
        }
    }
}
