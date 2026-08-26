package com.google.android.gms.internal.measurement;

import android.os.Binder;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final /* synthetic */ class zzch {
    public static <V> V zza(zzcg<V> zzcgVar) {
        try {
            return zzcgVar.zzrj();
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return zzcgVar.zzrj();
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }
}
