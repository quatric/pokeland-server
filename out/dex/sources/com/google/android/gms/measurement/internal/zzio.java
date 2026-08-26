package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzio implements Runnable {
    private final /* synthetic */ zzin zzrs;
    private final /* synthetic */ zzdx zzrv;

    zzio(zzin zzinVar, zzdx zzdxVar) {
        this.zzrs = zzinVar;
        this.zzrv = zzdxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzrs) {
            zzin.zza(this.zzrs, false);
            if (!this.zzrs.zzrd.isConnected()) {
                this.zzrs.zzrd.zzab().zzgr().zzao("Connected to remote service");
                this.zzrs.zzrd.zza(this.zzrv);
            }
        }
    }
}
