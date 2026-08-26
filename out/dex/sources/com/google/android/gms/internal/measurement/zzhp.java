package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zzhp<T, B> {
    zzhp() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzdp zzdpVar);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzim zzimVar) throws IOException;

    abstract boolean zza(zzgy zzgyVar);

    final boolean zza(B b, zzgy zzgyVar) throws IOException {
        int tag = zzgyVar.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza(b, i, zzgyVar.zzsi());
            return true;
        }
        if (i2 == 1) {
            zzb(b, i, zzgyVar.zzsk());
            return true;
        }
        if (i2 == 2) {
            zza((Object) b, i, zzgyVar.zzso());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzfi.zzuy();
            }
            zzc(b, i, zzgyVar.zzsl());
            return true;
        }
        B bZzwp = zzwp();
        int i3 = 4 | (i << 3);
        while (zzgyVar.zzsy() != Integer.MAX_VALUE && zza(bZzwp, zzgyVar)) {
        }
        if (i3 != zzgyVar.getTag()) {
            throw zzfi.zzux();
        }
        zza(b, i, zzp(bZzwp));
        return true;
    }

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzim zzimVar) throws IOException;

    abstract void zze(Object obj, T t);

    abstract void zzf(Object obj, B b);

    abstract T zzg(T t, T t2);

    abstract void zzj(Object obj);

    abstract T zzp(B b);

    abstract int zzt(T t);

    abstract B zzwp();

    abstract T zzx(Object obj);

    abstract B zzy(Object obj);

    abstract int zzz(T t);
}
