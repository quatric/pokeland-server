package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkg implements zzkd {
    private static final zzcm<Boolean> zzaru = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.collection.event_safelist", false);

    @Override // com.google.android.gms.internal.measurement.zzkd
    public final boolean zzzd() {
        return zzaru.get().booleanValue();
    }
}
