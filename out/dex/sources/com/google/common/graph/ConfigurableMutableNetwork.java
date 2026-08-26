package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class ConfigurableMutableNetwork<N, E> extends ConfigurableNetwork<N, E> implements MutableNetwork<N, E> {
    ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        super(networkBuilder);
    }

    @CanIgnoreReturnValue
    private NetworkConnections<N, E> addNodeInternal(N n) {
        NetworkConnections<N, E> networkConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.nodeConnections.put(n, networkConnectionsNewConnections) == null);
        return networkConnectionsNewConnections;
    }

    private NetworkConnections<N, E> newConnections() {
        if (isDirected()) {
            return allowsParallelEdges() ? DirectedMultiNetworkConnections.m587of() : DirectedNetworkConnections.m588of();
        }
        return allowsParallelEdges() ? UndirectedMultiNetworkConnections.m593of() : UndirectedNetworkConnections.m594of();
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addEdge(N n, N n2, E e) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(e, "edge");
        if (containsEdge(e)) {
            EndpointPair<N> endpointPairIncidentNodes = incidentNodes(e);
            EndpointPair endpointPairM590of = EndpointPair.m590of(this, n, n2);
            Preconditions.checkArgument(endpointPairIncidentNodes.equals(endpointPairM590of), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e, endpointPairIncidentNodes, endpointPairM590of);
            return false;
        }
        NetworkConnections<N, E> networkConnectionsAddNodeInternal = this.nodeConnections.get(n);
        if (!allowsParallelEdges()) {
            Preconditions.checkArgument(networkConnectionsAddNodeInternal == null || !networkConnectionsAddNodeInternal.successors().contains(n2), "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", n, n2);
        }
        boolean zEquals = n.equals(n2);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!zEquals, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        if (networkConnectionsAddNodeInternal == null) {
            networkConnectionsAddNodeInternal = addNodeInternal(n);
        }
        networkConnectionsAddNodeInternal.addOutEdge(e, n2);
        NetworkConnections<N, E> networkConnectionsAddNodeInternal2 = this.nodeConnections.get(n2);
        if (networkConnectionsAddNodeInternal2 == null) {
            networkConnectionsAddNodeInternal2 = addNodeInternal(n2);
        }
        networkConnectionsAddNodeInternal2.addInEdge(e, n, zEquals);
        this.edgeToReferenceNode.put(e, n);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (containsNode(n)) {
            return false;
        }
        addNodeInternal(n);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeEdge(Object obj) {
        Preconditions.checkNotNull(obj, "edge");
        N n = this.edgeToReferenceNode.get(obj);
        boolean z = false;
        if (n == null) {
            return false;
        }
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n);
        N nOppositeNode = networkConnections.oppositeNode(obj);
        NetworkConnections<N, E> networkConnections2 = this.nodeConnections.get(nOppositeNode);
        networkConnections.removeOutEdge(obj);
        if (allowsSelfLoops() && n.equals(nOppositeNode)) {
            z = true;
        }
        networkConnections2.removeInEdge(obj, z);
        this.edgeToReferenceNode.remove(obj);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeNode(Object obj) {
        Preconditions.checkNotNull(obj, "node");
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(obj);
        if (networkConnections == null) {
            return false;
        }
        Iterator it = ImmutableList.copyOf((Collection) networkConnections.incidentEdges()).iterator();
        while (it.hasNext()) {
            removeEdge(it.next());
        }
        this.nodeConnections.remove(obj);
        return true;
    }
}
