package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtIncompatible
public final class Stats implements Serializable {
    static final int BYTES = 40;
    private static final long serialVersionUID = 0;
    private final long count;
    private final double max;
    private final double mean;
    private final double min;
    private final double sumOfSquaresOfDeltas;

    Stats(long j, double d, double d2, double d3, double d4) {
        this.count = j;
        this.mean = d;
        this.sumOfSquaresOfDeltas = d2;
        this.min = d3;
        this.max = d4;
    }

    public static Stats fromByteArray(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkArgument(bArr.length == 40, "Expected Stats.BYTES = %s remaining , got %s", 40, bArr.length);
        return readFrom(ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN));
    }

    public static double meanOf(Iterable<? extends Number> iterable) {
        return meanOf(iterable.iterator());
    }

    public static double meanOf(Iterator<? extends Number> it) {
        Preconditions.checkArgument(it.hasNext());
        double dDoubleValue = it.next().doubleValue();
        long j = 1;
        while (it.hasNext()) {
            double dDoubleValue2 = it.next().doubleValue();
            j++;
            if (Doubles.isFinite(dDoubleValue2) && Doubles.isFinite(dDoubleValue)) {
                double d = j;
                Double.isNaN(d);
                dDoubleValue += (dDoubleValue2 - dDoubleValue) / d;
            } else {
                dDoubleValue = StatsAccumulator.calculateNewMeanNonFinite(dDoubleValue, dDoubleValue2);
            }
        }
        return dDoubleValue;
    }

    public static double meanOf(double... dArr) {
        Preconditions.checkArgument(dArr.length > 0);
        double dCalculateNewMeanNonFinite = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            double d = dArr[i];
            if (Doubles.isFinite(d) && Doubles.isFinite(dCalculateNewMeanNonFinite)) {
                double d2 = i + 1;
                Double.isNaN(d2);
                dCalculateNewMeanNonFinite += (d - dCalculateNewMeanNonFinite) / d2;
            } else {
                dCalculateNewMeanNonFinite = StatsAccumulator.calculateNewMeanNonFinite(dCalculateNewMeanNonFinite, d);
            }
        }
        return dCalculateNewMeanNonFinite;
    }

    public static double meanOf(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        double dCalculateNewMeanNonFinite = iArr[0];
        for (int i = 1; i < iArr.length; i++) {
            double d = iArr[i];
            if (Doubles.isFinite(d) && Doubles.isFinite(dCalculateNewMeanNonFinite)) {
                Double.isNaN(d);
                double d2 = i + 1;
                Double.isNaN(d2);
                dCalculateNewMeanNonFinite += (d - dCalculateNewMeanNonFinite) / d2;
            } else {
                dCalculateNewMeanNonFinite = StatsAccumulator.calculateNewMeanNonFinite(dCalculateNewMeanNonFinite, d);
            }
        }
        return dCalculateNewMeanNonFinite;
    }

    public static double meanOf(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        double dCalculateNewMeanNonFinite = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            double d = jArr[i];
            if (Doubles.isFinite(d) && Doubles.isFinite(dCalculateNewMeanNonFinite)) {
                Double.isNaN(d);
                double d2 = i + 1;
                Double.isNaN(d2);
                dCalculateNewMeanNonFinite += (d - dCalculateNewMeanNonFinite) / d2;
            } else {
                dCalculateNewMeanNonFinite = StatsAccumulator.calculateNewMeanNonFinite(dCalculateNewMeanNonFinite, d);
            }
        }
        return dCalculateNewMeanNonFinite;
    }

    /* JADX INFO: renamed from: of */
    public static Stats m595of(Iterable<? extends Number> iterable) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(iterable);
        return statsAccumulator.snapshot();
    }

    /* JADX INFO: renamed from: of */
    public static Stats m596of(Iterator<? extends Number> it) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(it);
        return statsAccumulator.snapshot();
    }

    /* JADX INFO: renamed from: of */
    public static Stats m597of(double... dArr) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(dArr);
        return statsAccumulator.snapshot();
    }

    /* JADX INFO: renamed from: of */
    public static Stats m598of(int... iArr) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(iArr);
        return statsAccumulator.snapshot();
    }

    /* JADX INFO: renamed from: of */
    public static Stats m599of(long... jArr) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(jArr);
        return statsAccumulator.snapshot();
    }

    static Stats readFrom(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        Preconditions.checkArgument(byteBuffer.remaining() >= 40, "Expected at least Stats.BYTES = %s remaining , got %s", 40, byteBuffer.remaining());
        return new Stats(byteBuffer.getLong(), byteBuffer.getDouble(), byteBuffer.getDouble(), byteBuffer.getDouble(), byteBuffer.getDouble());
    }

    public long count() {
        return this.count;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Stats stats = (Stats) obj;
        return this.count == stats.count && Double.doubleToLongBits(this.mean) == Double.doubleToLongBits(stats.mean) && Double.doubleToLongBits(this.sumOfSquaresOfDeltas) == Double.doubleToLongBits(stats.sumOfSquaresOfDeltas) && Double.doubleToLongBits(this.min) == Double.doubleToLongBits(stats.min) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(stats.max);
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.count), Double.valueOf(this.mean), Double.valueOf(this.sumOfSquaresOfDeltas), Double.valueOf(this.min), Double.valueOf(this.max));
    }

    public double max() {
        Preconditions.checkState(this.count != 0);
        return this.max;
    }

    public double mean() {
        Preconditions.checkState(this.count != 0);
        return this.mean;
    }

    public double min() {
        Preconditions.checkState(this.count != 0);
        return this.min;
    }

    public double populationStandardDeviation() {
        return Math.sqrt(populationVariance());
    }

    public double populationVariance() {
        Preconditions.checkState(this.count > 0);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count == 1) {
            return 0.0d;
        }
        double dEnsureNonNegative = DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas);
        double dCount = count();
        Double.isNaN(dCount);
        return dEnsureNonNegative / dCount;
    }

    public double sampleStandardDeviation() {
        return Math.sqrt(sampleVariance());
    }

    public double sampleVariance() {
        Preconditions.checkState(this.count > 1);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        double dEnsureNonNegative = DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas);
        double d = this.count - 1;
        Double.isNaN(d);
        return dEnsureNonNegative / d;
    }

    public double sum() {
        double d = this.mean;
        double d2 = this.count;
        Double.isNaN(d2);
        return d * d2;
    }

    double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(40).order(ByteOrder.LITTLE_ENDIAN);
        writeTo(byteBufferOrder);
        return byteBufferOrder.array();
    }

    public String toString() {
        return count() > 0 ? MoreObjects.toStringHelper(this).add("count", this.count).add("mean", this.mean).add("populationStandardDeviation", populationStandardDeviation()).add("min", this.min).add("max", this.max).toString() : MoreObjects.toStringHelper(this).add("count", this.count).toString();
    }

    void writeTo(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        Preconditions.checkArgument(byteBuffer.remaining() >= 40, "Expected at least Stats.BYTES = %s remaining , got %s", 40, byteBuffer.remaining());
        byteBuffer.putLong(this.count).putDouble(this.mean).putDouble(this.sumOfSquaresOfDeltas).putDouble(this.min).putDouble(this.max);
    }
}
