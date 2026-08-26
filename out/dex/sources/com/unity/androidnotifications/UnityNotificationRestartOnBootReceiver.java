package com.unity.androidnotifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Keep
public class UnityNotificationRestartOnBootReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            for (Intent intent2 : UnityNotificationManager.LoadNotificationIntents(context)) {
                long longExtra = intent2.getLongExtra("fireTime", 0L);
                Date time = Calendar.getInstance().getTime();
                Date date = new Date(longExtra);
                int intExtra = intent2.getIntExtra("id", -1);
                if (date.after(time)) {
                    Intent intentPrepareNotificationIntent = UnityNotificationManager.prepareNotificationIntent(intent2, context, PendingIntent.getActivity(context, intExtra, UnityNotificationManager.buildOpenAppIntent(intent2, context, UnityNotificationManager.GetOpenAppActivity(context, true)), 0));
                    UnityNotificationManager.scheduleNotificationIntentAlarm(intentPrepareNotificationIntent, context, PendingIntent.getBroadcast(context, intExtra, intentPrepareNotificationIntent, 134217728));
                } else {
                    UnityNotificationManager.deleteExpiredNotificationIntent(intExtra, context);
                }
            }
        }
    }
}
