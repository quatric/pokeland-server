package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzga<K, V> {
    static <K, V> int zza(zzfz<K, V> zzfzVar, K k, V v) {
        return zzeo.zza(zzfzVar.zzakb, 1, k) + zzeo.zza(zzfzVar.zzakd, 2, v);
    }

    static <K, V> void zza(zzee zzeeVar, zzfz<K, V> zzfzVar, K k, V v) throws IOException {
        zzeo.zza(zzeeVar, zzfzVar.zzakb, 1, k);
        zzeo.zza(zzeeVar, zzfzVar.zzakd, 2, v);
    }
}
