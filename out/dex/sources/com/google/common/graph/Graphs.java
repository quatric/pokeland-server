package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class Graphs {

    private enum NodeVisitState {
        PENDING,
        COMPLETE
    }

    private static class TransposedGraph<N> extends AbstractGraph<N> {
        private final Graph<N> graph;

        TransposedGraph(Graph<N> graph) {
            this.graph = graph;
        }

        @Override // com.google.common.graph.Graph
        public Set<N> adjacentNodes(Object obj) {
            return this.graph.adjacentNodes(obj);
        }

        @Override // com.google.common.graph.Graph
        public boolean allowsSelfLoops() {
            return this.graph.allowsSelfLoops();
        }

        @Override // com.google.common.graph.AbstractGraph
        protected long edgeCount() {
            return this.graph.edges().size();
        }

        @Override // com.google.common.graph.Graph
        public boolean isDirected() {
            return this.graph.isDirected();
        }

        @Override // com.google.common.graph.Graph
        public ElementOrder<N> nodeOrder() {
            return this.graph.nodeOrder();
        }

        @Override // com.google.common.graph.Graph
        public Set<N> nodes() {
            return this.graph.nodes();
        }

        @Override // com.google.common.graph.Graph
        public Set<N> predecessors(Object obj) {
            return this.graph.successors(obj);
        }

        @Override // com.google.common.graph.Graph
        public Set<N> successors(Object obj) {
            return this.graph.predecessors(obj);
        }
    }

    private static class TransposedNetwork<N, E> extends AbstractNetwork<N, E> {
        private final Network<N, E> network;

        TransposedNetwork(Network<N, E> network) {
            this.network = network;
        }

        @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public Set<E> adjacentEdges(Object obj) {
            return this.network.adjacentEdges(obj);
        }

        @Override // com.google.common.graph.Network
        public Set<N> adjacentNodes(Object obj) {
            return this.network.adjacentNodes(obj);
        }

        @Override // com.google.common.graph.Network
        public boolean allowsParallelEdges() {
            return this.network.allowsParallelEdges();
        }

        @Override // com.google.common.graph.Network
        public boolean allowsSelfLoops() {
            return this.network.allowsSelfLoops();
        }

        @Override // com.google.common.graph.Network
        public ElementOrder<E> edgeOrder() {
            return this.network.edgeOrder();
        }

        @Override // com.google.common.graph.Network
        public Set<E> edges() {
            return this.network.edges();
        }

        @Override // com.google.common.graph.Network
        public Set<E> edgesConnecting(Object obj, Object obj2) {
            return this.network.edgesConnecting(obj2, obj);
        }

        @Override // com.google.common.graph.Network
        public Set<E> inEdges(Object obj) {
            return this.network.outEdges(obj);
        }

        @Override // com.google.common.graph.Network
        public Set<E> incidentEdges(Object obj) {
            return this.network.incidentEdges(obj);
        }

        @Override // com.google.common.graph.Network
        public EndpointPair<N> incidentNodes(Object obj) {
            EndpointPair<N> endpointPairIncidentNodes = this.network.incidentNodes(obj);
            return EndpointPair.m590of((Network<?, ?>) this.network, (Object) endpointPairIncidentNodes.nodeV(), (Object) endpointPairIncidentNodes.nodeU());
        }

        @Override // com.google.common.graph.Network
        public boolean isDirected() {
            return this.network.isDirected();
        }

        @Override // com.google.common.graph.Network
        public ElementOrder<N> nodeOrder() {
            return this.network.nodeOrder();
        }

        @Override // com.google.common.graph.Network
        public Set<N> nodes() {
            return this.network.nodes();
        }

        @Override // com.google.common.graph.Network
        public Set<E> outEdges(Object obj) {
            return this.network.inEdges(obj);
        }

        @Override // com.google.common.graph.Network
        public Set<N> predecessors(Object obj) {
            return this.network.successors(obj);
        }

        @Override // com.google.common.graph.Network
        public Set<N> successors(Object obj) {
            return this.network.predecessors(obj);
        }
    }

    private static class TransposedValueGraph<N, V> extends AbstractValueGraph<N, V> {
        private final ValueGraph<N, V> graph;

        TransposedValueGraph(ValueGraph<N, V> valueGraph) {
            this.graph = valueGraph;
        }

        @Override // com.google.common.graph.Graph
        public Set<N> adjacentNodes(Object obj) {
            return this.graph.adjacentNodes(obj);
        }

        @Override // com.google.common.graph.Graph
        public boolean allowsSelfLoops() {
            return this.graph.allowsSelfLoops();
        }

        @Override // com.google.common.graph.AbstractGraph
        protected long edgeCount() {
            return this.graph.edges().size();
        }

        @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.ValueGraph
        public V edgeValue(Object obj, Object obj2) {
            return this.graph.edgeValue(obj2, obj);
        }

        @Override // com.google.common.graph.ValueGraph
        public V edgeValueOrDefault(Object obj, Object obj2, @Nullable V v) {
            return this.graph.edgeValueOrDefault(obj2, obj, v);
        }

        @Override // com.google.common.graph.Graph
        public boolean isDirected() {
            return this.graph.isDirected();
        }

        @Override // com.google.common.graph.Graph
        public ElementOrder<N> nodeOrder() {
            return this.graph.nodeOrder();
        }

        @Override // com.google.common.graph.Graph
        public Set<N> nodes() {
            return this.graph.nodes();
        }

        @Override // com.google.common.graph.Graph
        public Set<N> predecessors(Object obj) {
            return this.graph.successors(obj);
        }

        @Override // com.google.common.graph.Graph
        public Set<N> successors(Object obj) {
            return this.graph.predecessors(obj);
        }
    }

    private Graphs() {
    }

    private static boolean canTraverseWithoutReusingEdge(Graph<?> graph, Object obj, @Nullable Object obj2) {
        return graph.isDirected() || !Objects.equal(obj2, obj);
    }

    @CanIgnoreReturnValue
    static int checkNonNegative(int i) {
        Preconditions.checkArgument(i >= 0, "Not true that %s is non-negative.", i);
        return i;
    }

    @CanIgnoreReturnValue
    static long checkNonNegative(long j) {
        Preconditions.checkArgument(j >= 0, "Not true that %s is non-negative.", j);
        return j;
    }

    @CanIgnoreReturnValue
    static int checkPositive(int i) {
        Preconditions.checkArgument(i > 0, "Not true that %s is positive.", i);
        return i;
    }

    @CanIgnoreReturnValue
    static long checkPositive(long j) {
        Preconditions.checkArgument(j > 0, "Not true that %s is positive.", j);
        return j;
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> graph) {
        MutableGraph<N> mutableGraph = (MutableGraph<N>) GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        Iterator<N> it = graph.nodes().iterator();
        while (it.hasNext()) {
            mutableGraph.addNode(it.next());
        }
        for (EndpointPair<N> endpointPair : graph.edges()) {
            mutableGraph.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return mutableGraph;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork<N, E> mutableNetwork = (MutableNetwork<N, E>) NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        Iterator<N> it = network.nodes().iterator();
        while (it.hasNext()) {
            mutableNetwork.addNode(it.next());
        }
        for (E e : network.edges()) {
            EndpointPair<N> endpointPairIncidentNodes = network.incidentNodes(e);
            mutableNetwork.addEdge(endpointPairIncidentNodes.nodeU(), endpointPairIncidentNodes.nodeV(), e);
        }
        return mutableNetwork;
    }

    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        MutableValueGraph<N, V> mutableValueGraph = (MutableValueGraph<N, V>) ValueGraphBuilder.from(valueGraph).expectedNodeCount(valueGraph.nodes().size()).build();
        Iterator<N> it = valueGraph.nodes().iterator();
        while (it.hasNext()) {
            mutableValueGraph.addNode(it.next());
        }
        for (EndpointPair<N> endpointPair : valueGraph.edges()) {
            mutableValueGraph.putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), valueGraph.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()));
        }
        return mutableValueGraph;
    }

    public static boolean equivalent(@Nullable Graph<?> graph, @Nullable Graph<?> graph2) {
        if (graph == graph2) {
            return true;
        }
        return graph != null && graph2 != null && graph.isDirected() == graph2.isDirected() && graph.nodes().equals(graph2.nodes()) && graph.edges().equals(graph2.edges());
    }

    public static boolean equivalent(@Nullable Network<?, ?> network, @Nullable Network<?, ?> network2) {
        if (network == network2) {
            return true;
        }
        if (network == null || network2 == null || network.isDirected() != network2.isDirected() || !network.nodes().equals(network2.nodes()) || !network.edges().equals(network2.edges())) {
            return false;
        }
        for (Object obj : network.edges()) {
            if (!network.incidentNodes(obj).equals(network2.incidentNodes(obj))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equivalent(@Nullable ValueGraph<?, ?> valueGraph, @Nullable ValueGraph<?, ?> valueGraph2) {
        if (valueGraph == valueGraph2) {
            return true;
        }
        if (valueGraph == null || valueGraph2 == null || valueGraph.isDirected() != valueGraph2.isDirected() || !valueGraph.nodes().equals(valueGraph2.nodes()) || !valueGraph.edges().equals(valueGraph2.edges())) {
            return false;
        }
        for (EndpointPair<?> endpointPair : valueGraph.edges()) {
            if (!valueGraph.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()).equals(valueGraph2.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasCycle(Graph<?> graph) {
        int size = graph.edges().size();
        if (size == 0) {
            return false;
        }
        if (!graph.isDirected() && size >= graph.nodes().size()) {
            return true;
        }
        HashMap mapNewHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(graph.nodes().size());
        Iterator<?> it = graph.nodes().iterator();
        while (it.hasNext()) {
            if (subgraphHasCycle(graph, mapNewHashMapWithExpectedSize, it.next(), null)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (network.isDirected() || !network.allowsParallelEdges() || network.edges().size() <= network.asGraph().edges().size()) {
            return hasCycle(network.asGraph());
        }
        return true;
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> iterable) {
        MutableGraph<N> mutableGraphBuild = GraphBuilder.from(graph).build();
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            mutableGraphBuild.addNode(it.next());
        }
        for (N n : mutableGraphBuild.nodes()) {
            for (N n2 : graph.successors(n)) {
                if (mutableGraphBuild.nodes().contains(n2)) {
                    mutableGraphBuild.putEdge(n, n2);
                }
            }
        }
        return mutableGraphBuild;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> iterable) {
        MutableNetwork<N, E> mutableNetworkBuild = NetworkBuilder.from(network).build();
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            mutableNetworkBuild.addNode(it.next());
        }
        for (N n : mutableNetworkBuild.nodes()) {
            for (E e : network.outEdges(n)) {
                N nAdjacentNode = network.incidentNodes(e).adjacentNode(n);
                if (mutableNetworkBuild.nodes().contains(nAdjacentNode)) {
                    mutableNetworkBuild.addEdge(n, nAdjacentNode, e);
                }
            }
        }
        return mutableNetworkBuild;
    }

    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> valueGraph, Iterable<? extends N> iterable) {
        MutableValueGraph<N, V> mutableValueGraphBuild = ValueGraphBuilder.from(valueGraph).build();
        Iterator<? extends N> it = iterable.iterator();
        while (it.hasNext()) {
            mutableValueGraphBuild.addNode(it.next());
        }
        for (N n : mutableValueGraphBuild.nodes()) {
            for (N n2 : valueGraph.successors(n)) {
                if (mutableValueGraphBuild.nodes().contains(n2)) {
                    mutableValueGraphBuild.putEdgeValue(n, n2, valueGraph.edgeValue(n, n2));
                }
            }
        }
        return mutableValueGraphBuild;
    }

    public static <N> Set<N> reachableNodes(Graph<N> graph, Object obj) {
        Preconditions.checkArgument(graph.nodes().contains(obj), "Node %s is not an element of this graph.", obj);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        linkedHashSet.add(obj);
        arrayDeque.add(obj);
        while (!arrayDeque.isEmpty()) {
            for (N n : graph.successors(arrayDeque.remove())) {
                if (linkedHashSet.add(n)) {
                    arrayDeque.add(n);
                }
            }
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    private static boolean subgraphHasCycle(Graph<?> graph, Map<Object, NodeVisitState> map, Object obj, @Nullable Object obj2) {
        NodeVisitState nodeVisitState = map.get(obj);
        if (nodeVisitState == NodeVisitState.COMPLETE) {
            return false;
        }
        if (nodeVisitState == NodeVisitState.PENDING) {
            return true;
        }
        map.put(obj, NodeVisitState.PENDING);
        for (Object obj3 : graph.successors(obj)) {
            if (canTraverseWithoutReusingEdge(graph, obj3, obj2) && subgraphHasCycle(graph, map, obj3, obj)) {
                return true;
            }
        }
        map.put(obj, NodeVisitState.COMPLETE);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <N> Graph<N> transitiveClosure(Graph<N> graph) {
        ConfigurableMutableGraph configurableMutableGraphBuild = GraphBuilder.from(graph).allowsSelfLoops(true).build();
        if (graph.isDirected()) {
            for (N n : graph.nodes()) {
                Iterator it = reachableNodes(graph, n).iterator();
                while (it.hasNext()) {
                    configurableMutableGraphBuild.putEdge(n, it.next());
                }
            }
        } else {
            HashSet hashSet = new HashSet();
            for (N n2 : graph.nodes()) {
                if (!hashSet.contains(n2)) {
                    Set setReachableNodes = reachableNodes(graph, n2);
                    hashSet.addAll(setReachableNodes);
                    int i = 1;
                    for (Object obj : setReachableNodes) {
                        int i2 = i + 1;
                        Iterator it2 = Iterables.limit(setReachableNodes, i).iterator();
                        while (it2.hasNext()) {
                            configurableMutableGraphBuild.putEdge(obj, it2.next());
                        }
                        i = i2;
                    }
                }
            }
        }
        return configurableMutableGraphBuild;
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        if (graph.isDirected()) {
            return graph instanceof TransposedGraph ? ((TransposedGraph) graph).graph : new TransposedGraph(graph);
        }
        return graph;
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (network.isDirected()) {
            return network instanceof TransposedNetwork ? ((TransposedNetwork) network).network : new TransposedNetwork(network);
        }
        return network;
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> valueGraph) {
        if (valueGraph.isDirected()) {
            return valueGraph instanceof TransposedValueGraph ? ((TransposedValueGraph) valueGraph).graph : new TransposedValueGraph(valueGraph);
        }
        return valueGraph;
    }
}
