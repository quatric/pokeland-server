package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(serializable = true)
final class CompoundOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final ImmutableList<Comparator<? super T>> comparators;

    CompoundOrdering(Iterable<? extends Comparator<? super T>> iterable) {
        this.comparators = ImmutableList.copyOf(iterable);
    }

    CompoundOrdering(Comparator<? super T> comparator, Comparator<? super T> comparator2) {
        this.comparators = ImmutableList.m493of(comparator, comparator2);
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(T t, T t2) {
        int size = this.comparators.size();
        for (int i = 0; i < size; i++) {
            int iCompare = this.comparators.get(i).compare(t, t2);
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CompoundOrdering) {
            return this.comparators.equals(((CompoundOrdering) obj).comparators);
        }
        return false;
    }

    public int hashCode() {
        return this.comparators.hashCode();
    }

    public String toString() {
        return "Ordering.compound(" + this.comparators + ")";
    }
}
