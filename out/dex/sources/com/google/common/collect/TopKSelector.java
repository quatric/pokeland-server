package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
final class TopKSelector<T> {
    private final T[] buffer;
    private int bufferSize;
    private final Comparator<? super T> comparator;

    /* JADX INFO: renamed from: k */
    private final int f322k;
    private T threshold;

    private TopKSelector(Comparator<? super T> comparator, int i) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator, "comparator");
        this.f322k = i;
        Preconditions.checkArgument(i >= 0, "k must be nonnegative, was %s", i);
        this.buffer = (T[]) new Object[i * 2];
        this.bufferSize = 0;
        this.threshold = null;
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int i) {
        return greatest(i, Ordering.natural());
    }

    public static <T> TopKSelector<T> greatest(int i, Comparator<? super T> comparator) {
        return new TopKSelector<>(Ordering.from(comparator).reverse(), i);
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int i) {
        return least(i, Ordering.natural());
    }

    public static <T> TopKSelector<T> least(int i, Comparator<? super T> comparator) {
        return new TopKSelector<>(comparator, i);
    }

    private int partition(int i, int i2, int i3) {
        T[] tArr = this.buffer;
        T t = tArr[i3];
        tArr[i3] = tArr[i2];
        int i4 = i;
        while (i < i2) {
            if (this.comparator.compare(this.buffer[i], t) < 0) {
                swap(i4, i);
                i4++;
            }
            i++;
        }
        T[] tArr2 = this.buffer;
        tArr2[i2] = tArr2[i4];
        tArr2[i4] = t;
        return i4;
    }

    private void swap(int i, int i2) {
        T[] tArr = this.buffer;
        T t = tArr[i];
        tArr[i] = tArr[i2];
        tArr[i2] = t;
    }

    private void trim() {
        int i = (this.f322k * 2) - 1;
        int iLog2 = IntMath.log2(i + 0, RoundingMode.CEILING) * 3;
        int iMax = 0;
        int i2 = 0;
        int i3 = 0;
        while (iMax < i) {
            int iPartition = partition(iMax, i, ((iMax + i) + 1) >>> 1);
            int i4 = this.f322k;
            if (iPartition <= i4) {
                if (iPartition >= i4) {
                    break;
                }
                iMax = Math.max(iPartition, iMax + 1);
                i3 = iPartition;
            } else {
                i = iPartition - 1;
            }
            i2++;
            if (i2 >= iLog2) {
                Arrays.sort(this.buffer, iMax, i, this.comparator);
                break;
            }
        }
        this.bufferSize = this.f322k;
        this.threshold = this.buffer[i3];
        while (true) {
            i3++;
            if (i3 >= this.f322k) {
                return;
            }
            if (this.comparator.compare(this.buffer[i3], this.threshold) > 0) {
                this.threshold = this.buffer[i3];
            }
        }
    }

    public void offer(@Nullable T t) {
        int i = this.f322k;
        if (i == 0) {
            return;
        }
        int i2 = this.bufferSize;
        if (i2 == 0) {
            this.buffer[0] = t;
            this.threshold = t;
            this.bufferSize = 1;
            return;
        }
        if (i2 < i) {
            T[] tArr = this.buffer;
            this.bufferSize = i2 + 1;
            tArr[i2] = t;
            if (this.comparator.compare(t, this.threshold) > 0) {
                this.threshold = t;
                return;
            }
            return;
        }
        if (this.comparator.compare(t, this.threshold) < 0) {
            T[] tArr2 = this.buffer;
            int i3 = this.bufferSize;
            this.bufferSize = i3 + 1;
            tArr2[i3] = t;
            if (this.bufferSize == this.f322k * 2) {
                trim();
            }
        }
    }

    public void offerAll(Iterable<? extends T> iterable) {
        offerAll(iterable.iterator());
    }

    public void offerAll(Iterator<? extends T> it) {
        while (it.hasNext()) {
            offer(it.next());
        }
    }

    public List<T> topK() {
        Arrays.sort(this.buffer, 0, this.bufferSize, this.comparator);
        int i = this.bufferSize;
        int i2 = this.f322k;
        if (i > i2) {
            T[] tArr = this.buffer;
            Arrays.fill(tArr, i2, tArr.length, (Object) null);
            int i3 = this.f322k;
            this.bufferSize = i3;
            this.threshold = this.buffer[i3 - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
    }
}
