package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfv implements Callable<List<zzq>> {
    private final /* synthetic */ String zzas;
    private final /* synthetic */ String zzdn;
    private final /* synthetic */ zzfk zzph;
    private final /* synthetic */ String zzx;

    zzfv(zzfk zzfkVar, String str, String str2, String str3) {
        this.zzph = zzfkVar;
        this.zzdn = str;
        this.zzx = str2;
        this.zzas = str3;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzq> call() throws Exception {
        this.zzph.zzkz.zzjq();
        return this.zzph.zzkz.zzgy().zzb(this.zzdn, this.zzx, this.zzas);
    }
}
