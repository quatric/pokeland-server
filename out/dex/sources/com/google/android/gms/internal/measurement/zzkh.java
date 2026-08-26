package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkh implements zzki {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzarv;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzarv = zzctVar.zzb("measurement.fetch_config_with_admob_app_id", true);
        zzapw = zzctVar.zze("measurement.id.fetch_config_with_admob_app_id", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzki
    public final boolean zzze() {
        return zzarv.get().booleanValue();
    }
}
