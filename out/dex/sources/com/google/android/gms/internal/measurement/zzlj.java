package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlj implements zzdb<zzlm> {
    private static zzlj zzasv = new zzlj();
    private final zzdb<zzlm> zzapj;

    public zzlj() {
        this(zzda.zzg(new zzll()));
    }

    private zzlj(zzdb<zzlm> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzzw() {
        return ((zzlm) zzasv.get()).zzzw();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzlm get() {
        return this.zzapj.get();
    }
}
