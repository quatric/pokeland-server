package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjz implements zzdb<zzkc> {
    private static zzjz zzarm = new zzjz();
    private final zzdb<zzkc> zzapj;

    public zzjz() {
        this(zzda.zzg(new zzkb()));
    }

    private zzjz(zzdb<zzkc> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzza() {
        return ((zzkc) zzarm.get()).zzza();
    }

    public static boolean zzzb() {
        return ((zzkc) zzarm.get()).zzzb();
    }

    public static boolean zzzc() {
        return ((zzkc) zzarm.get()).zzzc();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzkc get() {
        return this.zzapj.get();
    }
}
