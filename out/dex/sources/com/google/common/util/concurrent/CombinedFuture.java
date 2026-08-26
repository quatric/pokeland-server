package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
final class CombinedFuture<V> extends AggregateFuture<Object, V> {

    private final class AsyncCallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask {
        private final AsyncCallable<V> callable;

        public AsyncCallableInterruptibleTask(AsyncCallable<V> asyncCallable, Executor executor) {
            super(executor);
            this.callable = (AsyncCallable) Preconditions.checkNotNull(asyncCallable);
        }

        @Override // com.google.common.util.concurrent.CombinedFuture.CombinedFutureInterruptibleTask
        void setValue() throws Exception {
            CombinedFuture.this.setFuture(this.callable.call());
        }
    }

    private final class CallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask {
        private final Callable<V> callable;

        public CallableInterruptibleTask(Callable<V> callable, Executor executor) {
            super(executor);
            this.callable = (Callable) Preconditions.checkNotNull(callable);
        }

        @Override // com.google.common.util.concurrent.CombinedFuture.CombinedFutureInterruptibleTask
        void setValue() throws Exception {
            CombinedFuture.this.set(this.callable.call());
        }
    }

    private abstract class CombinedFutureInterruptibleTask extends InterruptibleTask {
        private final Executor listenerExecutor;
        volatile boolean thrownByExecute = true;

        public CombinedFutureInterruptibleTask(Executor executor) {
            this.listenerExecutor = (Executor) Preconditions.checkNotNull(executor);
        }

        final void execute() {
            try {
                this.listenerExecutor.execute(this);
            } catch (RejectedExecutionException e) {
                if (this.thrownByExecute) {
                    CombinedFuture.this.setException(e);
                }
            }
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        final void runInterruptibly() {
            this.thrownByExecute = false;
            if (CombinedFuture.this.isDone()) {
                return;
            }
            try {
                setValue();
            } catch (CancellationException unused) {
                CombinedFuture.this.cancel(false);
            } catch (ExecutionException e) {
                CombinedFuture.this.setException(e.getCause());
            } catch (Throwable th) {
                CombinedFuture.this.setException(th);
            }
        }

        abstract void setValue() throws Exception;

        @Override // com.google.common.util.concurrent.InterruptibleTask
        final boolean wasInterrupted() {
            return CombinedFuture.this.wasInterrupted();
        }
    }

    private final class CombinedFutureRunningState extends AggregateFuture<Object, V>.RunningState {
        private CombinedFuture<V>.CombinedFutureInterruptibleTask task;

        CombinedFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends Object>> immutableCollection, boolean z, CombinedFuture<V>.CombinedFutureInterruptibleTask combinedFutureInterruptibleTask) {
            super(immutableCollection, z, false);
            this.task = combinedFutureInterruptibleTask;
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        void collectOneValue(boolean z, int i, @Nullable Object obj) {
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        void handleAllCompleted() {
            CombinedFuture<V>.CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.task;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.execute();
            } else {
                Preconditions.checkState(CombinedFuture.this.isDone());
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        void interruptTask() {
            CombinedFuture<V>.CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.task;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.interruptTask();
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.task = null;
        }
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, Executor executor, AsyncCallable<V> asyncCallable) {
        init(new CombinedFutureRunningState(immutableCollection, z, new AsyncCallableInterruptibleTask(asyncCallable, executor)));
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, Executor executor, Callable<V> callable) {
        init(new CombinedFutureRunningState(immutableCollection, z, new CallableInterruptibleTask(callable, executor)));
    }
}
