package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Collection;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(serializable = true)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset<>(ImmutableList.m491of());

    @LazyInit
    private transient ImmutableSet<E> elementSet;
    private final transient Multisets.ImmutableEntry<E>[] entries;
    private final transient int hashCode;
    private final transient Multisets.ImmutableEntry<E>[] hashTable;
    private final transient int size;

    private final class ElementSet extends ImmutableSet.Indexed<E> {
        private ElementSet() {
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@Nullable Object obj) {
            return RegularImmutableMultiset.this.contains(obj);
        }

        @Override // com.google.common.collect.ImmutableSet.Indexed
        E get(int i) {
            return (E) RegularImmutableMultiset.this.entries[i].getElement();
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return RegularImmutableMultiset.this.entries.length;
        }
    }

    private static final class NonTerminalEntry<E> extends Multisets.ImmutableEntry<E> {
        private final Multisets.ImmutableEntry<E> nextInBucket;

        NonTerminalEntry(E e, int i, Multisets.ImmutableEntry<E> immutableEntry) {
            super(e, i);
            this.nextInBucket = immutableEntry;
        }

        @Override // com.google.common.collect.Multisets.ImmutableEntry
        public Multisets.ImmutableEntry<E> nextInBucket() {
            return this.nextInBucket;
        }
    }

    RegularImmutableMultiset(Collection<? extends Multiset.Entry<? extends E>> collection) {
        int size = collection.size();
        Multisets.ImmutableEntry<E>[] immutableEntryArr = new Multisets.ImmutableEntry[size];
        if (size == 0) {
            this.entries = immutableEntryArr;
            this.hashTable = null;
            this.size = 0;
            this.hashCode = 0;
            this.elementSet = ImmutableSet.m533of();
            return;
        }
        int iClosedTableSize = Hashing.closedTableSize(size, 1.0d);
        int i = iClosedTableSize - 1;
        Multisets.ImmutableEntry<E>[] immutableEntryArr2 = new Multisets.ImmutableEntry[iClosedTableSize];
        long j = 0;
        int i2 = 0;
        int i3 = 0;
        for (Multiset.Entry<? extends E> entry : collection) {
            Object objCheckNotNull = Preconditions.checkNotNull(entry.getElement());
            int count = entry.getCount();
            int iHashCode = objCheckNotNull.hashCode();
            int iSmear = Hashing.smear(iHashCode) & i;
            Multisets.ImmutableEntry<E> immutableEntry = immutableEntryArr2[iSmear];
            Multisets.ImmutableEntry<E> immutableEntry2 = immutableEntry == null ? (entry instanceof Multisets.ImmutableEntry) && !(entry instanceof NonTerminalEntry) ? (Multisets.ImmutableEntry) entry : new Multisets.ImmutableEntry<>(objCheckNotNull, count) : new NonTerminalEntry<>(objCheckNotNull, count, immutableEntry);
            i2 += iHashCode ^ count;
            immutableEntryArr[i3] = immutableEntry2;
            immutableEntryArr2[iSmear] = immutableEntry2;
            j += (long) count;
            i3++;
        }
        this.entries = immutableEntryArr;
        this.hashTable = immutableEntryArr2;
        this.size = Ints.saturatedCast(j);
        this.hashCode = i2;
    }

    @Override // com.google.common.collect.Multiset
    public int count(@Nullable Object obj) {
        Multisets.ImmutableEntry<E>[] immutableEntryArr = this.hashTable;
        if (obj != null && immutableEntryArr != null) {
            for (Multisets.ImmutableEntry<E> immutableEntryNextInBucket = immutableEntryArr[Hashing.smearedHash(obj) & (immutableEntryArr.length - 1)]; immutableEntryNextInBucket != null; immutableEntryNextInBucket = immutableEntryNextInBucket.nextInBucket()) {
                if (Objects.equal(obj, immutableEntryNextInBucket.getElement())) {
                    return immutableEntryNextInBucket.getCount();
                }
            }
        }
        return 0;
    }

    @Override // com.google.common.collect.Multiset
    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> immutableSet = this.elementSet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ElementSet elementSet = new ElementSet();
        this.elementSet = elementSet;
        return elementSet;
    }

    @Override // com.google.common.collect.ImmutableMultiset
    Multiset.Entry<E> getEntry(int i) {
        return this.entries[i];
    }

    @Override // com.google.common.collect.ImmutableMultiset, java.util.Collection, com.google.common.collect.Multiset
    public int hashCode() {
        return this.hashCode;
    }

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.size;
    }
}
