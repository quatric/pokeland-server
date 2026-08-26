package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjt implements zzdb<zzjw> {
    private static zzjt zzari = new zzjt();
    private final zzdb<zzjw> zzapj;

    public zzjt() {
        this(zzda.zzg(new zzjv()));
    }

    private zzjt(zzdb<zzjw> zzdbVar) {
        this.zzapj = zzda.zza(zzdbVar);
    }

    public static boolean zzyy() {
        return ((zzjw) zzari.get()).zzyy();
    }

    @Override // com.google.android.gms.internal.measurement.zzdb
    public final /* synthetic */ zzjw get() {
        return this.zzapj.get();
    }
}
