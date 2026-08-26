package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Comparator;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
public final class UnsignedBytes {
    public static final byte MAX_POWER_OF_TWO = -128;
    public static final byte MAX_VALUE = -1;
    private static final int UNSIGNED_MASK = 255;

    @VisibleForTesting
    static class LexicographicalComparatorHolder {
        static final String UNSAFE_COMPARATOR_NAME = LexicographicalComparatorHolder.class.getName() + "$UnsafeComparator";
        static final Comparator<byte[]> BEST_COMPARATOR = getBestComparator();

        enum PureJavaComparator implements Comparator<byte[]> {
            INSTANCE;

            @Override // java.util.Comparator
            public int compare(byte[] bArr, byte[] bArr2) {
                int iMin = Math.min(bArr.length, bArr2.length);
                for (int i = 0; i < iMin; i++) {
                    int iCompare = UnsignedBytes.compare(bArr[i], bArr2[i]);
                    if (iCompare != 0) {
                        return iCompare;
                    }
                }
                return bArr.length - bArr2.length;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "UnsignedBytes.lexicographicalComparator() (pure Java version)";
            }
        }

        @VisibleForTesting
        enum UnsafeComparator implements Comparator<byte[]> {
            INSTANCE;

            static final boolean BIG_ENDIAN = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
            static final Unsafe theUnsafe = getUnsafe();
            static final int BYTE_ARRAY_BASE_OFFSET = theUnsafe.arrayBaseOffset(byte[].class);

            static {
                if (theUnsafe.arrayIndexScale(byte[].class) != 1) {
                    throw new AssertionError();
                }
            }

            private static Unsafe getUnsafe() {
                try {
                    try {
                        return Unsafe.getUnsafe();
                    } catch (PrivilegedActionException e) {
                        throw new RuntimeException("Could not initialize intrinsics", e.getCause());
                    }
                } catch (SecurityException unused) {
                    return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.primitives.UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator.1
                        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
                        @Override // java.security.PrivilegedExceptionAction
                        public Unsafe run() throws Exception {
                            for (Field field : Unsafe.class.getDeclaredFields()) {
                                field.setAccessible(true);
                                Object obj = field.get(null);
                                if (Unsafe.class.isInstance(obj)) {
                                    return (Unsafe) Unsafe.class.cast(obj);
                                }
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
                }
            }

            @Override // java.util.Comparator
            public int compare(byte[] bArr, byte[] bArr2) {
                int iMin = Math.min(bArr.length, bArr2.length);
                int i = iMin / 8;
                int i2 = 0;
                while (true) {
                    int i3 = i * 8;
                    if (i2 >= i3) {
                        while (i3 < iMin) {
                            int iCompare = UnsignedBytes.compare(bArr[i3], bArr2[i3]);
                            if (iCompare != 0) {
                                return iCompare;
                            }
                            i3++;
                        }
                        return bArr.length - bArr2.length;
                    }
                    long j = i2;
                    long j2 = theUnsafe.getLong(bArr, ((long) BYTE_ARRAY_BASE_OFFSET) + j);
                    long j3 = theUnsafe.getLong(bArr2, ((long) BYTE_ARRAY_BASE_OFFSET) + j);
                    if (j2 != j3) {
                        if (BIG_ENDIAN) {
                            return UnsignedLongs.compare(j2, j3);
                        }
                        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j2 ^ j3) & (-8);
                        return ((int) ((j2 >>> iNumberOfTrailingZeros) & 255)) - ((int) ((j3 >>> iNumberOfTrailingZeros) & 255));
                    }
                    i2 += 8;
                }
            }

            @Override // java.lang.Enum
            public String toString() {
                return "UnsignedBytes.lexicographicalComparator() (sun.misc.Unsafe version)";
            }
        }

        LexicographicalComparatorHolder() {
        }

        static Comparator<byte[]> getBestComparator() {
            try {
                return (Comparator) Class.forName(UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
            } catch (Throwable unused) {
                return UnsignedBytes.lexicographicalComparatorJavaImpl();
            }
        }
    }

    private UnsignedBytes() {
    }

    @CanIgnoreReturnValue
    public static byte checkedCast(long j) {
        if ((j >> 8) == 0) {
            return (byte) j;
        }
        throw new IllegalArgumentException("Out of range: " + j);
    }

    public static int compare(byte b, byte b2) {
        return toInt(b) - toInt(b2);
    }

    public static String join(String str, byte... bArr) {
        Preconditions.checkNotNull(str);
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * (str.length() + 3));
        sb.append(toInt(bArr[0]));
        for (int i = 1; i < bArr.length; i++) {
            sb.append(str);
            sb.append(toString(bArr[i]));
        }
        return sb.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparatorHolder.BEST_COMPARATOR;
    }

    @VisibleForTesting
    static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }

    public static byte max(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        int i = toInt(bArr[0]);
        for (int i2 = 1; i2 < bArr.length; i2++) {
            int i3 = toInt(bArr[i2]);
            if (i3 > i) {
                i = i3;
            }
        }
        return (byte) i;
    }

    public static byte min(byte... bArr) {
        Preconditions.checkArgument(bArr.length > 0);
        int i = toInt(bArr[0]);
        for (int i2 = 1; i2 < bArr.length; i2++) {
            int i3 = toInt(bArr[i2]);
            if (i3 < i) {
                i = i3;
            }
        }
        return (byte) i;
    }

    @CanIgnoreReturnValue
    @Beta
    public static byte parseUnsignedByte(String str) {
        return parseUnsignedByte(str, 10);
    }

    @CanIgnoreReturnValue
    @Beta
    public static byte parseUnsignedByte(String str, int i) {
        int i2 = Integer.parseInt((String) Preconditions.checkNotNull(str), i);
        if ((i2 >> 8) == 0) {
            return (byte) i2;
        }
        throw new NumberFormatException("out of range: " + i2);
    }

    public static byte saturatedCast(long j) {
        if (j > toInt((byte) -1)) {
            return (byte) -1;
        }
        if (j < 0) {
            return (byte) 0;
        }
        return (byte) j;
    }

    public static int toInt(byte b) {
        return b & 255;
    }

    @Beta
    public static String toString(byte b) {
        return toString(b, 10);
    }

    @Beta
    public static String toString(byte b, int i) {
        Preconditions.checkArgument(i >= 2 && i <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", i);
        return Integer.toString(toInt(b), i);
    }
}
