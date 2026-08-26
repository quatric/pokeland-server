package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class EndpointPairIterator<N> extends AbstractIterator<EndpointPair<N>> {
    private final Graph<N> graph;
    protected N node;
    private final Iterator<N> nodeIterator;
    protected Iterator<N> successorIterator;

    private static final class Directed<N> extends EndpointPairIterator<N> {
        private Directed(Graph<N> graph) {
            super(graph);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.AbstractIterator
        public EndpointPair<N> computeNext() {
            while (!this.successorIterator.hasNext()) {
                if (!advance()) {
                    return endOfData();
                }
            }
            return EndpointPair.ordered(this.node, this.successorIterator.next());
        }
    }

    private static final class Undirected<N> extends EndpointPairIterator<N> {
        private Set<N> visitedNodes;

        private Undirected(Graph<N> graph) {
            super(graph);
            this.visitedNodes = Sets.newHashSetWithExpectedSize(graph.nodes().size());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.AbstractIterator
        public EndpointPair<N> computeNext() {
            while (true) {
                if (this.successorIterator.hasNext()) {
                    N next = this.successorIterator.next();
                    if (!this.visitedNodes.contains(next)) {
                        return EndpointPair.unordered(this.node, next);
                    }
                } else {
                    this.visitedNodes.add(this.node);
                    if (!advance()) {
                        this.visitedNodes = null;
                        return endOfData();
                    }
                }
            }
        }
    }

    private EndpointPairIterator(Graph<N> graph) {
        this.node = null;
        this.successorIterator = ImmutableSet.m533of().iterator();
        this.graph = graph;
        this.nodeIterator = graph.nodes().iterator();
    }

    /* JADX INFO: renamed from: of */
    static <N> EndpointPairIterator<N> m591of(Graph<N> graph) {
        return graph.isDirected() ? new Directed(graph) : new Undirected(graph);
    }

    protected final boolean advance() {
        Preconditions.checkState(!this.successorIterator.hasNext());
        if (!this.nodeIterator.hasNext()) {
            return false;
        }
        this.node = this.nodeIterator.next();
        this.successorIterator = this.graph.successors(this.node).iterator();
        return true;
    }
}
