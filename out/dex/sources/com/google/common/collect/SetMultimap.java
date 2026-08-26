package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public interface SetMultimap<K, V> extends Multimap<K, V> {
    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    Map<K, Collection<V>> asMap();

    @Override // com.google.common.collect.Multimap
    Set<Map.Entry<K, V>> entries();

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    boolean equals(@Nullable Object obj);

    @Override // com.google.common.collect.Multimap
    Set<V> get(@Nullable K k);

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    Set<V> removeAll(@Nullable Object obj);

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    Set<V> replaceValues(K k, Iterable<? extends V> iterable);
}
