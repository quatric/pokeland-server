package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfo implements Runnable {
    private final /* synthetic */ zzq zzpf;
    private final /* synthetic */ zzfk zzph;

    zzfo(zzfk zzfkVar, zzq zzqVar) {
        this.zzph = zzfkVar;
        this.zzpf = zzqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zzf(this.zzpf);
    }
}
