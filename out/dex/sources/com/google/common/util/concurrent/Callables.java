package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
public final class Callables {
    private Callables() {
    }

    @Beta
    @GwtIncompatible
    public static <T> AsyncCallable<T> asAsyncCallable(final Callable<T> callable, final ListeningExecutorService listeningExecutorService) {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(listeningExecutorService);
        return new AsyncCallable<T>() { // from class: com.google.common.util.concurrent.Callables.2
            @Override // com.google.common.util.concurrent.AsyncCallable
            public ListenableFuture<T> call() throws Exception {
                return listeningExecutorService.submit((Callable) callable);
            }
        };
    }

    public static <T> Callable<T> returning(@Nullable final T t) {
        return new Callable<T>() { // from class: com.google.common.util.concurrent.Callables.1
            @Override // java.util.concurrent.Callable
            public T call() {
                return (T) t;
            }
        };
    }

    @GwtIncompatible
    static Runnable threadRenaming(final Runnable runnable, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(runnable);
        return new Runnable() { // from class: com.google.common.util.concurrent.Callables.4
            @Override // java.lang.Runnable
            public void run() {
                Thread threadCurrentThread = Thread.currentThread();
                String name = threadCurrentThread.getName();
                boolean zTrySetName = Callables.trySetName((String) supplier.get(), threadCurrentThread);
                try {
                    runnable.run();
                } finally {
                    if (zTrySetName) {
                        Callables.trySetName(name, threadCurrentThread);
                    }
                }
            }
        };
    }

    @GwtIncompatible
    static <T> Callable<T> threadRenaming(final Callable<T> callable, final Supplier<String> supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(callable);
        return new Callable<T>() { // from class: com.google.common.util.concurrent.Callables.3
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                Thread threadCurrentThread = Thread.currentThread();
                String name = threadCurrentThread.getName();
                boolean zTrySetName = Callables.trySetName((String) supplier.get(), threadCurrentThread);
                try {
                    return (T) callable.call();
                } finally {
                    if (zTrySetName) {
                        Callables.trySetName(name, threadCurrentThread);
                    }
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GwtIncompatible
    public static boolean trySetName(String str, Thread thread) {
        try {
            thread.setName(str);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }
}
