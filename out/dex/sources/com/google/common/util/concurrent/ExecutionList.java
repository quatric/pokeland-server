package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
public final class ExecutionList {
    private static final Logger log = Logger.getLogger(ExecutionList.class.getName());

    @GuardedBy("this")
    private boolean executed;

    @GuardedBy("this")
    private RunnableExecutorPair runnables;

    private static final class RunnableExecutorPair {
        final Executor executor;

        @Nullable
        RunnableExecutorPair next;
        final Runnable runnable;

        RunnableExecutorPair(Runnable runnable, Executor executor, RunnableExecutorPair runnableExecutorPair) {
            this.runnable = runnable;
            this.executor = executor;
            this.next = runnableExecutorPair;
        }
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (this.executed) {
                executeListener(runnable, executor);
            } else {
                this.runnables = new RunnableExecutorPair(runnable, executor, this.runnables);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void execute() {
        synchronized (this) {
            if (this.executed) {
                return;
            }
            this.executed = true;
            RunnableExecutorPair runnableExecutorPair = this.runnables;
            this.runnables = null;
            RunnableExecutorPair runnableExecutorPair2 = runnableExecutorPair;
            RunnableExecutorPair runnableExecutorPair3 = null;
            while (runnableExecutorPair2 != null) {
                RunnableExecutorPair runnableExecutorPair4 = runnableExecutorPair2.next;
                runnableExecutorPair2.next = runnableExecutorPair3;
                runnableExecutorPair3 = runnableExecutorPair2;
                runnableExecutorPair2 = runnableExecutorPair4;
            }
            while (runnableExecutorPair3 != null) {
                executeListener(runnableExecutorPair3.runnable, runnableExecutorPair3.executor);
                runnableExecutorPair3 = runnableExecutorPair3.next;
            }
        }
    }
}
