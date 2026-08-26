package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzmi implements zzmf {
    private static final zzcm<Boolean> zzatw = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.sdk.collection.retrieve_deeplink_from_bow", false);

    @Override // com.google.android.gms.internal.measurement.zzmf
    public final boolean zzaam() {
        return zzatw.get().booleanValue();
    }
}
