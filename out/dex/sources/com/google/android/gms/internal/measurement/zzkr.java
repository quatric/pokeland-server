package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkr implements zzdb<zzku> {
    private static zzkr zzasc = new zzkr();
    private final zzdb<zzku> zzapj;

    public zzkr() {
        this(zzda.zzg(new zzkt()));
    }

    private zzkr(zzdb<zzku> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzj() {
        return ((zzku) zzasc.get()).zzzj();
    }

    public static boolean zzzk() {
        return ((zzku) zzasc.get()).zzzk();
    }

    public static boolean zzzl() {
        return ((zzku) zzasc.get()).zzzl();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzku get() {
        return this.zzapj.get();
    }
}
