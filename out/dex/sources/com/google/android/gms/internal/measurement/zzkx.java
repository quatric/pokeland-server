package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkx implements zzdb<zzla> {
    private static zzkx zzasi = new zzkx();
    private final zzdb<zzla> zzapj;

    public zzkx() {
        this(zzda.zzg(new zzkz()));
    }

    private zzkx(zzdb<zzla> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzo() {
        return ((zzla) zzasi.get()).zzzo();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzla get() {
        return this.zzapj.get();
    }
}
