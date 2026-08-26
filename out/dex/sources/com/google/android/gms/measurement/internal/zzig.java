package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzig implements Runnable {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzhv zzrd;
    private final /* synthetic */ boolean zzrm;
    private final /* synthetic */ boolean zzro;
    private final /* synthetic */ zzq zzrp;
    private final /* synthetic */ zzq zzrq;

    zzig(zzhv zzhvVar, boolean z, boolean z2, zzq zzqVar, zzn zznVar, zzq zzqVar2) {
        this.zzrd = zzhvVar;
        this.zzro = z;
        this.zzrm = z2;
        this.zzrp = zzqVar;
        this.zzpg = zznVar;
        this.zzrq = zzqVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzdx zzdxVar = this.zzrd.zzrf;
        if (zzdxVar == null) {
            this.zzrd.zzab().zzgk().zzao("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzro) {
            this.zzrd.zza(zzdxVar, this.zzrm ? null : this.zzrp, this.zzpg);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzrq.packageName)) {
                    zzdxVar.zza(this.zzrp, this.zzpg);
                } else {
                    zzdxVar.zzb(this.zzrp);
                }
            } catch (RemoteException e) {
                this.zzrd.zzab().zzgk().zza("Failed to send conditional user property to the service", e);
            }
        }
        this.zzrd.zzir();
    }
}
