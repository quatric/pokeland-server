package com.google.android.gms.common.config;

import android.content.Context;
import android.os.Binder;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.HashSet;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public abstract class GservicesValue<T> {
    private static final Object sLock = new Object();
    private static zza zzbm = null;
    private static int zzbn = 0;
    private static Context zzbo;

    @GuardedBy("sLock")
    private static HashSet<String> zzbp;
    protected final String mKey;
    protected final T zzbq;
    private T zzbr = null;

    /* JADX INFO: Access modifiers changed from: private */
    interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Float zza(String str, Float f);

        Integer zza(String str, Integer num);
    }

    protected GservicesValue(String str, T t) {
        this.mKey = str;
        this.zzbq = t;
    }

    @KeepForSdk
    public static boolean isInitialized() {
        synchronized (sLock) {
        }
        return false;
    }

    @KeepForSdk
    public static GservicesValue<Float> value(String str, Float f) {
        return new zzd(str, f);
    }

    @KeepForSdk
    public static GservicesValue<Integer> value(String str, Integer num) {
        return new zzc(str, num);
    }

    @KeepForSdk
    public static GservicesValue<Long> value(String str, Long l) {
        return new zzb(str, l);
    }

    @KeepForSdk
    public static GservicesValue<String> value(String str, String str2) {
        return new zze(str, str2);
    }

    @KeepForSdk
    public static GservicesValue<Boolean> value(String str, boolean z) {
        return new com.google.android.gms.common.config.zza(str, Boolean.valueOf(z));
    }

    private static boolean zzi() {
        synchronized (sLock) {
        }
        return false;
    }

    @KeepForSdk
    public final T get() {
        T t = this.zzbr;
        if (t != null) {
            return t;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        synchronized (sLock) {
        }
        synchronized (sLock) {
            zzbp = null;
            zzbo = null;
        }
        try {
            try {
                T tZzd = zzd(this.mKey);
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return tZzd;
            } catch (SecurityException unused) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    T tZzd2 = zzd(this.mKey);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                    return tZzd2;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th2;
        }
    }

    @KeepForSdk
    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    @VisibleForTesting
    @KeepForSdk
    public void override(T t) {
        Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        this.zzbr = t;
        synchronized (sLock) {
            zzi();
        }
    }

    @VisibleForTesting
    @KeepForSdk
    public void resetOverride() {
        this.zzbr = null;
    }

    protected abstract T zzd(String str);
}
