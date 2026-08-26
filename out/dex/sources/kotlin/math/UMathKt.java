package kotlin.math;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.comparisons.UComparisonsKt;
import kotlin.internal.InlineOnly;

/* JADX INFO: compiled from: UMath.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a#\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a#\u0010\u0000\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\b\u001a#\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\n\u0010\u0005\u001a#\u0010\t\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, m1984d2 = {"max", "Lkotlin/UInt;", "a", "b", "max-J1ME1BU", "(II)I", "Lkotlin/ULong;", "max-eb3DHEI", "(JJ)J", "min", "min-J1ME1BU", "min-eb3DHEI", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class UMathKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: max-J1ME1BU, reason: not valid java name */
    private static final int m2878maxJ1ME1BU(int i, int i2) {
        return UComparisonsKt.m2859maxOfJ1ME1BU(i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: max-eb3DHEI, reason: not valid java name */
    private static final long m2879maxeb3DHEI(long j, long j2) {
        return UComparisonsKt.m2864maxOfeb3DHEI(j, j2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: min-J1ME1BU, reason: not valid java name */
    private static final int m2880minJ1ME1BU(int i, int i2) {
        return UComparisonsKt.m2867minOfJ1ME1BU(i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: min-eb3DHEI, reason: not valid java name */
    private static final long m2881mineb3DHEI(long j, long j2) {
        return UComparisonsKt.m2872minOfeb3DHEI(j, j2);
    }
}
