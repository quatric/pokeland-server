package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlc implements zzdb<zzlb> {
    private static zzlc zzasm = new zzlc();
    private final zzdb<zzlb> zzapj;

    public zzlc() {
        this(zzda.zzg(new zzle()));
    }

    private zzlc(zzdb<zzlb> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzp() {
        return ((zzlb) zzasm.get()).zzzp();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzlb get() {
        return this.zzapj.get();
    }
}
