package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class ImmutableNetwork<N, E> extends ConfigurableNetwork<N, E> {
    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), getNodeConnections(network), getEdgeToReferenceNode(network));
    }

    private static <N, E> Function<E, N> adjacentNodeFn(final Network<N, E> network, final N n) {
        return new Function<E, N>() { // from class: com.google.common.graph.ImmutableNetwork.4
            @Override // com.google.common.base.Function
            public N apply(E e) {
                return network.incidentNodes(e).adjacentNode(n);
            }
        };
    }

    private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> network, N n) {
        if (!network.isDirected()) {
            Map mapAsMap = Maps.asMap(network.incidentEdges(n), adjacentNodeFn(network, n));
            return network.allowsParallelEdges() ? UndirectedMultiNetworkConnections.ofImmutable(mapAsMap) : UndirectedNetworkConnections.ofImmutable(mapAsMap);
        }
        Map mapAsMap2 = Maps.asMap(network.inEdges(n), sourceNodeFn(network));
        Map mapAsMap3 = Maps.asMap(network.outEdges(n), targetNodeFn(network));
        int size = network.edgesConnecting(n, n).size();
        return network.allowsParallelEdges() ? DirectedMultiNetworkConnections.ofImmutable(mapAsMap2, mapAsMap3, size) : DirectedNetworkConnections.ofImmutable(mapAsMap2, mapAsMap3, size);
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return (ImmutableNetwork) Preconditions.checkNotNull(immutableNetwork);
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
        return network instanceof ImmutableNetwork ? (ImmutableNetwork) network : new ImmutableNetwork<>(network);
    }

    private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> network) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (E e : network.edges()) {
            builder.put(e, network.incidentNodes(e).nodeU());
        }
        return builder.build();
    }

    private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> network) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n : network.nodes()) {
            builder.put(n, connectionsOf(network, n));
        }
        return builder.build();
    }

    private static <N, E> Function<E, N> sourceNodeFn(final Network<N, E> network) {
        return new Function<E, N>() { // from class: com.google.common.graph.ImmutableNetwork.2
            @Override // com.google.common.base.Function
            public N apply(E e) {
                return network.incidentNodes(e).source();
            }
        };
    }

    private static <N, E> Function<E, N> targetNodeFn(final Network<N, E> network) {
        return new Function<E, N>() { // from class: com.google.common.graph.ImmutableNetwork.3
            @Override // com.google.common.base.Function
            public N apply(E e) {
                return network.incidentNodes(e).target();
            }
        };
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean allowsParallelEdges() {
        return super.allowsParallelEdges();
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public ImmutableGraph<N> asGraph() {
        final Graph<N> graphAsGraph = super.asGraph();
        return new ImmutableGraph<N>() { // from class: com.google.common.graph.ImmutableNetwork.1
            @Override // com.google.common.graph.ForwardingGraph
            protected Graph<N> delegate() {
                return graphAsGraph;
            }
        };
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ ElementOrder edgeOrder() {
        return super.edgeOrder();
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set edges() {
        return super.edges();
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set edgesConnecting(Object obj, Object obj2) {
        return super.edgesConnecting(obj, obj2);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set inEdges(Object obj) {
        return super.inEdges(obj);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set incidentEdges(Object obj) {
        return super.incidentEdges(obj);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ EndpointPair incidentNodes(Object obj) {
        return super.incidentNodes(obj);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set outEdges(Object obj) {
        return super.outEdges(obj);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors(obj);
    }

    @Override // com.google.common.graph.ConfigurableNetwork, com.google.common.graph.Network
    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors(obj);
    }
}
