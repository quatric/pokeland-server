package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzia implements Runnable {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzhv zzrd;
    private final /* synthetic */ boolean zzrn;

    zzia(zzhv zzhvVar, zzn zznVar, boolean z) {
        this.zzrd = zzhvVar;
        this.zzpg = zznVar;
        this.zzrn = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzdx zzdxVar = this.zzrd.zzrf;
        if (zzdxVar == null) {
            this.zzrd.zzab().zzgk().zzao("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzdxVar.zza(this.zzpg);
            if (this.zzrn) {
                this.zzrd.zzu().zzgi();
            }
            this.zzrd.zza(zzdxVar, (AbstractSafeParcelable) null, this.zzpg);
            this.zzrd.zzir();
        } catch (RemoteException e) {
            this.zzrd.zzab().zzgk().zza("Failed to send app launch to the service", e);
        }
    }
}
