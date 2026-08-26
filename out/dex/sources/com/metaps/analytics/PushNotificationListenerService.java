package com.metaps.analytics;

import android.app.IntentService;
import android.content.Intent;
import com.metaps.common.C0856j;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PushNotificationListenerService extends IntentService {
    public PushNotificationListenerService() {
        super("PushNotificationListenerService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        C0856j.m992a(this).m1010a(this, intent);
    }
}
