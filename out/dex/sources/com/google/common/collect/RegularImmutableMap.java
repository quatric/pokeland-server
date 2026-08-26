package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    private static final double MAX_LOAD_FACTOR = 1.2d;
    private static final long serialVersionUID = 0;
    private final transient Map.Entry<K, V>[] entries;
    private final transient int mask;
    private final transient ImmutableMapEntry<K, V>[] table;

    @GwtCompatible(emulated = true)
    private static final class KeySet<K, V> extends ImmutableSet.Indexed<K> {

        @Weak
        private final RegularImmutableMap<K, V> map;

        @GwtIncompatible
        private static class SerializedForm<K> implements Serializable {
            private static final long serialVersionUID = 0;
            final ImmutableMap<K, ?> map;

            SerializedForm(ImmutableMap<K, ?> immutableMap) {
                this.map = immutableMap;
            }

            Object readResolve() {
                return this.map.keySet();
            }
        }

        KeySet(RegularImmutableMap<K, V> regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // com.google.common.collect.ImmutableSet.Indexed
        K get(int i) {
            return (K) ((RegularImmutableMap) this.map).entries[i].getKey();
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        @GwtIncompatible
        Object writeReplace() {
            return new SerializedForm(this.map);
        }
    }

    @GwtCompatible(emulated = true)
    private static final class Values<K, V> extends ImmutableList<V> {

        @Weak
        final RegularImmutableMap<K, V> map;

        @GwtIncompatible
        private static class SerializedForm<V> implements Serializable {
            private static final long serialVersionUID = 0;
            final ImmutableMap<?, V> map;

            SerializedForm(ImmutableMap<?, V> immutableMap) {
                this.map = immutableMap;
            }

            Object readResolve() {
                return this.map.values();
            }
        }

        Values(RegularImmutableMap<K, V> regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override // java.util.List
        public V get(int i) {
            return (V) ((RegularImmutableMap) this.map).entries[i].getValue();
        }

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
        @GwtIncompatible
        Object writeReplace() {
            return new SerializedForm(this.map);
        }
    }

    private RegularImmutableMap(Map.Entry<K, V>[] entryArr, ImmutableMapEntry<K, V>[] immutableMapEntryArr, int i) {
        this.entries = entryArr;
        this.table = immutableMapEntryArr;
        this.mask = i;
    }

    static void checkNoConflictInKeyBucket(Object obj, Map.Entry<?, ?> entry, @Nullable ImmutableMapEntry<?, ?> immutableMapEntry) {
        while (immutableMapEntry != null) {
            checkNoConflict(!obj.equals(immutableMapEntry.getKey()), "key", entry, immutableMapEntry);
            immutableMapEntry = immutableMapEntry.getNextInKeyBucket();
        }
    }

    static <K, V> RegularImmutableMap<K, V> fromEntries(Map.Entry<K, V>... entryArr) {
        return fromEntryArray(entryArr.length, entryArr);
    }

    static <K, V> RegularImmutableMap<K, V> fromEntryArray(int i, Map.Entry<K, V>[] entryArr) {
        Preconditions.checkPositionIndex(i, entryArr.length);
        Map.Entry<K, V>[] entryArrCreateEntryArray = i == entryArr.length ? entryArr : ImmutableMapEntry.createEntryArray(i);
        int iClosedTableSize = Hashing.closedTableSize(i, MAX_LOAD_FACTOR);
        ImmutableMapEntry[] immutableMapEntryArrCreateEntryArray = ImmutableMapEntry.createEntryArray(iClosedTableSize);
        int i2 = iClosedTableSize - 1;
        for (int i3 = 0; i3 < i; i3++) {
            Map.Entry<K, V> entry = entryArr[i3];
            K key = entry.getKey();
            V value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            int iSmear = Hashing.smear(key.hashCode()) & i2;
            ImmutableMapEntry immutableMapEntry = immutableMapEntryArrCreateEntryArray[iSmear];
            ImmutableMapEntry immutableMapEntry2 = immutableMapEntry == null ? (entry instanceof ImmutableMapEntry) && ((ImmutableMapEntry) entry).isReusable() ? (ImmutableMapEntry) entry : new ImmutableMapEntry(key, value) : new ImmutableMapEntry.NonTerminalImmutableMapEntry(key, value, immutableMapEntry);
            immutableMapEntryArrCreateEntryArray[iSmear] = immutableMapEntry2;
            entryArrCreateEntryArray[i3] = immutableMapEntry2;
            checkNoConflictInKeyBucket(key, immutableMapEntry2, immutableMapEntry);
        }
        return new RegularImmutableMap<>(entryArrCreateEntryArray, immutableMapEntryArrCreateEntryArray, i2);
    }

    @Nullable
    static <V> V get(@Nullable Object obj, ImmutableMapEntry<?, V>[] immutableMapEntryArr, int i) {
        if (obj == null) {
            return null;
        }
        for (ImmutableMapEntry<?, V> nextInKeyBucket = immutableMapEntryArr[i & Hashing.smear(obj.hashCode())]; nextInKeyBucket != null; nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket()) {
            if (obj.equals(nextInKeyBucket.getKey())) {
                return nextInKeyBucket.getValue();
            }
        }
        return null;
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet(this, this.entries);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> createKeySet() {
        return new KeySet(this);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> createValues() {
        return new Values(this);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public V get(@Nullable Object obj) {
        return (V) get(obj, this.table, this.mask);
    }

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.Map
    public int size() {
        return this.entries.length;
    }
}
