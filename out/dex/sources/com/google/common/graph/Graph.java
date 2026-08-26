package com.google.common.graph;

import com.google.common.annotations.Beta;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface Graph<N> {
    Set<N> adjacentNodes(Object obj);

    boolean allowsSelfLoops();

    int degree(Object obj);

    Set<EndpointPair<N>> edges();

    boolean equals(@Nullable Object obj);

    int hashCode();

    int inDegree(Object obj);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(Object obj);

    Set<N> predecessors(Object obj);

    Set<N> successors(Object obj);
}
