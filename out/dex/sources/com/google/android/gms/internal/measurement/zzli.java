package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzli implements zzdb<zzlh> {
    private static zzli zzasu = new zzli();
    private final zzdb<zzlh> zzapj;

    public zzli() {
        this(zzda.zzg(new zzlk()));
    }

    private zzli(zzdb<zzlh> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzv() {
        return ((zzlh) zzasu.get()).zzzv();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzlh get() {
        return this.zzapj.get();
    }
}
