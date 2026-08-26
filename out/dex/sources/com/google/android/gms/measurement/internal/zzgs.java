package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgs implements Runnable {
    private final /* synthetic */ AtomicReference zzps;
    private final /* synthetic */ zzgp zzpt;

    zzgs(zzgp zzgpVar, AtomicReference atomicReference) {
        this.zzpt = zzgpVar;
        this.zzps = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zzs().zza(this.zzps);
    }
}
