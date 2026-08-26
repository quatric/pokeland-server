package com.google.android.gms.internal.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzg {
    private static volatile boolean zziy = !zzam();

    @RequiresApi(MotionEventCompat.AXIS_DISTANCE)
    @TargetApi(MotionEventCompat.AXIS_DISTANCE)
    public static Context getDeviceProtectedStorageContext(Context context) {
        return context.isDeviceProtectedStorage() ? context : context.createDeviceProtectedStorageContext();
    }

    public static boolean zzam() {
        return Build.VERSION.SDK_INT >= 24;
    }
}
