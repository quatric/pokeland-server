package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtIncompatible
public abstract class LinearTransformation {

    public static final class LinearTransformationBuilder {

        /* JADX INFO: renamed from: x1 */
        private final double f347x1;

        /* JADX INFO: renamed from: y1 */
        private final double f348y1;

        private LinearTransformationBuilder(double d, double d2) {
            this.f347x1 = d;
            this.f348y1 = d2;
        }

        public LinearTransformation and(double d, double d2) {
            Preconditions.checkArgument(DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2));
            double d3 = this.f347x1;
            if (d != d3) {
                return withSlope((d2 - this.f348y1) / (d - d3));
            }
            Preconditions.checkArgument(d2 != this.f348y1);
            return new VerticalLinearTransformation(this.f347x1);
        }

        public LinearTransformation withSlope(double d) {
            Preconditions.checkArgument(!Double.isNaN(d));
            return DoubleUtils.isFinite(d) ? new RegularLinearTransformation(d, this.f348y1 - (this.f347x1 * d)) : new VerticalLinearTransformation(this.f347x1);
        }
    }

    private static final class NaNLinearTransformation extends LinearTransformation {
        static final NaNLinearTransformation INSTANCE = new NaNLinearTransformation();

        private NaNLinearTransformation() {
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            return this;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            return Double.NaN;
        }

        public String toString() {
            return "NaN";
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d) {
            return Double.NaN;
        }
    }

    private static final class RegularLinearTransformation extends LinearTransformation {

        @LazyInit
        LinearTransformation inverse;
        final double slope;
        final double yIntercept;

        RegularLinearTransformation(double d, double d2) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = null;
        }

        RegularLinearTransformation(double d, double d2, LinearTransformation linearTransformation) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = linearTransformation;
        }

        private LinearTransformation createInverse() {
            double d = this.slope;
            return d != 0.0d ? new RegularLinearTransformation(1.0d / d, (this.yIntercept * (-1.0d)) / d, this) : new VerticalLinearTransformation(this.yIntercept, this);
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.inverse;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation linearTransformationCreateInverse = createInverse();
            this.inverse = linearTransformationCreateInverse;
            return linearTransformationCreateInverse;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return this.slope == 0.0d;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            return this.slope;
        }

        public String toString() {
            return String.format("y = %g * x + %g", Double.valueOf(this.slope), Double.valueOf(this.yIntercept));
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d) {
            return (d * this.slope) + this.yIntercept;
        }
    }

    private static final class VerticalLinearTransformation extends LinearTransformation {

        @LazyInit
        LinearTransformation inverse;

        /* JADX INFO: renamed from: x */
        final double f349x;

        VerticalLinearTransformation(double d) {
            this.f349x = d;
            this.inverse = null;
        }

        VerticalLinearTransformation(double d, LinearTransformation linearTransformation) {
            this.f349x = d;
            this.inverse = linearTransformation;
        }

        private LinearTransformation createInverse() {
            return new RegularLinearTransformation(0.0d, this.f349x, this);
        }

        @Override // com.google.common.math.LinearTransformation
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.inverse;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation linearTransformationCreateInverse = createInverse();
            this.inverse = linearTransformationCreateInverse;
            return linearTransformationCreateInverse;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isHorizontal() {
            return false;
        }

        @Override // com.google.common.math.LinearTransformation
        public boolean isVertical() {
            return true;
        }

        @Override // com.google.common.math.LinearTransformation
        public double slope() {
            throw new IllegalStateException();
        }

        public String toString() {
            return String.format("x = %g", Double.valueOf(this.f349x));
        }

        @Override // com.google.common.math.LinearTransformation
        public double transform(double d) {
            throw new IllegalStateException();
        }
    }

    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.INSTANCE;
    }

    public static LinearTransformation horizontal(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new RegularLinearTransformation(0.0d, d);
    }

    public static LinearTransformationBuilder mapping(double d, double d2) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2));
        return new LinearTransformationBuilder(d, d2);
    }

    public static LinearTransformation vertical(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new VerticalLinearTransformation(d);
    }

    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double d);
}
