package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkl implements zzdb<zzko> {
    private static zzkl zzarx = new zzkl();
    private final zzdb<zzko> zzapj;

    public zzkl() {
        this(zzda.zzg(new zzkn()));
    }

    private zzkl(zzdb<zzko> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzh() {
        return ((zzko) zzarx.get()).zzzh();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzko get() {
        return this.zzapj.get();
    }
}
