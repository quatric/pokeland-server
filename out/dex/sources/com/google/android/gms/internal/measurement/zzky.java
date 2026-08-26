package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzky implements zzkv {
    private static final zzcm<Boolean> zzasj;
    private static final zzcm<Boolean> zzask;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzasj = zzctVar.zzb("measurement.personalized_ads_signals_collection_enabled", true);
        zzask = zzctVar.zzb("measurement.personalized_ads_property_translation_enabled", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzkv
    public final boolean zzzm() {
        return zzasj.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzkv
    public final boolean zzzn() {
        return zzask.get().booleanValue();
    }
}
