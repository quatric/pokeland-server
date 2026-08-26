package com.google.common.util.concurrent;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
@GwtIncompatible
public abstract class AbstractService implements Service {
    private static final ListenerCallQueue.Callback<Service.Listener> STARTING_CALLBACK = new ListenerCallQueue.Callback<Service.Listener>("starting()") { // from class: com.google.common.util.concurrent.AbstractService.1
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.ListenerCallQueue.Callback
        public void call(Service.Listener listener) {
            listener.starting();
        }
    };
    private static final ListenerCallQueue.Callback<Service.Listener> RUNNING_CALLBACK = new ListenerCallQueue.Callback<Service.Listener>("running()") { // from class: com.google.common.util.concurrent.AbstractService.2
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.ListenerCallQueue.Callback
        public void call(Service.Listener listener) {
            listener.running();
        }
    };
    private static final ListenerCallQueue.Callback<Service.Listener> STOPPING_FROM_STARTING_CALLBACK = stoppingCallback(Service.State.STARTING);
    private static final ListenerCallQueue.Callback<Service.Listener> STOPPING_FROM_RUNNING_CALLBACK = stoppingCallback(Service.State.RUNNING);
    private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_NEW_CALLBACK = terminatedCallback(Service.State.NEW);
    private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_RUNNING_CALLBACK = terminatedCallback(Service.State.RUNNING);
    private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_STOPPING_CALLBACK = terminatedCallback(Service.State.STOPPING);
    private final Monitor monitor = new Monitor();
    private final Monitor.Guard isStartable = new IsStartableGuard();
    private final Monitor.Guard isStoppable = new IsStoppableGuard();
    private final Monitor.Guard hasReachedRunning = new HasReachedRunningGuard();
    private final Monitor.Guard isStopped = new IsStoppedGuard();

    @GuardedBy("monitor")
    private final List<ListenerCallQueue<Service.Listener>> listeners = Collections.synchronizedList(new ArrayList());

    @GuardedBy("monitor")
    private volatile StateSnapshot snapshot = new StateSnapshot(Service.State.NEW);

