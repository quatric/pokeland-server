package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface MutableGraph<N> extends Graph<N> {
    @CanIgnoreReturnValue
    boolean addNode(N n);

    @CanIgnoreReturnValue
    boolean putEdge(N n, N n2);

    @CanIgnoreReturnValue
    boolean removeEdge(Object obj, Object obj2);

    @CanIgnoreReturnValue
    boolean removeNode(Object obj);
}
