package com.google.common.graph;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface ValueGraph<N, V> extends Graph<N> {
    V edgeValue(Object obj, Object obj2);

    V edgeValueOrDefault(Object obj, Object obj2, @Nullable V v);

    @Override // com.google.common.graph.Graph
    boolean equals(@Nullable Object obj);

    @Override // com.google.common.graph.Graph
    int hashCode();
}
