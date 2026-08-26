package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zziu implements Runnable {
    private final /* synthetic */ zzjg zzsa;
    private final /* synthetic */ Runnable zzsb;

    zziu(zzit zzitVar, zzjg zzjgVar, Runnable runnable) {
        this.zzsa = zzjgVar;
        this.zzsb = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzsa.zzjq();
        this.zzsa.zzf(this.zzsb);
        this.zzsa.zzjl();
    }
}
