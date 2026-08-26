package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzge implements zzgb {
    zzge() {
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final int zzb(int i, Object obj, Object obj2) {
        zzgc zzgcVar = (zzgc) obj;
        if (zzgcVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzgcVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final Object zzb(Object obj, Object obj2) {
        zzgc zzgcVarZzvm = (zzgc) obj;
        zzgc zzgcVar = (zzgc) obj2;
        if (!zzgcVar.isEmpty()) {
            if (!zzgcVarZzvm.isMutable()) {
                zzgcVarZzvm = zzgcVarZzvm.zzvm();
            }
            zzgcVarZzvm.zza(zzgcVar);
        }
        return zzgcVarZzvm;
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final Map<?, ?> zzm(Object obj) {
        return (zzgc) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final Map<?, ?> zzn(Object obj) {
        return (zzgc) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final boolean zzo(Object obj) {
        return !((zzgc) obj).isMutable();
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final Object zzp(Object obj) {
        ((zzgc) obj).zzry();
        return obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final Object zzq(Object obj) {
        return zzgc.zzvl().zzvm();
    }

    @Override // com.google.android.gms.internal.measurement.zzgb
    public final zzfz<?, ?> zzr(Object obj) {
        throw new NoSuchMethodError();
    }
}
