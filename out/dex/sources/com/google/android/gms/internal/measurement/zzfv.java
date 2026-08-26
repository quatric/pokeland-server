package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfv implements zzha {
    private static final zzgf zzajx = new zzfy();
    private final zzgf zzajw;

    public zzfv() {
        this(new zzfx(zzew.zzua(), zzvj()));
    }

    private zzfv(zzgf zzgfVar) {
        this.zzajw = (zzgf) zzez.zza(zzgfVar, "messageInfoFactory");
    }

    private static boolean zza(zzgg zzggVar) {
        return zzggVar.zzvr() == zzey.zzd.zzail;
    }

    private static zzgf zzvj() {
        try {
            return (zzgf) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzajx;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzha
    public final <T> zzgx<T> zze(Class<T> cls) {
        zzgz.zzg(cls);
        zzgg zzggVarZzb = this.zzajw.zzb(cls);
        if (zzggVarZzb.zzvs()) {
            return zzey.class.isAssignableFrom(cls) ? zzgo.zza(zzgz.zzwe(), zzep.zztu(), zzggVarZzb.zzvt()) : zzgo.zza(zzgz.zzwc(), zzep.zztv(), zzggVarZzb.zzvt());
        }
        if (zzey.class.isAssignableFrom(cls)) {
            return zza(zzggVarZzb) ? zzgm.zza(cls, zzggVarZzb, zzgs.zzvw(), zzfs.zzvi(), zzgz.zzwe(), zzep.zztu(), zzgd.zzvp()) : zzgm.zza(cls, zzggVarZzb, zzgs.zzvw(), zzfs.zzvi(), zzgz.zzwe(), (zzen<?>) null, zzgd.zzvp());
        }
        return zza(zzggVarZzb) ? zzgm.zza(cls, zzggVarZzb, zzgs.zzvv(), zzfs.zzvh(), zzgz.zzwc(), zzep.zztv(), zzgd.zzvo()) : zzgm.zza(cls, zzggVarZzb, zzgs.zzvv(), zzfs.zzvh(), zzgz.zzwd(), (zzen<?>) null, zzgd.zzvo());
    }
}
