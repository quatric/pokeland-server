package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtCompatible
@Deprecated
public final class MapConstraints {

    static class ConstrainedAsMapEntries<K, V> extends ForwardingSet<Map.Entry<K, Collection<V>>> {
        private final MapConstraint<? super K, ? super V> constraint;
        private final Set<Map.Entry<K, Collection<V>>> entries;

        ConstrainedAsMapEntries(Set<Map.Entry<K, Collection<V>>> set, MapConstraint<? super K, ? super V> mapConstraint) {
            this.entries = set;
            this.constraint = mapConstraint;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Maps.containsEntryImpl(delegate(), obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<Map.Entry<K, Collection<V>>> delegate() {
            return this.entries;
        }

        @Override // com.google.common.collect.ForwardingSet, java.util.Collection, java.util.Set
        public boolean equals(@Nullable Object obj) {
            return standardEquals(obj);
        }

        @Override // com.google.common.collect.ForwardingSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return standardHashCode();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(this.entries.iterator()) { // from class: com.google.common.collect.MapConstraints.ConstrainedAsMapEntries.1
                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.google.common.collect.TransformedIterator
                public Map.Entry<K, Collection<V>> transform(Map.Entry<K, Collection<V>> entry) {
                    return MapConstraints.constrainedAsMapEntry(entry, ConstrainedAsMapEntries.this.constraint);
                }
            };
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return Maps.removeEntryImpl(delegate(), obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }
    }

    private static class ConstrainedAsMapValues<K, V> extends ForwardingCollection<Collection<V>> {
        final Collection<Collection<V>> delegate;
        final Set<Map.Entry<K, Collection<V>>> entrySet;

