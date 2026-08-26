package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
abstract class SmoothRateLimiter extends RateLimiter {
    double maxPermits;
    private long nextFreeTicketMicros;
    double stableIntervalMicros;
    double storedPermits;

    static final class SmoothBursty extends SmoothRateLimiter {
        final double maxBurstSeconds;

        SmoothBursty(RateLimiter.SleepingStopwatch sleepingStopwatch, double d) {
            super(sleepingStopwatch);
            this.maxBurstSeconds = d;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * d;
            if (d3 == Double.POSITIVE_INFINITY) {
                this.storedPermits = this.maxPermits;
            } else {
                this.storedPermits = d3 != 0.0d ? (this.storedPermits * this.maxPermits) / d3 : 0.0d;
            }
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        long storedPermitsToWaitTime(double d, double d2) {
            return 0L;
        }
    }

    static final class SmoothWarmingUp extends SmoothRateLimiter {
        private double coldFactor;
        private double slope;
        private double thresholdPermits;
        private final long warmupPeriodMicros;

        SmoothWarmingUp(RateLimiter.SleepingStopwatch sleepingStopwatch, long j, TimeUnit timeUnit, double d) {
            super(sleepingStopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(j);
            this.coldFactor = d;
        }

        private double permitsToTime(double d) {
            return this.stableIntervalMicros + (d * this.slope);
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        double coolDownIntervalMicros() {
            double d = this.warmupPeriodMicros;
            double d2 = this.maxPermits;
            Double.isNaN(d);
            return d / d2;
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            double d4 = this.coldFactor * d2;
            long j = this.warmupPeriodMicros;
            double d5 = j;
            Double.isNaN(d5);
            this.thresholdPermits = (d5 * 0.5d) / d2;
            double d6 = this.thresholdPermits;
            double d7 = j;
            Double.isNaN(d7);
            this.maxPermits = d6 + ((d7 * 2.0d) / (d2 + d4));
            this.slope = (d4 - d2) / (this.maxPermits - this.thresholdPermits);
            if (d3 == Double.POSITIVE_INFINITY) {
                this.storedPermits = 0.0d;
            } else {
                this.storedPermits = d3 == 0.0d ? this.maxPermits : (this.storedPermits * this.maxPermits) / d3;
            }
        }

        @Override // com.google.common.util.concurrent.SmoothRateLimiter
        long storedPermitsToWaitTime(double d, double d2) {
            long jPermitsToTime;
            double d3 = d - this.thresholdPermits;
            if (d3 > 0.0d) {
                double dMin = Math.min(d3, d2);
                jPermitsToTime = (long) (((permitsToTime(d3) + permitsToTime(d3 - dMin)) * dMin) / 2.0d);
                d2 -= dMin;
            } else {
                jPermitsToTime = 0;
            }
            double d4 = jPermitsToTime;
            double d5 = this.stableIntervalMicros * d2;
            Double.isNaN(d4);
            return (long) (d4 + d5);
        }
    }

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
        this.nextFreeTicketMicros = 0L;
    }

    abstract double coolDownIntervalMicros();

    @Override // com.google.common.util.concurrent.RateLimiter
    final double doGetRate() {
        double micros = TimeUnit.SECONDS.toMicros(1L);
        double d = this.stableIntervalMicros;
        Double.isNaN(micros);
        return micros / d;
    }

    abstract void doSetRate(double d, double d2);

    @Override // com.google.common.util.concurrent.RateLimiter
    final void doSetRate(double d, long j) {
        resync(j);
        double micros = TimeUnit.SECONDS.toMicros(1L);
        Double.isNaN(micros);
        double d2 = micros / d;
        this.stableIntervalMicros = d2;
        doSetRate(d, d2);
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final long queryEarliestAvailable(long j) {
        return this.nextFreeTicketMicros;
    }

    @Override // com.google.common.util.concurrent.RateLimiter
    final long reserveEarliestAvailable(int i, long j) {
        resync(j);
        long j2 = this.nextFreeTicketMicros;
        double d = i;
        double dMin = Math.min(d, this.storedPermits);
        Double.isNaN(d);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, storedPermitsToWaitTime(this.storedPermits, dMin) + ((long) ((d - dMin) * this.stableIntervalMicros)));
        this.storedPermits -= dMin;
        return j2;
    }

    void resync(long j) {
        long j2 = this.nextFreeTicketMicros;
        if (j > j2) {
            double d = j - j2;
            double dCoolDownIntervalMicros = coolDownIntervalMicros();
            Double.isNaN(d);
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + (d / dCoolDownIntervalMicros));
            this.nextFreeTicketMicros = j;
        }
    }

    abstract long storedPermitsToWaitTime(double d, double d2);
}
