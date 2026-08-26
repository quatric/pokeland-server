package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: UArraySorting.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, m1984d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class UArraySortingKt {
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m2334partitionnroSd4(long[] jArr, int i, int i2) {
        long jM2220getimpl = ULongArray.m2220getimpl(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m2220getimpl(jArr, i), jM2220getimpl) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m2220getimpl(jArr, i2), jM2220getimpl) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM2220getimpl2 = ULongArray.m2220getimpl(jArr, i);
                ULongArray.m2225setk8EXiF4(jArr, i, ULongArray.m2220getimpl(jArr, i2));
                ULongArray.m2225setk8EXiF4(jArr, i2, jM2220getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m2335partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM2082getimpl = UByteArray.m2082getimpl(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = bM2082getimpl & 255;
                if (Intrinsics.compare(UByteArray.m2082getimpl(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m2082getimpl(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM2082getimpl2 = UByteArray.m2082getimpl(bArr, i);
                UByteArray.m2087setVurrAj0(bArr, i, UByteArray.m2082getimpl(bArr, i2));
                UByteArray.m2087setVurrAj0(bArr, i2, bM2082getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m2336partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM2315getimpl = UShortArray.m2315getimpl(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM2315getimpl = UShortArray.m2315getimpl(sArr, i) & UShort.MAX_VALUE;
                i3 = sM2315getimpl & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM2315getimpl, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m2315getimpl(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM2315getimpl2 = UShortArray.m2315getimpl(sArr, i);
                UShortArray.m2320set01HTLdE(sArr, i, UShortArray.m2315getimpl(sArr, i2));
                UShortArray.m2320set01HTLdE(sArr, i2, sM2315getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m2337partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM2151getimpl = UIntArray.m2151getimpl(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m2151getimpl(iArr, i), iM2151getimpl) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m2151getimpl(iArr, i2), iM2151getimpl) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM2151getimpl2 = UIntArray.m2151getimpl(iArr, i);
                UIntArray.m2156setVXSXFK8(iArr, i, UIntArray.m2151getimpl(iArr, i2));
                UIntArray.m2156setVXSXFK8(iArr, i2, iM2151getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m2338quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM2334partitionnroSd4 = m2334partitionnroSd4(jArr, i, i2);
        int i3 = iM2334partitionnroSd4 - 1;
        if (i < i3) {
            m2338quickSortnroSd4(jArr, i, i3);
        }
        if (iM2334partitionnroSd4 < i2) {
            m2338quickSortnroSd4(jArr, iM2334partitionnroSd4, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m2339quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM2335partition4UcCI2c = m2335partition4UcCI2c(bArr, i, i2);
        int i3 = iM2335partition4UcCI2c - 1;
        if (i < i3) {
            m2339quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM2335partition4UcCI2c < i2) {
            m2339quickSort4UcCI2c(bArr, iM2335partition4UcCI2c, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m2340quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM2336partitionAa5vz7o = m2336partitionAa5vz7o(sArr, i, i2);
        int i3 = iM2336partitionAa5vz7o - 1;
        if (i < i3) {
            m2340quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM2336partitionAa5vz7o < i2) {
            m2340quickSortAa5vz7o(sArr, iM2336partitionAa5vz7o, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m2341quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM2337partitionoBK06Vg = m2337partitionoBK06Vg(iArr, i, i2);
        int i3 = iM2337partitionoBK06Vg - 1;
        if (i < i3) {
            m2341quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM2337partitionoBK06Vg < i2) {
            m2341quickSortoBK06Vg(iArr, iM2337partitionoBK06Vg, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray--ajY-9A, reason: not valid java name */
    public static final void m2342sortArrayajY9A(@NotNull int[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m2341quickSortoBK06Vg(array, 0, UIntArray.m2152getSizeimpl(array) - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-GBYM_sE, reason: not valid java name */
    public static final void m2343sortArrayGBYM_sE(@NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m2339quickSort4UcCI2c(array, 0, UByteArray.m2083getSizeimpl(array) - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-QwZRm1k, reason: not valid java name */
    public static final void m2344sortArrayQwZRm1k(@NotNull long[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m2338quickSortnroSd4(array, 0, ULongArray.m2221getSizeimpl(array) - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-rL5Bavg, reason: not valid java name */
    public static final void m2345sortArrayrL5Bavg(@NotNull short[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m2340quickSortAa5vz7o(array, 0, UShortArray.m2316getSizeimpl(array) - 1);
    }
}
