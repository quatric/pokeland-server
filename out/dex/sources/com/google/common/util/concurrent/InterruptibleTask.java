package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible(emulated = true)
abstract class InterruptibleTask implements Runnable {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log = Logger.getLogger(InterruptibleTask.class.getName());
    private volatile boolean doneInterrupting;
    private volatile Thread runner;

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract boolean compareAndSetRunner(InterruptibleTask interruptibleTask, Thread thread, Thread thread2);
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<InterruptibleTask, Thread> runnerUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater) {
            super();
            this.runnerUpdater = atomicReferenceFieldUpdater;
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask.AtomicHelper
        boolean compareAndSetRunner(InterruptibleTask interruptibleTask, Thread thread, Thread thread2) {
            return this.runnerUpdater.compareAndSet(interruptibleTask, thread, thread2);
        }
    }

    private static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask.AtomicHelper
        boolean compareAndSetRunner(InterruptibleTask interruptibleTask, Thread thread, Thread thread2) {
            synchronized (interruptibleTask) {
                if (interruptibleTask.runner == thread) {
                    interruptibleTask.runner = thread2;
                }
            }
            return true;
        }
    }

    static {
        AtomicHelper synchronizedAtomicHelper;
        try {
            synchronizedAtomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(InterruptibleTask.class, Thread.class, "runner"));
        } catch (Throwable th) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
            synchronizedAtomicHelper = new SynchronizedAtomicHelper();
        }
        ATOMIC_HELPER = synchronizedAtomicHelper;
    }

    InterruptibleTask() {
    }

    final void interruptTask() {
        Thread thread = this.runner;
        if (thread != null) {
            thread.interrupt();
        }
        this.doneInterrupting = true;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // java.lang.Runnable
    public final void run() {
        if (ATOMIC_HELPER.compareAndSetRunner(this, null, Thread.currentThread())) {
            try {
                runInterruptibly();
            } finally {
                if (wasInterrupted()) {
                    while (!this.doneInterrupting) {
                        Thread.yield();
                    }
                }
            }
        }
    }

    abstract void runInterruptibly();

    abstract boolean wasInterrupted();
}
