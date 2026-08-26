package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzi implements Runnable {
    private final /* synthetic */ String zzas;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzp zzdi;
    private final /* synthetic */ AppMeasurementDynamiteService zzdj;
    private final /* synthetic */ boolean zzdl;
    private final /* synthetic */ String zzx;

    zzi(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzp zzpVar, String str, String str2, boolean z) {
        this.zzdj = appMeasurementDynamiteService;
        this.zzdi = zzpVar;
        this.zzx = str;
        this.zzas = str2;
        this.zzdl = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzdj.zzj.zzs().zza(this.zzdi, this.zzx, this.zzas, this.zzdl);
    }
}
