package com.google.api.client.util;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface Sleeper {
    public static final Sleeper DEFAULT = new Sleeper() { // from class: com.google.api.client.util.Sleeper.1
        @Override // com.google.api.client.util.Sleeper
        public void sleep(long j) throws InterruptedException {
            Thread.sleep(j);
        }
    };

    void sleep(long j) throws InterruptedException;
}
