package com.google.common.p015io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtIncompatible
public final class Closer implements Closeable {
    private static final Suppressor SUPPRESSOR;
    private final Deque<Closeable> stack = new ArrayDeque(4);

    @VisibleForTesting
    final Suppressor suppressor;
    private Throwable thrown;

    @VisibleForTesting
    static final class LoggingSuppressor implements Suppressor {
        static final LoggingSuppressor INSTANCE = new LoggingSuppressor();

        LoggingSuppressor() {
        }

        @Override // com.google.common.io.Closer.Suppressor
        public void suppress(Closeable closeable, Throwable th, Throwable th2) {
            Closeables.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, th2);
        }
    }

    @VisibleForTesting
    static final class SuppressingSuppressor implements Suppressor {
        static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
        static final Method addSuppressed = getAddSuppressed();

        SuppressingSuppressor() {
        }

        private static Method getAddSuppressed() {
            try {
                return Throwable.class.getMethod("addSuppressed", Throwable.class);
            } catch (Throwable unused) {
                return null;
            }
        }

        static boolean isAvailable() {
            return addSuppressed != null;
        }

        @Override // com.google.common.io.Closer.Suppressor
        public void suppress(Closeable closeable, Throwable th, Throwable th2) {
            if (th == th2) {
                return;
            }
            try {
                addSuppressed.invoke(th, th2);
            } catch (Throwable unused) {
                LoggingSuppressor.INSTANCE.suppress(closeable, th, th2);
            }
        }
    }

    @VisibleForTesting
    interface Suppressor {
        void suppress(Closeable closeable, Throwable th, Throwable th2);
    }

    static {
        SUPPRESSOR = SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE;
    }

    @VisibleForTesting
    Closer(Suppressor suppressor) {
        this.suppressor = (Suppressor) Preconditions.checkNotNull(suppressor);
    }

    public static Closer create() {
        return new Closer(SUPPRESSOR);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        Throwable th = this.thrown;
        while (!this.stack.isEmpty()) {
            Closeable closeableRemoveFirst = this.stack.removeFirst();
            try {
                closeableRemoveFirst.close();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                } else {
                    this.suppressor.suppress(closeableRemoveFirst, th, th2);
                }
            }
        }
        if (this.thrown != null || th == null) {
            return;
        }
        Throwables.propagateIfPossible(th, IOException.class);
        throw new AssertionError(th);
    }

    @CanIgnoreReturnValue
    public <C extends Closeable> C register(@Nullable C c) {
        if (c != null) {
            this.stack.addFirst(c);
        }
        return c;
    }

    public RuntimeException rethrow(Throwable th) throws Throwable {
        Preconditions.checkNotNull(th);
        this.thrown = th;
        Throwables.propagateIfPossible(th, IOException.class);
        throw new RuntimeException(th);
    }

    public <X extends Exception> RuntimeException rethrow(Throwable th, Class<X> cls) throws Exception {
        Preconditions.checkNotNull(th);
        this.thrown = th;
        Throwables.propagateIfPossible(th, IOException.class);
        Throwables.propagateIfPossible(th, cls);
        throw new RuntimeException(th);
    }

    public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable th, Class<X1> cls, Class<X2> cls2) throws Exception {
        Preconditions.checkNotNull(th);
        this.thrown = th;
        Throwables.propagateIfPossible(th, IOException.class);
        Throwables.propagateIfPossible(th, cls, cls2);
        throw new RuntimeException(th);
    }
}
