package kotlin.comparisons;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _UComparisons.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\b\u001a\"\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u001a+\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\bø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\"\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a+\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012\u001a\"\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001a+\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a\"\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u0005\u001a+\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\b\u001a\"\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u000b\u001a+\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\r\u001a\"\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0010\u001a+\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0012\u001a\"\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0015\u001a+\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b \u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, m1984d2 = {"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-5PvTz6A", "minOf-VKSA0NQ", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/comparisons/UComparisonsKt")
public class UComparisonsKt___UComparisonsKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: maxOf-5PvTz6A, reason: not valid java name */
    public static final short m2858maxOf5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s & UShort.MAX_VALUE, 65535 & s2) >= 0 ? s : s2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: maxOf-J1ME1BU, reason: not valid java name */
    public static final int m2859maxOfJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) >= 0 ? i : i2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: maxOf-Kr8caGY, reason: not valid java name */
    public static final byte m2860maxOfKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b & 255, b2 & 255) >= 0 ? b : b2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: maxOf-VKSA0NQ, reason: not valid java name */
    private static final short m2861maxOfVKSA0NQ(short s, short s2, short s3) {
        return UComparisonsKt.m2858maxOf5PvTz6A(s, UComparisonsKt.m2858maxOf5PvTz6A(s2, s3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: maxOf-WZ9TVnA, reason: not valid java name */
    private static final int m2862maxOfWZ9TVnA(int i, int i2, int i3) {
        return UComparisonsKt.m2859maxOfJ1ME1BU(i, UComparisonsKt.m2859maxOfJ1ME1BU(i2, i3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: maxOf-b33U2AM, reason: not valid java name */
    private static final byte m2863maxOfb33U2AM(byte b, byte b2, byte b3) {
        return UComparisonsKt.m2860maxOfKr8caGY(b, UComparisonsKt.m2860maxOfKr8caGY(b2, b3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: maxOf-eb3DHEI, reason: not valid java name */
    public static final long m2864maxOfeb3DHEI(long j, long j2) {
        return UnsignedKt.ulongCompare(j, j2) >= 0 ? j : j2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: maxOf-sambcqE, reason: not valid java name */
    private static final long m2865maxOfsambcqE(long j, long j2, long j3) {
        return UComparisonsKt.m2864maxOfeb3DHEI(j, UComparisonsKt.m2864maxOfeb3DHEI(j2, j3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: minOf-5PvTz6A, reason: not valid java name */
    public static final short m2866minOf5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s & UShort.MAX_VALUE, 65535 & s2) <= 0 ? s : s2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: minOf-J1ME1BU, reason: not valid java name */
    public static final int m2867minOfJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) <= 0 ? i : i2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: minOf-Kr8caGY, reason: not valid java name */
    public static final byte m2868minOfKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b & 255, b2 & 255) <= 0 ? b : b2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: minOf-VKSA0NQ, reason: not valid java name */
    private static final short m2869minOfVKSA0NQ(short s, short s2, short s3) {
        return UComparisonsKt.m2866minOf5PvTz6A(s, UComparisonsKt.m2866minOf5PvTz6A(s2, s3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: minOf-WZ9TVnA, reason: not valid java name */
    private static final int m2870minOfWZ9TVnA(int i, int i2, int i3) {
        return UComparisonsKt.m2867minOfJ1ME1BU(i, UComparisonsKt.m2867minOfJ1ME1BU(i2, i3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: minOf-b33U2AM, reason: not valid java name */
    private static final byte m2871minOfb33U2AM(byte b, byte b2, byte b3) {
        return UComparisonsKt.m2868minOfKr8caGY(b, UComparisonsKt.m2868minOfKr8caGY(b2, b3));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: minOf-eb3DHEI, reason: not valid java name */
    public static final long m2872minOfeb3DHEI(long j, long j2) {
        return UnsignedKt.ulongCompare(j, j2) <= 0 ? j : j2;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: minOf-sambcqE, reason: not valid java name */
    private static final long m2873minOfsambcqE(long j, long j2, long j3) {
        return UComparisonsKt.m2872minOfeb3DHEI(j, UComparisonsKt.m2872minOfeb3DHEI(j2, j3));
    }
}
