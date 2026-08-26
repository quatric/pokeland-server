package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhg implements Runnable {
    private final /* synthetic */ long zzba;
    private final /* synthetic */ zzgp zzpt;

    zzhg(zzgp zzgpVar, long j) {
        this.zzpt = zzgpVar;
        this.zzba = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzpt.zzac().zzlv.set(this.zzba);
        this.zzpt.zzab().zzgr().zza("Session timeout duration set", Long.valueOf(this.zzba));
    }
}
