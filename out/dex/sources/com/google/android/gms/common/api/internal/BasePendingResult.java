package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
@KeepName
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zadm = new zap();

    @KeepName
    private zaa mResultGuardian;
    private Status mStatus;
    private R zaci;
    private final Object zadn;
    private final CallbackHandler<R> zado;
    private final WeakReference<GoogleApiClient> zadp;
    private final CountDownLatch zadq;
    private final ArrayList<PendingResult.StatusListener> zadr;
    private ResultCallback<? super R> zads;
    private final AtomicReference<zacs> zadt;
    private volatile boolean zadu;
    private boolean zadv;
    private boolean zadw;
    private ICancelToken zadx;
    private volatile zacm<R> zady;
    private boolean zadz;

    @VisibleForTesting
    public static class CallbackHandler<R extends Result> extends com.google.android.gms.internal.base.zal {
        public CallbackHandler() {
            this(Looper.getMainLooper());
        }

        public CallbackHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Pair pair = (Pair) message.obj;
                ResultCallback resultCallback = (ResultCallback) pair.first;
                Result result = (Result) pair.second;
                try {
                    resultCallback.onResult(result);
                    return;
                } catch (RuntimeException e) {
                    BasePendingResult.zab(result);
                    throw e;
                }
            }
            if (i == 2) {
                ((BasePendingResult) message.obj).zab(Status.RESULT_TIMEOUT);
                return;
            }
            int i2 = message.what;
            StringBuilder sb = new StringBuilder(45);
            sb.append("Don't know how to handle message: ");
            sb.append(i2);
            Log.wtf("BasePendingResult", sb.toString(), new Exception());
        }

        public final void zaa(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }
    }

    private final class zaa {
        private zaa() {
        }

        /* synthetic */ zaa(BasePendingResult basePendingResult, zap zapVar) {
            this();
        }

        protected final void finalize() throws Throwable {
            BasePendingResult.zab(BasePendingResult.this.zaci);
            super.finalize();
        }
    }

    @Deprecated
    BasePendingResult() {
        this.zadn = new Object();
        this.zadq = new CountDownLatch(1);
        this.zadr = new ArrayList<>();
        this.zadt = new AtomicReference<>();
        this.zadz = false;
        this.zado = new CallbackHandler<>(Looper.getMainLooper());
        this.zadp = new WeakReference<>(null);
    }

    @KeepForSdk
    @Deprecated
    protected BasePendingResult(Looper looper) {
        this.zadn = new Object();
        this.zadq = new CountDownLatch(1);
        this.zadr = new ArrayList<>();
        this.zadt = new AtomicReference<>();
        this.zadz = false;
        this.zado = new CallbackHandler<>(looper);
        this.zadp = new WeakReference<>(null);
    }

    @KeepForSdk
    protected BasePendingResult(GoogleApiClient googleApiClient) {
        this.zadn = new Object();
        this.zadq = new CountDownLatch(1);
        this.zadr = new ArrayList<>();
        this.zadt = new AtomicReference<>();
        this.zadz = false;
        this.zado = new CallbackHandler<>(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zadp = new WeakReference<>(googleApiClient);
    }

    @VisibleForTesting
    @KeepForSdk
    protected BasePendingResult(@NonNull CallbackHandler<R> callbackHandler) {
        this.zadn = new Object();
        this.zadq = new CountDownLatch(1);
        this.zadr = new ArrayList<>();
        this.zadt = new AtomicReference<>();
        this.zadz = false;
        this.zado = (CallbackHandler) Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        this.zadp = new WeakReference<>(null);
    }

    private final R get() {
        R r;
        synchronized (this.zadn) {
            Preconditions.checkState(!this.zadu, "Result has already been consumed.");
            Preconditions.checkState(isReady(), "Result is not ready.");
            r = this.zaci;
            this.zaci = null;
            this.zads = null;
            this.zadu = true;
        }
        zacs andSet = this.zadt.getAndSet(null);
        if (andSet != null) {
            andSet.zac(this);
        }
        return r;
    }

    private final void zaa(R r) {
        this.zaci = r;
        zap zapVar = null;
        this.zadx = null;
        this.zadq.countDown();
        this.mStatus = this.zaci.getStatus();
        if (this.zadv) {
            this.zads = null;
        } else if (this.zads != null) {
            this.zado.removeMessages(2);
            this.zado.zaa(this.zads, get());
        } else if (this.zaci instanceof Releasable) {
            this.mResultGuardian = new zaa(this, zapVar);
        }
        ArrayList<PendingResult.StatusListener> arrayList = this.zadr;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            PendingResult.StatusListener statusListener = arrayList.get(i);
            i++;
            statusListener.onComplete(this.mStatus);
        }
        this.zadr.clear();
    }

    public static void zab(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String strValueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(strValueOf);
                Log.w("BasePendingResult", sb.toString(), e);
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        Preconditions.checkArgument(statusListener != null, "Callback cannot be null.");
        synchronized (this.zadn) {
            if (isReady()) {
                statusListener.onComplete(this.mStatus);
            } else {
                this.zadr.add(statusListener);
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await() {
        Preconditions.checkNotMainThread("await must not be called on the UI thread");
        Preconditions.checkState(!this.zadu, "Result has already been consumed");
        Preconditions.checkState(this.zady == null, "Cannot await if then() has been called.");
        try {
            this.zadq.await();
        } catch (InterruptedException unused) {
            zab(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return (R) get();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await(long j, TimeUnit timeUnit) {
        if (j > 0) {
            Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
        }
        Preconditions.checkState(!this.zadu, "Result has already been consumed.");
        Preconditions.checkState(this.zady == null, "Cannot await if then() has been called.");
        try {
            if (!this.zadq.await(j, timeUnit)) {
                zab(Status.RESULT_TIMEOUT);
            }
        } catch (InterruptedException unused) {
            zab(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return (R) get();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public void cancel() {
        synchronized (this.zadn) {
            if (!this.zadv && !this.zadu) {
                if (this.zadx != null) {
                    try {
                        this.zadx.cancel();
                    } catch (RemoteException unused) {
                    }
                }
                zab(this.zaci);
                this.zadv = true;
                zaa(createFailedResult(Status.RESULT_CANCELED));
            }
        }
    }

    @NonNull
    @KeepForSdk
    protected abstract R createFailedResult(Status status);

    @Override // com.google.android.gms.common.api.PendingResult
    public boolean isCanceled() {
        boolean z;
        synchronized (this.zadn) {
            z = this.zadv;
        }
        return z;
    }

    @KeepForSdk
    public final boolean isReady() {
        return this.zadq.getCount() == 0;
    }

    @KeepForSdk
    protected final void setCancelToken(ICancelToken iCancelToken) {
        synchronized (this.zadn) {
            this.zadx = iCancelToken;
        }
    }

    @KeepForSdk
    public final void setResult(R r) {
        synchronized (this.zadn) {
            if (this.zadw || this.zadv) {
                zab(r);
                return;
            }
            isReady();
            boolean z = true;
            Preconditions.checkState(!isReady(), "Results have already been set");
            if (this.zadu) {
                z = false;
            }
            Preconditions.checkState(z, "Result has already been consumed");
            zaa(r);
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        synchronized (this.zadn) {
            try {
                if (resultCallback == null) {
                    this.zads = null;
                    return;
                }
                boolean z = true;
                Preconditions.checkState(!this.zadu, "Result has already been consumed.");
                if (this.zady != null) {
                    z = false;
                }
                Preconditions.checkState(z, "Cannot set callbacks if then() has been called.");
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.zado.zaa(resultCallback, get());
                } else {
                    this.zads = resultCallback;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j, TimeUnit timeUnit) {
        synchronized (this.zadn) {
            try {
                if (resultCallback == null) {
                    this.zads = null;
                    return;
                }
                boolean z = true;
                Preconditions.checkState(!this.zadu, "Result has already been consumed.");
                if (this.zady != null) {
                    z = false;
                }
                Preconditions.checkState(z, "Cannot set callbacks if then() has been called.");
                if (isCanceled()) {
                    return;
                }
                if (isReady()) {
                    this.zado.zaa(resultCallback, get());
                } else {
                    this.zads = resultCallback;
                    CallbackHandler<R> callbackHandler = this.zado;
                    callbackHandler.sendMessageDelayed(callbackHandler.obtainMessage(2, this), timeUnit.toMillis(j));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> transformedResultThen;
        Preconditions.checkState(!this.zadu, "Result has already been consumed.");
        synchronized (this.zadn) {
            Preconditions.checkState(this.zady == null, "Cannot call then() twice.");
            Preconditions.checkState(this.zads == null, "Cannot call then() if callbacks are set.");
            Preconditions.checkState(this.zadv ? false : true, "Cannot call then() if result was canceled.");
            this.zadz = true;
            this.zady = new zacm<>(this.zadp);
            transformedResultThen = this.zady.then(resultTransform);
            if (isReady()) {
                this.zado.zaa(this.zady, get());
            } else {
                this.zads = this.zady;
            }
        }
        return transformedResultThen;
    }

    public final void zaa(zacs zacsVar) {
        this.zadt.set(zacsVar);
    }

    public final void zab(Status status) {
        synchronized (this.zadn) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zadw = true;
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final Integer zam() {
        return null;
    }

    public final boolean zat() {
        boolean zIsCanceled;
        synchronized (this.zadn) {
            if (this.zadp.get() == null || !this.zadz) {
                cancel();
            }
            zIsCanceled = isCanceled();
        }
        return zIsCanceled;
    }

    public final void zau() {
        this.zadz = this.zadz || zadm.get().booleanValue();
    }
}
