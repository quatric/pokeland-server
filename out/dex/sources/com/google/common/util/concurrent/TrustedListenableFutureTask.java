package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
class TrustedListenableFutureTask<V> extends AbstractFuture.TrustedFuture<V> implements RunnableFuture<V> {
    private TrustedListenableFutureTask<V>.TrustedFutureInterruptibleTask task;

    private final class TrustedFutureInterruptibleTask extends InterruptibleTask {
        private final Callable<V> callable;

        TrustedFutureInterruptibleTask(Callable<V> callable) {
            this.callable = (Callable) Preconditions.checkNotNull(callable);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        void runInterruptibly() {
            if (TrustedListenableFutureTask.this.isDone()) {
                return;
            }
            try {
                TrustedListenableFutureTask.this.set(this.callable.call());
            } catch (Throwable th) {
                TrustedListenableFutureTask.this.setException(th);
            }
        }

        public String toString() {
            return this.callable.toString();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        boolean wasInterrupted() {
            return TrustedListenableFutureTask.this.wasInterrupted();
        }
    }

    TrustedListenableFutureTask(Callable<V> callable) {
        this.task = new TrustedFutureInterruptibleTask(callable);
    }

    static <V> TrustedListenableFutureTask<V> create(Runnable runnable, @Nullable V v) {
        return new TrustedListenableFutureTask<>(Executors.callable(runnable, v));
    }

    static <V> TrustedListenableFutureTask<V> create(Callable<V> callable) {
        return new TrustedListenableFutureTask<>(callable);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected void afterDone() {
        TrustedListenableFutureTask<V>.TrustedFutureInterruptibleTask trustedFutureInterruptibleTask;
        super.afterDone();
        if (wasInterrupted() && (trustedFutureInterruptibleTask = this.task) != null) {
            trustedFutureInterruptibleTask.interruptTask();
        }
        this.task = null;
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        TrustedListenableFutureTask<V>.TrustedFutureInterruptibleTask trustedFutureInterruptibleTask = this.task;
        if (trustedFutureInterruptibleTask != null) {
            trustedFutureInterruptibleTask.run();
        }
    }

    public String toString() {
        return super.toString() + " (delegate = " + this.task + ")";
    }
}
