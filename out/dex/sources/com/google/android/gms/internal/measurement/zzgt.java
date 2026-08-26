package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzgt {
    private static final zzgt zzalc = new zzgt();
    private final ConcurrentMap<Class<?>, zzgx<?>> zzale = new ConcurrentHashMap();
    private final zzha zzald = new zzfv();

    private zzgt() {
    }

    public static zzgt zzvy() {
        return zzalc;
    }

    public final <T> zzgx<T> zzf(Class<T> cls) {
        zzez.zza(cls, "messageType");
        zzgx<T> zzgxVar = (zzgx) this.zzale.get(cls);
        if (zzgxVar != null) {
            return zzgxVar;
        }
        zzgx<T> zzgxVarZze = this.zzald.zze(cls);
        zzez.zza(cls, "messageType");
        zzez.zza(zzgxVarZze, "schema");
        zzgx<T> zzgxVar2 = (zzgx) this.zzale.putIfAbsent(cls, zzgxVarZze);
        return zzgxVar2 != null ? zzgxVar2 : zzgxVarZze;
    }

    public final <T> zzgx<T> zzw(T t) {
        return zzf(t.getClass());
    }
}
