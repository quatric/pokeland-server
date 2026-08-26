package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public abstract class AbstractNetwork<N, E> implements Network<N, E> {

    /* JADX INFO: renamed from: com.google.common.graph.AbstractNetwork$1 */
    class C06081 extends AbstractGraph<N> {
        C06081() {
        }

        @Override // com.google.common.graph.Graph
        public Set<N> adjacentNodes(Object obj) {
            return AbstractNetwork.this.adjacentNodes(obj);
        }

        @Override // com.google.common.graph.Graph
        public boolean allowsSelfLoops() {
            return AbstractNetwork.this.allowsSelfLoops();
        }

        @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.Graph
        public Set<EndpointPair<N>> edges() {
            return AbstractNetwork.this.allowsParallelEdges() ? super.edges() : new AbstractSet<EndpointPair<N>>() { // from class: com.google.common.graph.AbstractNetwork.1.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean contains(@Nullable Object obj) {
                    if (!(obj instanceof EndpointPair)) {
                        return false;
                    }
                    EndpointPair endpointPair = (EndpointPair) obj;
                    return C06081.this.isDirected() == endpointPair.isOrdered() && C06081.this.nodes().contains(endpointPair.nodeU()) && C06081.this.successors(endpointPair.nodeU()).contains(endpointPair.nodeV());
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<EndpointPair<N>> iterator() {
                    return Iterators.transform(AbstractNetwork.this.edges().iterator(), new Function<E, EndpointPair<N>>() { // from class: com.google.common.graph.AbstractNetwork.1.1.1
                        @Override // com.google.common.base.Function
                        public EndpointPair<N> apply(E e) {
                            return AbstractNetwork.this.incidentNodes(e);
                        }
                    });
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return AbstractNetwork.this.edges().size();
                }
            };
        }

        @Override // com.google.common.graph.Graph
        public boolean isDirected() {
            return AbstractNetwork.this.isDirected();
        }

        @Override // com.google.common.graph.Graph
        public ElementOrder<N> nodeOrder() {
            return AbstractNetwork.this.nodeOrder();
        }

        @Override // com.google.common.graph.Graph
        public Set<N> nodes() {
            return AbstractNetwork.this.nodes();
        }

        @Override // com.google.common.graph.Graph
        public Set<N> predecessors(Object obj) {
            return AbstractNetwork.this.predecessors(obj);
        }

        @Override // com.google.common.graph.Graph
        public Set<N> successors(Object obj) {
            return AbstractNetwork.this.successors(obj);
        }
    }

    private Map<E, EndpointPair<N>> edgeIncidentNodesMap() {
        return Maps.asMap(edges(), new Function<E, EndpointPair<N>>() { // from class: com.google.common.graph.AbstractNetwork.2
            @Override // com.google.common.base.Function
            public EndpointPair<N> apply(E e) {
                return AbstractNetwork.this.incidentNodes(e);
            }
        });
    }

    @Override // com.google.common.graph.Network
    public Set<E> adjacentEdges(Object obj) {
        EndpointPair<N> endpointPairIncidentNodes = incidentNodes(obj);
        return Sets.difference(Sets.union(incidentEdges(endpointPairIncidentNodes.nodeU()), incidentEdges(endpointPairIncidentNodes.nodeV())), ImmutableSet.m534of(obj));
    }

    @Override // com.google.common.graph.Network
    public Graph<N> asGraph() {
        return new C06081();
    }

    @Override // com.google.common.graph.Network
    public int degree(Object obj) {
        return isDirected() ? IntMath.saturatedAdd(inEdges(obj).size(), outEdges(obj).size()) : IntMath.saturatedAdd(incidentEdges(obj).size(), edgesConnecting(obj, obj).size());
    }

    @Override // com.google.common.graph.Network
    public int inDegree(Object obj) {
        return isDirected() ? inEdges(obj).size() : degree(obj);
    }

    @Override // com.google.common.graph.Network
    public int outDegree(Object obj) {
        return isDirected() ? outEdges(obj).size() : degree(obj);
    }

    public String toString() {
        return String.format("%s, nodes: %s, edges: %s", String.format("isDirected: %s, allowsParallelEdges: %s, allowsSelfLoops: %s", Boolean.valueOf(isDirected()), Boolean.valueOf(allowsParallelEdges()), Boolean.valueOf(allowsSelfLoops())), nodes(), edgeIncidentNodesMap());
    }
}
