package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzji implements zzjf {
    private static final zzcm<Boolean> zzapp = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.module.collection.conditionally_omit_admob_app_id", true);

    @Override // com.google.android.gms.internal.measurement.zzjf
    public final boolean zzxi() {
        return zzapp.get().booleanValue();
    }
}
