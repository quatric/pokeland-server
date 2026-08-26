package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzfx implements zzgf {
    private zzgf[] zzaka;

    zzfx(zzgf... zzgfVarArr) {
        this.zzaka = zzgfVarArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzgf
    public final boolean zza(Class<?> cls) {
        for (zzgf zzgfVar : this.zzaka) {
            if (zzgfVar.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzgf
    public final zzgg zzb(Class<?> cls) {
        for (zzgf zzgfVar : this.zzaka) {
            if (zzgfVar.zza(cls)) {
                return zzgfVar.zzb(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
