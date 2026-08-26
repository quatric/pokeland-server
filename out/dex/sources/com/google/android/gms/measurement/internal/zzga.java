package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzga implements Callable<List<zzjp>> {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzfk zzph;

    zzga(zzfk zzfkVar, zzn zznVar) {
        this.zzph = zzfkVar;
        this.zzpg = zznVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzjp> call() throws Exception {
        this.zzph.zzkz.zzjq();
        return this.zzph.zzkz.zzgy().zzaa(this.zzpg.packageName);
    }
}
