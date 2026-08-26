package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzda {
    public static <T> zzdb<T> zza(zzdb<T> zzdbVar) {
        if ((zzdbVar instanceof zzdc) || (zzdbVar instanceof zzdd)) {
            return zzdbVar;
        }
        return zzdbVar instanceof Serializable ? new zzdd(zzdbVar) : new zzdc(zzdbVar);
    }

    public static <T> zzdb<T> zzg(@NullableDecl T t) {
        return new zzde(t);
    }
}
