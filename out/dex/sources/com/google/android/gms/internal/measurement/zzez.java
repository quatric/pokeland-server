package com.google.android.gms.internal.measurement;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzez {
    public static final byte[] zzair;
    private static final ByteBuffer zzais;
    private static final zzeb zzait;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    static {
        byte[] bArr = new byte[0];
        zzair = bArr;
        zzais = ByteBuffer.wrap(bArr);
        byte[] bArr2 = zzair;
        zzait = zzeb.zza(bArr2, 0, bArr2.length, false);
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int iZza = zza(length, bArr, 0, length);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 = (i4 * 31) + bArr[i5];
        }
        return i4;
    }

    static Object zza(Object obj, Object obj2) {
        return ((zzgi) obj).zzuo().zza((zzgi) obj2).zzuf();
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static int zzbx(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static boolean zzf(zzgi zzgiVar) {
        return false;
    }

    public static boolean zzh(byte[] bArr) {
        return zzhy.zzh(bArr);
    }

    public static String zzi(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static int zzs(boolean z) {
        return z ? 1231 : 1237;
    }
}
