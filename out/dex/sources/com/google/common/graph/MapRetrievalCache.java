package com.google.common.graph;

import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class MapRetrievalCache<K, V> extends MapIteratorCache<K, V> {

    @Nullable
    private transient CacheEntry<K, V> cacheEntry1;

    @Nullable
    private transient CacheEntry<K, V> cacheEntry2;

    private static final class CacheEntry<K, V> {
        final K key;
        final V value;

        CacheEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    MapRetrievalCache(Map<K, V> map) {
        super(map);
    }

    private void addToCache(CacheEntry<K, V> cacheEntry) {
        this.cacheEntry2 = this.cacheEntry1;
        this.cacheEntry1 = cacheEntry;
    }

    private void addToCache(K k, V v) {
        addToCache(new CacheEntry<>(k, v));
    }

    @Override // com.google.common.graph.MapIteratorCache
    protected void clearCache() {
        super.clearCache();
        this.cacheEntry1 = null;
        this.cacheEntry2 = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.MapIteratorCache
    public V get(@Nullable Object obj) {
        V ifCached = getIfCached(obj);
        if (ifCached != null) {
            return ifCached;
        }
        V withoutCaching = getWithoutCaching(obj);
        if (withoutCaching != null) {
            addToCache(obj, withoutCaching);
        }
        return withoutCaching;
    }

    @Override // com.google.common.graph.MapIteratorCache
    protected V getIfCached(@Nullable Object obj) {
        V v = (V) super.getIfCached(obj);
        if (v != null) {
            return v;
        }
        CacheEntry<K, V> cacheEntry = this.cacheEntry1;
        if (cacheEntry != null && cacheEntry.key == obj) {
            return cacheEntry.value;
        }
        CacheEntry<K, V> cacheEntry2 = this.cacheEntry2;
        if (cacheEntry2 == null || cacheEntry2.key != obj) {
            return null;
        }
        addToCache(cacheEntry2);
        return cacheEntry2.value;
    }
}
