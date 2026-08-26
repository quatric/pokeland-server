package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzks implements zzkp {
    private static final zzcm<Boolean> zzasd = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.referrer.enable_logging_install_referrer_cmp_from_apk", false);

    @Override // com.google.android.gms.internal.measurement.zzkp
    public final boolean zzzi() {
        return zzasd.get().booleanValue();
    }
}