    private final class HasReachedRunningGuard extends Monitor.Guard {
        HasReachedRunningGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0;
        }
    }

    private final class IsStartableGuard extends Monitor.Guard {
        IsStartableGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state() == Service.State.NEW;
        }
    }

    private final class IsStoppableGuard extends Monitor.Guard {
        IsStoppableGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0;
        }
    }

    private final class IsStoppedGuard extends Monitor.Guard {
        IsStoppedGuard() {
            super(AbstractService.this.monitor);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().isTerminal();
        }
    }

    @Immutable
    private static final class StateSnapshot {

        @Nullable
        final Throwable failure;
        final boolean shutdownWhenStartupFinishes;
        final Service.State state;

        StateSnapshot(Service.State state) {
            this(state, false, null);
        }

        StateSnapshot(Service.State state, boolean z, @Nullable Throwable th) {
            Preconditions.checkArgument(!z || state == Service.State.STARTING, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", state);
            Preconditions.checkArgument((th != null) ^ (state == Service.State.FAILED) ? false : true, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", state, th);
            this.state = state;
            this.shutdownWhenStartupFinishes = z;
            this.failure = th;
        }

        Service.State externalState() {
            return (this.shutdownWhenStartupFinishes && this.state == Service.State.STARTING) ? Service.State.STOPPING : this.state;
        }

        Throwable failureCause() {
            Preconditions.checkState(this.state == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.state);
            return this.failure;
        }
    }

    protected AbstractService() {
    }

    @GuardedBy("monitor")
    private void checkCurrentState(Service.State state) {
        Service.State state2 = state();
        if (state2 != state) {
            if (state2 == Service.State.FAILED) {
                throw new IllegalStateException("Expected the service " + this + " to be " + state + ", but the service has FAILED", failureCause());
            }
            throw new IllegalStateException("Expected the service " + this + " to be " + state + ", but was " + state2);
        }
    }

    private void executeListeners() {
        if (this.monitor.isOccupiedByCurrentThread()) {
            return;
        }
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).execute();
        }
    }

    @GuardedBy("monitor")
    private void failed(final Service.State state, final Throwable th) {
        new ListenerCallQueue.Callback<Service.Listener>("failed({from = " + state + ", cause = " + th + "})") { // from class: com.google.common.util.concurrent.AbstractService.5
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.util.concurrent.ListenerCallQueue.Callback
            public void call(Service.Listener listener) {
                listener.failed(state, th);
            }
        }.enqueueOn(this.listeners);
    }

    @GuardedBy("monitor")
    private void running() {
        RUNNING_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy("monitor")
    private void starting() {
        STARTING_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy("monitor")
    private void stopping(Service.State state) {
        if (state == Service.State.STARTING) {
            STOPPING_FROM_STARTING_CALLBACK.enqueueOn(this.listeners);
        } else {
            if (state != Service.State.RUNNING) {
                throw new AssertionError();
            }
            STOPPING_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
        }
    }

    private static ListenerCallQueue.Callback<Service.Listener> stoppingCallback(final Service.State state) {
        return new ListenerCallQueue.Callback<Service.Listener>("stopping({from = " + state + "})") { // from class: com.google.common.util.concurrent.AbstractService.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.util.concurrent.ListenerCallQueue.Callback
            public void call(Service.Listener listener) {
                listener.stopping(state);
            }
        };
    }

    @GuardedBy("monitor")
    private void terminated(Service.State state) {
        int i = C07156.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()];
        if (i == 1) {
            TERMINATED_FROM_NEW_CALLBACK.enqueueOn(this.listeners);
        } else if (i == 3) {
            TERMINATED_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
        } else {
            if (i != 4) {
                throw new AssertionError();
            }
            TERMINATED_FROM_STOPPING_CALLBACK.enqueueOn(this.listeners);
        }
    }

    private static ListenerCallQueue.Callback<Service.Listener> terminatedCallback(final Service.State state) {
        return new ListenerCallQueue.Callback<Service.Listener>("terminated({from = " + state + "})") { // from class: com.google.common.util.concurrent.AbstractService.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.util.concurrent.ListenerCallQueue.Callback
            public void call(Service.Listener listener) {
                listener.terminated(state);
            }
        };
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        Preconditions.checkNotNull(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        Preconditions.checkNotNull(executor, "executor");
        this.monitor.enter();
        try {
            if (!state().isTerminal()) {
                this.listeners.add(new ListenerCallQueue<>(listener, executor));
            }
        } finally {
            this.monitor.leave();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            checkCurrentState(Service.State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, j, timeUnit)) {
            try {
                checkCurrentState(Service.State.RUNNING);
            } finally {
                this.monitor.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state.");
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            checkCurrentState(Service.State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, j, timeUnit)) {
            try {
                checkCurrentState(Service.State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. Current state: " + state());
        }
    }

    protected abstract void doStart();

    protected abstract void doStop();

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return state() == Service.State.RUNNING;
    }

    protected final void notifyFailed(Throwable th) {
        Preconditions.checkNotNull(th);
        this.monitor.enter();
        try {
            Service.State state = state();
            switch (state) {
                case NEW:
                case TERMINATED:
                    throw new IllegalStateException("Failed while in state:" + state, th);
                case STARTING:
                case RUNNING:
                case STOPPING:
                    this.snapshot = new StateSnapshot(Service.State.FAILED, false, th);
                    failed(state, th);
                    break;
                case FAILED:
                    break;
                default:
                    throw new AssertionError("Unexpected state: " + state);
            }
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th2) {
            this.monitor.leave();
            executeListeners();
            throw th2;
        }
    }

    protected final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state != Service.State.STARTING) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
                notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            if (this.snapshot.shutdownWhenStartupFinishes) {
                this.snapshot = new StateSnapshot(Service.State.STOPPING);
                doStop();
            } else {
                this.snapshot = new StateSnapshot(Service.State.RUNNING);
                running();
            }
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th) {
            this.monitor.leave();
            executeListeners();
            throw th;
        }
    }

    protected final void notifyStopped() {
        this.monitor.enter();
        try {
            Service.State state = this.snapshot.state;
            if (state != Service.State.STOPPING && state != Service.State.RUNNING) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStopped() when the service is " + state);
                notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            this.snapshot = new StateSnapshot(Service.State.TERMINATED);
            terminated(state);
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th) {
            this.monitor.leave();
            executeListeners();
            throw th;
        }
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (!this.monitor.enterIf(this.isStartable)) {
            throw new IllegalStateException("Service " + this + " has already been started");
        }
        try {
            try {
                this.snapshot = new StateSnapshot(Service.State.STARTING);
                starting();
                doStart();
            } catch (Throwable th) {
                notifyFailed(th);
            }
            return this;
        } finally {
            this.monitor.leave();
            executeListeners();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.snapshot.externalState();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        try {
            if (this.monitor.enterIf(this.isStoppable)) {
                try {
                    Service.State state = state();
                    switch (state) {
                        case NEW:
                            this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                            terminated(Service.State.NEW);
                            break;
                        case STARTING:
                            this.snapshot = new StateSnapshot(Service.State.STARTING, true, null);
                            stopping(Service.State.STARTING);
                            break;
                        case RUNNING:
                            this.snapshot = new StateSnapshot(Service.State.STOPPING);
                            stopping(Service.State.RUNNING);
                            doStop();
                            break;
                        case STOPPING:
                        case TERMINATED:
                        case FAILED:
                            throw new AssertionError("isStoppable is incorrectly implemented, saw: " + state);
                        default:
                            throw new AssertionError("Unexpected state: " + state);
                    }
                } catch (Throwable th) {
                    notifyFailed(th);
                }
                this.monitor.leave();
                executeListeners();
            }
            return this;
        } catch (Throwable th2) {
            this.monitor.leave();
            executeListeners();
            throw th2;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " [" + state() + "]";
    }
}
