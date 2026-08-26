package kotlin;

import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: UnsignedUtils.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, m1984d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
@JvmName(name = "UnsignedKt")
public final class UnsignedKt {
    @PublishedApi
    public static final int doubleToUInt(double d) {
        if (Double.isNaN(d) || d <= uintToDouble(0)) {
            return 0;
        }
        if (d >= uintToDouble(-1)) {
            return -1;
        }
        double d2 = Integer.MAX_VALUE;
        if (d <= d2) {
            return UInt.m2100constructorimpl((int) d);
        }
        Double.isNaN(d2);
        return UInt.m2100constructorimpl(UInt.m2100constructorimpl((int) (d - d2)) + UInt.m2100constructorimpl(Integer.MAX_VALUE));
    }

    @PublishedApi
    public static final long doubleToULong(double d) {
        if (Double.isNaN(d) || d <= ulongToDouble(0L)) {
            return 0L;
        }
        if (d >= ulongToDouble(-1L)) {
            return -1L;
        }
        return d < ((double) LongCompanionObject.MAX_VALUE) ? ULong.m2169constructorimpl((long) d) : ULong.m2169constructorimpl(ULong.m2169constructorimpl((long) (d - 9.223372036854776E18d)) - Long.MIN_VALUE);
    }

    @PublishedApi
    public static final int uintCompare(int i, int i2) {
        return Intrinsics.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE);
    }

    @PublishedApi
    /* JADX INFO: renamed from: uintDivide-J1ME1BU, reason: not valid java name */
    public static final int m2326uintDivideJ1ME1BU(int i, int i2) {
        return UInt.m2100constructorimpl((int) ((((long) i) & 4294967295L) / (((long) i2) & 4294967295L)));
    }

    @PublishedApi
    /* JADX INFO: renamed from: uintRemainder-J1ME1BU, reason: not valid java name */
    public static final int m2327uintRemainderJ1ME1BU(int i, int i2) {
        return UInt.m2100constructorimpl((int) ((((long) i) & 4294967295L) % (((long) i2) & 4294967295L)));
    }

    @PublishedApi
    public static final double uintToDouble(int i) {
        double d = Integer.MAX_VALUE & i;
        double d2 = (i >>> 31) << 30;
        double d3 = 2;
        Double.isNaN(d2);
        Double.isNaN(d3);
        Double.isNaN(d);
        return d + (d2 * d3);
    }

    @PublishedApi
    public static final int ulongCompare(long j, long j2) {
        return ((j ^ Long.MIN_VALUE) > (j2 ^ Long.MIN_VALUE) ? 1 : ((j ^ Long.MIN_VALUE) == (j2 ^ Long.MIN_VALUE) ? 0 : -1));
    }

    @PublishedApi
    /* JADX INFO: renamed from: ulongDivide-eb3DHEI, reason: not valid java name */
    public static final long m2328ulongDivideeb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return ulongCompare(j, j2) < 0 ? ULong.m2169constructorimpl(0L) : ULong.m2169constructorimpl(1L);
        }
        if (j >= 0) {
            return ULong.m2169constructorimpl(j / j2);
        }
        long j3 = ((j >>> 1) / j2) << 1;
        return ULong.m2169constructorimpl(j3 + ((long) (ulongCompare(ULong.m2169constructorimpl(j - (j3 * j2)), ULong.m2169constructorimpl(j2)) < 0 ? 0 : 1)));
    }

    @PublishedApi
    /* JADX INFO: renamed from: ulongRemainder-eb3DHEI, reason: not valid java name */
    public static final long m2329ulongRemaindereb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return ulongCompare(j, j2) < 0 ? j : ULong.m2169constructorimpl(j - j2);
        }
        if (j >= 0) {
            return ULong.m2169constructorimpl(j % j2);
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (ulongCompare(ULong.m2169constructorimpl(j3), ULong.m2169constructorimpl(j2)) < 0) {
            j2 = 0;
        }
        return ULong.m2169constructorimpl(j3 - j2);
    }

    @PublishedApi
    public static final double ulongToDouble(long j) {
        double d = j >>> 11;
        double d2 = 2048;
        Double.isNaN(d);
        Double.isNaN(d2);
        double d3 = j & 2047;
        Double.isNaN(d3);
        return (d * d2) + d3;
    }

    @NotNull
    public static final String ulongToString(long j) {
        return ulongToString(j, 10);
    }

    @NotNull
    public static final String ulongToString(long j, int i) {
        if (j >= 0) {
            String string = Long.toString(j, CharsKt.checkRadix(i));
            Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Long.toString(this, checkRadix(radix))");
            return string;
        }
        long j2 = i;
        long j3 = ((j >>> 1) / j2) << 1;
        long j4 = j - (j3 * j2);
        if (j4 >= j2) {
            j4 -= j2;
            j3++;
        }
        StringBuilder sb = new StringBuilder();
        String string2 = Long.toString(j3, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Long.toString(this, checkRadix(radix))");
        sb.append(string2);
        String string3 = Long.toString(j4, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(string3, "java.lang.Long.toString(this, checkRadix(radix))");
        sb.append(string3);
        return sb.toString();
    }
}
