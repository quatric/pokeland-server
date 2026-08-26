package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjy implements zzdb<zzjx> {
    private static zzjy zzarl = new zzjy();
    private final zzdb<zzjx> zzapj;

    public zzjy() {
        this(zzda.zzg(new zzka()));
    }

    private zzjy(zzdb<zzjx> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzyz() {
        return ((zzjx) zzarl.get()).zzyz();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzjx get() {
        return this.zzapj.get();
    }
}
