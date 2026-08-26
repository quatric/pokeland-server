package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
abstract class AggregateFuture<InputT, OutputT> extends AbstractFuture.TrustedFuture<OutputT> {
    private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    private AggregateFuture<InputT, OutputT>.RunningState runningState;

    abstract class RunningState extends AggregateFutureState implements Runnable {
        private final boolean allMustSucceed;
        private final boolean collectsValues;
        private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

        RunningState(ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean z, boolean z2) {
            super(immutableCollection.size());
            this.futures = (ImmutableCollection) Preconditions.checkNotNull(immutableCollection);
            this.allMustSucceed = z;
            this.collectsValues = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void decrementCountAndMaybeComplete() {
            int iDecrementRemainingAndGet = decrementRemainingAndGet();
            Preconditions.checkState(iDecrementRemainingAndGet >= 0, "Less than 0 remaining futures");
            if (iDecrementRemainingAndGet == 0) {
                processCompleted();
            }
        }

        /* JADX WARN: Code duplicated, block: B:13:0x0027  */
        /* JADX WARN: Code duplicated, block: B:16:0x002e A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:17:0x0030  */
        /* JADX WARN: Code duplicated, block: B:18:0x0033  */
        /* JADX WARN: Code duplicated, block: B:21:? A[RETURN, SYNTHETIC] */
        private void handleException(Throwable th) {
            boolean exception;
            boolean zAddCausalChain;
            boolean z;
            String str;
            Preconditions.checkNotNull(th);
            if (this.allMustSucceed) {
                exception = AggregateFuture.this.setException(th);
                if (exception) {
                    releaseResourcesAfterFailure();
                } else {
                    zAddCausalChain = AggregateFuture.addCausalChain(getOrInitSeenExceptions(), th);
                }
                z = th instanceof Error;
                if ((this.allMustSucceed & (exception ? false : true) & zAddCausalChain) || z) {
                    if (z) {
                        str = "Input Future failed with Error";
                    } else {
                        str = "Got more than one input Future failure. Logging failures after the first";
                    }
                    AggregateFuture.logger.log(Level.SEVERE, str, th);
                }
            }
            exception = false;
            zAddCausalChain = true;
            z = th instanceof Error;
            if ((this.allMustSucceed & (exception ? false : true) & zAddCausalChain) || z) {
                if (z) {
                    str = "Input Future failed with Error";
                } else {
                    str = "Got more than one input Future failure. Logging failures after the first";
                }
                AggregateFuture.logger.log(Level.SEVERE, str, th);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public void handleOneInputDone(int i, Future<? extends InputT> future) {
            Preconditions.checkState(this.allMustSucceed || !AggregateFuture.this.isDone() || AggregateFuture.this.isCancelled(), "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.allMustSucceed) {
                    if (future.isCancelled()) {
                        AggregateFuture.this.runningState = null;
                        AggregateFuture.this.cancel(false);
                    } else {
                        Object done = Futures.getDone(future);
                        if (this.collectsValues) {
                            collectOneValue(this.allMustSucceed, i, done);
                        }
                    }
                } else if (this.collectsValues && !future.isCancelled()) {
                    collectOneValue(this.allMustSucceed, i, Futures.getDone(future));
                }
            } catch (ExecutionException e) {
                handleException(e.getCause());
            } catch (Throwable th) {
                handleException(th);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            if (this.futures.isEmpty()) {
                handleAllCompleted();
                return;
            }
            if (!this.allMustSucceed) {
                Iterator it = this.futures.iterator();
                while (it.hasNext()) {
                    ((ListenableFuture) it.next()).addListener(this, MoreExecutors.directExecutor());
                }
                return;
            }
            final int i = 0;
            Iterator it2 = this.futures.iterator();
            while (it2.hasNext()) {
                final ListenableFuture listenableFuture = (ListenableFuture) it2.next();
                listenableFuture.addListener(new Runnable() { // from class: com.google.common.util.concurrent.AggregateFuture.RunningState.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            RunningState.this.handleOneInputDone(i, listenableFuture);
                        } finally {
                            RunningState.this.decrementCountAndMaybeComplete();
                        }
                    }
                }, MoreExecutors.directExecutor());
                i++;
            }
        }

        private void processCompleted() {
            if (this.collectsValues & (!this.allMustSucceed)) {
                int i = 0;
                Iterator it = this.futures.iterator();
                while (it.hasNext()) {
                    handleOneInputDone(i, (ListenableFuture) it.next());
                    i++;
                }
            }
            handleAllCompleted();
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState
        final void addInitialException(Set<Throwable> set) {
            if (AggregateFuture.this.isCancelled()) {
                return;
            }
            AggregateFuture.addCausalChain(set, AggregateFuture.this.trustedGetException());
        }

        abstract void collectOneValue(boolean z, int i, @Nullable InputT inputt);

        abstract void handleAllCompleted();

        void interruptTask() {
        }

        void releaseResourcesAfterFailure() {
            this.futures = null;
        }

        @Override // java.lang.Runnable
        public final void run() {
            decrementCountAndMaybeComplete();
        }
    }

    AggregateFuture() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean addCausalChain(Set<Throwable> set, Throwable th) {
        while (th != null) {
            if (!set.add(th)) {
                return false;
            }
            th = th.getCause();
        }
        return true;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void afterDone() {
        super.afterDone();
        AggregateFuture<InputT, OutputT>.RunningState runningState = this.runningState;
        if (runningState != null) {
            this.runningState = null;
            ImmutableCollection immutableCollection = ((RunningState) runningState).futures;
            boolean zWasInterrupted = wasInterrupted();
            if (wasInterrupted()) {
                runningState.interruptTask();
            }
            if (isCancelled() && (immutableCollection != null)) {
                Iterator it = immutableCollection.iterator();
                while (it.hasNext()) {
                    ((ListenableFuture) it.next()).cancel(zWasInterrupted);
                }
            }
        }
    }

    final void init(AggregateFuture<InputT, OutputT>.RunningState runningState) {
        this.runningState = runningState;
        runningState.init();
    }
}
