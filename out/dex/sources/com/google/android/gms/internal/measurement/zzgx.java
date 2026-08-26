package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
interface zzgx<T> {
    boolean equals(T t, T t2);

    int hashCode(T t);

    T newInstance();

    void zza(T t, zzgy zzgyVar, zzel zzelVar) throws IOException;

    void zza(T t, zzim zzimVar) throws IOException;

    void zza(T t, byte[] bArr, int i, int i2, zzdk zzdkVar) throws IOException;

    void zzc(T t, T t2);

    void zzj(T t);

    int zzt(T t);

    boolean zzv(T t);
}
