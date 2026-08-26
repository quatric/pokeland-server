package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjv implements zzjw {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzark;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzark = zzctVar.zzb("measurement.upload_dsid_enabled", false);
        zzapw = zzctVar.zze("measurement.id.upload_dsid_enabled", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzjw
    public final boolean zzyy() {
        return zzark.get().booleanValue();
    }
}
