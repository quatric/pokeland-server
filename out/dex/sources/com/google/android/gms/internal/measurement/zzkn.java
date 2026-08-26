package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzkn implements zzko {
    private static final zzcm<Boolean> zzasa = new zzct(zzcn.zzdh("com.google.android.gms.measurement")).zzb("measurement.collection.firebase_global_collection_flag_enabled", true);

    @Override // com.google.android.gms.internal.measurement.zzko
    public final boolean zzzh() {
        return zzasa.get().booleanValue();
    }
}
