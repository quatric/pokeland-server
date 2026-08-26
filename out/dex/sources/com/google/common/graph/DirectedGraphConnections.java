package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    private final Map<N, Object> adjacentNodeValues;
    private int predecessorCount;
    private int successorCount;

    private static final class PredAndSucc {
        private final Object successorValue;

        PredAndSucc(Object obj) {
            this.successorValue = obj;
        }
    }

    private DirectedGraphConnections(Map<N, Object> map, int i, int i2) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(map);
        this.predecessorCount = Graphs.checkNonNegative(i);
        this.successorCount = Graphs.checkNonNegative(i2);
        Preconditions.checkState(i <= map.size() && i2 <= map.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPredecessor(@Nullable Object obj) {
        return obj == PRED || (obj instanceof PredAndSucc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSuccessor(@Nullable Object obj) {
        return (obj == PRED || obj == null) ? false : true;
    }

    /* JADX INFO: renamed from: of */
    static <N, V> DirectedGraphConnections<N, V> m586of() {
        return new DirectedGraphConnections<>(new HashMap(4, 1.0f), 0, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <N, V> DirectedGraphConnections<N, V> ofImmutable(Set<N> set, Map<N, V> map) {
        HashMap map2 = new HashMap();
        map2.putAll(map);
        for (N n : set) {
            Object objPut = map2.put(n, PRED);
            if (objPut != null) {
                map2.put(n, new PredAndSucc(objPut));
            }
        }
        return new DirectedGraphConnections<>(ImmutableMap.copyOf((Map) map2), set.size(), map.size());
    }

    @Override // com.google.common.graph.GraphConnections
    public void addPredecessor(N n, V v) {
        Object objPut = this.adjacentNodeValues.put(n, PRED);
        if (objPut == null) {
            int i = this.predecessorCount + 1;
            this.predecessorCount = i;
            Graphs.checkPositive(i);
        } else if (objPut instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, objPut);
        } else if (objPut != PRED) {
            this.adjacentNodeValues.put(n, new PredAndSucc(objPut));
            int i2 = this.predecessorCount + 1;
            this.predecessorCount = i2;
            Graphs.checkPositive(i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    public V addSuccessor(N n, V v) {
        V v2 = (V) this.adjacentNodeValues.put(n, v);
        if (v2 == 0) {
            int i = this.successorCount + 1;
            this.successorCount = i;
            Graphs.checkPositive(i);
            return null;
        }
        if (v2 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, new PredAndSucc(v));
            return (V) ((PredAndSucc) v2).successorValue;
        }
        if (v2 != PRED) {
            return v2;
        }
        this.adjacentNodeValues.put(n, new PredAndSucc(v));
        int i2 = this.successorCount + 1;
        this.successorCount = i2;
        Graphs.checkPositive(i2);
        return null;
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> predecessors() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@Nullable Object obj) {
                return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() { // from class: com.google.common.graph.DirectedGraphConnections.1.1
                    @Override // com.google.common.collect.AbstractIterator
                    protected N computeNext() {
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (DirectedGraphConnections.isPredecessor(entry.getValue())) {
                                return (N) entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.predecessorCount;
            }
        };
    }

    @Override // com.google.common.graph.GraphConnections
    public void removePredecessor(Object obj) {
        Object obj2 = this.adjacentNodeValues.get(obj);
        if (obj2 == PRED) {
            this.adjacentNodeValues.remove(obj);
            int i = this.predecessorCount - 1;
            this.predecessorCount = i;
            Graphs.checkNonNegative(i);
            return;
        }
        if (obj2 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(obj, ((PredAndSucc) obj2).successorValue);
            int i2 = this.predecessorCount - 1;
            this.predecessorCount = i2;
            Graphs.checkNonNegative(i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    public V removeSuccessor(Object obj) {
        Object obj2;
        V v = (V) this.adjacentNodeValues.get(obj);
        if (v == 0 || v == (obj2 = PRED)) {
            return null;
        }
        if (v instanceof PredAndSucc) {
            this.adjacentNodeValues.put(obj, obj2);
            int i = this.successorCount - 1;
            this.successorCount = i;
            Graphs.checkNonNegative(i);
            return (V) ((PredAndSucc) v).successorValue;
        }
        this.adjacentNodeValues.remove(obj);
        int i2 = this.successorCount - 1;
        this.successorCount = i2;
        Graphs.checkNonNegative(i2);
        return v;
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> successors() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@Nullable Object obj) {
                return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() { // from class: com.google.common.graph.DirectedGraphConnections.2.1
                    @Override // com.google.common.collect.AbstractIterator
                    protected N computeNext() {
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (DirectedGraphConnections.isSuccessor(entry.getValue())) {
                                return (N) entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.successorCount;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    public V value(Object obj) {
        V v = (V) this.adjacentNodeValues.get(obj);
        if (v == PRED) {
            return null;
        }
        return v instanceof PredAndSucc ? (V) ((PredAndSucc) v).successorValue : v;
    }
}
