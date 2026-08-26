package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlk implements zzlh {
    private static final zzcm<Boolean> zzasw = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.collection.log_event_and_bundle_v2", true);

    @Override // com.google.android.gms.internal.measurement.zzlh
    public final boolean zzzv() {
        return zzasw.get().booleanValue();
    }
}
