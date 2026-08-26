package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzja implements zzdb<zziz> {
    private static zzja zzapi = new zzja();
    private final zzdb<zziz> zzapj;

    public zzja() {
        this(zzda.zzg(new zzjc()));
    }

    private zzja(zzdb<zziz> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzxg() {
        return ((zziz) zzapi.get()).zzxg();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zziz get() {
        return this.zzapj.get();
    }
}
