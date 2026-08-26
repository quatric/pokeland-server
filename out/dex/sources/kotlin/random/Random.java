package kotlin.random;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Random.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@SinceKotlin(version = "1.3")
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b'\u0018\u0000 \u00182\u00020\u0001:\u0002\u0017\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016¨\u0006\u0019"}, m1984d2 = {"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Companion", "Default", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
public abstract class Random {

    /* JADX INFO: renamed from: Default, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Random defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();

    @JvmField
    @NotNull
    public static final Companion Companion = Companion.INSTANCE;

    /* JADX INFO: compiled from: Random.kt */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Use Default companion object instead")
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, m1984d2 = {"Lkotlin/random/Random$Companion;", "Lkotlin/random/Random;", "()V", "nextBits", "", "bitCount", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class Companion extends Random {
        public static final Companion INSTANCE = new Companion();

        private Companion() {
        }

        @Override // kotlin.random.Random
        public int nextBits(int bitCount) {
            return Random.INSTANCE.nextBits(bitCount);
        }
    }

    /* JADX INFO: renamed from: kotlin.random.Random$Default, reason: from kotlin metadata */
    /* JADX INFO: compiled from: Random.kt */
    @Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, m1984d2 = {"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "()V", "Companion", "Lkotlin/random/Random$Companion;", "Companion$annotations", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "kotlin-stdlib"}, m1985k = 1, m1986mv = {1, 1, 15})
    public static final class Companion extends Random {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Use Default companion object instead")
        public static /* synthetic */ void Companion$annotations() {
        }

        @Override // kotlin.random.Random
        public int nextBits(int bitCount) {
            return Random.defaultRandom.nextBits(bitCount);
        }

        @Override // kotlin.random.Random
        public boolean nextBoolean() {
            return Random.defaultRandom.nextBoolean();
        }

        @Override // kotlin.random.Random
        @NotNull
        public byte[] nextBytes(int size) {
            return Random.defaultRandom.nextBytes(size);
        }

        @Override // kotlin.random.Random
        @NotNull
        public byte[] nextBytes(@NotNull byte[] array) {
            Intrinsics.checkParameterIsNotNull(array, "array");
            return Random.defaultRandom.nextBytes(array);
        }

        @Override // kotlin.random.Random
        @NotNull
        public byte[] nextBytes(@NotNull byte[] array, int fromIndex, int toIndex) {
            Intrinsics.checkParameterIsNotNull(array, "array");
            return Random.defaultRandom.nextBytes(array, fromIndex, toIndex);
        }

        @Override // kotlin.random.Random
        public double nextDouble() {
            return Random.defaultRandom.nextDouble();
        }

        @Override // kotlin.random.Random
        public double nextDouble(double until) {
            return Random.defaultRandom.nextDouble(until);
        }

        @Override // kotlin.random.Random
        public double nextDouble(double from, double until) {
            return Random.defaultRandom.nextDouble(from, until);
        }

        @Override // kotlin.random.Random
        public float nextFloat() {
            return Random.defaultRandom.nextFloat();
        }

        @Override // kotlin.random.Random
        public int nextInt() {
            return Random.defaultRandom.nextInt();
        }

        @Override // kotlin.random.Random
        public int nextInt(int until) {
            return Random.defaultRandom.nextInt(until);
        }

        @Override // kotlin.random.Random
        public int nextInt(int from, int until) {
            return Random.defaultRandom.nextInt(from, until);
        }

        @Override // kotlin.random.Random
        public long nextLong() {
            return Random.defaultRandom.nextLong();
        }

        @Override // kotlin.random.Random
        public long nextLong(long until) {
            return Random.defaultRandom.nextLong(until);
        }

        @Override // kotlin.random.Random
        public long nextLong(long from, long until) {
            return Random.defaultRandom.nextLong(from, until);
        }
    }

    public static /* synthetic */ byte[] nextBytes$default(Random random, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return random.nextBytes(bArr, i, i2);
    }

    public abstract int nextBits(int bitCount);

    public boolean nextBoolean() {
        return nextBits(1) != 0;
    }

    @NotNull
    public byte[] nextBytes(int size) {
        return nextBytes(new byte[size]);
    }

    @NotNull
    public byte[] nextBytes(@NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        return nextBytes(array, 0, array.length);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0015  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public byte[] nextBytes(@NotNull byte[] array, int fromIndex, int toIndex) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(array, "array");
        int length = array.length;
        if (fromIndex >= 0 && length >= fromIndex) {
            int length2 = array.length;
            if (toIndex >= 0 && length2 >= toIndex) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(("fromIndex (" + fromIndex + ") or toIndex (" + toIndex + ") are out of range: 0.." + array.length + '.').toString());
        }
        if (!(fromIndex <= toIndex)) {
            throw new IllegalArgumentException(("fromIndex (" + fromIndex + ") must be not greater than toIndex (" + toIndex + ").").toString());
        }
        int i = (toIndex - fromIndex) / 4;
        int i2 = fromIndex;
        for (int i3 = 0; i3 < i; i3++) {
            int iNextInt = nextInt();
            array[i2] = (byte) iNextInt;
            array[i2 + 1] = (byte) (iNextInt >>> 8);
            array[i2 + 2] = (byte) (iNextInt >>> 16);
            array[i2 + 3] = (byte) (iNextInt >>> 24);
            i2 += 4;
        }
        int i4 = toIndex - i2;
        int iNextBits = nextBits(i4 * 8);
        for (int i5 = 0; i5 < i4; i5++) {
            array[i2 + i5] = (byte) (iNextBits >>> (i5 * 8));
        }
        return array;
    }

    public double nextDouble() {
        return PlatformRandomKt.doubleFromParts(nextBits(26), nextBits(27));
    }

    public double nextDouble(double until) {
        return nextDouble(0.0d, until);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0044  */
    public double nextDouble(double from, double until) {
        double dNextDouble;
        RandomKt.checkRangeBounds(from, until);
        double d = until - from;
        if (Double.isInfinite(d)) {
            if ((Double.isInfinite(from) || Double.isNaN(from)) ? false : true) {
                if ((Double.isInfinite(until) || Double.isNaN(until)) ? false : true) {
                    double dNextDouble2 = nextDouble();
                    double d2 = 2;
                    Double.isNaN(d2);
                    Double.isNaN(d2);
                    double d3 = dNextDouble2 * ((until / d2) - (from / d2));
                    dNextDouble = from + d3 + d3;
                } else {
                    dNextDouble = from + (nextDouble() * d);
                }
            } else {
                dNextDouble = from + (nextDouble() * d);
            }
        } else {
            dNextDouble = from + (nextDouble() * d);
        }
        return dNextDouble >= until ? Math.nextAfter(until, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY()) : dNextDouble;
    }

    public float nextFloat() {
        return nextBits(24) / 16777216;
    }

    public int nextInt() {
        return nextBits(32);
    }

    public int nextInt(int until) {
        return nextInt(0, until);
    }

    public int nextInt(int from, int until) {
        int iNextInt;
        int i;
        int iNextBits;
        RandomKt.checkRangeBounds(from, until);
        int i2 = until - from;
        if (i2 > 0 || i2 == Integer.MIN_VALUE) {
            if (((-i2) & i2) == i2) {
                iNextBits = nextBits(RandomKt.fastLog2(i2));
            } else {
                do {
                    iNextInt = nextInt() >>> 1;
                    i = iNextInt % i2;
                } while ((iNextInt - i) + (i2 - 1) < 0);
                iNextBits = i;
            }
            return from + iNextBits;
        }
        while (true) {
            int iNextInt2 = nextInt();
            if (from <= iNextInt2 && until > iNextInt2) {
                return iNextInt2;
            }
        }
    }

    public long nextLong() {
        return (((long) nextInt()) << 32) + ((long) nextInt());
    }

    public long nextLong(long until) {
        return nextLong(0L, until);
    }

    public long nextLong(long from, long until) {
        long jNextLong;
        long j;
        long jNextBits;
        int iNextInt;
        RandomKt.checkRangeBounds(from, until);
        long j2 = until - from;
        if (j2 > 0) {
            if (((-j2) & j2) == j2) {
                int i = (int) j2;
                int i2 = (int) (j2 >>> 32);
                if (i != 0) {
                    iNextInt = nextBits(RandomKt.fastLog2(i));
                } else if (i2 == 1) {
                    iNextInt = nextInt();
                } else {
                    jNextBits = (((long) nextBits(RandomKt.fastLog2(i2))) << 32) + ((long) nextInt());
                }
                jNextBits = ((long) iNextInt) & 4294967295L;
            } else {
                do {
                    jNextLong = nextLong() >>> 1;
                    j = jNextLong % j2;
                } while ((jNextLong - j) + (j2 - 1) < 0);
                jNextBits = j;
            }
            return from + jNextBits;
        }
        while (true) {
            long jNextLong2 = nextLong();
            if (from <= jNextLong2 && until > jNextLong2) {
                return jNextLong2;
            }
        }
    }
}
