package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public enum zzfk {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, 0),
    LONG(Long.TYPE, Long.class, 0L),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.TYPE, Boolean.class, false),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzdp.class, zzdp.class, zzdp.zzadh),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);

    private final Class<?> zzajh;
    private final Class<?> zzaji;
    private final Object zzajj;

    zzfk(Class cls, Class cls2, Object obj) {
        this.zzajh = cls;
        this.zzaji = cls2;
        this.zzajj = obj;
    }

    public final Class<?> zzvd() {
        return this.zzaji;
    }
}
