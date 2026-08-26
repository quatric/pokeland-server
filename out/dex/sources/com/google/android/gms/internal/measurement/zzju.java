package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzju implements zzjr {
    private static final zzcm<Boolean> zzarj = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.upload.disable_is_uploader", true);

    @Override // com.google.android.gms.internal.measurement.zzjr
    public final boolean zzyx() {
        return zzarj.get().booleanValue();
    }
}
