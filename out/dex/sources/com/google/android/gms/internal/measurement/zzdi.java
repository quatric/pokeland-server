package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdi {
    private static final Class<?> zzacx = zzdp("libcore.io.Memory");
    private static final boolean zzacy;

    static {
        zzacy = zzdp("org.robolectric.Robolectric") != null;
    }

    private static <T> Class<T> zzdp(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zzrv() {
        return (zzacx == null || zzacy) ? false : true;
    }

    static Class<?> zzrw() {
        return zzacx;
    }
}
