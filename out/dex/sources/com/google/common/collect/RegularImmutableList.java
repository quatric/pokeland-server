package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableList<E> extends ImmutableList<E> {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList(ObjectArrays.EMPTY_ARRAY);
    private final transient Object[] array;

    RegularImmutableList(Object[] objArr) {
        this.array = objArr;
    }

    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i) {
        Object[] objArr2 = this.array;
        System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
        return i + this.array.length;
    }

    @Override // java.util.List
    public E get(int i) {
        return (E) this.array[i];
    }

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableList, java.util.List
    public UnmodifiableListIterator<E> listIterator(int i) {
        Object[] objArr = this.array;
        return Iterators.forArray(objArr, 0, objArr.length, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.array.length;
    }
}
