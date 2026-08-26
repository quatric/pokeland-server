package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
abstract class ImmediateFuture<V> implements ListenableFuture<V> {
    private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

    static final class ImmediateCancelledFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateCancelledFuture() {
            cancel(false);
        }
    }

    @GwtIncompatible
    static class ImmediateFailedCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        private final X thrown;

        ImmediateFailedCheckedFuture(X x) {
            this.thrown = x;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Exception */
        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet() throws Exception {
            throw this.thrown;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Exception */
        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet(long j, TimeUnit timeUnit) throws Exception {
            Preconditions.checkNotNull(timeUnit);
            throw this.thrown;
        }

        @Override // com.google.common.util.concurrent.ImmediateFuture, java.util.concurrent.Future
        public V get() throws ExecutionException {
            throw new ExecutionException(this.thrown);
        }
    }

    static final class ImmediateFailedFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateFailedFuture(Throwable th) {
            setException(th);
        }
    }

    @GwtIncompatible
    static class ImmediateSuccessfulCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {

        @Nullable
        private final V value;

        ImmediateSuccessfulCheckedFuture(@Nullable V v) {
            this.value = v;
        }

        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet() {
            return this.value;
        }

        @Override // com.google.common.util.concurrent.CheckedFuture
        public V checkedGet(long j, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            return this.value;
        }

        @Override // com.google.common.util.concurrent.ImmediateFuture, java.util.concurrent.Future
        public V get() {
            return this.value;
        }
    }

    static class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {
        static final ImmediateSuccessfulFuture<Object> NULL = new ImmediateSuccessfulFuture<>(null);

        @Nullable
        private final V value;

        ImmediateSuccessfulFuture(@Nullable V v) {
            this.value = v;
        }

        @Override // com.google.common.util.concurrent.ImmediateFuture, java.util.concurrent.Future
        public V get() {
            return this.value;
        }
    }

    ImmediateFuture() {
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // java.util.concurrent.Future
    public abstract V get() throws ExecutionException;

    @Override // java.util.concurrent.Future
    public V get(long j, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(timeUnit);
        return get();
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return true;
    }
}
