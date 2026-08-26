package com.google.api.client.util;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ExponentialBackOff implements BackOff {
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    public static final double DEFAULT_MULTIPLIER = 1.5d;
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5d;
    private int currentIntervalMillis;
    private final int initialIntervalMillis;
    private final int maxElapsedTimeMillis;
    private final int maxIntervalMillis;
    private final double multiplier;
    private final NanoClock nanoClock;
    private final double randomizationFactor;
    long startTimeNanos;

    public static class Builder {
        int initialIntervalMillis = 500;
        double randomizationFactor = 0.5d;
        double multiplier = 1.5d;
        int maxIntervalMillis = 60000;
        int maxElapsedTimeMillis = 900000;
        NanoClock nanoClock = NanoClock.SYSTEM;

        public ExponentialBackOff build() {
            return new ExponentialBackOff(this);
        }

        public final int getInitialIntervalMillis() {
            return this.initialIntervalMillis;
        }

        public final int getMaxElapsedTimeMillis() {
            return this.maxElapsedTimeMillis;
        }

        public final int getMaxIntervalMillis() {
            return this.maxIntervalMillis;
        }

        public final double getMultiplier() {
            return this.multiplier;
        }

        public final NanoClock getNanoClock() {
            return this.nanoClock;
        }

        public final double getRandomizationFactor() {
            return this.randomizationFactor;
        }

        public Builder setInitialIntervalMillis(int i) {
            this.initialIntervalMillis = i;
            return this;
        }

        public Builder setMaxElapsedTimeMillis(int i) {
            this.maxElapsedTimeMillis = i;
            return this;
        }

        public Builder setMaxIntervalMillis(int i) {
            this.maxIntervalMillis = i;
            return this;
        }

        public Builder setMultiplier(double d) {
            this.multiplier = d;
            return this;
        }

        public Builder setNanoClock(NanoClock nanoClock) {
            this.nanoClock = (NanoClock) Preconditions.checkNotNull(nanoClock);
            return this;
        }

        public Builder setRandomizationFactor(double d) {
            this.randomizationFactor = d;
            return this;
        }
    }

    public ExponentialBackOff() {
        this(new Builder());
    }

    protected ExponentialBackOff(Builder builder) {
        this.initialIntervalMillis = builder.initialIntervalMillis;
        this.randomizationFactor = builder.randomizationFactor;
        this.multiplier = builder.multiplier;
        this.maxIntervalMillis = builder.maxIntervalMillis;
        this.maxElapsedTimeMillis = builder.maxElapsedTimeMillis;
        this.nanoClock = builder.nanoClock;
        Preconditions.checkArgument(this.initialIntervalMillis > 0);
        double d = this.randomizationFactor;
        Preconditions.checkArgument(0.0d <= d && d < 1.0d);
        Preconditions.checkArgument(this.multiplier >= 1.0d);
        Preconditions.checkArgument(this.maxIntervalMillis >= this.initialIntervalMillis);
        Preconditions.checkArgument(this.maxElapsedTimeMillis > 0);
        reset();
    }

    static int getRandomValueFromInterval(double d, double d2, int i) {
        double d3 = i;
        Double.isNaN(d3);
        double d4 = d * d3;
        Double.isNaN(d3);
        double d5 = d3 - d4;
        Double.isNaN(d3);
        return (int) (d5 + (d2 * (((d3 + d4) - d5) + 1.0d)));
    }

    private void incrementCurrentInterval() {
        int i = this.currentIntervalMillis;
        double d = i;
        int i2 = this.maxIntervalMillis;
        double d2 = i2;
        double d3 = this.multiplier;
        Double.isNaN(d2);
        if (d >= d2 / d3) {
            this.currentIntervalMillis = i2;
            return;
        }
        double d4 = i;
        Double.isNaN(d4);
        this.currentIntervalMillis = (int) (d4 * d3);
    }

    public final int getCurrentIntervalMillis() {
        return this.currentIntervalMillis;
    }

    public final long getElapsedTimeMillis() {
        return (this.nanoClock.nanoTime() - this.startTimeNanos) / 1000000;
    }

    public final int getInitialIntervalMillis() {
        return this.initialIntervalMillis;
    }

    public final int getMaxElapsedTimeMillis() {
        return this.maxElapsedTimeMillis;
    }

    public final int getMaxIntervalMillis() {
        return this.maxIntervalMillis;
    }

    public final double getMultiplier() {
        return this.multiplier;
    }

    public final double getRandomizationFactor() {
        return this.randomizationFactor;
    }

    @Override // com.google.api.client.util.BackOff
    public long nextBackOffMillis() throws IOException {
        if (getElapsedTimeMillis() > this.maxElapsedTimeMillis) {
            return -1L;
        }
        int randomValueFromInterval = getRandomValueFromInterval(this.randomizationFactor, Math.random(), this.currentIntervalMillis);
        incrementCurrentInterval();
        return randomValueFromInterval;
    }

    @Override // com.google.api.client.util.BackOff
    public final void reset() {
        this.currentIntervalMillis = this.initialIntervalMillis;
        this.startTimeNanos = this.nanoClock.nanoTime();
    }
}
