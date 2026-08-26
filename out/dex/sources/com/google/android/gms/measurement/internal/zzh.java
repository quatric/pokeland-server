package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzh implements Runnable {
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzp zzdi;
    private final /* synthetic */ AppMeasurementDynamiteService zzdj;

    zzh(AppMeasurementDynamiteService appMeasurementDynamiteService, com.google.android.gms.internal.measurement.zzp zzpVar) {
        this.zzdj = appMeasurementDynamiteService;
        this.zzdi = zzpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzdj.zzj.zzs().getAppInstanceId(this.zzdi);
    }
}
