package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkb implements zzkc {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzaro;
    private static final zzcm<Boolean> zzarp;
    private static final zzcm<Boolean> zzarq;
    private static final zzcm<Boolean> zzarr;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzaro = zzctVar.zzb("measurement.sdk.dynamite.allow_remote_dynamite", false);
        zzarp = zzctVar.zzb("measurement.collection.init_params_control_enabled", true);
        zzarq = zzctVar.zzb("measurement.sdk.dynamite.use_dynamite", false);
        zzarr = zzctVar.zzb("measurement.sdk.dynamite.use_dynamite2", false);
        zzapw = zzctVar.zze("measurement.id.sdk.dynamite.use_dynamite", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    public final boolean zzza() {
        return zzaro.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    public final boolean zzzb() {
        return zzarp.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzkc
    public final boolean zzzc() {
        return zzarq.get().booleanValue();
    }
}
