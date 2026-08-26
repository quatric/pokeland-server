package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzjf extends zzaa {
    private final /* synthetic */ zzjg zzsa;
    private final /* synthetic */ zzjc zzsm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzjf(zzjc zzjcVar, zzgh zzghVar, zzjg zzjgVar) {
        super(zzghVar);
        this.zzsm = zzjcVar;
        this.zzsa = zzjgVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaa
    public final void run() {
        this.zzsm.cancel();
        this.zzsm.zzab().zzgs().zzao("Starting upload from DelayedRunnable");
        this.zzsa.zzjl();
    }
}
