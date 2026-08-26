package com.google.api.client.googleapis.notifications;

import java.util.UUID;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class NotificationUtils {
    private NotificationUtils() {
    }

    public static String randomUuidString() {
        return UUID.randomUUID().toString();
    }
}
