package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {

    @GwtIncompatible
    private static final long serialVersionUID = -2250766705698539974L;
    private transient Map<E, Count> backingMap;
    private transient long size = super.size();

    private class MapBasedMultisetIterator implements Iterator<E> {
        boolean canRemove;
        Map.Entry<E, Count> currentEntry;
        final Iterator<Map.Entry<E, Count>> entryIterator;
        int occurrencesLeft;

        MapBasedMultisetIterator() {
            this.entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
        }

        @Override // java.util.Iterator
        public E next() {
            if (this.occurrencesLeft == 0) {
                this.currentEntry = this.entryIterator.next();
                this.occurrencesLeft = this.currentEntry.getValue().get();
            }
            this.occurrencesLeft--;
            this.canRemove = true;
            return this.currentEntry.getKey();
        }

        @Override // java.util.Iterator
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            if (this.currentEntry.getValue().get() <= 0) {
                throw new ConcurrentModificationException();
            }
            if (this.currentEntry.getValue().addAndGet(-1) == 0) {
                this.entryIterator.remove();
            }
            AbstractMapBasedMultiset.access$110(AbstractMapBasedMultiset.this);
            this.canRemove = false;
        }
    }

    protected AbstractMapBasedMultiset(Map<E, Count> map) {
        this.backingMap = (Map) Preconditions.checkNotNull(map);
    }

    static /* synthetic */ long access$110(AbstractMapBasedMultiset abstractMapBasedMultiset) {
        long j = abstractMapBasedMultiset.size;
        abstractMapBasedMultiset.size = j - 1;
        return j;
    }

    static /* synthetic */ long access$122(AbstractMapBasedMultiset abstractMapBasedMultiset, long j) {
        long j2 = abstractMapBasedMultiset.size - j;
        abstractMapBasedMultiset.size = j2;
        return j2;
    }

    private static int getAndSet(@Nullable Count count, int i) {
        if (count == null) {
            return 0;
        }
        return count.getAndSet(i);
    }

    @GwtIncompatible
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(@Nullable E e, int i) {
        int i2;
        if (i == 0) {
            return count(e);
        }
        Preconditions.checkArgument(i > 0, "occurrences cannot be negative: %s", i);
        Count count = this.backingMap.get(e);
        if (count == null) {
            this.backingMap.put(e, new Count(i));
            i2 = 0;
        } else {
            i2 = count.get();
            long j = ((long) i2) + ((long) i);
            Preconditions.checkArgument(j <= 2147483647L, "too many occurrences: %s", j);
            count.add(i);
        }
        this.size += (long) i;
        return i2;
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        Iterator<Count> it = this.backingMap.values().iterator();
        while (it.hasNext()) {
            it.next().set(0);
        }
        this.backingMap.clear();
        this.size = 0L;
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public int count(@Nullable Object obj) {
        Count count = (Count) Maps.safeGet(this.backingMap, obj);
        if (count == null) {
            return 0;
        }
        return count.get();
    }

    @Override // com.google.common.collect.AbstractMultiset
    int distinctElements() {
        return this.backingMap.size();
    }

    @Override // com.google.common.collect.AbstractMultiset
    Iterator<Multiset.Entry<E>> entryIterator() {
        final Iterator<Map.Entry<E, Count>> it = this.backingMap.entrySet().iterator();
        return new Iterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.AbstractMapBasedMultiset.1
            Map.Entry<E, Count> toRemove;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public Multiset.Entry<E> next() {
                final Map.Entry<E, Count> entry = (Map.Entry) it.next();
                this.toRemove = entry;
                return new Multisets.AbstractEntry<E>() { // from class: com.google.common.collect.AbstractMapBasedMultiset.1.1
                    @Override // com.google.common.collect.Multiset.Entry
                    public int getCount() {
                        Count count;
                        Count count2 = (Count) entry.getValue();
                        if ((count2 == null || count2.get() == 0) && (count = (Count) AbstractMapBasedMultiset.this.backingMap.get(getElement())) != null) {
                            return count.get();
                        }
                        if (count2 == null) {
                            return 0;
                        }
                        return count2.get();
                    }

                    @Override // com.google.common.collect.Multiset.Entry
                    public E getElement() {
                        return (E) entry.getKey();
                    }
                };
            }

            @Override // java.util.Iterator
            public void remove() {
                CollectPreconditions.checkRemove(this.toRemove != null);
                AbstractMapBasedMultiset.access$122(AbstractMapBasedMultiset.this, this.toRemove.getValue().getAndSet(0));
                it.remove();
                this.toRemove = null;
            }
        };
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    public Set<Multiset.Entry<E>> entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public Iterator<E> iterator() {
        return new MapBasedMultisetIterator();
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@Nullable Object obj, int i) {
        if (i == 0) {
            return count(obj);
        }
        Preconditions.checkArgument(i > 0, "occurrences cannot be negative: %s", i);
        Count count = this.backingMap.get(obj);
        if (count == null) {
            return 0;
        }
        int i2 = count.get();
        if (i2 <= i) {
            this.backingMap.remove(obj);
            i = i2;
        }
        count.add(-i);
        this.size -= (long) i;
        return i2;
    }

    void setBackingMap(Map<E, Count> map) {
        this.backingMap = map;
    }

    @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(@Nullable E e, int i) {
        int andSet;
        CollectPreconditions.checkNonnegative(i, "count");
        if (i == 0) {
            andSet = getAndSet(this.backingMap.remove(e), i);
        } else {
            Count count = this.backingMap.get(e);
            int andSet2 = getAndSet(count, i);
            if (count == null) {
                this.backingMap.put(e, new Count(i));
            }
            andSet = andSet2;
        }
        this.size += (long) (i - andSet);
        return andSet;
    }

    @Override // com.google.common.collect.AbstractMultiset, java.util.AbstractCollection, java.util.Collection
    public int size() {
        return Ints.saturatedCast(this.size);
    }
}
