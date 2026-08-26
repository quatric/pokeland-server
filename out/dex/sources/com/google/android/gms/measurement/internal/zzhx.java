package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhx implements Runnable {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzjn zzpi;
    private final /* synthetic */ zzhv zzrd;
    private final /* synthetic */ boolean zzrm;

    zzhx(zzhv zzhvVar, boolean z, zzjn zzjnVar, zzn zznVar) {
        this.zzrd = zzhvVar;
        this.zzrm = z;
        this.zzpi = zzjnVar;
        this.zzpg = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzdx zzdxVar = this.zzrd.zzrf;
        if (zzdxVar == null) {
            this.zzrd.zzab().zzgk().zzao("Discarding data. Failed to set user attribute");
        } else {
            this.zzrd.zza(zzdxVar, this.zzrm ? null : this.zzpi, this.zzpg);
            this.zzrd.zzir();
        }
    }
}
