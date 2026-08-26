package com.google.android.gms.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.stats.WakeLockTracker;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@ShowFirstParty
@KeepForSdk
public abstract class GCoreWakefulBroadcastReceiver extends WakefulBroadcastReceiver {
    private static String TAG = "GCoreWakefulBroadcastReceiver";

    @KeepForSdk
    @SuppressLint({"UnwrappedWakefulBroadcastReceiver"})
    public static boolean completeWakefulIntent(Context context, Intent intent) {
        if (intent == null) {
            return false;
        }
        if (context != null) {
            WakeLockTracker.getInstance().registerReleaseEvent(context, intent);
        } else {
            String str = TAG;
            String strValueOf = String.valueOf(intent.toUri(0));
            Log.w(str, strValueOf.length() != 0 ? "context shouldn't be null. intent: ".concat(strValueOf) : new String("context shouldn't be null. intent: "));
        }
        return WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
}
