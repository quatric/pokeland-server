package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class UndirectedNetworkConnections<N, E> extends AbstractUndirectedNetworkConnections<N, E> {
    protected UndirectedNetworkConnections(Map<E, N> map) {
        super(map);
    }

    /* JADX INFO: renamed from: of */
    static <N, E> UndirectedNetworkConnections<N, E> m594of() {
        return new UndirectedNetworkConnections<>(HashBiMap.create(2));
    }

    static <N, E> UndirectedNetworkConnections<N, E> ofImmutable(Map<E, N> map) {
        return new UndirectedNetworkConnections<>(ImmutableBiMap.copyOf((Map) map));
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(((BiMap) this.incidentEdgeMap).values());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> edgesConnecting(Object obj) {
        return new EdgesConnecting(((BiMap) this.incidentEdgeMap).inverse(), obj);
    }
}
