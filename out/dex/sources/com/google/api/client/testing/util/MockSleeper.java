package com.google.api.client.testing.util;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Sleeper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class MockSleeper implements Sleeper {
    private int count;
    private long lastMillis;

    public final int getCount() {
        return this.count;
    }

    public final long getLastMillis() {
        return this.lastMillis;
    }

    @Override // com.google.api.client.util.Sleeper
    public void sleep(long j) throws InterruptedException {
        this.count++;
        this.lastMillis = j;
    }
}
