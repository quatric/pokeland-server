package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedLongs;
import java.math.RoundingMode;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
public final class LongMath {

    @VisibleForTesting
    static final long FLOOR_SQRT_MAX_LONG = 3037000499L;

    @VisibleForTesting
    static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;

    @VisibleForTesting
    static final long MAX_SIGNED_POWER_OF_TWO = 4611686018427387904L;
    private static final int SIEVE_30 = -545925251;

    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = {19, Ascii.DC2, Ascii.DC2, Ascii.DC2, Ascii.DC2, 17, 17, 17, Ascii.DLE, Ascii.DLE, Ascii.DLE, Ascii.f291SI, Ascii.f291SI, Ascii.f291SI, Ascii.f291SI, Ascii.f292SO, Ascii.f292SO, Ascii.f292SO, Ascii.f282CR, Ascii.f282CR, Ascii.f282CR, Ascii.f284FF, Ascii.f284FF, Ascii.f284FF, Ascii.f284FF, Ascii.f295VT, Ascii.f295VT, Ascii.f295VT, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};

    @VisibleForTesting
    @GwtIncompatible
    static final long[] powersOf10 = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};

    @VisibleForTesting
    @GwtIncompatible
    static final long[] halfPowersOf10 = {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
    static final long[] factorials = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    static final int[] biggestBinomials = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};

    @VisibleForTesting
    static final int[] biggestSimpleBinomials = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    private static final long[][] millerRabinBaseSets = {new long[]{291830, 126401071349994536L}, new long[]{885594168, 725270293939359937L, 3569819667048198375L}, new long[]{273919523040L, 15, 7363882082L, 992620450144556L}, new long[]{47636622961200L, 2, 2570940, 211991001, 3749873356L}, new long[]{7999252175582850L, 2, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L}, new long[]{585226005592931976L, 2, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L}, new long[]{LongCompanionObject.MAX_VALUE, 2, 325, 9375, 28178, 450775, 9780504, 1795265022}};

    /* JADX INFO: renamed from: com.google.common.math.LongMath$1 */
    static /* synthetic */ class C06621 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode = new int[RoundingMode.values().length];

        static {
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private enum MillerRabinTester {
        SMALL { // from class: com.google.common.math.LongMath.MillerRabinTester.1
            @Override // com.google.common.math.LongMath.MillerRabinTester
            long mulMod(long j, long j2, long j3) {
                return (j * j2) % j3;
            }

            @Override // com.google.common.math.LongMath.MillerRabinTester
            long squareMod(long j, long j2) {
                return (j * j) % j2;
            }
        },
        LARGE { // from class: com.google.common.math.LongMath.MillerRabinTester.2
            private long plusMod(long j, long j2, long j3) {
                return j >= j3 - j2 ? (j + j2) - j3 : j + j2;
            }

            private long times2ToThe32Mod(long j, long j2) {
                int i = 32;
                do {
                    int iMin = Math.min(i, Long.numberOfLeadingZeros(j));
                    j = UnsignedLongs.remainder(j << iMin, j2);
                    i -= iMin;
                } while (i > 0);
                return j;
            }

            @Override // com.google.common.math.LongMath.MillerRabinTester
            long mulMod(long j, long j2, long j3) {
                long j4 = j >>> 32;
                long j5 = j2 >>> 32;
                long j6 = j & 4294967295L;
                long j7 = j2 & 4294967295L;
                long jTimes2ToThe32Mod = times2ToThe32Mod(j4 * j5, j3) + (j4 * j7);
                if (jTimes2ToThe32Mod < 0) {
                    jTimes2ToThe32Mod = UnsignedLongs.remainder(jTimes2ToThe32Mod, j3);
                }
                Long.signum(j6);
                return plusMod(times2ToThe32Mod(jTimes2ToThe32Mod + (j5 * j6), j3), UnsignedLongs.remainder(j6 * j7, j3), j3);
            }

            @Override // com.google.common.math.LongMath.MillerRabinTester
            long squareMod(long j, long j2) {
                long j3 = j >>> 32;
                long j4 = j & 4294967295L;
                long jTimes2ToThe32Mod = times2ToThe32Mod(j3 * j3, j2);
                long jRemainder = j3 * j4 * 2;
                if (jRemainder < 0) {
                    jRemainder = UnsignedLongs.remainder(jRemainder, j2);
                }
                return plusMod(times2ToThe32Mod(jTimes2ToThe32Mod + jRemainder, j2), UnsignedLongs.remainder(j4 * j4, j2), j2);
            }
        };

        /* synthetic */ MillerRabinTester(C06621 c06621) {
            this();
        }

        private long powMod(long j, long j2, long j3) {
            long jMulMod = 1;
            while (j2 != 0) {
                if ((j2 & 1) != 0) {
                    jMulMod = mulMod(jMulMod, j, j3);
                }
                j = squareMod(j, j3);
                j2 >>= 1;
            }
            return jMulMod;
        }

        static boolean test(long j, long j2) {
            return (j2 <= LongMath.FLOOR_SQRT_MAX_LONG ? SMALL : LARGE).testWitness(j, j2);
        }

        private boolean testWitness(long j, long j2) {
            long j3 = j2 - 1;
            int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j3);
            long j4 = j3 >> iNumberOfTrailingZeros;
            long j5 = j % j2;
            if (j5 == 0) {
                return true;
            }
            long jPowMod = powMod(j5, j4, j2);
            if (jPowMod == 1) {
                return true;
            }
            int i = 0;
            while (jPowMod != j3) {
                i++;
                if (i == iNumberOfTrailingZeros) {
                    return false;
                }
                jPowMod = squareMod(jPowMod, j2);
            }
            return true;
        }

        abstract long mulMod(long j, long j2, long j3);

        abstract long squareMod(long j, long j2);
    }

    private LongMath() {
    }

    public static long binomial(int i, int i2) {
        MathPreconditions.checkNonNegative("n", i);
        MathPreconditions.checkNonNegative("k", i2);
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        if (i2 == 0) {
            return 1L;
        }
        if (i2 == 1) {
            return i;
        }
        long[] jArr = factorials;
        if (i < jArr.length) {
            return jArr[i] / (jArr[i2] * jArr[i - i2]);
        }
        int[] iArr = biggestBinomials;
        if (i2 >= iArr.length || i > iArr[i2]) {
            return LongCompanionObject.MAX_VALUE;
        }
        int[] iArr2 = biggestSimpleBinomials;
        int i3 = 2;
        if (i2 < iArr2.length && i <= iArr2[i2]) {
            int i4 = i - 1;
            long j = i;
            while (i3 <= i2) {
                j = (j * ((long) i4)) / ((long) i3);
                i4--;
                i3++;
            }
            return j;
        }
        long j2 = i;
        int iLog2 = log2(j2, RoundingMode.CEILING);
        long j3 = 1;
        long j4 = j2;
        int i5 = i - 1;
        int i6 = iLog2;
        long j5 = 1;
        while (i3 <= i2) {
            i6 += iLog2;
            if (i6 < 63) {
                j4 *= (long) i5;
                j3 *= (long) i3;
            } else {
                long jMultiplyFraction = multiplyFraction(j5, j4, j3);
                i6 = iLog2;
                j3 = i3;
                j4 = i5;
                j5 = jMultiplyFraction;
            }
            i3++;
            i5--;
        }
        return multiplyFraction(j5, j4, j3);
    }

    @Beta
    public static long ceilingPowerOfTwo(long j) {
        MathPreconditions.checkPositive("x", j);
        if (j <= 4611686018427387904L) {
            return 1 << (-Long.numberOfLeadingZeros(j - 1));
        }
        throw new ArithmeticException("ceilingPowerOfTwo(" + j + ") is not representable as a long");
    }

    @GwtIncompatible
    public static long checkedAdd(long j, long j2) {
        long j3 = j + j2;
        MathPreconditions.checkNoOverflow(((j2 ^ j) < 0) | ((j ^ j3) >= 0));
        return j3;
    }

    @GwtIncompatible
    public static long checkedMultiply(long j, long j2) {
        int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(j ^ (-1)) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros((-1) ^ j2);
        if (iNumberOfLeadingZeros > 65) {
            return j * j2;
        }
        boolean z = true;
        MathPreconditions.checkNoOverflow(iNumberOfLeadingZeros >= 64);
        MathPreconditions.checkNoOverflow((j >= 0) | (j2 != Long.MIN_VALUE));
        long j3 = j * j2;
        if (j != 0 && j3 / j != j2) {
            z = false;
        }
        MathPreconditions.checkNoOverflow(z);
        return j3;
    }

    @GwtIncompatible
    public static long checkedPow(long j, int i) {
        MathPreconditions.checkNonNegative("exponent", i);
        long jCheckedMultiply = 1;
        if (!(j >= -2) || !(j <= 2)) {
            while (i != 0) {
                if (i == 1) {
                    return checkedMultiply(jCheckedMultiply, j);
                }
                if ((i & 1) != 0) {
                    jCheckedMultiply = checkedMultiply(jCheckedMultiply, j);
                }
                i >>= 1;
                if (i > 0) {
                    MathPreconditions.checkNoOverflow(-3037000499L <= j && j <= FLOOR_SQRT_MAX_LONG);
                    j *= j;
                }
            }
            return jCheckedMultiply;
        }
        int i2 = (int) j;
        if (i2 == -2) {
            MathPreconditions.checkNoOverflow(i < 64);
            return (i & 1) == 0 ? 1 << i : (-1) << i;
        }
        if (i2 == -1) {
            return (i & 1) == 0 ? 1L : -1L;
        }
        if (i2 == 0) {
            return i == 0 ? 1L : 0L;
        }
        if (i2 == 1) {
            return 1L;
        }
        if (i2 != 2) {
            throw new AssertionError();
        }
        MathPreconditions.checkNoOverflow(i < 63);
        return 1 << i;
    }

    @GwtIncompatible
    public static long checkedSubtract(long j, long j2) {
        long j3 = j - j2;
        MathPreconditions.checkNoOverflow(((j2 ^ j) >= 0) | ((j ^ j3) >= 0));
        return j3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:36:0x0065  */
    /* JADX WARN: Code duplicated, block: B:38:0x0068  */
    /* JADX WARN: Code duplicated, block: B:40:? A[RETURN, SYNTHETIC] */
    @GwtIncompatible
    public static long divide(long j, long j2, RoundingMode roundingMode) {
        Preconditions.checkNotNull(roundingMode);
        long j3 = j / j2;
        long j4 = j - (j2 * j3);
        if (j4 == 0) {
            return j3;
        }
        int i = (int) ((j ^ j2) >> 63);
        boolean z = true;
        int i2 = i | 1;
        switch (C06621.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(j4 == 0);
                z = false;
                if (z) {
                    return j3 + ((long) i2);
                }
                return j3;
            case 2:
                z = false;
                if (z) {
                    return j3 + ((long) i2);
                }
                return j3;
            case 3:
                if (i2 >= 0) {
                    z = false;
                }
                if (z) {
                    return j3 + ((long) i2);
                }
                return j3;
            case 4:
                if (z) {
                    return j3 + ((long) i2);
                }
                return j3;
            case 5:
                if (i2 <= 0) {
                    z = false;
                }
                if (z) {
                    return j3 + ((long) i2);
                }
                return j3;
            case 6:
            case 7:
            case 8:
                long jAbs = Math.abs(j4);
                long jAbs2 = jAbs - (Math.abs(j2) - jAbs);
                if (jAbs2 == 0) {
                    z = (((1 & j3) != 0) & (roundingMode == RoundingMode.HALF_EVEN)) | (roundingMode == RoundingMode.HALF_UP);
                } else if (jAbs2 <= 0) {
                    z = false;
                }
                if (z) {
                    return j3 + ((long) i2);
                }
                return j3;
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    public static long factorial(int i) {
        MathPreconditions.checkNonNegative("n", i);
        long[] jArr = factorials;
        return i < jArr.length ? jArr[i] : LongCompanionObject.MAX_VALUE;
    }

    static boolean fitsInInt(long j) {
        return ((long) ((int) j)) == j;
    }

    @Beta
    public static long floorPowerOfTwo(long j) {
        MathPreconditions.checkPositive("x", j);
        return 1 << (63 - Long.numberOfLeadingZeros(j));
    }

    public static long gcd(long j, long j2) {
        MathPreconditions.checkNonNegative("a", j);
        MathPreconditions.checkNonNegative("b", j2);
        if (j == 0) {
            return j2;
        }
        if (j2 == 0) {
            return j;
        }
        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long jNumberOfTrailingZeros = j >> iNumberOfTrailingZeros;
        int iNumberOfTrailingZeros2 = Long.numberOfTrailingZeros(j2);
        long j3 = j2 >> iNumberOfTrailingZeros2;
        while (jNumberOfTrailingZeros != j3) {
            long j4 = jNumberOfTrailingZeros - j3;
            long j5 = (j4 >> 63) & j4;
            long j6 = (j4 - j5) - j5;
            j3 += j5;
            jNumberOfTrailingZeros = j6 >> Long.numberOfTrailingZeros(j6);
        }
        return jNumberOfTrailingZeros << Math.min(iNumberOfTrailingZeros, iNumberOfTrailingZeros2);
    }

    public static boolean isPowerOfTwo(long j) {
        return (j > 0) & ((j & (j - 1)) == 0);
    }

    @Beta
    @GwtIncompatible
    public static boolean isPrime(long j) {
        if (j < 2) {
            MathPreconditions.checkNonNegative("n", j);
            return false;
        }
        if (j == 2 || j == 3 || j == 5 || j == 7 || j == 11 || j == 13) {
            return true;
        }
        if ((SIEVE_30 & (1 << ((int) (j % 30)))) != 0 || j % 7 == 0 || j % 11 == 0 || j % 13 == 0) {
            return false;
        }
        if (j < 289) {
            return true;
        }
        for (long[] jArr : millerRabinBaseSets) {
            if (j <= jArr[0]) {
                for (int i = 1; i < jArr.length; i++) {
                    if (!MillerRabinTester.test(jArr[i], j)) {
                        return false;
                    }
                }
                return true;
            }
        }
        throw new AssertionError();
    }

    @VisibleForTesting
    static int lessThanBranchFree(long j, long j2) {
        return (int) ((((j - j2) ^ (-1)) ^ (-1)) >>> 63);
    }

    @GwtIncompatible
    public static int log10(long j, RoundingMode roundingMode) {
        int iLessThanBranchFree;
        MathPreconditions.checkPositive("x", j);
        int iLog10Floor = log10Floor(j);
        long j2 = powersOf10[iLog10Floor];
        switch (C06621.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(j == j2);
            case 2:
            case 3:
                return iLog10Floor;
            case 4:
            case 5:
                iLessThanBranchFree = lessThanBranchFree(j2, j);
                return iLog10Floor + iLessThanBranchFree;
            case 6:
            case 7:
            case 8:
                iLessThanBranchFree = lessThanBranchFree(halfPowersOf10[iLog10Floor], j);
                return iLog10Floor + iLessThanBranchFree;
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    static int log10Floor(long j) {
        byte b = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(j)];
        return b - lessThanBranchFree(j, powersOf10[b]);
    }

    public static int log2(long j, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", j);
        switch (C06621.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(j));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 64 - Long.numberOfLeadingZeros(j - 1);
            case 6:
            case 7:
            case 8:
                int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j);
                return (63 - iNumberOfLeadingZeros) + lessThanBranchFree(MAX_POWER_OF_SQRT2_UNSIGNED >>> iNumberOfLeadingZeros, j);
            default:
                throw new AssertionError("impossible");
        }
        return 63 - Long.numberOfLeadingZeros(j);
    }

    public static long mean(long j, long j2) {
        return (j & j2) + ((j ^ j2) >> 1);
    }

    @GwtIncompatible
    public static int mod(long j, int i) {
        return (int) mod(j, i);
    }

    @GwtIncompatible
    public static long mod(long j, long j2) {
        if (j2 <= 0) {
            throw new ArithmeticException("Modulus must be positive");
        }
        long j3 = j % j2;
        return j3 >= 0 ? j3 : j3 + j2;
    }

    static long multiplyFraction(long j, long j2, long j3) {
        if (j == 1) {
            return j2 / j3;
        }
        long jGcd = gcd(j, j3);
        return (j / jGcd) * (j2 / (j3 / jGcd));
    }

    @GwtIncompatible
    public static long pow(long j, int i) {
        MathPreconditions.checkNonNegative("exponent", i);
        if (-2 > j || j > 2) {
            long j2 = j;
            long j3 = 1;
            while (i != 0) {
                if (i == 1) {
                    return j3 * j2;
                }
                j3 *= (i & 1) == 0 ? 1L : j2;
                j2 *= j2;
                i >>= 1;
            }
            return j3;
        }
        int i2 = (int) j;
        if (i2 == -2) {
            if (i < 64) {
                return (i & 1) == 0 ? 1 << i : -(1 << i);
            }
            return 0L;
        }
        if (i2 == -1) {
            return (i & 1) == 0 ? 1L : -1L;
        }
        if (i2 == 0) {
            return i == 0 ? 1L : 0L;
        }
        if (i2 == 1) {
            return 1L;
        }
        if (i2 != 2) {
            throw new AssertionError();
        }
        if (i < 64) {
            return 1 << i;
        }
        return 0L;
    }

    @Beta
    public static long saturatedAdd(long j, long j2) {
        long j3 = j + j2;
        return (((j2 ^ j) > 0L ? 1 : ((j2 ^ j) == 0L ? 0 : -1)) < 0) | ((j ^ j3) >= 0) ? j3 : ((j3 >>> 63) ^ 1) + LongCompanionObject.MAX_VALUE;
    }

    @Beta
    public static long saturatedMultiply(long j, long j2) {
        int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(j ^ (-1)) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros((-1) ^ j2);
        if (iNumberOfLeadingZeros > 65) {
            return j * j2;
        }
        long j3 = ((j ^ j2) >>> 63) + LongCompanionObject.MAX_VALUE;
        if ((iNumberOfLeadingZeros < 64) || ((j < 0) & (j2 == Long.MIN_VALUE))) {
            return j3;
        }
        long j4 = j * j2;
        return (j == 0 || j4 / j == j2) ? j4 : j3;
    }

    @Beta
    public static long saturatedPow(long j, int i) {
        MathPreconditions.checkNonNegative("exponent", i);
        long jSaturatedMultiply = 1;
        if (!(j >= -2) || !(j <= 2)) {
            long j2 = ((j >>> 63) & ((long) (i & 1))) + LongCompanionObject.MAX_VALUE;
            while (i != 0) {
                if (i == 1) {
                    return saturatedMultiply(jSaturatedMultiply, j);
                }
                if ((i & 1) != 0) {
                    jSaturatedMultiply = saturatedMultiply(jSaturatedMultiply, j);
                }
                i >>= 1;
                if (i > 0) {
                    if ((-3037000499L > j) || (j > FLOOR_SQRT_MAX_LONG)) {
                        return j2;
                    }
                    j *= j;
                }
            }
            return jSaturatedMultiply;
        }
        int i2 = (int) j;
        if (i2 == -2) {
            if (i >= 64) {
                return ((long) (i & 1)) + LongCompanionObject.MAX_VALUE;
            }
            return (i & 1) == 0 ? 1 << i : (-1) << i;
        }
        if (i2 == -1) {
            return (i & 1) == 0 ? 1L : -1L;
        }
        if (i2 == 0) {
            return i == 0 ? 1L : 0L;
        }
        if (i2 == 1) {
            return 1L;
        }
        if (i2 == 2) {
            return i >= 63 ? LongCompanionObject.MAX_VALUE : 1 << i;
        }
        throw new AssertionError();
    }

    @Beta
    public static long saturatedSubtract(long j, long j2) {
        long j3 = j - j2;
        return (((j2 ^ j) > 0L ? 1 : ((j2 ^ j) == 0L ? 0 : -1)) >= 0) | ((j ^ j3) >= 0) ? j3 : ((j3 >>> 63) ^ 1) + LongCompanionObject.MAX_VALUE;
    }

    @GwtIncompatible
    public static long sqrt(long j, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", j);
        if (fitsInInt(j)) {
            return IntMath.sqrt((int) j, roundingMode);
        }
        long jSqrt = (long) Math.sqrt(j);
        long j2 = jSqrt * jSqrt;
        switch (C06621.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(j2 == j);
                return jSqrt;
            case 2:
            case 3:
                return j < j2 ? jSqrt - 1 : jSqrt;
            case 4:
            case 5:
                return j > j2 ? jSqrt + 1 : jSqrt;
            case 6:
            case 7:
            case 8:
                long j3 = jSqrt - ((long) (j >= j2 ? 0 : 1));
                return j3 + ((long) lessThanBranchFree((j3 * j3) + j3, j));
            default:
                throw new AssertionError();
        }
    }
}
