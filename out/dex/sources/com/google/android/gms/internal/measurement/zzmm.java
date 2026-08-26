package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzmm implements zzdb<zzml> {
    private static zzmm zzaty = new zzmm();
    private final zzdb<zzml> zzapj;

    public zzmm() {
        this(zzda.zzg(new zzmn()));
    }

    private zzmm(zzdb<zzml> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzaao() {
        return ((zzml) zzaty.get()).zzaao();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzml get() {
        return this.zzapj.get();
    }
}
