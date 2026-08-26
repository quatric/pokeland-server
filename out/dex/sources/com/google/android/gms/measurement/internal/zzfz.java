package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfz implements Callable<byte[]> {
    private final /* synthetic */ zzai zzdm;
    private final /* synthetic */ String zzdn;
    private final /* synthetic */ zzfk zzph;

    zzfz(zzfk zzfkVar, zzai zzaiVar, String str) {
        this.zzph = zzfkVar;
        this.zzdm = zzaiVar;
        this.zzdn = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ byte[] call() throws Exception {
        this.zzph.zzkz.zzjq();
        return this.zzph.zzkz.zzji().zzb(this.zzdm, this.zzdn);
    }
}
