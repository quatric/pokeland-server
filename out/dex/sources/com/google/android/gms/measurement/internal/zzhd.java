package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhd implements Runnable {
    private final /* synthetic */ AtomicReference zzps;
    private final /* synthetic */ zzgp zzpt;

    zzhd(zzgp zzgpVar, AtomicReference atomicReference) {
        this.zzpt = zzgpVar;
        this.zzps = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzps) {
            try {
                this.zzps.set(Integer.valueOf(this.zzpt.zzad().zzb(this.zzpt.zzr().zzag(), zzak.zzhp)));
                this.zzps.notify();
            } catch (Throwable th) {
                this.zzps.notify();
                throw th;
            }
        }
    }
}
