package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzke implements zzdb<zzkd> {
    private static zzke zzars = new zzke();
    private final zzdb<zzkd> zzapj;

    public zzke() {
        this(zzda.zzg(new zzkg()));
    }

    private zzke(zzdb<zzkd> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzd() {
        return ((zzkd) zzars.get()).zzzd();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzkd get() {
        return this.zzapj.get();
    }
}
