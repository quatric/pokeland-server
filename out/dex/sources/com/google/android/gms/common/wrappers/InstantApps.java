package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class InstantApps {
    private static Context zzhv;
    private static Boolean zzhw;

    @KeepForSdk
    public static synchronized boolean isInstantApp(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (zzhv != null && zzhw != null && zzhv == applicationContext) {
            return zzhw.booleanValue();
        }
        zzhw = null;
        if (PlatformVersion.isAtLeastO()) {
            zzhw = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
        } else {
            try {
                context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                zzhw = true;
            } catch (ClassNotFoundException unused) {
                zzhw = false;
            }
        }
        zzhv = applicationContext;
        return zzhw.booleanValue();
    }
}
