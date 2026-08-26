package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzj implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzp zzdi;
    private final /* synthetic */ AppMeasurementDynamiteService zzdj;
    private final /* synthetic */ zzai zzdm;
    private final /* synthetic */ String zzdn;

    zzj(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzp zzpVar, zzai zzaiVar, String str) {
        this.zzdj = appMeasurementDynamiteService;
        this.zzdi = zzpVar;
        this.zzdm = zzaiVar;
        this.zzdn = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzdj.zzj.zzs().zza(this.zzdi, this.zzdm, this.zzdn);
    }
}
