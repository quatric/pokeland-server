package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Random.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0004H\u0007\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\fH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0000\u001a\u0014\u0010\u000f\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001a\u0014\u0010\u0012\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0013H\u0007\u001a\u0014\u0010\u0014\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u0000¨\u0006\u0016"}, m1984d2 = {"Random", "Lkotlin/random/Random;", "seed", "", "", "boundsErrorMessage", "", "from", "", "until", "checkRangeBounds", "", "", "fastLog2", "value", "nextInt", "range", "Lkotlin/ranges/IntRange;", "nextLong", "Lkotlin/ranges/LongRange;", "takeUpperBits", "bitCount", "kotlin-stdlib"}, m1985k = 2, m1986mv = {1, 1, 15})
public final class RandomKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random Random(int i) {
        return new XorWowRandom(i, i >> 31);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random Random(long j) {
        return new XorWowRandom((int) j, (int) (j >> 32));
    }

    @NotNull
    public static final String boundsErrorMessage(@NotNull Object from, @NotNull Object until) {
        Intrinsics.checkParameterIsNotNull(from, "from");
        Intrinsics.checkParameterIsNotNull(until, "until");
        return "Random range is empty: [" + from + ", " + until + ").";
    }

    public static final void checkRangeBounds(double d, double d2) {
        if (!(d2 > d)) {
            throw new IllegalArgumentException(boundsErrorMessage(Double.valueOf(d), Double.valueOf(d2)).toString());
        }
    }

    public static final void checkRangeBounds(int i, int i2) {
        if (!(i2 > i)) {
            throw new IllegalArgumentException(boundsErrorMessage(Integer.valueOf(i), Integer.valueOf(i2)).toString());
        }
    }

    public static final void checkRangeBounds(long j, long j2) {
        if (!(j2 > j)) {
            throw new IllegalArgumentException(boundsErrorMessage(Long.valueOf(j), Long.valueOf(j2)).toString());
        }
    }

    public static final int fastLog2(int i) {
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    @SinceKotlin(version = "1.3")
    public static final int nextInt(@NotNull Random nextInt, @NotNull IntRange range) {
        Intrinsics.checkParameterIsNotNull(nextInt, "$this$nextInt");
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (!range.isEmpty()) {
            if (range.getLast() < Integer.MAX_VALUE) {
                return nextInt.nextInt(range.getFirst(), range.getLast() + 1);
            }
            return range.getFirst() > Integer.MIN_VALUE ? nextInt.nextInt(range.getFirst() - 1, range.getLast()) + 1 : nextInt.nextInt();
        }
        throw new IllegalArgumentException("Cannot get random in empty range: " + range);
    }

    @SinceKotlin(version = "1.3")
    public static final long nextLong(@NotNull Random nextLong, @NotNull LongRange range) {
        Intrinsics.checkParameterIsNotNull(nextLong, "$this$nextLong");
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (!range.isEmpty()) {
            if (range.getLast() < LongCompanionObject.MAX_VALUE) {
                return nextLong.nextLong(range.getFirst(), range.getLast() + 1);
            }
            return range.getFirst() > Long.MIN_VALUE ? nextLong.nextLong(range.getFirst() - 1, range.getLast()) + 1 : nextLong.nextLong();
        }
        throw new IllegalArgumentException("Cannot get random in empty range: " + range);
    }

    public static final int takeUpperBits(int i, int i2) {
        return (i >>> (32 - i2)) & ((-i2) >> 31);
    }
}
