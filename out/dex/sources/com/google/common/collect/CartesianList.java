package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
final class CartesianList<E> extends AbstractList<List<E>> implements RandomAccess {
    private final transient ImmutableList<List<E>> axes;
    private final transient int[] axesSizeProduct;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    CartesianList(ImmutableList<List<E>> immutableList) {
        this.axes = immutableList;
        int[] iArr = new int[immutableList.size() + 1];
        iArr[immutableList.size()] = 1;
        try {
            for (int size = immutableList.size() - 1; size >= 0; size--) {
                iArr[size] = IntMath.checkedMultiply(iArr[size + 1], immutableList.get(size).size());
            }
            this.axesSizeProduct = iArr;
        } catch (ArithmeticException unused) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
    }

    static <E> List<List<E>> create(List<? extends List<? extends E>> list) {
        ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        Iterator<? extends List<? extends E>> it = list.iterator();
        while (it.hasNext()) {
            ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) it.next());
            if (immutableListCopyOf.isEmpty()) {
                return ImmutableList.m491of();
            }
            builder.add(immutableListCopyOf);
        }
        return new CartesianList(builder.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getAxisIndexForProductIndex(int i, int i2) {
        return (i / this.axesSizeProduct[i2 + 1]) % this.axes.get(i2).size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(@Nullable Object obj) {
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        if (list.size() != this.axes.size()) {
            return false;
        }
        ListIterator<E> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (!this.axes.get(listIterator.nextIndex()).contains(listIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public ImmutableList<E> get(final int i) {
        Preconditions.checkElementIndex(i, size());
        return new ImmutableList<E>() { // from class: com.google.common.collect.CartesianList.1
            @Override // java.util.List
            public E get(int i2) {
                Preconditions.checkElementIndex(i2, size());
                return (E) ((List) CartesianList.this.axes.get(i2)).get(CartesianList.this.getAxisIndexForProductIndex(i, i2));
            }

            @Override // com.google.common.collect.ImmutableCollection
            boolean isPartialView() {
                return true;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return CartesianList.this.axes.size();
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.axesSizeProduct[0];
    }
}
