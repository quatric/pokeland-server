package com.google.api.client.util;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface Clock {
    public static final Clock SYSTEM = new Clock() { // from class: com.google.api.client.util.Clock.1
        @Override // com.google.api.client.util.Clock
        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }
    };

    long currentTimeMillis();
}
