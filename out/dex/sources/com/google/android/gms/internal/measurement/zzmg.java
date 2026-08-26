package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzmg implements zzdb<zzmf> {
    private static zzmg zzatu = new zzmg();
    private final zzdb<zzmf> zzapj;

    public zzmg() {
        this(zzda.zzg(new zzmi()));
    }

    private zzmg(zzdb<zzmf> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzaam() {
        return ((zzmf) zzatu.get()).zzaam();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzmf get() {
        return this.zzapj.get();
    }
}
