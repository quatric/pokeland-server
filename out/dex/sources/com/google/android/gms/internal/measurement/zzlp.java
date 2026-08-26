package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlp implements zzdb<zzls> {
    private static zzlp zzasz = new zzlp();
    private final zzdb<zzls> zzapj;

    public zzlp() {
        this(zzda.zzg(new zzlr()));
    }

    private zzlp(zzdb<zzls> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzy() {
        return ((zzls) zzasz.get()).zzzy();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzls get() {
        return this.zzapj.get();
    }
}
