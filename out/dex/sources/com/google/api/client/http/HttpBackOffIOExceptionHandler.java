package com.google.api.client.http;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class HttpBackOffIOExceptionHandler implements HttpIOExceptionHandler {
    private final BackOff backOff;
    private Sleeper sleeper = Sleeper.DEFAULT;

    public HttpBackOffIOExceptionHandler(BackOff backOff) {
        this.backOff = (BackOff) Preconditions.checkNotNull(backOff);
    }

    public final BackOff getBackOff() {
        return this.backOff;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    @Override // com.google.api.client.http.HttpIOExceptionHandler
    public boolean handleIOException(HttpRequest httpRequest, boolean z) throws IOException {
        if (!z) {
            return false;
        }
        try {
            return BackOffUtils.next(this.sleeper, this.backOff);
        } catch (InterruptedException unused) {
            return false;
        }
    }

    public HttpBackOffIOExceptionHandler setSleeper(Sleeper sleeper) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper);
        return this;
    }
}
