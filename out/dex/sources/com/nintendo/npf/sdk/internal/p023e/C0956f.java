package com.nintendo.npf.sdk.internal.p023e;

import com.google.common.base.Ascii;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.e.f */
/* JADX INFO: compiled from: TOTP.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0956f {

    /* JADX INFO: renamed from: a */
    private static final int[] f1287a = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    /* JADX INFO: renamed from: a */
    public static String m1397a(byte[] bArr, int i, int i2, String str) {
        String upperCase = Long.toHexString((Calendar.getInstance().getTimeInMillis() / 1000) / ((long) i)).toUpperCase();
        while (upperCase.length() < 16) {
            upperCase = "0" + upperCase;
        }
        byte[] bArrM1399a = m1399a(str, bArr, m1398a(upperCase));
        int i3 = bArrM1399a[bArrM1399a.length - 1] & Ascii.f291SI;
        String string = Integer.toString(((bArrM1399a[i3 + 3] & 255) | ((((bArrM1399a[i3] & 127) << 24) | ((bArrM1399a[i3 + 1] & 255) << 16)) | ((bArrM1399a[i3 + 2] & 255) << 8))) % f1287a[i2]);
        while (string.length() < i2) {
            string = "0" + string;
        }
        return string;
    }

    /* JADX INFO: renamed from: a */
    private static byte[] m1398a(String str) {
        byte[] byteArray = new BigInteger("10" + str, 16).toByteArray();
        byte[] bArr = new byte[byteArray.length - 1];
        System.arraycopy(byteArray, 1, bArr, 0, bArr.length);
        return bArr;
    }

    /* JADX INFO: renamed from: a */
    private static byte[] m1399a(String str, byte[] bArr, byte[] bArr2) {
        try {
            Mac mac = Mac.getInstance(str);
            mac.init(new SecretKeySpec(bArr, "RAW"));
            return mac.doFinal(bArr2);
        } catch (GeneralSecurityException e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
