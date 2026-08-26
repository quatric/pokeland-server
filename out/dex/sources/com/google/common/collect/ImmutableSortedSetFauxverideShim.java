package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
abstract class ImmutableSortedSetFauxverideShim<E> extends ImmutableSet<E> {
    ImmutableSortedSetFauxverideShim() {
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> copyOf(E[] eArr) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* JADX INFO: renamed from: of */
    public static <E> ImmutableSortedSet<E> m578of(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* JADX INFO: renamed from: of */
    public static <E> ImmutableSortedSet<E> m579of(E e, E e2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* JADX INFO: renamed from: of */
    public static <E> ImmutableSortedSet<E> m580of(E e, E e2, E e3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* JADX INFO: renamed from: of */
    public static <E> ImmutableSortedSet<E> m581of(E e, E e2, E e3, E e4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* JADX INFO: renamed from: of */
    public static <E> ImmutableSortedSet<E> m582of(E e, E e2, E e3, E e4, E e5) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* JADX INFO: renamed from: of */
    public static <E> ImmutableSortedSet<E> m583of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        throw new UnsupportedOperationException();
    }
}
