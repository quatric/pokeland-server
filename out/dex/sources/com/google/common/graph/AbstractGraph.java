package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public abstract class AbstractGraph<N> implements Graph<N> {
    @Override // com.google.common.graph.Graph
    public int degree(Object obj) {
        if (isDirected()) {
            return IntMath.saturatedAdd(predecessors(obj).size(), successors(obj).size());
        }
        Set<N> setAdjacentNodes = adjacentNodes(obj);
        return IntMath.saturatedAdd(setAdjacentNodes.size(), (allowsSelfLoops() && setAdjacentNodes.contains(obj)) ? 1 : 0);
    }

    protected long edgeCount() {
        Iterator<N> it = nodes().iterator();
        long jDegree = 0;
        while (it.hasNext()) {
            jDegree += (long) degree(it.next());
        }
        Preconditions.checkState((1 & jDegree) == 0);
        return jDegree >>> 1;
    }

    @Override // com.google.common.graph.Graph
    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>() { // from class: com.google.common.graph.AbstractGraph.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@Nullable Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                return AbstractGraph.this.isDirected() == endpointPair.isOrdered() && AbstractGraph.this.nodes().contains(endpointPair.nodeU()) && AbstractGraph.this.successors(endpointPair.nodeU()).contains(endpointPair.nodeV());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return EndpointPairIterator.m591of(AbstractGraph.this);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return Ints.saturatedCast(AbstractGraph.this.edgeCount());
            }
        };
    }

    @Override // com.google.common.graph.Graph
    public int inDegree(Object obj) {
        return isDirected() ? predecessors(obj).size() : degree(obj);
    }

    @Override // com.google.common.graph.Graph
    public int outDegree(Object obj) {
        return isDirected() ? successors(obj).size() : degree(obj);
    }

    public String toString() {
        return String.format("%s, nodes: %s, edges: %s", String.format("isDirected: %s, allowsSelfLoops: %s", Boolean.valueOf(isDirected()), Boolean.valueOf(allowsSelfLoops())), nodes(), edges());
    }
}
