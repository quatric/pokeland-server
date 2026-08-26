package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface MutableValueGraph<N, V> extends ValueGraph<N, V> {
    @CanIgnoreReturnValue
    boolean addNode(N n);

    @CanIgnoreReturnValue
    V putEdgeValue(N n, N n2, V v);

    @CanIgnoreReturnValue
    V removeEdge(Object obj, Object obj2);

    @CanIgnoreReturnValue
    boolean removeNode(Object obj);
}
