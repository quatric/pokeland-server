package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzmj implements zzmk {
    private static final zzcm<Boolean> zzatx = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.upload.file_lock_state_check", false);

    @Override // com.google.android.gms.internal.measurement.zzmk
    public final boolean zzaan() {
        return zzatx.get().booleanValue();
    }
}
