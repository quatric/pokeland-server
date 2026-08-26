package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzid implements Runnable {
    private final /* synthetic */ zzhr zzqy;
    private final /* synthetic */ zzhv zzrd;

    zzid(zzhv zzhvVar, zzhr zzhrVar) {
        this.zzrd = zzhvVar;
        this.zzqy = zzhrVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzdx zzdxVar = this.zzrd.zzrf;
        if (zzdxVar == null) {
            this.zzrd.zzab().zzgk().zzao("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzqy == null) {
                zzdxVar.zza(0L, (String) null, (String) null, this.zzrd.getContext().getPackageName());
            } else {
                zzdxVar.zza(this.zzqy.zzqw, this.zzqy.zzqu, this.zzqy.zzqv, this.zzrd.getContext().getPackageName());
            }
            this.zzrd.zzir();
        } catch (RemoteException e) {
            this.zzrd.zzab().zzgk().zza("Failed to send current screen to the service", e);
        }
    }
}
