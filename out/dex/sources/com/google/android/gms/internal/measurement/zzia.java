package com.google.android.gms.internal.measurement;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.common.base.Ascii;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzia {
    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws zzfi {
        if (zzg(b2) || (((b << Ascii.f285FS) + (b2 + 112)) >> 30) != 0 || zzg(b3) || zzg(b4)) {
            throw zzfi.zzvb();
        }
        int i2 = ((b & 7) << 18) | ((b2 & 63) << 12) | ((b3 & 63) << 6) | (b4 & 63);
        cArr[i] = (char) ((i2 >>> 10) + 55232);
        cArr[i + 1] = (char) ((i2 & 1023) + GeneratorBase.SURR2_FIRST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(byte b, byte b2, byte b3, char[] cArr, int i) throws zzfi {
        if (zzg(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || zzg(b3)))) {
            throw zzfi.zzvb();
        }
        cArr[i] = (char) (((b & Ascii.f291SI) << 12) | ((b2 & 63) << 6) | (b3 & 63));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(byte b, byte b2, char[] cArr, int i) throws zzfi {
        if (b < -62 || zzg(b2)) {
            throw zzfi.zzvb();
        }
        cArr[i] = (char) (((b & Ascii.f294US) << 6) | (b2 & 63));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(byte b, char[] cArr, int i) {
        cArr[i] = (char) b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzd(byte b) {
        return b >= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zze(byte b) {
        return b < -32;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzf(byte b) {
        return b < -16;
    }

    private static boolean zzg(byte b) {
        return b > -65;
    }
}
