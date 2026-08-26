package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlw implements zzlt {
    private static final zzcm<Boolean> zzate;
    private static final zzcm<Boolean> zzatf;
    private static final zzcm<Boolean> zzatg;
    private static final zzcm<Boolean> zzath;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzate = zzctVar.zzb("measurement.service.audience.scoped_filters_v27", false);
        zzatf = zzctVar.zzb("measurement.service.audience.session_scoped_user_engagement", false);
        zzatg = zzctVar.zzb("measurement.service.audience.session_scoped_event_aggregates", false);
        zzath = zzctVar.zzb("measurement.service.audience.remove_disabled_session_scoped_user_engagement", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final boolean zzaaa() {
        return zzatf.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final boolean zzaab() {
        return zzatg.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final boolean zzaac() {
        return zzath.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlt
    public final boolean zzzz() {
        return zzate.get().booleanValue();
    }
}
