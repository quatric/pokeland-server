package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfr implements Runnable {
    private final /* synthetic */ zzq zzpf;
    private final /* synthetic */ zzfk zzph;

    zzfr(zzfk zzfkVar, zzq zzqVar) {
        this.zzph = zzfkVar;
        this.zzpf = zzqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zze(this.zzpf);
    }
}
