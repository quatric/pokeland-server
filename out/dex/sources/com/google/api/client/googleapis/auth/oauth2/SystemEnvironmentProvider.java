package com.google.api.client.googleapis.auth.oauth2;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class SystemEnvironmentProvider {
    static final SystemEnvironmentProvider INSTANCE = new SystemEnvironmentProvider();

    SystemEnvironmentProvider() {
    }

    String getEnv(String str) {
        return System.getenv(str);
    }

    boolean getEnvEquals(String str, String str2) {
        return System.getenv().containsKey(str) && System.getenv(str).equals(str2);
    }
}
