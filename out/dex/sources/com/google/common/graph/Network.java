package com.google.common.graph;

import com.google.common.annotations.Beta;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public interface Network<N, E> {
    Set<E> adjacentEdges(Object obj);

    Set<N> adjacentNodes(Object obj);

    boolean allowsParallelEdges();

    boolean allowsSelfLoops();

    Graph<N> asGraph();

    int degree(Object obj);

    ElementOrder<E> edgeOrder();

    Set<E> edges();

    Set<E> edgesConnecting(Object obj, Object obj2);

    boolean equals(@Nullable Object obj);

    int hashCode();

    int inDegree(Object obj);

    Set<E> inEdges(Object obj);

    Set<E> incidentEdges(Object obj);

    EndpointPair<N> incidentNodes(Object obj);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(Object obj);

    Set<E> outEdges(Object obj);

    Set<N> predecessors(Object obj);

    Set<N> successors(Object obj);
}
