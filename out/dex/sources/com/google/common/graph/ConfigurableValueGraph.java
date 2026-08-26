package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class ConfigurableValueGraph<N, V> extends AbstractValueGraph<N, V> {
    private final boolean allowsSelfLoops;
    protected long edgeCount;
    private final boolean isDirected;
    protected final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this(abstractGraphBuilder, abstractGraphBuilder.nodeOrder.createMap(abstractGraphBuilder.expectedNodeCount.mo462or(10).intValue()), 0L);
    }

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder, Map<N, GraphConnections<N, V>> map, long j) {
        this.isDirected = abstractGraphBuilder.directed;
        this.allowsSelfLoops = abstractGraphBuilder.allowsSelfLoops;
        this.nodeOrder = (ElementOrder<N>) abstractGraphBuilder.nodeOrder.cast();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.edgeCount = Graphs.checkNonNegative(j);
    }

    @Override // com.google.common.graph.Graph
    public Set<N> adjacentNodes(Object obj) {
        return checkedConnections(obj).adjacentNodes();
    }

    @Override // com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    protected final GraphConnections<N, V> checkedConnections(Object obj) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(obj);
        if (graphConnections != null) {
            return graphConnections;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", obj));
    }

    protected final boolean containsNode(@Nullable Object obj) {
        return this.nodeConnections.containsKey(obj);
    }

    @Override // com.google.common.graph.AbstractGraph
    protected long edgeCount() {
        return this.edgeCount;
    }

    @Override // com.google.common.graph.ValueGraph
    public V edgeValueOrDefault(Object obj, Object obj2, @Nullable V v) {
        V vValue;
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(obj);
        return (graphConnections == null || (vValue = graphConnections.value(obj2)) == null) ? v : vValue;
    }

    @Override // com.google.common.graph.Graph
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.Graph
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.Graph
    public Set<N> predecessors(Object obj) {
        return checkedConnections(obj).predecessors();
    }

    @Override // com.google.common.graph.Graph
    public Set<N> successors(Object obj) {
        return checkedConnections(obj).successors();
    }
}
