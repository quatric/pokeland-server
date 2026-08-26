package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
interface GraphConnections<N, V> {
    void addPredecessor(N n, V v);

    @CanIgnoreReturnValue
    V addSuccessor(N n, V v);

    Set<N> adjacentNodes();

    Set<N> predecessors();

    void removePredecessor(Object obj);

    @CanIgnoreReturnValue
    V removeSuccessor(Object obj);

    Set<N> successors();

    @Nullable
    V value(Object obj);
}
