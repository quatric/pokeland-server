package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class UndirectedMultiNetworkConnections<N, E> extends AbstractUndirectedNetworkConnections<N, E> {

    @LazyInit
    private transient Reference<Multiset<N>> adjacentNodesReference;

    private UndirectedMultiNetworkConnections(Map<E, N> map) {
        super(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Multiset<N> adjacentNodesMultiset() {
        Multiset<N> multiset = (Multiset) getReference(this.adjacentNodesReference);
        if (multiset != null) {
            return multiset;
        }
        HashMultiset hashMultisetCreate = HashMultiset.create(this.incidentEdgeMap.values());
        this.adjacentNodesReference = new SoftReference(hashMultisetCreate);
        return hashMultisetCreate;
    }

    @Nullable
    private static <T> T getReference(@Nullable Reference<T> reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    /* JADX INFO: renamed from: of */
    static <N, E> UndirectedMultiNetworkConnections<N, E> m593of() {
        return new UndirectedMultiNetworkConnections<>(new HashMap(2, 1.0f));
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> map) {
        return new UndirectedMultiNetworkConnections<>(ImmutableMap.copyOf((Map) map));
    }

    @Override // com.google.common.graph.AbstractUndirectedNetworkConnections, com.google.common.graph.NetworkConnections
    public void addInEdge(E e, N n, boolean z) {
        if (z) {
            return;
        }
        addOutEdge(e, n);
    }

    @Override // com.google.common.graph.AbstractUndirectedNetworkConnections, com.google.common.graph.NetworkConnections
    public void addOutEdge(E e, N n) {
        super.addOutEdge(e, n);
        Multiset multiset = (Multiset) getReference(this.adjacentNodesReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(adjacentNodesMultiset().elementSet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> edgesConnecting(final Object obj) {
        return new MultiEdgesConnecting<E>(this.incidentEdgeMap, obj) { // from class: com.google.common.graph.UndirectedMultiNetworkConnections.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return UndirectedMultiNetworkConnections.this.adjacentNodesMultiset().count(obj);
            }
        };
    }

    @Override // com.google.common.graph.AbstractUndirectedNetworkConnections, com.google.common.graph.NetworkConnections
    public N removeInEdge(Object obj, boolean z) {
        if (z) {
            return null;
        }
        return removeOutEdge(obj);
    }

    @Override // com.google.common.graph.AbstractUndirectedNetworkConnections, com.google.common.graph.NetworkConnections
    public N removeOutEdge(Object obj) {
        N n = (N) super.removeOutEdge(obj);
        Multiset multiset = (Multiset) getReference(this.adjacentNodesReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }
}
