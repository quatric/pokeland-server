package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfm implements Runnable {
    private final /* synthetic */ zzq zzpf;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzfk zzph;

    zzfm(zzfk zzfkVar, zzq zzqVar, zzn zznVar) {
        this.zzph = zzfkVar;
        this.zzpf = zzqVar;
        this.zzpg = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zzc(this.zzpf, this.zzpg);
    }
}
