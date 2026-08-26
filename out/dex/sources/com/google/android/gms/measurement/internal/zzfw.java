package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfw implements Runnable {
    private final /* synthetic */ zzai zzdm;
    private final /* synthetic */ String zzdn;
    private final /* synthetic */ zzfk zzph;

    zzfw(zzfk zzfkVar, zzai zzaiVar, String str) {
        this.zzph = zzfkVar;
        this.zzdm = zzaiVar;
        this.zzdn = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzph.zzkz.zzjq();
        this.zzph.zzkz.zzd(this.zzdm, this.zzdn);
    }
}
