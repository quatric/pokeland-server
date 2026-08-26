package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhf implements Runnable {
    private final /* synthetic */ boolean zzaz;
    private final /* synthetic */ zzgp zzpt;

    zzhf(zzgp zzgpVar, boolean z) {
        this.zzpt = zzgpVar;
        this.zzaz = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zzg(this.zzaz);
    }
}
