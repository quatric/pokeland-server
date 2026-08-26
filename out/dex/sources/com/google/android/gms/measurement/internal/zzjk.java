package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzjk implements Callable<String> {
    private final /* synthetic */ zzn zzpg;
    private final /* synthetic */ zzjg zztl;

    zzjk(zzjg zzjgVar, zzn zznVar) {
        this.zztl = zzjgVar;
        this.zzpg = zznVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        zzf zzfVarZzg = this.zztl.zzg(this.zzpg);
        if (zzfVarZzg != null) {
            return zzfVarZzg.getAppInstanceId();
        }
        this.zztl.zzab().zzgn().zzao("App info was null when attempting to get app instance id");
        return null;
    }
}
