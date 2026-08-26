package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    static final Map.Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];

    @LazyInit
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;

    @LazyInit
    private transient ImmutableSet<K> keySet;

    @LazyInit
    private transient ImmutableSetMultimap<K, V> multimapView;

    @LazyInit
    private transient ImmutableCollection<V> values;

    public static class Builder<K, V> {
        ImmutableMapEntry<K, V>[] entries;
        boolean entriesUsed;
        int size;
        Comparator<? super V> valueComparator;

        public Builder() {
            this(4);
        }

        Builder(int i) {
            this.entries = new ImmutableMapEntry[i];
            this.size = 0;
            this.entriesUsed = false;
        }

        private void ensureCapacity(int i) {
            ImmutableMapEntry<K, V>[] immutableMapEntryArr = this.entries;
            if (i > immutableMapEntryArr.length) {
                this.entries = (ImmutableMapEntry[]) ObjectArrays.arraysCopyOf(immutableMapEntryArr, ImmutableCollection.Builder.expandedCapacity(immutableMapEntryArr.length, i));
                this.entriesUsed = false;
            }
        }

        public ImmutableMap<K, V> build() {
            int i = this.size;
            if (i == 0) {
                return ImmutableMap.m510of();
            }
            if (i == 1) {
                return ImmutableMap.m511of((Object) this.entries[0].getKey(), (Object) this.entries[0].getValue());
            }
            if (this.valueComparator != null) {
                if (this.entriesUsed) {
                    this.entries = (ImmutableMapEntry[]) ObjectArrays.arraysCopyOf(this.entries, i);
                }
                Arrays.sort(this.entries, 0, this.size, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
            }
            this.entriesUsed = this.size == this.entries.length;
            return RegularImmutableMap.fromEntryArray(this.size, this.entries);
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            Preconditions.checkState(this.valueComparator == null, "valueComparator was already set");
            this.valueComparator = (Comparator) Preconditions.checkNotNull(comparator, "valueComparator");
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            ensureCapacity(this.size + 1);
            ImmutableMapEntry<K, V> immutableMapEntryEntryOf = ImmutableMap.entryOf(k, v);
            ImmutableMapEntry<K, V>[] immutableMapEntryArr = this.entries;
            int i = this.size;
            this.size = i + 1;
            immutableMapEntryArr[i] = immutableMapEntryEntryOf;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            if (iterable instanceof Collection) {
                ensureCapacity(this.size + ((Collection) iterable).size());
            }
            Iterator<? extends Map.Entry<? extends K, ? extends V>> it = iterable.iterator();
            while (it.hasNext()) {
                put(it.next());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return putAll(map.entrySet());
        }
    }

    static abstract class IteratorBasedImmutableMap<K, V> extends ImmutableMap<K, V> {
        IteratorBasedImmutableMap() {
        }

        @Override // com.google.common.collect.ImmutableMap
        ImmutableSet<Map.Entry<K, V>> createEntrySet() {
            return new ImmutableMapEntrySet<K, V>() { // from class: com.google.common.collect.ImmutableMap.IteratorBasedImmutableMap.1EntrySetImpl
                @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet, com.google.common.collect.SortedIterable
                public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedImmutableMap.this.entryIterator();
                }

                @Override // com.google.common.collect.ImmutableMapEntrySet
                ImmutableMap<K, V> map() {
                    return IteratorBasedImmutableMap.this;
                }
            };
        }

        abstract UnmodifiableIterator<Map.Entry<K, V>> entryIterator();

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Set entrySet() {
            return super.entrySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Set keySet() {
            return super.keySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Collection values() {
            return super.values();
        }
    }

    private final class MapViewOfValuesAsSingletonSets extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {
        private MapViewOfValuesAsSingletonSets() {
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public boolean containsKey(@Nullable Object obj) {
            return ImmutableMap.this.containsKey(obj);
        }

        @Override // com.google.common.collect.ImmutableMap.IteratorBasedImmutableMap
        UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>> entryIterator() {
            final UnmodifiableIterator<Map.Entry<K, V>> it = ImmutableMap.this.entrySet().iterator();
            return new UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>>() { // from class: com.google.common.collect.ImmutableMap.MapViewOfValuesAsSingletonSets.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public Map.Entry<K, ImmutableSet<V>> next() {
                    final Map.Entry entry = (Map.Entry) it.next();
                    return new AbstractMapEntry<K, ImmutableSet<V>>() { // from class: com.google.common.collect.ImmutableMap.MapViewOfValuesAsSingletonSets.1.1
                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        public K getKey() {
                            return (K) entry.getKey();
                        }

                        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.m534of(entry.getValue());
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public ImmutableSet<V> get(@Nullable Object obj) {
            Object obj2 = ImmutableMap.this.get(obj);
            if (obj2 == null) {
                return null;
            }
            return ImmutableSet.m534of(obj2);
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public int hashCode() {
            return ImmutableMap.this.hashCode();
        }

        @Override // com.google.common.collect.ImmutableMap
        boolean isHashCodeFast() {
            return ImmutableMap.this.isHashCodeFast();
        }

        @Override // com.google.common.collect.ImmutableMap
        boolean isPartialView() {
            return ImmutableMap.this.isPartialView();
        }

        @Override // com.google.common.collect.ImmutableMap.IteratorBasedImmutableMap, com.google.common.collect.ImmutableMap, java.util.Map
        public ImmutableSet<K> keySet() {
            return ImmutableMap.this.keySet();
        }

        @Override // java.util.Map
        public int size() {
            return ImmutableMap.this.size();
        }
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object[] keys;
        private final Object[] values;

        SerializedForm(ImmutableMap<?, ?> immutableMap) {
            this.keys = new Object[immutableMap.size()];
            this.values = new Object[immutableMap.size()];
            Iterator it = immutableMap.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                this.keys[i] = entry.getKey();
                this.values[i] = entry.getValue();
                i++;
            }
        }

        Object createMap(Builder<Object, Object> builder) {
            int i = 0;
            while (true) {
                Object[] objArr = this.keys;
                if (i >= objArr.length) {
                    return builder.build();
                }
                builder.put(objArr[i], this.values[i]);
                i++;
            }
        }

        Object readResolve() {
            return createMap(new Builder<>(this.keys.length));
        }
    }

    ImmutableMap() {
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    static void checkNoConflict(boolean z, String str, Map.Entry<?, ?> entry, Map.Entry<?, ?> entry2) {
        if (z) {
            return;
        }
        throw new IllegalArgumentException("Multiple entries with same " + str + ": " + entry + " and " + entry2);
    }

    @Beta
    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Map.Entry[] entryArr = (Map.Entry[]) Iterables.toArray(iterable, EMPTY_ENTRY_ARRAY);
        int length = entryArr.length;
        if (length == 0) {
            return m510of();
        }
        if (length != 1) {
            return RegularImmutableMap.fromEntries(entryArr);
        }
        Map.Entry entry = entryArr[0];
        return m511of(entry.getKey(), entry.getValue());
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap) && !(map instanceof ImmutableSortedMap)) {
            ImmutableMap<K, V> immutableMap = (ImmutableMap) map;
            if (!immutableMap.isPartialView()) {
                return immutableMap;
            }
        } else if (map instanceof EnumMap) {
            return copyOfEnumMap((EnumMap) map);
        }
        return copyOf(map.entrySet());
    }

    private static <K extends Enum<K>, V> ImmutableMap<K, V> copyOfEnumMap(EnumMap<K, ? extends V> enumMap) {
        EnumMap enumMap2 = new EnumMap((EnumMap) enumMap);
        for (Map.Entry<K, V> entry : enumMap2.entrySet()) {
            CollectPreconditions.checkEntryNotNull(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(enumMap2);
    }

    static <K, V> ImmutableMapEntry<K, V> entryOf(K k, V v) {
        return new ImmutableMapEntry<>(k, v);
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableMap<K, V> m510of() {
        return ImmutableBiMap.m483of();
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableMap<K, V> m511of(K k, V v) {
        return ImmutableBiMap.m484of((Object) k, (Object) v);
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableMap<K, V> m512of(K k, V v, K k2, V v2) {
        return RegularImmutableMap.fromEntries(entryOf(k, v), entryOf(k2, v2));
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableMap<K, V> m513of(K k, V v, K k2, V v2, K k3, V v3) {
        return RegularImmutableMap.fromEntries(entryOf(k, v), entryOf(k2, v2), entryOf(k3, v3));
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableMap<K, V> m514of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return RegularImmutableMap.fromEntries(entryOf(k, v), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableMap<K, V> m515of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return RegularImmutableMap.fromEntries(entryOf(k, v), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
    }

    public ImmutableSetMultimap<K, V> asMultimap() {
        if (isEmpty()) {
            return ImmutableSetMultimap.m540of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.multimapView;
        if (immutableSetMultimap != null) {
            return immutableSetMultimap;
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap2 = new ImmutableSetMultimap<>(new MapViewOfValuesAsSingletonSets(), size(), null);
        this.multimapView = immutableSetMultimap2;
        return immutableSetMultimap2;
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean containsKey(@Nullable Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(@Nullable Object obj) {
        return values().contains(obj);
    }

    abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();

    ImmutableSet<K> createKeySet() {
        return isEmpty() ? ImmutableSet.m533of() : new ImmutableMapKeySet(this);
    }

    ImmutableCollection<V> createValues() {
        return new ImmutableMapValues(this);
    }

    @Override // java.util.Map
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Map.Entry<K, V>> immutableSetCreateEntrySet = createEntrySet();
        this.entrySet = immutableSetCreateEntrySet;
        return immutableSetCreateEntrySet;
    }

    @Override // java.util.Map
    public boolean equals(@Nullable Object obj) {
        return Maps.equalsImpl(this, obj);
    }

    @Override // java.util.Map
    public abstract V get(@Nullable Object obj);

    @Override // java.util.Map
    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    boolean isHashCodeFast() {
        return false;
    }

    abstract boolean isPartialView();

    UnmodifiableIterator<K> keyIterator() {
        final UnmodifiableIterator<Map.Entry<K, V>> it = entrySet().iterator();
        return new UnmodifiableIterator<K>() { // from class: com.google.common.collect.ImmutableMap.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public K next() {
                return (K) ((Map.Entry) it.next()).getKey();
            }
        };
    }

    @Override // java.util.Map
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.keySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<K> immutableSetCreateKeySet = createKeySet();
        this.keySet = immutableSetCreateKeySet;
        return immutableSetCreateKeySet;
    }

    @Override // java.util.Map
    @CanIgnoreReturnValue
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    @Override // java.util.Map
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.values;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection<V> immutableCollectionCreateValues = createValues();
        this.values = immutableCollectionCreateValues;
        return immutableCollectionCreateValues;
    }

    Object writeReplace() {
        return new SerializedForm(this);
    }
}
