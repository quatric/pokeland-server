package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjm implements zzdb<zzjl> {
    private static zzjm zzapt = new zzjm();
    private final zzdb<zzjl> zzapj;

    public zzjm() {
        this(zzda.zzg(new zzjo()));
    }

    private zzjm(zzdb<zzjl> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzxm() {
        return ((zzjl) zzapt.get()).zzxm();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzjl get() {
        return this.zzapj.get();
    }
}
