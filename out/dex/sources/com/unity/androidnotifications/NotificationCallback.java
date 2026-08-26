package com.unity.androidnotifications;

import android.content.Intent;
import android.support.annotation.Keep;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Keep
public interface NotificationCallback {
    void onSentNotification(Intent intent);
}
