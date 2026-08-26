package com.google.common.graph;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class ConfigurableMutableGraph<N> extends ForwardingGraph<N> implements MutableGraph<N> {
    private final MutableValueGraph<N, GraphConstants.Presence> backingValueGraph;

    ConfigurableMutableGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this.backingValueGraph = new ConfigurableMutableValueGraph(abstractGraphBuilder);
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean addNode(N n) {
        return this.backingValueGraph.addNode(n);
    }

    @Override // com.google.common.graph.ForwardingGraph
    protected Graph<N> delegate() {
        return this.backingValueGraph;
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean putEdge(N n, N n2) {
        return this.backingValueGraph.putEdgeValue(n, n2, GraphConstants.Presence.EDGE_EXISTS) == null;
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean removeEdge(Object obj, Object obj2) {
        return this.backingValueGraph.removeEdge(obj, obj2) != null;
    }

    @Override // com.google.common.graph.MutableGraph
    public boolean removeNode(Object obj) {
        return this.backingValueGraph.removeNode(obj);
    }
}
