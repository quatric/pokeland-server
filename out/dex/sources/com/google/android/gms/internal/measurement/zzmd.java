package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzmd implements zzme {
    private static final zzcm<Long> zzapw;
    private static final zzcm<Boolean> zzatp;
    private static final zzcm<Boolean> zzatq;
    private static final zzcm<Boolean> zzatr;
    private static final zzcm<Boolean> zzats;
    private static final zzcm<Boolean> zzatt;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzatp = zzctVar.zzb("measurement.client.sessions.background_sessions_enabled", true);
        zzatq = zzctVar.zzb("measurement.client.sessions.immediate_start_enabled_foreground", false);
        zzatr = zzctVar.zzb("measurement.client.sessions.immediate_start_enabled", false);
        zzats = zzctVar.zzb("measurement.client.sessions.remove_expired_session_properties_enabled", true);
        zzatt = zzctVar.zzb("measurement.client.sessions.session_id_enabled", true);
        zzapw = zzctVar.zze("measurement.id.sessionization_client", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzme
    public final boolean zzaai() {
        return zzatp.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzme
    public final boolean zzaaj() {
        return zzatq.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzme
    public final boolean zzaak() {
        return zzats.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzme
    public final boolean zzaal() {
        return zzatt.get().booleanValue();
    }
}
