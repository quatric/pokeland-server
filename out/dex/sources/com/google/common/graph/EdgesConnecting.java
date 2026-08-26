package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class EdgesConnecting<E> extends AbstractSet<E> {
    private final Map<?, E> nodeToOutEdge;
    private final Object targetNode;

    EdgesConnecting(Map<?, E> map, Object obj) {
        this.nodeToOutEdge = (Map) Preconditions.checkNotNull(map);
        this.targetNode = Preconditions.checkNotNull(obj);
    }

    @Nullable
    private E getConnectingEdge() {
        return this.nodeToOutEdge.get(this.targetNode);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@Nullable Object obj) {
        E connectingEdge = getConnectingEdge();
        return connectingEdge != null && connectingEdge.equals(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public UnmodifiableIterator<E> iterator() {
        E connectingEdge = getConnectingEdge();
        return connectingEdge == null ? ImmutableSet.m533of().iterator() : Iterators.singletonIterator(connectingEdge);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return getConnectingEdge() == null ? 0 : 1;
    }
}
