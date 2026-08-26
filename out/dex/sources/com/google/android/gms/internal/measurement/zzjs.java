package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjs implements zzdb<zzjr> {
    private static zzjs zzarh = new zzjs();
    private final zzdb<zzjr> zzapj;

    public zzjs() {
        this(zzda.zzg(new zzju()));
    }

    private zzjs(zzdb<zzjr> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzyx() {
        return ((zzjr) zzarh.get()).zzyx();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzjr get() {
        return this.zzapj.get();
    }
}
