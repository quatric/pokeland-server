package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzld implements zzdb<zzlg> {
    private static zzld zzasn = new zzld();
    private final zzdb<zzlg> zzapj;

    public zzld() {
        this(zzda.zzg(new zzlf()));
    }

    private zzld(zzdb<zzlg> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzq() {
        return ((zzlg) zzasn.get()).zzzq();
    }

    public static double zzzr() {
        return ((zzlg) zzasn.get()).zzzr();
    }

    public static long zzzs() {
        return ((zzlg) zzasn.get()).zzzs();
    }

    public static long zzzt() {
        return ((zzlg) zzasn.get()).zzzt();
    }

    public static String zzzu() {
        return ((zzlg) zzasn.get()).zzzu();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzlg get() {
        return this.zzapj.get();
    }
}
