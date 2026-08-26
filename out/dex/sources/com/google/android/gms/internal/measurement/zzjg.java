package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjg implements zzdb<zzjf> {
    private static zzjg zzapn = new zzjg();
    private final zzdb<zzjf> zzapj;

    public zzjg() {
        this(zzda.zzg(new zzji()));
    }

    private zzjg(zzdb<zzjf> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzxi() {
        return ((zzjf) zzapn.get()).zzxi();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzjf get() {
        return this.zzapj.get();
    }
}
