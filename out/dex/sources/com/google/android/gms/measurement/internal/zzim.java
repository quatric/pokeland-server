package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzim implements Runnable {
    private final /* synthetic */ zzdx zzrr;
    private final /* synthetic */ zzin zzrs;

    zzim(zzin zzinVar, zzdx zzdxVar) {
        this.zzrs = zzinVar;
        this.zzrr = zzdxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzrs) {
            zzin.zza(this.zzrs, false);
            if (!this.zzrs.zzrd.isConnected()) {
                this.zzrs.zzrd.zzab().zzgs().zzao("Connected to service");
                this.zzrs.zzrd.zza(this.zzrr);
            }
        }
    }
}
