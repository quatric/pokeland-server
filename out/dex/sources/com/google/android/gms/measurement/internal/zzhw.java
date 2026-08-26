package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhw implements Runnable {
    private final /* synthetic */ boolean zzbi;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzhv zzrd;
    private final /* synthetic */ AtomicReference zzrl;

    zzhw(zzhv zzhvVar, AtomicReference atomicReference, zzn zznVar, boolean z) {
        this.zzrd = zzhvVar;
        this.zzrl = atomicReference;
        this.zzpg = zznVar;
        this.zzbi = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzrl) {
            try {
                try {
                    zzdx zzdxVar = this.zzrd.zzrf;
                    if (zzdxVar == null) {
                        this.zzrd.zzab().zzgk().zzao("Failed to get user properties");
                        this.zzrl.notify();
                    } else {
                        this.zzrl.set(zzdxVar.zza(this.zzpg, this.zzbi));
                        this.zzrd.zzir();
                        this.zzrl.notify();
                    }
                } catch (RemoteException e) {
                    this.zzrd.zzab().zzgk().zza("Failed to get user properties", e);
                    this.zzrl.notify();
                }
            } catch (Throwable th) {
                this.zzrl.notify();
                throw th;
            }
        }
    }
}
