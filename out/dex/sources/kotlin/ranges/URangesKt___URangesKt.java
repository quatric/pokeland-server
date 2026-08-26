package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _URanges.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010\u0000\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\nø\u0001\u0000¢\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\nø\u0001\u0000¢\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010H\u001a\f\u0010I\u001a\u000208*\u000208H\u0007\u001a\f\u0010I\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010J\u001a\u000208*\u0002082\u0006\u0010J\u001a\u00020KH\u0087\u0004\u001a\u0015\u0010J\u001a\u00020>*\u00020>2\u0006\u0010J\u001a\u00020LH\u0087\u0004\u001a\u001f\u0010M\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bN\u0010O\u001a\u001f\u0010M\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bP\u0010Q\u001a\u001f\u0010M\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bR\u0010S\u001a\u001f\u0010M\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bT\u0010U\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006V"}, m1984d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/ranges/URangesKt")
class URangesKt___URangesKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtLeast-5PvTz6A, reason: not valid java name */
    public static final short m2895coerceAtLeast5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s & UShort.MAX_VALUE, 65535 & s2) < 0 ? s2 : s;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtLeast-J1ME1BU, reason: not valid java name */
    public static final int m2896coerceAtLeastJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) < 0 ? i2 : i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtLeast-Kr8caGY, reason: not valid java name */
    public static final byte m2897coerceAtLeastKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b & 255, b2 & 255) < 0 ? b2 : b;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtLeast-eb3DHEI, reason: not valid java name */
    public static final long m2898coerceAtLeasteb3DHEI(long j, long j2) {
        return UnsignedKt.ulongCompare(j, j2) < 0 ? j2 : j;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtMost-5PvTz6A, reason: not valid java name */
    public static final short m2899coerceAtMost5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s & UShort.MAX_VALUE, 65535 & s2) > 0 ? s2 : s;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtMost-J1ME1BU, reason: not valid java name */
    public static final int m2900coerceAtMostJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) > 0 ? i2 : i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtMost-Kr8caGY, reason: not valid java name */
    public static final byte m2901coerceAtMostKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b & 255, b2 & 255) > 0 ? b2 : b;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceAtMost-eb3DHEI, reason: not valid java name */
    public static final long m2902coerceAtMosteb3DHEI(long j, long j2) {
        return UnsignedKt.ulongCompare(j, j2) > 0 ? j2 : j;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceIn-JPwROB0, reason: not valid java name */
    public static final long m2903coerceInJPwROB0(long j, @NotNull ClosedRange<ULong> range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((ULong) RangesKt.coerceIn(ULong.m2163boximpl(j), (ClosedFloatingPointRange<ULong>) range)).getData();
        }
        if (!range.isEmpty()) {
            if (UnsignedKt.ulongCompare(j, ((ULong) range.getStart()).getData()) < 0) {
                return ((ULong) range.getStart()).getData();
            }
            return UnsignedKt.ulongCompare(j, ((ULong) range.getEndInclusive()).getData()) > 0 ? ((ULong) range.getEndInclusive()).getData() : j;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceIn-VKSA0NQ, reason: not valid java name */
    public static final short m2904coerceInVKSA0NQ(short s, short s2, short s3) {
        int i = s2 & UShort.MAX_VALUE;
        int i2 = s3 & UShort.MAX_VALUE;
        if (Intrinsics.compare(i, i2) <= 0) {
            int i3 = 65535 & s;
            if (Intrinsics.compare(i3, i) < 0) {
                return s2;
            }
            return Intrinsics.compare(i3, i2) > 0 ? s3 : s;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UShort.m2301toStringimpl(s3) + " is less than minimum " + UShort.m2301toStringimpl(s2) + '.');
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceIn-WZ9TVnA, reason: not valid java name */
    public static final int m2905coerceInWZ9TVnA(int i, int i2, int i3) {
        if (UnsignedKt.uintCompare(i2, i3) <= 0) {
            if (UnsignedKt.uintCompare(i, i2) < 0) {
                return i2;
            }
            return UnsignedKt.uintCompare(i, i3) > 0 ? i3 : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UInt.m2137toStringimpl(i3) + " is less than minimum " + UInt.m2137toStringimpl(i2) + '.');
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceIn-b33U2AM, reason: not valid java name */
    public static final byte m2906coerceInb33U2AM(byte b, byte b2, byte b3) {
        int i = b2 & 255;
        int i2 = b3 & 255;
        if (Intrinsics.compare(i, i2) <= 0) {
            int i3 = b & 255;
            if (Intrinsics.compare(i3, i) < 0) {
                return b2;
            }
            return Intrinsics.compare(i3, i2) > 0 ? b3 : b;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UByte.m2068toStringimpl(b3) + " is less than minimum " + UByte.m2068toStringimpl(b2) + '.');
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceIn-sambcqE, reason: not valid java name */
    public static final long m2907coerceInsambcqE(long j, long j2, long j3) {
        if (UnsignedKt.ulongCompare(j2, j3) <= 0) {
            if (UnsignedKt.ulongCompare(j, j2) < 0) {
                return j2;
            }
            return UnsignedKt.ulongCompare(j, j3) > 0 ? j3 : j;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ULong.m2206toStringimpl(j3) + " is less than minimum " + ULong.m2206toStringimpl(j2) + '.');
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: coerceIn-wuiCnnA, reason: not valid java name */
    public static final int m2908coerceInwuiCnnA(int i, @NotNull ClosedRange<UInt> range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((UInt) RangesKt.coerceIn(UInt.m2094boximpl(i), (ClosedFloatingPointRange<UInt>) range)).getData();
        }
        if (!range.isEmpty()) {
            if (UnsignedKt.uintCompare(i, ((UInt) range.getStart()).getData()) < 0) {
                return ((UInt) range.getStart()).getData();
            }
            return UnsignedKt.uintCompare(i, ((UInt) range.getEndInclusive()).getData()) > 0 ? ((UInt) range.getEndInclusive()).getData() : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contains-68kG9v0, reason: not valid java name */
    public static final boolean m2909contains68kG9v0(@NotNull UIntRange contains, byte b) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m2892containsWZ4Q5Ns(UInt.m2100constructorimpl(b & 255));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: contains-GYNo2lE, reason: not valid java name */
    private static final boolean m2910containsGYNo2lE(@NotNull ULongRange contains, ULong uLong) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return uLong != null && contains.m2894containsVKZWuLQ(uLong.getData());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contains-Gab390E, reason: not valid java name */
    public static final boolean m2911containsGab390E(@NotNull ULongRange contains, int i) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m2894containsVKZWuLQ(ULong.m2169constructorimpl(((long) i) & 4294967295L));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contains-ULb-yJY, reason: not valid java name */
    public static final boolean m2912containsULbyJY(@NotNull ULongRange contains, byte b) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m2894containsVKZWuLQ(ULong.m2169constructorimpl(((long) b) & 255));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contains-ZsK3CEQ, reason: not valid java name */
    public static final boolean m2913containsZsK3CEQ(@NotNull UIntRange contains, short s) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m2892containsWZ4Q5Ns(UInt.m2100constructorimpl(s & UShort.MAX_VALUE));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: contains-biwQdVI, reason: not valid java name */
    private static final boolean m2914containsbiwQdVI(@NotNull UIntRange contains, UInt uInt) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return uInt != null && contains.m2892containsWZ4Q5Ns(uInt.getData());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contains-fz5IDCE, reason: not valid java name */
    public static final boolean m2915containsfz5IDCE(@NotNull UIntRange contains, long j) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return ULong.m2169constructorimpl(j >>> 32) == 0 && contains.m2892containsWZ4Q5Ns(UInt.m2100constructorimpl((int) j));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contains-uhHAxoY, reason: not valid java name */
    public static final boolean m2916containsuhHAxoY(@NotNull ULongRange contains, short s) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m2894containsVKZWuLQ(ULong.m2169constructorimpl(((long) s) & 65535));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: downTo-5PvTz6A, reason: not valid java name */
    public static final UIntProgression m2917downTo5PvTz6A(short s, short s2) {
        return UIntProgression.INSTANCE.m2891fromClosedRangeNkh28Cs(UInt.m2100constructorimpl(s & UShort.MAX_VALUE), UInt.m2100constructorimpl(s2 & UShort.MAX_VALUE), -1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: downTo-J1ME1BU, reason: not valid java name */
    public static final UIntProgression m2918downToJ1ME1BU(int i, int i2) {
        return UIntProgression.INSTANCE.m2891fromClosedRangeNkh28Cs(i, i2, -1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: downTo-Kr8caGY, reason: not valid java name */
    public static final UIntProgression m2919downToKr8caGY(byte b, byte b2) {
        return UIntProgression.INSTANCE.m2891fromClosedRangeNkh28Cs(UInt.m2100constructorimpl(b & 255), UInt.m2100constructorimpl(b2 & 255), -1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: downTo-eb3DHEI, reason: not valid java name */
    public static final ULongProgression m2920downToeb3DHEI(long j, long j2) {
        return ULongProgression.INSTANCE.m2893fromClosedRange7ftBX0g(j, j2, -1L);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int random(@NotNull UIntRange uIntRange) {
        return URangesKt.random(uIntRange, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final int random(@NotNull UIntRange random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        try {
            return URandomKt.nextUInt(random2, random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final long random(@NotNull ULongRange uLongRange) {
        return URangesKt.random(uLongRange, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final long random(@NotNull ULongRange random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        try {
            return URandomKt.nextULong(random2, random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final UIntProgression reversed(@NotNull UIntProgression reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return UIntProgression.INSTANCE.m2891fromClosedRangeNkh28Cs(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final ULongProgression reversed(@NotNull ULongProgression reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return ULongProgression.INSTANCE.m2893fromClosedRange7ftBX0g(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final UIntProgression step(@NotNull UIntProgression step, int i) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(i > 0, Integer.valueOf(i));
        UIntProgression.Companion companion = UIntProgression.INSTANCE;
        int first = step.getFirst();
        int last = step.getLast();
        if (step.getStep() <= 0) {
            i = -i;
        }
        return companion.m2891fromClosedRangeNkh28Cs(first, last, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final ULongProgression step(@NotNull ULongProgression step, long j) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(j > 0, Long.valueOf(j));
        ULongProgression.Companion companion = ULongProgression.INSTANCE;
        long first = step.getFirst();
        long last = step.getLast();
        if (step.getStep() <= 0) {
            j = -j;
        }
        return companion.m2893fromClosedRange7ftBX0g(first, last, j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: until-5PvTz6A, reason: not valid java name */
    public static final UIntRange m2921until5PvTz6A(short s, short s2) {
        int i = s2 & UShort.MAX_VALUE;
        return Intrinsics.compare(i, 0) <= 0 ? UIntRange.INSTANCE.getEMPTY() : new UIntRange(UInt.m2100constructorimpl(s & UShort.MAX_VALUE), UInt.m2100constructorimpl(UInt.m2100constructorimpl(i) - 1), null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: until-J1ME1BU, reason: not valid java name */
    public static final UIntRange m2922untilJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i2, 0) <= 0 ? UIntRange.INSTANCE.getEMPTY() : new UIntRange(i, UInt.m2100constructorimpl(i2 - 1), null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: until-Kr8caGY, reason: not valid java name */
    public static final UIntRange m2923untilKr8caGY(byte b, byte b2) {
        int i = b2 & 255;
        return Intrinsics.compare(i, 0) <= 0 ? UIntRange.INSTANCE.getEMPTY() : new UIntRange(UInt.m2100constructorimpl(b & 255), UInt.m2100constructorimpl(UInt.m2100constructorimpl(i) - 1), null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: until-eb3DHEI, reason: not valid java name */
    public static final ULongRange m2924untileb3DHEI(long j, long j2) {
        return UnsignedKt.ulongCompare(j2, 0L) <= 0 ? ULongRange.INSTANCE.getEMPTY() : new ULongRange(j, ULong.m2169constructorimpl(j2 - ULong.m2169constructorimpl(((long) 1) & 4294967295L)), null);
    }
}
