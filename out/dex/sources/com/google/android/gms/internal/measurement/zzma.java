package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzma implements zzdb<zzlz> {
    private static zzma zzatk = new zzma();
    private final zzdb<zzlz> zzapj;

    public zzma() {
        this(zzda.zzg(new zzmc()));
    }

    private zzma(zzdb<zzlz> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzaaf() {
        return ((zzlz) zzatk.get()).zzaaf();
    }

    public static boolean zzaag() {
        return ((zzlz) zzatk.get()).zzaag();
    }

    public static boolean zzaah() {
        return ((zzlz) zzatk.get()).zzaah();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzlz get() {
        return this.zzapj.get();
    }
}
