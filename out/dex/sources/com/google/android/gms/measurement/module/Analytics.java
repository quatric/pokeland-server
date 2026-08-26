package com.google.android.gms.measurement.module;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.internal.measurement.zzx;
import com.google.android.gms.measurement.internal.zzfj;
import com.google.android.gms.measurement.internal.zzgi;
import com.google.android.gms.measurement.internal.zzgj;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@ShowFirstParty
public class Analytics {

    @ShowFirstParty
    @KeepForSdk
    public static final String CRASH_ORIGIN = "crash";

    @ShowFirstParty
    @KeepForSdk
    public static final String FCM_ORIGIN = "fcm";

    @ShowFirstParty
    @KeepForSdk
    public static final String FIAM_ORIGIN = "fiam";
    private static volatile Analytics zzub;
    private final zzfj zzj;

    @ShowFirstParty
    @KeepForSdk
    public static final class Event extends zzgj {

        @ShowFirstParty
        @KeepForSdk
        public static final String AD_REWARD = "_ar";

        @ShowFirstParty
        @KeepForSdk
        public static final String APP_EXCEPTION = "_ae";

        private Event() {
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public static final class Param extends zzgi {

        @ShowFirstParty
        @KeepForSdk
        public static final String FATAL = "fatal";

        @ShowFirstParty
        @KeepForSdk
        public static final String TIMESTAMP = "timestamp";

        @ShowFirstParty
        @KeepForSdk
        public static final String TYPE = "type";

        private Param() {
        }
    }

    private Analytics(zzfj zzfjVar) {
        Preconditions.checkNotNull(zzfjVar);
        this.zzj = zzfjVar;
    }

    @Keep
    @ShowFirstParty
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    public static Analytics getInstance(Context context) {
        if (zzub == null) {
            synchronized (Analytics.class) {
                if (zzub == null) {
                    zzub = new Analytics(zzfj.zza(context, (zzx) null));
                }
            }
        }
        return zzub;
    }
}
