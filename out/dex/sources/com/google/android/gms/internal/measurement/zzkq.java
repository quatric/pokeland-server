package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkq implements zzdb<zzkp> {
    private static zzkq zzasb = new zzkq();
    private final zzdb<zzkp> zzapj;

    public zzkq() {
        this(zzda.zzg(new zzks()));
    }

    private zzkq(zzdb<zzkp> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzi() {
        return ((zzkp) zzasb.get()).zzzi();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzkp get() {
        return this.zzapj.get();
    }
}
