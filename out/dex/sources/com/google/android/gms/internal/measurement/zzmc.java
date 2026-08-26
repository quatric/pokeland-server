package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzmc implements zzlz {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzatm;
    private static final zzcm<Boolean> zzatn;
    private static final zzcm<Boolean> zzato;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzatm = zzctVar.zzb("measurement.service.sessions.remove_disabled_session_number", false);
        zzatn = zzctVar.zzb("measurement.service.sessions.session_number_enabled", false);
        zzato = zzctVar.zzb("measurement.service.sessions.session_number_backfill_enabled", false);
        zzapw = zzctVar.zze("measurement.id.session_number", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzlz
    public final boolean zzaaf() {
        return zzatm.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlz
    public final boolean zzaag() {
        return zzatn.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlz
    public final boolean zzaah() {
        return zzato.get().booleanValue();
    }
}
