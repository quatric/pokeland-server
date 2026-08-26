package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlu implements zzdb<zzlt> {
    private static zzlu zzatc = new zzlu();
    private final zzdb<zzlt> zzapj;

    public zzlu() {
        this(zzda.zzg(new zzlw()));
    }

    private zzlu(zzdb<zzlt> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzaaa() {
        return ((zzlt) zzatc.get()).zzaaa();
    }

    public static boolean zzaab() {
        return ((zzlt) zzatc.get()).zzaab();
    }

    public static boolean zzaac() {
        return ((zzlt) zzatc.get()).zzaac();
    }

    public static boolean zzzz() {
        return ((zzlt) zzatc.get()).zzzz();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzlt get() {
        return this.zzapj.get();
    }
}
