package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzlf implements zzlg {
    private static final zzcm<Boolean> zzasp;
    private static final zzcm<Double> zzasq;
    private static final zzcm<Long> zzasr;
    private static final zzcm<Long> zzass;
    private static final zzcm<String> zzast;

    static {
        zzct zzctVar = new zzct(zzcn.zzdh("com.google.android.gms.measurement"));
        zzasp = zzctVar.zzb("measurement.test.boolean_flag", false);
        zzasq = zzctVar.zza("measurement.test.double_flag", -3.0d);
        zzasr = zzctVar.zze("measurement.test.int_flag", -2L);
        zzass = zzctVar.zze("measurement.test.long_flag", -1L);
        zzast = zzctVar.zzt("measurement.test.string_flag", "---");
    }

    @Override // com.google.android.gms.internal.measurement.zzlg
    public final boolean zzzq() {
        return zzasp.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlg
    public final double zzzr() {
        return zzasq.get().doubleValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlg
    public final long zzzs() {
        return zzasr.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlg
    public final long zzzt() {
        return zzass.get().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzlg
    public final String zzzu() {
        return zzast.get();
    }
}