        ConstrainedAsMapValues(Collection<Collection<V>> collection, Set<Map.Entry<K, Collection<V>>> set) {
            this.delegate = collection;
            this.entrySet = set;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return standardContains(obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<Collection<V>> delegate() {
            return this.delegate;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Collection<V>> iterator() {
            final Iterator<Map.Entry<K, Collection<V>>> it = this.entrySet.iterator();
            return new Iterator<Collection<V>>() { // from class: com.google.common.collect.MapConstraints.ConstrainedAsMapValues.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public Collection<V> next() {
                    return (Collection) ((Map.Entry) it.next()).getValue();
                }

                @Override // java.util.Iterator
                public void remove() {
                    it.remove();
                }
            };
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return standardRemove(obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }
    }

    private static class ConstrainedEntries<K, V> extends ForwardingCollection<Map.Entry<K, V>> {
        final MapConstraint<? super K, ? super V> constraint;
        final Collection<Map.Entry<K, V>> entries;

        ConstrainedEntries(Collection<Map.Entry<K, V>> collection, MapConstraint<? super K, ? super V> mapConstraint) {
            this.entries = collection;
            this.constraint = mapConstraint;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Maps.containsEntryImpl(delegate(), obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(this.entries.iterator()) { // from class: com.google.common.collect.MapConstraints.ConstrainedEntries.1
                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.google.common.collect.TransformedIterator
                public Map.Entry<K, V> transform(Map.Entry<K, V> entry) {
                    return MapConstraints.constrainedEntry(entry, ConstrainedEntries.this.constraint);
                }
            };
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return Maps.removeEntryImpl(delegate(), obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }
    }

    static class ConstrainedEntrySet<K, V> extends ConstrainedEntries<K, V> implements Set<Map.Entry<K, V>> {
        ConstrainedEntrySet(Set<Map.Entry<K, V>> set, MapConstraint<? super K, ? super V> mapConstraint) {
            super(set, mapConstraint);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@Nullable Object obj) {
            return Sets.equalsImpl(this, obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    private static class ConstrainedListMultimap<K, V> extends ConstrainedMultimap<K, V> implements ListMultimap<K, V> {
        ConstrainedListMultimap(ListMultimap<K, V> listMultimap, MapConstraint<? super K, ? super V> mapConstraint) {
            super(listMultimap, mapConstraint);
        }

        @Override // com.google.common.collect.MapConstraints.ConstrainedMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public List<V> get(K k) {
            return (List) super.get((Object) k);
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public List<V> removeAll(Object obj) {
            return (List) super.removeAll(obj);
        }

        @Override // com.google.common.collect.MapConstraints.ConstrainedMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            return (List) super.replaceValues((Object) k, (Iterable) iterable);
        }
    }

    static class ConstrainedMap<K, V> extends ForwardingMap<K, V> {
        final MapConstraint<? super K, ? super V> constraint;
        private final Map<K, V> delegate;
        private transient Set<Map.Entry<K, V>> entrySet;

        ConstrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> mapConstraint) {
            this.delegate = (Map) Preconditions.checkNotNull(map);
            this.constraint = (MapConstraint) Preconditions.checkNotNull(mapConstraint);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public Map<K, V> delegate() {
            return this.delegate;
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.entrySet;
            if (set != null) {
                return set;
            }
            Set<Map.Entry<K, V>> setConstrainedEntrySet = MapConstraints.constrainedEntrySet(this.delegate.entrySet(), this.constraint);
            this.entrySet = setConstrainedEntrySet;
            return setConstrainedEntrySet;
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
        @CanIgnoreReturnValue
        public V put(K k, V v) {
            this.constraint.checkKeyValue(k, v);
            return this.delegate.put(k, v);
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
        public void putAll(Map<? extends K, ? extends V> map) {
            this.delegate.putAll(MapConstraints.checkMap(map, this.constraint));
        }
    }

    private static class ConstrainedMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        transient Map<K, Collection<V>> asMap;
        final MapConstraint<? super K, ? super V> constraint;
        final Multimap<K, V> delegate;
        transient Collection<Map.Entry<K, V>> entries;

        public ConstrainedMultimap(Multimap<K, V> multimap, MapConstraint<? super K, ? super V> mapConstraint) {
            this.delegate = (Multimap) Preconditions.checkNotNull(multimap);
            this.constraint = (MapConstraint) Preconditions.checkNotNull(mapConstraint);
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map = this.asMap;
            if (map != null) {
                return map;
            }
            final Map<K, Collection<V>> mapAsMap = this.delegate.asMap();
            ForwardingMap<K, Collection<V>> forwardingMap = new ForwardingMap<K, Collection<V>>() { // from class: com.google.common.collect.MapConstraints.ConstrainedMultimap.1AsMap
                Set<Map.Entry<K, Collection<V>>> entrySet;
                Collection<Collection<V>> values;

                @Override // com.google.common.collect.ForwardingMap, java.util.Map
                public boolean containsValue(Object obj) {
                    return values().contains(obj);
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
                public Map<K, Collection<V>> delegate() {
                    return mapAsMap;
                }

                @Override // com.google.common.collect.ForwardingMap, java.util.Map
                public Set<Map.Entry<K, Collection<V>>> entrySet() {
                    Set<Map.Entry<K, Collection<V>>> set = this.entrySet;
                    if (set != null) {
                        return set;
                    }
                    Set<Map.Entry<K, Collection<V>>> setConstrainedAsMapEntries = MapConstraints.constrainedAsMapEntries(mapAsMap.entrySet(), ConstrainedMultimap.this.constraint);
                    this.entrySet = setConstrainedAsMapEntries;
                    return setConstrainedAsMapEntries;
                }

                @Override // com.google.common.collect.ForwardingMap, java.util.Map
                public Collection<V> get(Object obj) {
                    try {
                        Collection<V> collection = ConstrainedMultimap.this.get(obj);
                        if (collection.isEmpty()) {
                            return null;
                        }
                        return collection;
                    } catch (ClassCastException unused) {
                        return null;
                    }
                }

                @Override // com.google.common.collect.ForwardingMap, java.util.Map
                public Collection<Collection<V>> values() {
                    Collection<Collection<V>> collection = this.values;
                    if (collection != null) {
                        return collection;
                    }
                    ConstrainedAsMapValues constrainedAsMapValues = new ConstrainedAsMapValues(delegate().values(), entrySet());
                    this.values = constrainedAsMapValues;
                    return constrainedAsMapValues;
                }
            };
            this.asMap = forwardingMap;
            return forwardingMap;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.ForwardingObject
        public Multimap<K, V> delegate() {
            return this.delegate;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection = this.entries;
            if (collection != null) {
                return collection;
            }
            Collection<Map.Entry<K, V>> collectionConstrainedEntries = MapConstraints.constrainedEntries(this.delegate.entries(), this.constraint);
            this.entries = collectionConstrainedEntries;
            return collectionConstrainedEntries;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Collection<V> get(final K k) {
            return Constraints.constrainedTypePreservingCollection(this.delegate.get(k), new Constraint<V>() { // from class: com.google.common.collect.MapConstraints.ConstrainedMultimap.1
                @Override // com.google.common.collect.Constraint
                public V checkElement(V v) {
                    ConstrainedMultimap.this.constraint.checkKeyValue((Object) k, v);
                    return v;
                }
            });
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean put(K k, V v) {
            this.constraint.checkKeyValue(k, v);
            return this.delegate.put(k, v);
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean zPut = false;
            for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
                zPut |= put(entry.getKey(), entry.getValue());
            }
            return zPut;
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            return this.delegate.putAll(k, MapConstraints.checkValues(k, iterable, this.constraint));
        }

        @Override // com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            return this.delegate.replaceValues(k, MapConstraints.checkValues(k, iterable, this.constraint));
        }
    }

    private MapConstraints() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Map<K, V> checkMap(Map<? extends K, ? extends V> map, MapConstraint<? super K, ? super V> mapConstraint) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        for (Map.Entry<K, V> entry : linkedHashMap.entrySet()) {
            mapConstraint.checkKeyValue(entry.getKey(), entry.getValue());
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Collection<V> checkValues(K k, Iterable<? extends V> iterable, MapConstraint<? super K, ? super V> mapConstraint) {
        ArrayList arrayListNewArrayList = Lists.newArrayList(iterable);
        Iterator it = arrayListNewArrayList.iterator();
        while (it.hasNext()) {
            mapConstraint.checkKeyValue(k, (Object) it.next());
        }
        return arrayListNewArrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Set<Map.Entry<K, Collection<V>>> constrainedAsMapEntries(Set<Map.Entry<K, Collection<V>>> set, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedAsMapEntries(set, mapConstraint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Map.Entry<K, Collection<V>> constrainedAsMapEntry(final Map.Entry<K, Collection<V>> entry, final MapConstraint<? super K, ? super V> mapConstraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(mapConstraint);
        return new ForwardingMapEntry<K, Collection<V>>() { // from class: com.google.common.collect.MapConstraints.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
            public Map.Entry<K, Collection<V>> delegate() {
                return entry;
            }

            @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
            public Collection<V> getValue() {
                return Constraints.constrainedTypePreservingCollection((Collection) entry.getValue(), new Constraint<V>() { // from class: com.google.common.collect.MapConstraints.2.1
                    @Override // com.google.common.collect.Constraint
                    public V checkElement(V v) {
                        mapConstraint.checkKeyValue(getKey(), v);
                        return v;
                    }
                });
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Collection<Map.Entry<K, V>> constrainedEntries(Collection<Map.Entry<K, V>> collection, MapConstraint<? super K, ? super V> mapConstraint) {
        return collection instanceof Set ? constrainedEntrySet((Set) collection, mapConstraint) : new ConstrainedEntries(collection, mapConstraint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Map.Entry<K, V> constrainedEntry(final Map.Entry<K, V> entry, final MapConstraint<? super K, ? super V> mapConstraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(mapConstraint);
        return new ForwardingMapEntry<K, V>() { // from class: com.google.common.collect.MapConstraints.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
            public Map.Entry<K, V> delegate() {
                return entry;
            }

            @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
            public V setValue(V v) {
                mapConstraint.checkKeyValue(getKey(), v);
                return (V) entry.setValue(v);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> Set<Map.Entry<K, V>> constrainedEntrySet(Set<Map.Entry<K, V>> set, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedEntrySet(set, mapConstraint);
    }

    public static <K, V> ListMultimap<K, V> constrainedListMultimap(ListMultimap<K, V> listMultimap, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedListMultimap(listMultimap, mapConstraint);
    }

    public static <K, V> Map<K, V> constrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> mapConstraint) {
        return new ConstrainedMap(map, mapConstraint);
    }
}
