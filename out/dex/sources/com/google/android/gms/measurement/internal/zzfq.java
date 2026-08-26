package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfq implements Callable<List<zzjp>> {
    private final /* synthetic */ String zzas;
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzfk zzph;
    private final /* synthetic */ String zzx;

    zzfq(zzfk zzfkVar, zzn zznVar, String str, String str2) {
        this.zzph = zzfkVar;
        this.zzpg = zznVar;
        this.zzx = str;
        this.zzas = str2;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzjp> call() throws Exception {
        this.zzph.zzkz.zzjq();
        return this.zzph.zzkz.zzgy().zza(this.zzpg.packageName, this.zzx, this.zzas);
    }
}
