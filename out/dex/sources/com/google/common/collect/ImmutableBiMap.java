package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {

    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        public Builder() {
        }

        Builder(int i) {
            super(i);
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        public ImmutableBiMap<K, V> build() {
            int i = this.size;
            if (i == 0) {
                return ImmutableBiMap.m483of();
            }
            if (i == 1) {
                return ImmutableBiMap.m484of((Object) this.entries[0].getKey(), (Object) this.entries[0].getValue());
            }
            if (this.valueComparator != null) {
                if (this.entriesUsed) {
                    this.entries = (ImmutableMapEntry[]) ObjectArrays.arraysCopyOf(this.entries, this.size);
                }
                Arrays.sort(this.entries, 0, this.size, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
            }
            this.entriesUsed = this.size == this.entries.length;
            return RegularImmutableBiMap.fromEntryArray(this.size, this.entries);
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            super.orderEntriesByValue((Comparator) comparator);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            super.put((Object) k, (Object) v);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll((Iterable) iterable);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll((Map) map);
            return this;
        }
    }

    private static class SerializedForm extends ImmutableMap.SerializedForm {
        private static final long serialVersionUID = 0;

        SerializedForm(ImmutableBiMap<?, ?> immutableBiMap) {
            super(immutableBiMap);
        }

        @Override // com.google.common.collect.ImmutableMap.SerializedForm
        Object readResolve() {
            return createMap(new Builder());
        }
    }

    ImmutableBiMap() {
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    @Beta
    public static <K, V> ImmutableBiMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Map.Entry[] entryArr = (Map.Entry[]) Iterables.toArray(iterable, EMPTY_ENTRY_ARRAY);
        int length = entryArr.length;
        if (length == 0) {
            return m483of();
        }
        if (length != 1) {
            return RegularImmutableBiMap.fromEntries(entryArr);
        }
        Map.Entry entry = entryArr[0];
        return m484of(entry.getKey(), entry.getValue());
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableBiMap) {
            ImmutableBiMap<K, V> immutableBiMap = (ImmutableBiMap) map;
            if (!immutableBiMap.isPartialView()) {
                return immutableBiMap;
            }
        }
        return copyOf((Iterable) map.entrySet());
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m483of() {
        return RegularImmutableBiMap.EMPTY;
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m484of(K k, V v) {
        return new SingletonImmutableBiMap(k, v);
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m485of(K k, V v, K k2, V v2) {
        return RegularImmutableBiMap.fromEntries(entryOf(k, v), entryOf(k2, v2));
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m486of(K k, V v, K k2, V v2, K k3, V v3) {
        return RegularImmutableBiMap.fromEntries(entryOf(k, v), entryOf(k2, v2), entryOf(k3, v3));
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m487of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return RegularImmutableBiMap.fromEntries(entryOf(k, v), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
    }

    /* JADX INFO: renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m488of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return RegularImmutableBiMap.fromEntries(entryOf(k, v), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @Deprecated
    public V forcePut(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.BiMap
    public abstract ImmutableBiMap<V, K> inverse();

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSet<V> values() {
        return inverse().keySet();
    }

    @Override // com.google.common.collect.ImmutableMap
    Object writeReplace() {
        return new SerializedForm(this);
    }
}
