package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfp implements Runnable {
    private final /* synthetic */ zzq zzpf;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzfk zzph;

    zzfp(zzfk zzfkVar, zzq zzqVar, zzn zznVar) {
        this.zzph = zzfkVar;
        this.zzpf = zzqVar;
        this.zzpg = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zzb(this.zzpf, this.zzpg);
    }
}
