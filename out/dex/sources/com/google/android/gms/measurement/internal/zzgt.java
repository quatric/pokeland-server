package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgt implements Runnable {
    private final /* synthetic */ boolean zzbi;
    private final /* synthetic */ AtomicReference zzps;
    private final /* synthetic */ zzgp zzpt;

    zzgt(zzgp zzgpVar, AtomicReference atomicReference, boolean z) {
        this.zzpt = zzgpVar;
        this.zzps = atomicReference;
        this.zzbi = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zzs().zza(this.zzps, this.zzbi);
    }
}
