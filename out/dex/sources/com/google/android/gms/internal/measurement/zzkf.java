package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkf implements zzdb<zzki> {
    private static zzkf zzart = new zzkf();
    private final zzdb<zzki> zzapj;

    public zzkf() {
        this(zzda.zzg(new zzkh()));
    }

    private zzkf(zzdb<zzki> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzze() {
        return ((zzki) zzart.get()).zzze();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzki get() {
        return this.zzapj.get();
    }
}
