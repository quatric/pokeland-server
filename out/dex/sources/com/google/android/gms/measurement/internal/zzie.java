package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzie implements Runnable {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzhv zzrd;

    zzie(zzhv zzhvVar, zzn zznVar) {
        this.zzrd = zzhvVar;
        this.zzpg = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzdx zzdxVar = this.zzrd.zzrf;
        if (zzdxVar == null) {
            this.zzrd.zzab().zzgk().zzao("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzdxVar.zzb(this.zzpg);
            this.zzrd.zzir();
        } catch (RemoteException e) {
            this.zzrd.zzab().zzgk().zza("Failed to send measurementEnabled to the service", e);
        }
    }
}
