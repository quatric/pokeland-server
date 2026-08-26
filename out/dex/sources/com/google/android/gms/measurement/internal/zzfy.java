package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfy implements Runnable {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzfk zzph;
    private final /* synthetic */ zzjn zzpi;

    zzfy(zzfk zzfkVar, zzjn zzjnVar, zzn zznVar) {
        this.zzph = zzfkVar;
        this.zzpi = zzjnVar;
        this.zzpg = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zzc(this.zzpi, this.zzpg);
    }
}
