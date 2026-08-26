package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzew implements zzgf {
    private static final zzew zzahu = new zzew();

    private zzew() {
    }

    public static zzew zzua() {
        return zzahu;
    }

    @Override // com.google.android.gms.internal.measurement.zzgf
    public final boolean zza(Class<?> cls) {
        return zzey.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.measurement.zzgf
    public final zzgg zzb(Class<?> cls) {
        if (!zzey.class.isAssignableFrom(cls)) {
            String strValueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unsupported message type: ".concat(strValueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzgg) zzey.zzd(cls.asSubclass(zzey.class)).zza(zzey.zzd.zzaif, (Object) null, (Object) null);
        } catch (Exception e) {
            String strValueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(strValueOf2.length() != 0 ? "Unable to get message info for ".concat(strValueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
