package com.google.common.graph;

import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class ForwardingGraph<N> extends AbstractGraph<N> {
    ForwardingGraph() {
    }

    @Override // com.google.common.graph.Graph
    public Set<N> adjacentNodes(Object obj) {
        return delegate().adjacentNodes(obj);
    }

    @Override // com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return delegate().allowsSelfLoops();
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.Graph
    public int degree(Object obj) {
        return delegate().degree(obj);
    }

    protected abstract Graph<N> delegate();

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.Graph
    public Set<EndpointPair<N>> edges() {
        return delegate().edges();
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.Graph
    public int inDegree(Object obj) {
        return delegate().inDegree(obj);
    }

    @Override // com.google.common.graph.Graph
    public boolean isDirected() {
        return delegate().isDirected();
    }

    @Override // com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return delegate().nodeOrder();
    }

    @Override // com.google.common.graph.Graph
    public Set<N> nodes() {
        return delegate().nodes();
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.Graph
    public int outDegree(Object obj) {
        return delegate().outDegree(obj);
    }

    @Override // com.google.common.graph.Graph
    public Set<N> predecessors(Object obj) {
        return delegate().predecessors(obj);
    }

    @Override // com.google.common.graph.Graph
    public Set<N> successors(Object obj) {
        return delegate().successors(obj);
    }
}
