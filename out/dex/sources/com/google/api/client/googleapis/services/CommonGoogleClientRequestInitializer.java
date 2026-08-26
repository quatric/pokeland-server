package com.google.api.client.googleapis.services;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class CommonGoogleClientRequestInitializer implements GoogleClientRequestInitializer {
    private final String key;
    private final String userIp;

    public CommonGoogleClientRequestInitializer() {
        this(null);
    }

    public CommonGoogleClientRequestInitializer(String str) {
        this(str, null);
    }

    public CommonGoogleClientRequestInitializer(String str, String str2) {
        this.key = str;
        this.userIp = str2;
    }

    public final String getKey() {
        return this.key;
    }

    public final String getUserIp() {
        return this.userIp;
    }

    @Override // com.google.api.client.googleapis.services.GoogleClientRequestInitializer
    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        String str = this.key;
        if (str != null) {
            abstractGoogleClientRequest.put("key", (Object) str);
        }
        String str2 = this.userIp;
        if (str2 != null) {
            abstractGoogleClientRequest.put("userIp", (Object) str2);
        }
    }
}
