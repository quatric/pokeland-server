package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class MapIteratorCache<K, V> {
    private final Map<K, V> backingMap;

    @Nullable
    private transient Map.Entry<K, V> entrySetCache;

    MapIteratorCache(Map<K, V> map) {
        this.backingMap = (Map) Preconditions.checkNotNull(map);
    }

    public void clear() {
        clearCache();
        this.backingMap.clear();
    }

    protected void clearCache() {
        this.entrySetCache = null;
    }

    public final boolean containsKey(@Nullable Object obj) {
        return getIfCached(obj) != null || this.backingMap.containsKey(obj);
    }

    public V get(@Nullable Object obj) {
        V ifCached = getIfCached(obj);
        return ifCached != null ? ifCached : getWithoutCaching(obj);
    }

    protected V getIfCached(@Nullable Object obj) {
        Map.Entry<K, V> entry = this.entrySetCache;
        if (entry == null || entry.getKey() != obj) {
            return null;
        }
        return entry.getValue();
    }

    public final V getWithoutCaching(@Nullable Object obj) {
        return this.backingMap.get(obj);
    }

    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        clearCache();
        return this.backingMap.put(k, v);
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj) {
        clearCache();
        return this.backingMap.remove(obj);
    }

    public final Set<K> unmodifiableKeySet() {
        return new AbstractSet<K>() { // from class: com.google.common.graph.MapIteratorCache.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@Nullable Object obj) {
                return MapIteratorCache.this.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<K> iterator() {
                final Iterator<Map.Entry<K, V>> it = MapIteratorCache.this.backingMap.entrySet().iterator();
                return new UnmodifiableIterator<K>() { // from class: com.google.common.graph.MapIteratorCache.1.1
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override // java.util.Iterator
                    public K next() {
                        Map.Entry entry = (Map.Entry) it.next();
                        MapIteratorCache.this.entrySetCache = entry;
                        return (K) entry.getKey();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return MapIteratorCache.this.backingMap.size();
            }
        };
    }
}
