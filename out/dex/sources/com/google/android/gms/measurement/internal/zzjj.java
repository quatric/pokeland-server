package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzjj implements Runnable {
    private final /* synthetic */ zzjg zztl;
    private final /* synthetic */ zzjm zztm;

    zzjj(zzjg zzjgVar, zzjm zzjmVar) {
        this.zztl = zzjgVar;
        this.zztm = zzjmVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zztl.zza(this.zztm);
        this.zztl.start();
    }
}
