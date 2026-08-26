package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final /* synthetic */ class zzes {
    static final /* synthetic */ int[] zzafe;
    static final /* synthetic */ int[] zzaff = new int[zzfk.values().length];

    static {
        try {
            zzaff[zzfk.BYTE_STRING.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            zzaff[zzfk.MESSAGE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            zzaff[zzfk.STRING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        zzafe = new int[zzev.values().length];
        try {
            zzafe[zzev.MAP.ordinal()] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            zzafe[zzev.VECTOR.ordinal()] = 2;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            zzafe[zzev.SCALAR.ordinal()] = 3;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
