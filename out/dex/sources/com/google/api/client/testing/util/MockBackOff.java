package com.google.api.client.testing.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class MockBackOff implements BackOff {
    private long backOffMillis;
    private int maxTries = 10;
    private int numTries;

    public final int getMaxTries() {
        return this.numTries;
    }

    public final int getNumberOfTries() {
        return this.numTries;
    }

    @Override // com.google.api.client.util.BackOff
    public long nextBackOffMillis() throws IOException {
        int i = this.numTries;
        if (i < this.maxTries) {
            long j = this.backOffMillis;
            if (j != -1) {
                this.numTries = i + 1;
                return j;
            }
        }
        return -1L;
    }

    @Override // com.google.api.client.util.BackOff
    public void reset() throws IOException {
        this.numTries = 0;
    }

    public MockBackOff setBackOffMillis(long j) {
        Preconditions.checkArgument(j == -1 || j >= 0);
        this.backOffMillis = j;
        return this;
    }

    public MockBackOff setMaxTries(int i) {
        Preconditions.checkArgument(i >= 0);
        this.maxTries = i;
        return this;
    }
}
