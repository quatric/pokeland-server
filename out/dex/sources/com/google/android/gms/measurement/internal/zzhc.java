package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhc implements Runnable {
    private final /* synthetic */ AtomicReference zzps;
    private final /* synthetic */ zzgp zzpt;

    zzhc(zzgp zzgpVar, AtomicReference atomicReference) {
        this.zzpt = zzgpVar;
        this.zzps = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzps) {
            try {
                this.zzps.set(Double.valueOf(this.zzpt.zzad().zzc(this.zzpt.zzr().zzag(), zzak.zzhq)));
                this.zzps.notify();
            } catch (Throwable th) {
                this.zzps.notify();
                throw th;
            }
        }
    }
}
