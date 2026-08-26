package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
final class SerializingExecutor implements Executor {
    private static final Logger log = Logger.getLogger(SerializingExecutor.class.getName());
    private final Executor executor;

    @GuardedBy("internalLock")
    private final Deque<Runnable> queue = new ArrayDeque();

    @GuardedBy("internalLock")
    private boolean isWorkerRunning = false;

    @GuardedBy("internalLock")
    private int suspensions = 0;
    private final Object internalLock = new Object();

    private final class QueueWorker implements Runnable {
        private QueueWorker() {
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        private void workOnQueue() {
            Runnable runnable;
            while (true) {
                synchronized (SerializingExecutor.this.internalLock) {
                    runnable = SerializingExecutor.this.suspensions == 0 ? (Runnable) SerializingExecutor.this.queue.poll() : null;
                    if (runnable == null) {
                        SerializingExecutor.this.isWorkerRunning = false;
                        return;
                    }
                }
                try {
                    runnable.run();
                } catch (RuntimeException e) {
                    SerializingExecutor.log.log(Level.SEVERE, "Exception while executing runnable " + runnable, (Throwable) e);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SerializingExecutor.this.internalLock) {
                    SerializingExecutor.this.isWorkerRunning = false;
                    throw e;
                }
            }
        }
    }

    public SerializingExecutor(Executor executor) {
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    private void startQueueWorker() {
        synchronized (this.internalLock) {
            if (this.queue.peek() == null) {
                return;
            }
            if (this.suspensions > 0) {
                return;
            }
            if (this.isWorkerRunning) {
                return;
            }
            this.isWorkerRunning = true;
            try {
                this.executor.execute(new QueueWorker());
            } catch (Throwable th) {
                synchronized (this.internalLock) {
                    this.isWorkerRunning = false;
                    throw th;
                }
            }
        }
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        synchronized (this.internalLock) {
            this.queue.add(runnable);
        }
        startQueueWorker();
    }

    public void executeFirst(Runnable runnable) {
        synchronized (this.internalLock) {
            this.queue.addFirst(runnable);
        }
        startQueueWorker();
    }

    public void resume() {
        synchronized (this.internalLock) {
            Preconditions.checkState(this.suspensions > 0);
            this.suspensions--;
        }
        startQueueWorker();
    }

    public void suspend() {
        synchronized (this.internalLock) {
            this.suspensions++;
        }
    }
}
