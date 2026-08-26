package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlr implements zzls {
    private static final zzcm<Boolean> zzatb = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.sampling.time_zone_offset_enabled", true);

    @Override // com.google.android.gms.internal.measurement.zzls
    public final boolean zzzy() {
        return zzatb.get().booleanValue();
    }
}
