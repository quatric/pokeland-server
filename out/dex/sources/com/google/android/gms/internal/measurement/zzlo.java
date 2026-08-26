package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlo implements zzdb<zzln> {
    private static zzlo zzasy = new zzlo();
    private final zzdb<zzln> zzapj;

    public zzlo() {
        this(zzda.zzg(new zzlq()));
    }

    private zzlo(zzdb<zzln> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzx() {
        return ((zzln) zzasy.get()).zzzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzln get() {
        return this.zzapj.get();
    }
}
