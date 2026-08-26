package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzjj implements zzjk {
    private static final zzcm<Boolean> zzapq;
    private static final zzcm<Boolean> zzapr;
    private static final zzcm<Boolean> zzaps;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzapq = zzctVar.zzb("measurement.log_installs_enabled", false);
        zzapr = zzctVar.zzb("measurement.log_third_party_store_events_enabled", false);
        zzaps = zzctVar.zzb("measurement.log_upgrades_enabled", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzjk
    public final boolean zzxj() {
        return zzapq.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjk
    public final boolean zzxk() {
        return zzapr.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzjk
    public final boolean zzxl() {
        return zzaps.get().booleanValue();
    }
}
