package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfx implements Runnable {
    private final /* synthetic */ zzai zzdm;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzfk zzph;

    zzfx(zzfk zzfkVar, zzai zzaiVar, zzn zznVar) {
        this.zzph = zzfkVar;
        this.zzdm = zzaiVar;
        this.zzpg = zznVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzai zzaiVarZzb = this.zzph.zzb(this.zzdm, this.zzpg);
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zzc(zzaiVarZzb, this.zzpg);
    }
}
