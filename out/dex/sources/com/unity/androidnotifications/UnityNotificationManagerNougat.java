package com.unity.androidnotifications;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Keep;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Keep
public class UnityNotificationManagerNougat extends UnityNotificationManager {
    public UnityNotificationManagerNougat(Context context, Activity activity) {
        super(context, activity);
    }

    public static void sendNotificationNougat(Intent intent, Context context) {
        Notification.Builder builderBuildNotification = UnityNotificationManager.buildNotification(intent, context);
        int intExtra = intent.getIntExtra("id", -1);
        if (Build.VERSION.SDK_INT > 24) {
            String stringExtra = intent.getStringExtra("group");
            boolean booleanExtra = intent.getBooleanExtra("groupSummary", false);
            String stringExtra2 = intent.getStringExtra("sortKey");
            int intExtra2 = intent.getIntExtra("groupAlertBehaviour", -1);
            if (stringExtra != null && stringExtra.length() > 0) {
                builderBuildNotification.setGroup(stringExtra);
            }
            if (booleanExtra) {
                builderBuildNotification.setGroupSummary(booleanExtra);
            }
            if (stringExtra2 != null && stringExtra2.length() > 0) {
                builderBuildNotification.setSortKey(stringExtra2);
            }
            if (Build.VERSION.SDK_INT >= 26 && intExtra2 >= 0) {
                builderBuildNotification.setGroupAlertBehavior(intExtra2);
            }
        }
        UnityNotificationManager.notify(context, intExtra, builderBuildNotification, intent);
    }
}
