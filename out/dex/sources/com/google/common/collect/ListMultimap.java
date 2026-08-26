package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
public interface ListMultimap<K, V> extends Multimap<K, V> {
    Map<K, Collection<V>> asMap();

    boolean equals(@Nullable Object obj);

    @Override // com.google.common.collect.Multimap
    List<V> get(@Nullable K k);

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    List<V> removeAll(@Nullable Object obj);

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    List<V> replaceValues(K k, Iterable<? extends V> iterable);
}
