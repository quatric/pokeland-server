package com.google.common.graph;

import com.google.common.base.Optional;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractGraphBuilder<N> {
    final boolean directed;
    boolean allowsSelfLoops = false;
    ElementOrder<N> nodeOrder = ElementOrder.insertion();
    Optional<Integer> expectedNodeCount = Optional.absent();

    AbstractGraphBuilder(boolean z) {
        this.directed = z;
    }
}
