package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzib implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzp zzdi;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzhv zzrd;

    zzib(zzhv zzhvVar, zzn zznVar, com.google.android.gms.internal.measurement.zzp zzpVar) {
        this.zzrd = zzhvVar;
        this.zzpg = zznVar;
        this.zzdi = zzpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            try {
                zzdx zzdxVar = this.zzrd.zzrf;
                if (zzdxVar == null) {
                    this.zzrd.zzab().zzgk().zzao("Failed to get app instance id");
                    this.zzrd.zzz().zzb(this.zzdi, (String) null);
                    return;
                }
                String strZzc = zzdxVar.zzc(this.zzpg);
                if (strZzc != null) {
                    this.zzrd.zzq().zzbg(strZzc);
                    this.zzrd.zzac().zzlq.zzau(strZzc);
                }
                this.zzrd.zzir();
                this.zzrd.zzz().zzb(this.zzdi, strZzc);
            } catch (RemoteException e) {
                this.zzrd.zzab().zzgk().zza("Failed to get app instance id", e);
                this.zzrd.zzz().zzb(this.zzdi, (String) null);
            }
        } catch (Throwable th) {
            this.zzrd.zzz().zzb(this.zzdi, (String) null);
            throw th;
        }
    }
}
