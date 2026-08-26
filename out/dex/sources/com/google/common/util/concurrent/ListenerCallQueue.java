package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtIncompatible
final class ListenerCallQueue<L> implements Runnable {
    private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final Executor executor;

    @GuardedBy("this")
    private boolean isThreadScheduled;
    private final L listener;

    @GuardedBy("this")
    private final Queue<Callback<L>> waitQueue = Queues.newArrayDeque();

    static abstract class Callback<L> {
        private final String methodCall;

        Callback(String str) {
            this.methodCall = str;
        }

        abstract void call(L l);

        void enqueueOn(Iterable<ListenerCallQueue<L>> iterable) {
            Iterator<ListenerCallQueue<L>> it = iterable.iterator();
            while (it.hasNext()) {
                it.next().add(this);
            }
        }
    }

    ListenerCallQueue(L l, Executor executor) {
        this.listener = (L) Preconditions.checkNotNull(l);
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    synchronized void add(Callback<L> callback) {
        this.waitQueue.add(callback);
    }

    void execute() {
        boolean z;
        synchronized (this) {
            z = true;
            if (this.isThreadScheduled) {
                z = false;
            } else {
                this.isThreadScheduled = true;
            }
        }
        if (z) {
            try {
                this.executor.execute(this);
            } catch (RuntimeException e) {
                synchronized (this) {
                    this.isThreadScheduled = false;
                    logger.log(Level.SEVERE, "Exception while running callbacks for " + this.listener + " on " + this.executor, (Throwable) e);
                    throw e;
                }
            }
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0049 */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x001b, code lost:
    
        r2.call(r8.listener);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0021, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        com.google.common.util.concurrent.ListenerCallQueue.logger.log(java.util.logging.Level.SEVERE, "Exception while executing callback: " + r8.listener + "." + ((com.google.common.util.concurrent.ListenerCallQueue.Callback) r2).methodCall, (java.lang.Throwable) r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x004a, code lost:
    
        r2 = th;
     */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            r8 = this;
        L0:
            r0 = 0
            r1 = 1
            monitor-enter(r8)     // Catch: java.lang.Throwable -> L4c
            boolean r2 = r8.isThreadScheduled     // Catch: java.lang.Throwable -> L49
            com.google.common.base.Preconditions.checkState(r2)     // Catch: java.lang.Throwable -> L49
            java.util.Queue<com.google.common.util.concurrent.ListenerCallQueue$Callback<L>> r2 = r8.waitQueue     // Catch: java.lang.Throwable -> L49
            java.lang.Object r2 = r2.poll()     // Catch: java.lang.Throwable -> L49
            com.google.common.util.concurrent.ListenerCallQueue$Callback r2 = (com.google.common.util.concurrent.ListenerCallQueue.Callback) r2     // Catch: java.lang.Throwable -> L49
            if (r2 != 0) goto L1a
            r8.isThreadScheduled = r0     // Catch: java.lang.Throwable -> L49
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L16
            return
        L16:
            r1 = move-exception
            r2 = r1
            r1 = 0
            goto L4a
        L1a:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L49
            L r3 = r8.listener     // Catch: java.lang.RuntimeException -> L21 java.lang.Throwable -> L4c
            r2.call(r3)     // Catch: java.lang.RuntimeException -> L21 java.lang.Throwable -> L4c
            goto L0
        L21:
            r3 = move-exception
            java.util.logging.Logger r4 = com.google.common.util.concurrent.ListenerCallQueue.logger     // Catch: java.lang.Throwable -> L4c
            java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: java.lang.Throwable -> L4c
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c
            r6.<init>()     // Catch: java.lang.Throwable -> L4c
            java.lang.String r7 = "Exception while executing callback: "
            r6.append(r7)     // Catch: java.lang.Throwable -> L4c
            L r7 = r8.listener     // Catch: java.lang.Throwable -> L4c
            r6.append(r7)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r7 = "."
            r6.append(r7)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r2 = com.google.common.util.concurrent.ListenerCallQueue.Callback.access$000(r2)     // Catch: java.lang.Throwable -> L4c
            r6.append(r2)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r2 = r6.toString()     // Catch: java.lang.Throwable -> L4c
            r4.log(r5, r2, r3)     // Catch: java.lang.Throwable -> L4c
            goto L0
        L49:
            r2 = move-exception
        L4a:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L49
            throw r2     // Catch: java.lang.Throwable -> L4c
        L4c:
            r2 = move-exception
            if (r1 == 0) goto L57
            monitor-enter(r8)
            r8.isThreadScheduled = r0     // Catch: java.lang.Throwable -> L54
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L54
            goto L57
        L54:
            r0 = move-exception
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L54
            throw r0
        L57:
            throw r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.run():void");
    }
}
