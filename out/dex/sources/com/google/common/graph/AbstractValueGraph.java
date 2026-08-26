package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public abstract class AbstractValueGraph<N, V> extends AbstractGraph<N> implements ValueGraph<N, V> {
    private Map<EndpointPair<N>, V> edgeValueMap() {
        return Maps.asMap(edges(), new Function<EndpointPair<N>, V>() { // from class: com.google.common.graph.AbstractValueGraph.1
            @Override // com.google.common.base.Function
            public V apply(EndpointPair<N> endpointPair) {
                return (V) AbstractValueGraph.this.edgeValue(endpointPair.nodeU(), endpointPair.nodeV());
            }
        });
    }

    @Override // com.google.common.graph.ValueGraph
    public V edgeValue(Object obj, Object obj2) {
        V vEdgeValueOrDefault = edgeValueOrDefault(obj, obj2, null);
        if (vEdgeValueOrDefault != null) {
            return vEdgeValueOrDefault;
        }
        Preconditions.checkArgument(nodes().contains(obj), "Node %s is not an element of this graph.", obj);
        Preconditions.checkArgument(nodes().contains(obj2), "Node %s is not an element of this graph.", obj2);
        throw new IllegalArgumentException(String.format("Edge connecting %s to %s is not present in this graph.", obj, obj2));
    }

    @Override // com.google.common.graph.AbstractGraph
    public String toString() {
        return String.format("%s, nodes: %s, edges: %s", String.format("isDirected: %s, allowsSelfLoops: %s", Boolean.valueOf(isDirected()), Boolean.valueOf(allowsSelfLoops())), nodes(), edgeValueMap());
    }
}
