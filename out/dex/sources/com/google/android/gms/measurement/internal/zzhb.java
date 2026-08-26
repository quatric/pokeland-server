package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhb implements Runnable {
    private final /* synthetic */ String zzas;
    private final /* synthetic */ boolean zzbi;
    private final /* synthetic */ String zzdn;
    private final /* synthetic */ AtomicReference zzps;
    private final /* synthetic */ zzgp zzpt;
    private final /* synthetic */ String zzx;

    zzhb(zzgp zzgpVar, AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        this.zzpt = zzgpVar;
        this.zzps = atomicReference;
        this.zzdn = str;
        this.zzx = str2;
        this.zzas = str3;
        this.zzbi = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zzj.zzs().zza(this.zzps, this.zzdn, this.zzx, this.zzas, this.zzbi);
    }
}
