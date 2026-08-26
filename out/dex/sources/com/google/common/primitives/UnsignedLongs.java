package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.util.Comparator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1;
    private static final long[] maxValueDivs = new long[37];
    private static final int[] maxValueMods = new int[37];
    private static final int[] maxSafeDigits = new int[37];

    enum LexicographicalComparator implements Comparator<long[]> {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(long[] jArr, long[] jArr2) {
            int iMin = Math.min(jArr.length, jArr2.length);
            for (int i = 0; i < iMin; i++) {
                if (jArr[i] != jArr2[i]) {
                    return UnsignedLongs.compare(jArr[i], jArr2[i]);
                }
            }
            return jArr.length - jArr2.length;
        }

        @Override // java.lang.Enum
        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }
    }

    static {
        BigInteger bigInteger = new BigInteger("10000000000000000", 16);
        for (int i = 2; i <= 36; i++) {
            long j = i;
            maxValueDivs[i] = divide(-1L, j);
            maxValueMods[i] = (int) remainder(-1L, j);
            maxSafeDigits[i] = bigInteger.toString(i).length() - 1;
        }
    }

    private UnsignedLongs() {
    }

    public static int compare(long j, long j2) {
        return Longs.compare(flip(j), flip(j2));
    }

    @CanIgnoreReturnValue
    public static long decode(String str) {
        ParseRequest parseRequestFromString = ParseRequest.fromString(str);
        try {
            return parseUnsignedLong(parseRequestFromString.rawValue, parseRequestFromString.radix);
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = new NumberFormatException("Error parsing value: " + str);
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    public static long divide(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? 0L : 1L;
        }
        if (j >= 0) {
            return j / j2;
        }
        long j3 = ((j >>> 1) / j2) << 1;
        return j3 + ((long) (compare(j - (j3 * j2), j2) < 0 ? 0 : 1));
    }

    private static long flip(long j) {
        return j ^ Long.MIN_VALUE;
    }

    public static String join(String str, long... jArr) {
        Preconditions.checkNotNull(str);
        if (jArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(jArr.length * 5);
        sb.append(toString(jArr[0]));
        for (int i = 1; i < jArr.length; i++) {
            sb.append(str);
            sb.append(toString(jArr[i]));
        }
        return sb.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long max(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long jFlip = flip(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            long jFlip2 = flip(jArr[i]);
            if (jFlip2 > jFlip) {
                jFlip = jFlip2;
            }
        }
        return flip(jFlip);
    }

    public static long min(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long jFlip = flip(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            long jFlip2 = flip(jArr[i]);
            if (jFlip2 < jFlip) {
                jFlip = jFlip2;
            }
        }
        return flip(jFlip);
    }

    private static boolean overflowInParse(long j, int i, int i2) {
        if (j < 0) {
            return true;
        }
        long[] jArr = maxValueDivs;
        if (j < jArr[i2]) {
            return false;
        }
        return j > jArr[i2] || i > maxValueMods[i2];
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str) {
        return parseUnsignedLong(str, 10);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str, int i) {
        Preconditions.checkNotNull(str);
        if (str.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        if (i < 2 || i > 36) {
            throw new NumberFormatException("illegal radix: " + i);
        }
        int i2 = maxSafeDigits[i] - 1;
        long j = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            int iDigit = Character.digit(str.charAt(i3), i);
            if (iDigit == -1) {
                throw new NumberFormatException(str);
            }
            if (i3 > i2 && overflowInParse(j, iDigit, i)) {
                throw new NumberFormatException("Too large for unsigned long: " + str);
            }
            j = (j * ((long) i)) + ((long) iDigit);
        }
        return j;
    }

    public static long remainder(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? j : j - j2;
        }
        if (j >= 0) {
            return j % j2;
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (compare(j3, j2) < 0) {
            j2 = 0;
        }
        return j3 - j2;
    }

    public static String toString(long j) {
        return toString(j, 10);
    }

    public static String toString(long j, int i) {
        Preconditions.checkArgument(i >= 2 && i <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", i);
        if (j == 0) {
            return "0";
        }
        if (j > 0) {
            return Long.toString(j, i);
        }
        char[] cArr = new char[64];
        int length = cArr.length;
        int i2 = i - 1;
        if ((i & i2) == 0) {
            int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
            do {
                length--;
                cArr[length] = Character.forDigit(((int) j) & i2, i);
                j >>>= iNumberOfTrailingZeros;
            } while (j != 0);
        } else {
            long jDivide = (i & 1) == 0 ? (j >>> 1) / ((long) (i >>> 1)) : divide(j, i);
            long j2 = i;
            length--;
            cArr[length] = Character.forDigit((int) (j - (jDivide * j2)), i);
            while (jDivide > 0) {
                length--;
                cArr[length] = Character.forDigit((int) (jDivide % j2), i);
                jDivide /= j2;
            }
        }
        return new String(cArr, length, cArr.length - length);
    }
}
