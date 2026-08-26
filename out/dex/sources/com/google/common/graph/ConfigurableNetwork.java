package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class ConfigurableNetwork<N, E> extends AbstractNetwork<N, E> {
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;
    private final ElementOrder<E> edgeOrder;
    protected final MapIteratorCache<E, N> edgeToReferenceNode;
    private final boolean isDirected;
    protected final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        this(networkBuilder, networkBuilder.nodeOrder.createMap(networkBuilder.expectedNodeCount.mo462or(10).intValue()), networkBuilder.edgeOrder.createMap(networkBuilder.expectedEdgeCount.mo462or(20).intValue()));
    }

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder, Map<N, NetworkConnections<N, E>> map, Map<E, N> map2) {
        this.isDirected = networkBuilder.directed;
        this.allowsParallelEdges = networkBuilder.allowsParallelEdges;
        this.allowsSelfLoops = networkBuilder.allowsSelfLoops;
        this.nodeOrder = (ElementOrder<N>) networkBuilder.nodeOrder.cast();
        this.edgeOrder = (ElementOrder<E>) networkBuilder.edgeOrder.cast();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.edgeToReferenceNode = new MapIteratorCache<>(map2);
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(Object obj) {
        return checkedConnections(obj).adjacentNodes();
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    protected final NetworkConnections<N, E> checkedConnections(Object obj) {
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(obj);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", obj));
    }

    protected final N checkedReferenceNode(Object obj) {
        N n = this.edgeToReferenceNode.get(obj);
        if (n != null) {
            return n;
        }
        Preconditions.checkNotNull(obj);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", obj));
    }

    protected final boolean containsEdge(@Nullable Object obj) {
        return this.edgeToReferenceNode.containsKey(obj);
    }

    protected final boolean containsNode(@Nullable Object obj) {
        return this.nodeConnections.containsKey(obj);
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return this.edgeToReferenceNode.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.Network
    public Set<E> edgesConnecting(Object obj, Object obj2) {
        NetworkConnections<N, E> networkConnectionsCheckedConnections = checkedConnections(obj);
        if (!this.allowsSelfLoops && obj == obj2) {
            return ImmutableSet.m533of();
        }
        Preconditions.checkArgument(containsNode(obj2), "Node %s is not an element of this graph.", obj2);
        return networkConnectionsCheckedConnections.edgesConnecting(obj2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(Object obj) {
        return checkedConnections(obj).inEdges();
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(Object obj) {
        return checkedConnections(obj).incidentEdges();
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(Object obj) {
        N nCheckedReferenceNode = checkedReferenceNode(obj);
        return EndpointPair.m590of(this, nCheckedReferenceNode, this.nodeConnections.get(nCheckedReferenceNode).oppositeNode(obj));
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(Object obj) {
        return checkedConnections(obj).outEdges();
    }

    @Override // com.google.common.graph.Network
    public Set<N> predecessors(Object obj) {
        return checkedConnections(obj).predecessors();
    }

    @Override // com.google.common.graph.Network
    public Set<N> successors(Object obj) {
        return checkedConnections(obj).successors();
    }
}
