package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkk implements zzdb<zzkj> {
    private static zzkk zzarw = new zzkk();
    private final zzdb<zzkj> zzapj;

    public zzkk() {
        this(zzda.zzg(new zzkm()));
    }

    private zzkk(zzdb<zzkj> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzf() {
        return ((zzkj) zzarw.get()).zzzf();
    }

    public static boolean zzzg() {
        return ((zzkj) zzarw.get()).zzzg();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzkj get() {
        return this.zzapj.get();
    }
}
