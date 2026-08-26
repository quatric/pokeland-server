package kotlin.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;

/* JADX INFO: compiled from: UProgressionUtil.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, m1984d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class UProgressionUtilKt {
    /* JADX INFO: renamed from: differenceModulo-WZ9TVnA, reason: not valid java name */
    private static final int m2874differenceModuloWZ9TVnA(int i, int i2, int i3) {
        int iM2327uintRemainderJ1ME1BU = UnsignedKt.m2327uintRemainderJ1ME1BU(i, i3);
        int iM2327uintRemainderJ1ME1BU2 = UnsignedKt.m2327uintRemainderJ1ME1BU(i2, i3);
        return UInt.m2100constructorimpl(UnsignedKt.uintCompare(iM2327uintRemainderJ1ME1BU, iM2327uintRemainderJ1ME1BU2) >= 0 ? iM2327uintRemainderJ1ME1BU - iM2327uintRemainderJ1ME1BU2 : UInt.m2100constructorimpl(iM2327uintRemainderJ1ME1BU - iM2327uintRemainderJ1ME1BU2) + i3);
    }

    /* JADX INFO: renamed from: differenceModulo-sambcqE, reason: not valid java name */
    private static final long m2875differenceModulosambcqE(long j, long j2, long j3) {
        long jM2329ulongRemaindereb3DHEI = UnsignedKt.m2329ulongRemaindereb3DHEI(j, j3);
        long jM2329ulongRemaindereb3DHEI2 = UnsignedKt.m2329ulongRemaindereb3DHEI(j2, j3);
        return ULong.m2169constructorimpl(UnsignedKt.ulongCompare(jM2329ulongRemaindereb3DHEI, jM2329ulongRemaindereb3DHEI2) >= 0 ? jM2329ulongRemaindereb3DHEI - jM2329ulongRemaindereb3DHEI2 : ULong.m2169constructorimpl(jM2329ulongRemaindereb3DHEI - jM2329ulongRemaindereb3DHEI2) + j3);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* JADX INFO: renamed from: getProgressionLastElement-7ftBX0g, reason: not valid java name */
    public static final long m2876getProgressionLastElement7ftBX0g(long j, long j2, long j3) {
        if (j3 > 0) {
            return UnsignedKt.ulongCompare(j, j2) >= 0 ? j2 : ULong.m2169constructorimpl(j2 - m2875differenceModulosambcqE(j2, j, ULong.m2169constructorimpl(j3)));
        }
        if (j3 < 0) {
            return UnsignedKt.ulongCompare(j, j2) <= 0 ? j2 : ULong.m2169constructorimpl(j2 + m2875differenceModulosambcqE(j, j2, ULong.m2169constructorimpl(-j3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* JADX INFO: renamed from: getProgressionLastElement-Nkh28Cs, reason: not valid java name */
    public static final int m2877getProgressionLastElementNkh28Cs(int i, int i2, int i3) {
        if (i3 > 0) {
            return UnsignedKt.uintCompare(i, i2) >= 0 ? i2 : UInt.m2100constructorimpl(i2 - m2874differenceModuloWZ9TVnA(i2, i, UInt.m2100constructorimpl(i3)));
        }
        if (i3 < 0) {
            return UnsignedKt.uintCompare(i, i2) <= 0 ? i2 : UInt.m2100constructorimpl(i2 + m2874differenceModuloWZ9TVnA(i, i2, UInt.m2100constructorimpl(-i3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
