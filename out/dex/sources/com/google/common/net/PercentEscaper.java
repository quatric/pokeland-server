package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.UnicodeEscaper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public final class PercentEscaper extends UnicodeEscaper {
    private static final char[] PLUS_SIGN = {'+'};
    private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    public PercentEscaper(String str, boolean z) {
        Preconditions.checkNotNull(str);
        if (str.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        String str2 = str + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (z && str2.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = z;
        this.safeOctets = createSafeOctets(str2);
    }

    private static boolean[] createSafeOctets(String str) {
        char[] charArray = str.toCharArray();
        int iMax = -1;
        for (char c : charArray) {
            iMax = Math.max((int) c, iMax);
        }
        boolean[] zArr = new boolean[iMax + 1];
        for (char c2 : charArray) {
            zArr[c2] = true;
        }
        return zArr;
    }

    @Override // com.google.common.escape.UnicodeEscaper, com.google.common.escape.Escaper
    public String escape(String str) {
        Preconditions.checkNotNull(str);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            boolean[] zArr = this.safeOctets;
            if (cCharAt >= zArr.length || !zArr[cCharAt]) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    @Override // com.google.common.escape.UnicodeEscaper
    protected char[] escape(int i) {
        boolean[] zArr = this.safeOctets;
        if (i < zArr.length && zArr[i]) {
            return null;
        }
        if (i == 32 && this.plusForSpace) {
            return PLUS_SIGN;
        }
        if (i <= 127) {
            char[] cArr = UPPER_HEX_DIGITS;
            return new char[]{'%', cArr[i >>> 4], cArr[i & 15]};
        }
        if (i <= 2047) {
            char[] cArr2 = UPPER_HEX_DIGITS;
            char[] cArr3 = {'%', cArr2[(i >>> 4) | 12], cArr2[i & 15], '%', cArr2[(i & 3) | 8], cArr2[i & 15]};
            int i2 = i >>> 4;
            int i3 = i2 >>> 2;
            return cArr3;
        }
        if (i <= 65535) {
            char[] cArr4 = UPPER_HEX_DIGITS;
            char[] cArr5 = {'%', 'E', cArr4[i >>> 2], '%', cArr4[(i & 3) | 8], cArr4[i & 15], '%', cArr4[(i & 3) | 8], cArr4[i & 15]};
            int i4 = i >>> 4;
            int i5 = i4 >>> 2;
            int i6 = i5 >>> 4;
            return cArr5;
        }
        if (i > 1114111) {
            throw new IllegalArgumentException("Invalid unicode character value " + i);
        }
        char[] cArr6 = UPPER_HEX_DIGITS;
        char[] cArr7 = {'%', 'F', cArr6[(i >>> 2) & 7], '%', cArr6[(i & 3) | 8], cArr6[i & 15], '%', cArr6[(i & 3) | 8], cArr6[i & 15], '%', cArr6[(i & 3) | 8], cArr6[i & 15]};
        int i7 = i >>> 4;
        int i8 = i7 >>> 2;
        int i9 = i8 >>> 4;
        int i10 = i9 >>> 2;
        int i11 = i10 >>> 4;
        return cArr7;
    }

    @Override // com.google.common.escape.UnicodeEscaper
    protected int nextEscapeIndex(CharSequence charSequence, int i, int i2) {
        Preconditions.checkNotNull(charSequence);
        while (i < i2) {
            char cCharAt = charSequence.charAt(i);
            boolean[] zArr = this.safeOctets;
            if (cCharAt >= zArr.length || !zArr[cCharAt]) {
                break;
            }
            i++;
        }
        return i;
    }
}
