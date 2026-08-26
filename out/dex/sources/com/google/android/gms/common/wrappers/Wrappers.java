package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class Wrappers {
    private static Wrappers zzhz = new Wrappers();
    private PackageManagerWrapper zzhy = null;

    @KeepForSdk
    public static PackageManagerWrapper packageManager(Context context) {
        return zzhz.zzi(context);
    }

    @VisibleForTesting
    private final synchronized PackageManagerWrapper zzi(Context context) {
        if (this.zzhy == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.zzhy = new PackageManagerWrapper(context);
        }
        return this.zzhy;
    }
}
