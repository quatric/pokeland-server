package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlq implements zzln {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzata;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzata = zzctVar.zzb("measurement.reset_analytics.persist_time", false);
        zzapw = zzctVar.zze("measurement.id.reset_analytics.persist_time", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzln
    public final boolean zzzx() {
        return zzata.get().booleanValue();
    }
}
