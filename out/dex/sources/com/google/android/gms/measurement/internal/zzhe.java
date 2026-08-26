package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhe implements Runnable {
    private final /* synthetic */ boolean zzaz;
    private final /* synthetic */ zzgp zzpt;

    zzhe(zzgp zzgpVar, boolean z) {
        this.zzpt = zzgpVar;
        this.zzaz = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zIsEnabled = this.zzpt.zzj.isEnabled();
        boolean zZzib = this.zzpt.zzj.zzib();
        this.zzpt.zzj.zza(this.zzaz);
        if (zZzib == this.zzaz) {
            this.zzpt.zzj.zzab().zzgs().zza("Default data collection state already set to", Boolean.valueOf(this.zzaz));
        }
        if (this.zzpt.zzj.isEnabled() == zIsEnabled || this.zzpt.zzj.isEnabled() != this.zzpt.zzj.zzib()) {
            this.zzpt.zzj.zzab().zzgp().zza("Default data collection is different than actual status", Boolean.valueOf(this.zzaz), Boolean.valueOf(zIsEnabled));
        }
        this.zzpt.zzil();
    }
}
