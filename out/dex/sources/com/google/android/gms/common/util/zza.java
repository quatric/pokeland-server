package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.firebase.analytics.FirebaseAnalytics;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zza {
    private static long zzgv;
    private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static float zzgw = Float.NaN;

    @TargetApi(20)
    public static int zzg(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
        int i = ((intentRegisterReceiver == null ? 0 : intentRegisterReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        return (PlatformVersion.isAtLeastKitKatWatch() ? powerManager.isInteractive() : powerManager.isScreenOn() ? 2 : 0) | i;
    }

    public static synchronized float zzh(Context context) {
        if (SystemClock.elapsedRealtime() - zzgv < 60000 && !Float.isNaN(zzgw)) {
            return zzgw;
        }
        Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
        if (intentRegisterReceiver != null) {
            zzgw = intentRegisterReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) / intentRegisterReceiver.getIntExtra("scale", -1);
        }
        zzgv = SystemClock.elapsedRealtime();
        return zzgw;
    }
}
