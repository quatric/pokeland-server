package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgv implements Runnable {
    private final /* synthetic */ zzgp zzpt;
    private final /* synthetic */ long zzqf;

    zzgv(zzgp zzgpVar, long j) {
        this.zzpt = zzgpVar;
        this.zzqf = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzgp zzgpVar = this.zzpt;
        long j = this.zzqf;
        zzgpVar.zzo();
        zzgpVar.zzm();
        zzgpVar.zzbi();
        zzgpVar.zzab().zzgr().zzao("Resetting analytics data (FE)");
        zzgpVar.zzv().zziz();
        if (zzgpVar.zzad().zzr(zzgpVar.zzr().zzag())) {
            zzgpVar.zzac().zzlo.set(j);
        }
        boolean zIsEnabled = zzgpVar.zzj.isEnabled();
        if (!zzgpVar.zzad().zzbp()) {
            zzgpVar.zzac().zzf(!zIsEnabled);
        }
        zzgpVar.zzs().resetAnalyticsData();
        zzgpVar.zzpz = !zIsEnabled;
        this.zzpt.zzs().zza(new AtomicReference<>());
    }
}
