package com.google.android.gms.internal.measurement;

import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zzfs {
    private static final zzfs zzajt;
    private static final zzfs zzaju;

    static {
        zzfr zzfrVar = null;
        zzajt = new zzfu();
        zzaju = new zzft();
    }

    private zzfs() {
    }

    static zzfs zzvh() {
        return zzajt;
    }

    static zzfs zzvi() {
        return zzaju;
    }

    abstract <L> List<L> zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    abstract void zzb(Object obj, long j);
}
