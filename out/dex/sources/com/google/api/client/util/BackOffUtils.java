package com.google.api.client.util;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class BackOffUtils {
    private BackOffUtils() {
    }

    public static boolean next(Sleeper sleeper, BackOff backOff) throws InterruptedException, IOException {
        long jNextBackOffMillis = backOff.nextBackOffMillis();
        if (jNextBackOffMillis == -1) {
            return false;
        }
        sleeper.sleep(jNextBackOffMillis);
        return true;
    }
}
