package com.unity.androidnotifications;

import android.app.Activity;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Keep;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Keep
public class UnityNotificationManagerOreo extends UnityNotificationManagerNougat {
    public UnityNotificationManagerOreo(Context context, Activity activity) {
        super(context, activity);
    }

    public static NotificationChannelWrapper NotificationChannelToWrapper(NotificationChannel notificationChannel) {
        NotificationChannelWrapper notificationChannelWrapper = new NotificationChannelWrapper();
        if (Build.VERSION.SDK_INT >= 26) {
            notificationChannelWrapper.f1836id = notificationChannel.getId();
            notificationChannelWrapper.name = notificationChannel.getName().toString();
            notificationChannelWrapper.importance = notificationChannel.getImportance();
            notificationChannelWrapper.description = notificationChannel.getDescription();
            notificationChannelWrapper.enableLights = notificationChannel.shouldShowLights();
            notificationChannelWrapper.enableVibration = notificationChannel.shouldVibrate();
            notificationChannelWrapper.canBypassDnd = notificationChannel.canBypassDnd();
            notificationChannelWrapper.canShowBadge = notificationChannel.canShowBadge();
            notificationChannelWrapper.vibrationPattern = notificationChannel.getVibrationPattern();
            notificationChannelWrapper.lockscreenVisibility = notificationChannel.getLockscreenVisibility();
        }
        return notificationChannelWrapper;
    }

    public static NotificationChannelWrapper getOreoNotificationChannel(String str, Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        new ArrayList();
        for (NotificationChannel notificationChannel : getNotificationManager(context).getNotificationChannels()) {
            if (notificationChannel.getId() == str) {
                return NotificationChannelToWrapper(notificationChannel);
            }
        }
        return null;
    }

    @Override // com.unity.androidnotifications.UnityNotificationManager
    public void deleteNotificationChannel(String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            getNotificationManager().deleteNotificationChannel(str);
        }
    }

    @Override // com.unity.androidnotifications.UnityNotificationManager
    public NotificationChannelWrapper[] getNotificationChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<NotificationChannel> it = getNotificationManager().getNotificationChannels().iterator();
        while (it.hasNext()) {
            arrayList.add(NotificationChannelToWrapper(it.next()));
        }
        return (NotificationChannelWrapper[]) arrayList.toArray(new NotificationChannelWrapper[arrayList.size()]);
    }

    @Override // com.unity.androidnotifications.UnityNotificationManager
    public void registerNotificationChannel(String str, String str2, int i, String str3, boolean z, boolean z2, boolean z3, boolean z4, long[] jArr, int i2) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(str, str2, i);
            notificationChannel.setDescription(str3);
            notificationChannel.enableLights(z);
            notificationChannel.enableVibration(z2);
            notificationChannel.setBypassDnd(z3);
            notificationChannel.setShowBadge(z4);
            notificationChannel.setVibrationPattern(jArr);
            notificationChannel.setLockscreenVisibility(i2);
            getNotificationManager().createNotificationChannel(notificationChannel);
        }
    }
}
