package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class ImmutableValueGraph<N, V> extends ImmutableGraph.ValueBackedImpl<N, V> implements ValueGraph<N, V> {
    private ImmutableValueGraph(ValueGraph<N, V> valueGraph) {
        super(ValueGraphBuilder.from(valueGraph), getNodeConnections((ValueGraph) valueGraph), valueGraph.edges().size());
    }

    private static <N, V> GraphConnections<N, V> connectionsOf(final ValueGraph<N, V> valueGraph, final N n) {
        Function<N, V> function = new Function<N, V>() { // from class: com.google.common.graph.ImmutableValueGraph.1
            @Override // com.google.common.base.Function
            public V apply(N n2) {
                return (V) valueGraph.edgeValue(n, n2);
            }
        };
        return valueGraph.isDirected() ? DirectedGraphConnections.ofImmutable(valueGraph.predecessors(n), Maps.asMap(valueGraph.successors(n), function)) : UndirectedGraphConnections.ofImmutable(Maps.asMap(valueGraph.adjacentNodes(n), function));
    }

    @Deprecated
    public static <N, V> ImmutableValueGraph<N, V> copyOf(ImmutableValueGraph<N, V> immutableValueGraph) {
        return (ImmutableValueGraph) Preconditions.checkNotNull(immutableValueGraph);
    }

    public static <N, V> ImmutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        return valueGraph instanceof ImmutableValueGraph ? (ImmutableValueGraph) valueGraph : new ImmutableValueGraph<>(valueGraph);
    }

    private static <N, V> ImmutableMap<N, GraphConnections<N, V>> getNodeConnections(ValueGraph<N, V> valueGraph) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n : valueGraph.nodes()) {
            builder.put(n, connectionsOf((ValueGraph) valueGraph, (Object) n));
        }
        return builder.build();
    }

    @Override // com.google.common.graph.ValueGraph
    public V edgeValue(Object obj, Object obj2) {
        return this.backingValueGraph.edgeValue(obj, obj2);
    }

    @Override // com.google.common.graph.ValueGraph
    public V edgeValueOrDefault(Object obj, Object obj2, @Nullable V v) {
        return this.backingValueGraph.edgeValueOrDefault(obj, obj2, v);
    }

    @Override // com.google.common.graph.AbstractGraph
    public String toString() {
        return this.backingValueGraph.toString();
    }
}
