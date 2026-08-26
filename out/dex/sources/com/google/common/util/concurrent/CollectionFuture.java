package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {

    abstract class CollectionFutureRunningState extends AggregateFuture<V, C>.RunningState {
        private List<Optional<V>> values;

        CollectionFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z) {
            super(immutableCollection, z, true);
            this.values = immutableCollection.isEmpty() ? ImmutableList.m491of() : Lists.newArrayListWithCapacity(immutableCollection.size());
            for (int i = 0; i < immutableCollection.size(); i++) {
                this.values.add(null);
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        final void collectOneValue(boolean z, int i, @Nullable V v) {
            List<Optional<V>> list = this.values;
            if (list != null) {
                list.set(i, Optional.fromNullable(v));
            } else {
                Preconditions.checkState(z || CollectionFuture.this.isCancelled(), "Future was done before all dependencies completed");
            }
        }

        abstract C combine(List<Optional<V>> list);

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        final void handleAllCompleted() {
            List<Optional<V>> list = this.values;
            if (list != null) {
                CollectionFuture.this.set(combine(list));
            } else {
                Preconditions.checkState(CollectionFuture.this.isDone());
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.values = null;
        }
    }

    static final class ListFuture<V> extends CollectionFuture<V, List<V>> {

        private final class ListFutureRunningState extends CollectionFuture<V, List<V>>.CollectionFutureRunningState {
            ListFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z) {
                super(immutableCollection, z);
            }

            @Override // com.google.common.util.concurrent.CollectionFuture.CollectionFutureRunningState
            public List<V> combine(List<Optional<V>> list) {
                ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(list.size());
                Iterator<Optional<V>> it = list.iterator();
                while (it.hasNext()) {
                    Optional<V> next = it.next();
                    arrayListNewArrayListWithCapacity.add(next != null ? next.orNull() : null);
                }
                return Collections.unmodifiableList(arrayListNewArrayListWithCapacity);
            }
        }

        ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z) {
            init(new ListFutureRunningState(immutableCollection, z));
        }
    }

    CollectionFuture() {
    }
}
