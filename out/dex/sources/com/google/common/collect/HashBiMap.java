package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
public final class HashBiMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final double LOAD_FACTOR = 1.0d;

    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient BiEntry<K, V> firstInKeyInsertionOrder;
    private transient BiEntry<K, V>[] hashTableKToV;
    private transient BiEntry<K, V>[] hashTableVToK;

    @RetainedWith
    private transient BiMap<V, K> inverse;
    private transient BiEntry<K, V> lastInKeyInsertionOrder;
    private transient int mask;
    private transient int modCount;
    private transient int size;

    private static final class BiEntry<K, V> extends ImmutableEntry<K, V> {
        final int keyHash;

        @Nullable
        BiEntry<K, V> nextInKToVBucket;

        @Nullable
        BiEntry<K, V> nextInKeyInsertionOrder;

        @Nullable
        BiEntry<K, V> nextInVToKBucket;

        @Nullable
        BiEntry<K, V> prevInKeyInsertionOrder;
        final int valueHash;

        BiEntry(K k, int i, V v, int i2) {
            super(k, v);
            this.keyHash = i;
            this.valueHash = i2;
        }
    }

    private final class Inverse extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {

        /* JADX INFO: renamed from: com.google.common.collect.HashBiMap$Inverse$1 */
        class C04201 extends Maps.EntrySet<V, K> {
            C04201() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<V, K>> iterator() {
                return new HashBiMap<K, V>.Itr<Map.Entry<V, K>>() { // from class: com.google.common.collect.HashBiMap.Inverse.1.1

                    /* JADX INFO: renamed from: com.google.common.collect.HashBiMap$Inverse$1$1$InverseEntry */
                    class InverseEntry extends AbstractMapEntry<V, K> {
                        BiEntry<K, V> delegate;

                        InverseEntry(BiEntry<K, V> biEntry) {
                            this.delegate = biEntry;
                        }

                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        public V getKey() {
                            return this.delegate.value;
                        }

                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        public K getValue() {
                            return this.delegate.key;
                        }

                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        public K setValue(K k) {
                            K k2 = this.delegate.key;
                            int iSmearedHash = Hashing.smearedHash(k);
                            if (iSmearedHash == this.delegate.keyHash && Objects.equal(k, k2)) {
                                return k;
                            }
                            Preconditions.checkArgument(HashBiMap.this.seekByKey(k, iSmearedHash) == null, "value already present: %s", k);
                            HashBiMap.this.delete(this.delegate);
                            BiEntry<K, V> biEntry = new BiEntry<>(k, iSmearedHash, this.delegate.value, this.delegate.valueHash);
                            this.delegate = biEntry;
                            HashBiMap.this.insert(biEntry, null);
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            anonymousClass1.expectedModCount = HashBiMap.this.modCount;
                            return k2;
                        }
                    }

                    {
                        HashBiMap hashBiMap = HashBiMap.this;
                    }

                    /* JADX INFO: Access modifiers changed from: package-private */
                    @Override // com.google.common.collect.HashBiMap.Itr
                    public Map.Entry<V, K> output(BiEntry<K, V> biEntry) {
                        return new InverseEntry(biEntry);
                    }
                };
            }

            @Override // com.google.common.collect.Maps.EntrySet
            Map<V, K> map() {
                return Inverse.this;
            }
        }

        private final class InverseKeySet extends Maps.KeySet<V, K> {
            InverseKeySet() {
                super(Inverse.this);
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<V> iterator() {
                return new HashBiMap<K, V>.Itr<V>() { // from class: com.google.common.collect.HashBiMap.Inverse.InverseKeySet.1
                    {
                        HashBiMap hashBiMap = HashBiMap.this;
                    }

                    @Override // com.google.common.collect.HashBiMap.Itr
                    V output(BiEntry<K, V> biEntry) {
                        return biEntry.value;
                    }
                };
            }

            @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(@Nullable Object obj) {
                BiEntry biEntrySeekByValue = HashBiMap.this.seekByValue(obj, Hashing.smearedHash(obj));
                if (biEntrySeekByValue == null) {
                    return false;
                }
                HashBiMap.this.delete(biEntrySeekByValue);
                return true;
            }
        }

        private Inverse() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            forward().clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@Nullable Object obj) {
            return forward().containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<V, K>> entrySet() {
            return new C04201();
        }

        @Override // com.google.common.collect.BiMap
        public K forcePut(@Nullable V v, @Nullable K k) {
            return (K) HashBiMap.this.putInverse(v, k, true);
        }

        BiMap<K, V> forward() {
            return HashBiMap.this;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public K get(@Nullable Object obj) {
            return (K) Maps.keyOrNull(HashBiMap.this.seekByValue(obj, Hashing.smearedHash(obj)));
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return forward();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return new InverseKeySet();
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        public K put(@Nullable V v, @Nullable K k) {
            return (K) HashBiMap.this.putInverse(v, k, false);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public K remove(@Nullable Object obj) {
            BiEntry biEntrySeekByValue = HashBiMap.this.seekByValue(obj, Hashing.smearedHash(obj));
            if (biEntrySeekByValue == null) {
                return null;
            }
            HashBiMap.this.delete(biEntrySeekByValue);
            biEntrySeekByValue.prevInKeyInsertionOrder = null;
            biEntrySeekByValue.nextInKeyInsertionOrder = null;
            return biEntrySeekByValue.key;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return HashBiMap.this.size;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> values() {
            return forward().keySet();
        }

        Object writeReplace() {
            return new InverseSerializedForm(HashBiMap.this);
        }
    }

    private static final class InverseSerializedForm<K, V> implements Serializable {
        private final HashBiMap<K, V> bimap;

        InverseSerializedForm(HashBiMap<K, V> hashBiMap) {
            this.bimap = hashBiMap;
        }

        Object readResolve() {
            return this.bimap.inverse();
        }
    }

    abstract class Itr<T> implements Iterator<T> {
        int expectedModCount;
        BiEntry<K, V> next;
        BiEntry<K, V> toRemove = null;

        Itr() {
            this.next = HashBiMap.this.firstInKeyInsertionOrder;
            this.expectedModCount = HashBiMap.this.modCount;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (HashBiMap.this.modCount == this.expectedModCount) {
                return this.next != null;
            }
            throw new ConcurrentModificationException();
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BiEntry<K, V> biEntry = this.next;
            this.next = biEntry.nextInKeyInsertionOrder;
            this.toRemove = biEntry;
            return output(biEntry);
        }

        abstract T output(BiEntry<K, V> biEntry);

        @Override // java.util.Iterator
        public void remove() {
            if (HashBiMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            CollectPreconditions.checkRemove(this.toRemove != null);
            HashBiMap.this.delete(this.toRemove);
            this.expectedModCount = HashBiMap.this.modCount;
            this.toRemove = null;
        }
    }

    private final class KeySet extends Maps.KeySet<K, V> {
        KeySet() {
            super(HashBiMap.this);
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new HashBiMap<K, V>.Itr<K>() { // from class: com.google.common.collect.HashBiMap.KeySet.1
                {
                    HashBiMap hashBiMap = HashBiMap.this;
                }

                @Override // com.google.common.collect.HashBiMap.Itr
                K output(BiEntry<K, V> biEntry) {
                    return biEntry.key;
                }
            };
        }

        @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@Nullable Object obj) {
            BiEntry biEntrySeekByKey = HashBiMap.this.seekByKey(obj, Hashing.smearedHash(obj));
            if (biEntrySeekByKey == null) {
                return false;
            }
            HashBiMap.this.delete(biEntrySeekByKey);
            biEntrySeekByKey.prevInKeyInsertionOrder = null;
            biEntrySeekByKey.nextInKeyInsertionOrder = null;
            return true;
        }
    }

    private HashBiMap(int i) {
        init(i);
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int i) {
        return new HashBiMap<>(i);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> hashBiMapCreate = create(map.size());
        hashBiMapCreate.putAll(map);
        return hashBiMapCreate;
    }

    private BiEntry<K, V>[] createTable(int i) {
        return new BiEntry[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delete(BiEntry<K, V> biEntry) {
        BiEntry<K, V> biEntry2;
        int i = biEntry.keyHash & this.mask;
        BiEntry<K, V> biEntry3 = null;
        BiEntry<K, V> biEntry4 = null;
        for (BiEntry<K, V> biEntry5 = this.hashTableKToV[i]; biEntry5 != biEntry; biEntry5 = biEntry5.nextInKToVBucket) {
            biEntry4 = biEntry5;
        }
        if (biEntry4 == null) {
            this.hashTableKToV[i] = biEntry.nextInKToVBucket;
        } else {
            biEntry4.nextInKToVBucket = biEntry.nextInKToVBucket;
        }
        int i2 = biEntry.valueHash & this.mask;
        BiEntry<K, V> biEntry6 = this.hashTableVToK[i2];
        while (true) {
            biEntry2 = biEntry3;
            biEntry3 = biEntry6;
            if (biEntry3 == biEntry) {
                break;
            } else {
                biEntry6 = biEntry3.nextInVToKBucket;
            }
        }
        if (biEntry2 == null) {
            this.hashTableVToK[i2] = biEntry.nextInVToKBucket;
        } else {
            biEntry2.nextInVToKBucket = biEntry.nextInVToKBucket;
        }
        if (biEntry.prevInKeyInsertionOrder == null) {
            this.firstInKeyInsertionOrder = biEntry.nextInKeyInsertionOrder;
        } else {
            biEntry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry.nextInKeyInsertionOrder;
        }
        if (biEntry.nextInKeyInsertionOrder == null) {
            this.lastInKeyInsertionOrder = biEntry.prevInKeyInsertionOrder;
        } else {
            biEntry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = biEntry.prevInKeyInsertionOrder;
        }
        this.size--;
        this.modCount++;
    }

    private void init(int i) {
        CollectPreconditions.checkNonnegative(i, "expectedSize");
        int iClosedTableSize = Hashing.closedTableSize(i, LOAD_FACTOR);
        this.hashTableKToV = createTable(iClosedTableSize);
        this.hashTableVToK = createTable(iClosedTableSize);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.size = 0;
        this.mask = iClosedTableSize - 1;
        this.modCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void insert(BiEntry<K, V> biEntry, @Nullable BiEntry<K, V> biEntry2) {
        int i = biEntry.keyHash & this.mask;
        BiEntry<K, V>[] biEntryArr = this.hashTableKToV;
        biEntry.nextInKToVBucket = biEntryArr[i];
        biEntryArr[i] = biEntry;
        int i2 = biEntry.valueHash & this.mask;
        BiEntry<K, V>[] biEntryArr2 = this.hashTableVToK;
        biEntry.nextInVToKBucket = biEntryArr2[i2];
        biEntryArr2[i2] = biEntry;
        if (biEntry2 == null) {
            BiEntry<K, V> biEntry3 = this.lastInKeyInsertionOrder;
            biEntry.prevInKeyInsertionOrder = biEntry3;
            biEntry.nextInKeyInsertionOrder = null;
            if (biEntry3 == null) {
                this.firstInKeyInsertionOrder = biEntry;
            } else {
                biEntry3.nextInKeyInsertionOrder = biEntry;
            }
            this.lastInKeyInsertionOrder = biEntry;
        } else {
            biEntry.prevInKeyInsertionOrder = biEntry2.prevInKeyInsertionOrder;
            if (biEntry.prevInKeyInsertionOrder == null) {
                this.firstInKeyInsertionOrder = biEntry;
            } else {
                biEntry.prevInKeyInsertionOrder.nextInKeyInsertionOrder = biEntry;
            }
            biEntry.nextInKeyInsertionOrder = biEntry2.nextInKeyInsertionOrder;
            if (biEntry.nextInKeyInsertionOrder == null) {
                this.lastInKeyInsertionOrder = biEntry;
            } else {
                biEntry.nextInKeyInsertionOrder.prevInKeyInsertionOrder = biEntry;
            }
        }
        this.size++;
        this.modCount++;
    }

    private V put(@Nullable K k, @Nullable V v, boolean z) {
        int iSmearedHash = Hashing.smearedHash(k);
        int iSmearedHash2 = Hashing.smearedHash(v);
        BiEntry<K, V> biEntrySeekByKey = seekByKey(k, iSmearedHash);
        if (biEntrySeekByKey != null && iSmearedHash2 == biEntrySeekByKey.valueHash && Objects.equal(v, biEntrySeekByKey.value)) {
            return v;
        }
        BiEntry<K, V> biEntrySeekByValue = seekByValue(v, iSmearedHash2);
        if (biEntrySeekByValue != null) {
            if (!z) {
                throw new IllegalArgumentException("value already present: " + v);
            }
            delete(biEntrySeekByValue);
        }
        BiEntry<K, V> biEntry = new BiEntry<>(k, iSmearedHash, v, iSmearedHash2);
        if (biEntrySeekByKey == null) {
            insert(biEntry, null);
            rehashIfNecessary();
            return null;
        }
        delete(biEntrySeekByKey);
        insert(biEntry, biEntrySeekByKey);
        biEntrySeekByKey.prevInKeyInsertionOrder = null;
        biEntrySeekByKey.nextInKeyInsertionOrder = null;
        rehashIfNecessary();
        return biEntrySeekByKey.value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public K putInverse(@Nullable V v, @Nullable K k, boolean z) {
        int iSmearedHash = Hashing.smearedHash(v);
        int iSmearedHash2 = Hashing.smearedHash(k);
        BiEntry<K, V> biEntrySeekByValue = seekByValue(v, iSmearedHash);
        if (biEntrySeekByValue != null && iSmearedHash2 == biEntrySeekByValue.keyHash && Objects.equal(k, biEntrySeekByValue.key)) {
            return k;
        }
        BiEntry<K, V> biEntrySeekByKey = seekByKey(k, iSmearedHash2);
        if (biEntrySeekByKey != null) {
            if (!z) {
                throw new IllegalArgumentException("value already present: " + k);
            }
            delete(biEntrySeekByKey);
        }
        if (biEntrySeekByValue != null) {
            delete(biEntrySeekByValue);
        }
        insert(new BiEntry<>(k, iSmearedHash2, v, iSmearedHash), biEntrySeekByKey);
        if (biEntrySeekByKey != null) {
            biEntrySeekByKey.prevInKeyInsertionOrder = null;
            biEntrySeekByKey.nextInKeyInsertionOrder = null;
        }
        rehashIfNecessary();
        return (K) Maps.keyOrNull(biEntrySeekByValue);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init(16);
        Serialization.populateMap(this, objectInputStream, Serialization.readCount(objectInputStream));
    }

    private void rehashIfNecessary() {
        BiEntry<K, V>[] biEntryArr = this.hashTableKToV;
        if (Hashing.needsResizing(this.size, biEntryArr.length, LOAD_FACTOR)) {
            int length = biEntryArr.length * 2;
            this.hashTableKToV = createTable(length);
            this.hashTableVToK = createTable(length);
            this.mask = length - 1;
            this.size = 0;
            for (BiEntry<K, V> biEntry = this.firstInKeyInsertionOrder; biEntry != null; biEntry = biEntry.nextInKeyInsertionOrder) {
                insert(biEntry, biEntry);
            }
            this.modCount++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BiEntry<K, V> seekByKey(@Nullable Object obj, int i) {
        for (BiEntry<K, V> biEntry = this.hashTableKToV[this.mask & i]; biEntry != null; biEntry = biEntry.nextInKToVBucket) {
            if (i == biEntry.keyHash && Objects.equal(obj, biEntry.key)) {
                return biEntry;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BiEntry<K, V> seekByValue(@Nullable Object obj, int i) {
        for (BiEntry<K, V> biEntry = this.hashTableVToK[this.mask & i]; biEntry != null; biEntry = biEntry.nextInVToKBucket) {
            if (i == biEntry.valueHash && Objects.equal(obj, biEntry.value)) {
                return biEntry;
            }
        }
        return null;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        this.size = 0;
        Arrays.fill(this.hashTableKToV, (Object) null);
        Arrays.fill(this.hashTableVToK, (Object) null);
        this.firstInKeyInsertionOrder = null;
        this.lastInKeyInsertionOrder = null;
        this.modCount++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@Nullable Object obj) {
        return seekByKey(obj, Hashing.smearedHash(obj)) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@Nullable Object obj) {
        return seekByValue(obj, Hashing.smearedHash(obj)) != null;
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new HashBiMap<K, V>.Itr<Map.Entry<K, V>>() { // from class: com.google.common.collect.HashBiMap.1

            /* JADX INFO: renamed from: com.google.common.collect.HashBiMap$1$MapEntry */
            class MapEntry extends AbstractMapEntry<K, V> {
                BiEntry<K, V> delegate;

                MapEntry(BiEntry<K, V> biEntry) {
                    this.delegate = biEntry;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                public K getKey() {
                    return this.delegate.key;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                public V getValue() {
                    return this.delegate.value;
                }

                @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                public V setValue(V v) {
                    V v2 = this.delegate.value;
                    int iSmearedHash = Hashing.smearedHash(v);
                    if (iSmearedHash == this.delegate.valueHash && Objects.equal(v, v2)) {
                        return v;
                    }
                    Preconditions.checkArgument(HashBiMap.this.seekByValue(v, iSmearedHash) == null, "value already present: %s", v);
                    HashBiMap.this.delete(this.delegate);
                    BiEntry<K, V> biEntry = new BiEntry<>(this.delegate.key, this.delegate.keyHash, v, iSmearedHash);
                    HashBiMap.this.insert(biEntry, this.delegate);
                    BiEntry<K, V> biEntry2 = this.delegate;
                    biEntry2.prevInKeyInsertionOrder = null;
                    biEntry2.nextInKeyInsertionOrder = null;
                    C04191 c04191 = C04191.this;
                    c04191.expectedModCount = HashBiMap.this.modCount;
                    if (C04191.this.toRemove == this.delegate) {
                        C04191.this.toRemove = biEntry;
                    }
                    this.delegate = biEntry;
                    return v2;
                }
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.HashBiMap.Itr
            public Map.Entry<K, V> output(BiEntry<K, V> biEntry) {
                return new MapEntry(biEntry);
            }
        };
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V forcePut(@Nullable K k, @Nullable V v) {
        return put(k, v, true);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @Nullable
    public V get(@Nullable Object obj) {
        return (V) Maps.valueOrNull(seekByKey(obj, Hashing.smearedHash(obj)));
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse();
        this.inverse = inverse;
        return inverse;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        return put(k, v, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj) {
        BiEntry<K, V> biEntrySeekByKey = seekByKey(obj, Hashing.smearedHash(obj));
        if (biEntrySeekByKey == null) {
            return null;
        }
        delete(biEntrySeekByKey);
        biEntrySeekByKey.prevInKeyInsertionOrder = null;
        biEntrySeekByKey.nextInKeyInsertionOrder = null;
        return biEntrySeekByKey.value;
    }

    @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<V> values() {
        return inverse().keySet();
    }
}
