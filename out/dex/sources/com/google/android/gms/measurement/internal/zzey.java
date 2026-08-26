package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzey implements Runnable {
    private final /* synthetic */ zzfj zzmu;
    private final /* synthetic */ zzef zzmv;

    zzey(zzez zzezVar, zzfj zzfjVar, zzef zzefVar) {
        this.zzmu = zzfjVar;
        this.zzmv = zzefVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzmu.zzht() == null) {
            this.zzmv.zzgk().zzao("Install Referrer Reporter is null");
            return;
        }
        zzeu zzeuVarZzht = this.zzmu.zzht();
        zzeuVarZzht.zzj.zzm();
        zzeuVarZzht.zzat(zzeuVarZzht.zzj.getContext().getPackageName());
    }
}
