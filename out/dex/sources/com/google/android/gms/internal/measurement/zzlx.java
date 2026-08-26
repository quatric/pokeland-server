package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlx implements zzly {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzati;
    private static final zzcm<Boolean> zzatj;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzati = zzctVar.zzb("measurement.audience.sequence_filters", false);
        zzatj = zzctVar.zzb("measurement.audience.sequence_filters_bundle_timestamp", false);
        zzapw = zzctVar.zze("measurement.id.audience.sequence_filters", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzly
    public final boolean zzaad() {
        return zzati.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzly
    public final boolean zzaae() {
        return zzatj.get().booleanValue();
    }
}
