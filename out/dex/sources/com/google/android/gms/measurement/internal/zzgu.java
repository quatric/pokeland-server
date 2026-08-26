package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgu implements Runnable {
    private final /* synthetic */ zzgk zzbc;
    private final /* synthetic */ zzgp zzpt;

    zzgu(zzgp zzgpVar, zzgk zzgkVar) {
        this.zzpt = zzgpVar;
        this.zzbc = zzgkVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zza(this.zzbc);
    }
}
