package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzif extends zzaa {
    private final /* synthetic */ zzhv zzrd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzif(zzhv zzhvVar, zzgh zzghVar) {
        super(zzghVar);
        this.zzrd = zzhvVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaa
    public final void run() {
        this.zzrd.zzab().zzgn().zzao("Tasks have been queued for a long time");
    }
}
