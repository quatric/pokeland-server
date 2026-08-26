package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfl implements Runnable {
    private final /* synthetic */ zzgm zzpd;
    private final /* synthetic */ zzfj zzpe;

    zzfl(zzfj zzfjVar, zzgm zzgmVar) {
        this.zzpe = zzfjVar;
        this.zzpd = zzgmVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpe.zza(this.zzpd);
        this.zzpe.start();
    }
}
