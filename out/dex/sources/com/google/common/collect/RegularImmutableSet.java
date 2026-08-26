package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSet<E> extends ImmutableSet.Indexed<E> {
    static final RegularImmutableSet<Object> EMPTY = new RegularImmutableSet<>(ObjectArrays.EMPTY_ARRAY, 0, null, 0);
    private final transient Object[] elements;
    private final transient int hashCode;
    private final transient int mask;

    @VisibleForTesting
    final transient Object[] table;

    RegularImmutableSet(Object[] objArr, int i, Object[] objArr2, int i2) {
        this.elements = objArr;
        this.table = objArr2;
        this.mask = i2;
        this.hashCode = i;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@Nullable Object obj) {
        Object[] objArr = this.table;
        if (obj == null || objArr == null) {
            return false;
        }
        int iSmearedHash = Hashing.smearedHash(obj);
        while (true) {
            int i = iSmearedHash & this.mask;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iSmearedHash = i + 1;
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i) {
        Object[] objArr2 = this.elements;
        System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
        return i + this.elements.length;
    }

    @Override // com.google.common.collect.ImmutableSet.Indexed, com.google.common.collect.ImmutableSet
    ImmutableList<E> createAsList() {
        return this.table == null ? ImmutableList.m491of() : new RegularImmutableAsList(this, this.elements);
    }

    @Override // com.google.common.collect.ImmutableSet.Indexed
    E get(int i) {
        return (E) this.elements[i];
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return this.hashCode;
    }

    @Override // com.google.common.collect.ImmutableSet
    boolean isHashCodeFast() {
        return true;
    }

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.elements.length;
    }
}
