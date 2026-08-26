package kotlin.text;

import android.support.v4.internal.view.SupportMenu;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: UStrings.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, m1984d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
@JvmName(name = "UStringsKt")
public final class UStringsKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toString-JSWoG40, reason: not valid java name */
    public static final String m2929toStringJSWoG40(long j, int i) {
        return UnsignedKt.ulongToString(j, CharsKt.checkRadix(i));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toString-LxnNnR4, reason: not valid java name */
    public static final String m2930toStringLxnNnR4(byte b, int i) {
        String string = Integer.toString(b & 255, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toString-V7xB4Y4, reason: not valid java name */
    public static final String m2931toStringV7xB4Y4(int i, int i2) {
        String string = Long.toString(((long) i) & 4294967295L, CharsKt.checkRadix(i2));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Long.toString(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toString-olVBNx4, reason: not valid java name */
    public static final String m2932toStringolVBNx4(short s, int i) {
        String string = Integer.toString(s & UShort.MAX_VALUE, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final byte toUByte(@NotNull String toUByte) {
        Intrinsics.checkParameterIsNotNull(toUByte, "$this$toUByte");
        UByte uByteOrNull = toUByteOrNull(toUByte);
        if (uByteOrNull != null) {
            return uByteOrNull.getData();
        }
        StringsKt.numberFormatError(toUByte);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final byte toUByte(@NotNull String toUByte, int i) {
        Intrinsics.checkParameterIsNotNull(toUByte, "$this$toUByte");
        UByte uByteOrNull = toUByteOrNull(toUByte, i);
        if (uByteOrNull != null) {
            return uByteOrNull.getData();
        }
        StringsKt.numberFormatError(toUByte);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final UByte toUByteOrNull(@NotNull String toUByteOrNull) {
        Intrinsics.checkParameterIsNotNull(toUByteOrNull, "$this$toUByteOrNull");
        return toUByteOrNull(toUByteOrNull, 10);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final UByte toUByteOrNull(@NotNull String toUByteOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(toUByteOrNull, "$this$toUByteOrNull");
        UInt uIntOrNull = toUIntOrNull(toUByteOrNull, i);
        if (uIntOrNull == null) {
            return null;
        }
        int data = uIntOrNull.getData();
        if (UnsignedKt.uintCompare(data, UInt.m2100constructorimpl(255)) > 0) {
            return null;
        }
        return UByte.m2027boximpl(UByte.m2033constructorimpl((byte) data));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final int toUInt(@NotNull String toUInt) {
        Intrinsics.checkParameterIsNotNull(toUInt, "$this$toUInt");
        UInt uIntOrNull = toUIntOrNull(toUInt);
        if (uIntOrNull != null) {
            return uIntOrNull.getData();
        }
        StringsKt.numberFormatError(toUInt);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final int toUInt(@NotNull String toUInt, int i) {
        Intrinsics.checkParameterIsNotNull(toUInt, "$this$toUInt");
        UInt uIntOrNull = toUIntOrNull(toUInt, i);
        if (uIntOrNull != null) {
            return uIntOrNull.getData();
        }
        StringsKt.numberFormatError(toUInt);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final UInt toUIntOrNull(@NotNull String toUIntOrNull) {
        Intrinsics.checkParameterIsNotNull(toUIntOrNull, "$this$toUIntOrNull");
        return toUIntOrNull(toUIntOrNull, 10);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final UInt toUIntOrNull(@NotNull String toUIntOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(toUIntOrNull, "$this$toUIntOrNull");
        CharsKt.checkRadix(i);
        int length = toUIntOrNull.length();
        if (length == 0) {
            return null;
        }
        int i2 = 0;
        char cCharAt = toUIntOrNull.charAt(0);
        int i3 = 1;
        if (cCharAt >= '0') {
            i3 = 0;
        } else if (length == 1 || cCharAt != '+') {
            return null;
        }
        int iM2100constructorimpl = UInt.m2100constructorimpl(i);
        int iM2326uintDivideJ1ME1BU = 119304647;
        while (i3 < length) {
            int iDigitOf = CharsKt.digitOf(toUIntOrNull.charAt(i3), i);
            if (iDigitOf < 0) {
                return null;
            }
            if (UnsignedKt.uintCompare(i2, iM2326uintDivideJ1ME1BU) > 0) {
                if (iM2326uintDivideJ1ME1BU == 119304647) {
                    iM2326uintDivideJ1ME1BU = UnsignedKt.m2326uintDivideJ1ME1BU(-1, iM2100constructorimpl);
                    if (UnsignedKt.uintCompare(i2, iM2326uintDivideJ1ME1BU) > 0) {
                    }
                }
                return null;
            }
            int iM2100constructorimpl2 = UInt.m2100constructorimpl(i2 * iM2100constructorimpl);
            int iM2100constructorimpl3 = UInt.m2100constructorimpl(UInt.m2100constructorimpl(iDigitOf) + iM2100constructorimpl2);
            if (UnsignedKt.uintCompare(iM2100constructorimpl3, iM2100constructorimpl2) < 0) {
                return null;
            }
            i3++;
            i2 = iM2100constructorimpl3;
        }
        return UInt.m2094boximpl(i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final long toULong(@NotNull String toULong) {
        Intrinsics.checkParameterIsNotNull(toULong, "$this$toULong");
        ULong uLongOrNull = toULongOrNull(toULong);
        if (uLongOrNull != null) {
            return uLongOrNull.getData();
        }
        StringsKt.numberFormatError(toULong);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final long toULong(@NotNull String toULong, int i) {
        Intrinsics.checkParameterIsNotNull(toULong, "$this$toULong");
        ULong uLongOrNull = toULongOrNull(toULong, i);
        if (uLongOrNull != null) {
            return uLongOrNull.getData();
        }
        StringsKt.numberFormatError(toULong);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final ULong toULongOrNull(@NotNull String toULongOrNull) {
        Intrinsics.checkParameterIsNotNull(toULongOrNull, "$this$toULongOrNull");
        return toULongOrNull(toULongOrNull, 10);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final ULong toULongOrNull(@NotNull String toULongOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(toULongOrNull, "$this$toULongOrNull");
        CharsKt.checkRadix(i);
        int length = toULongOrNull.length();
        if (length == 0) {
            return null;
        }
        long j = -1;
        int i2 = 0;
        char cCharAt = toULongOrNull.charAt(0);
        if (cCharAt < '0') {
            if (length == 1 || cCharAt != '+') {
                return null;
            }
            i2 = 1;
        }
        long jM2169constructorimpl = ULong.m2169constructorimpl(i);
        long j2 = 0;
        long jM2328ulongDivideeb3DHEI = 512409557603043100L;
        while (i2 < length) {
            int iDigitOf = CharsKt.digitOf(toULongOrNull.charAt(i2), i);
            if (iDigitOf < 0) {
                return null;
            }
            if (UnsignedKt.ulongCompare(j2, jM2328ulongDivideeb3DHEI) > 0) {
                if (jM2328ulongDivideeb3DHEI == 512409557603043100L) {
                    jM2328ulongDivideeb3DHEI = UnsignedKt.m2328ulongDivideeb3DHEI(j, jM2169constructorimpl);
                    if (UnsignedKt.ulongCompare(j2, jM2328ulongDivideeb3DHEI) > 0) {
                    }
                }
                return null;
            }
            long jM2169constructorimpl2 = ULong.m2169constructorimpl(j2 * jM2169constructorimpl);
            long jM2169constructorimpl3 = ULong.m2169constructorimpl(ULong.m2169constructorimpl(((long) UInt.m2100constructorimpl(iDigitOf)) & 4294967295L) + jM2169constructorimpl2);
            if (UnsignedKt.ulongCompare(jM2169constructorimpl3, jM2169constructorimpl2) < 0) {
                return null;
            }
            i2++;
            j2 = jM2169constructorimpl3;
            j = -1;
        }
        return ULong.m2163boximpl(j2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final short toUShort(@NotNull String toUShort) {
        Intrinsics.checkParameterIsNotNull(toUShort, "$this$toUShort");
        UShort uShortOrNull = toUShortOrNull(toUShort);
        if (uShortOrNull != null) {
            return uShortOrNull.getData();
        }
        StringsKt.numberFormatError(toUShort);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final short toUShort(@NotNull String toUShort, int i) {
        Intrinsics.checkParameterIsNotNull(toUShort, "$this$toUShort");
        UShort uShortOrNull = toUShortOrNull(toUShort, i);
        if (uShortOrNull != null) {
            return uShortOrNull.getData();
        }
        StringsKt.numberFormatError(toUShort);
        throw null;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final UShort toUShortOrNull(@NotNull String toUShortOrNull) {
        Intrinsics.checkParameterIsNotNull(toUShortOrNull, "$this$toUShortOrNull");
        return toUShortOrNull(toUShortOrNull, 10);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Nullable
    public static final UShort toUShortOrNull(@NotNull String toUShortOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(toUShortOrNull, "$this$toUShortOrNull");
        UInt uIntOrNull = toUIntOrNull(toUShortOrNull, i);
        if (uIntOrNull == null) {
            return null;
        }
        int data = uIntOrNull.getData();
        if (UnsignedKt.uintCompare(data, UInt.m2100constructorimpl(SupportMenu.USER_MASK)) > 0) {
            return null;
        }
        return UShort.m2260boximpl(UShort.m2266constructorimpl((short) data));
    }
}
