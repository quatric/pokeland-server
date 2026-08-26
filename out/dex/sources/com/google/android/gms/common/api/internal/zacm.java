package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zacm<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private final WeakReference<GoogleApiClient> zadp;
    private final zaco zaks;
    private ResultTransform<? super R, ? extends Result> zakn = null;
    private zacm<? extends Result> zako = null;
    private volatile ResultCallbacks<? super R> zakp = null;
    private PendingResult<R> zakq = null;
    private final Object zadn = new Object();
    private Status zakr = null;
    private boolean zakt = false;

    public zacm(WeakReference<GoogleApiClient> weakReference) {
        Preconditions.checkNotNull(weakReference, "GoogleApiClient reference must not be null");
        this.zadp = weakReference;
        GoogleApiClient googleApiClient = this.zadp.get();
        this.zaks = new zaco(this, googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zab(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String strValueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(strValueOf);
                Log.w("TransformedResultImpl", sb.toString(), e);
            }
        }
    }

    @GuardedBy("mSyncToken")
    private final void zabu() {
        if (this.zakn == null && this.zakp == null) {
            return;
        }
        GoogleApiClient googleApiClient = this.zadp.get();
        if (!this.zakt && this.zakn != null && googleApiClient != null) {
            googleApiClient.zaa(this);
            this.zakt = true;
        }
        Status status = this.zakr;
        if (status != null) {
            zae(status);
            return;
        }
        PendingResult<R> pendingResult = this.zakq;
        if (pendingResult != null) {
            pendingResult.setResultCallback(this);
        }
    }

    @GuardedBy("mSyncToken")
    private final boolean zabw() {
        return (this.zakp == null || this.zadp.get() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zad(Status status) {
        synchronized (this.zadn) {
            this.zakr = status;
            zae(this.zakr);
        }
    }

    private final void zae(Status status) {
        synchronized (this.zadn) {
            if (this.zakn != null) {
                Status statusOnFailure = this.zakn.onFailure(status);
                Preconditions.checkNotNull(statusOnFailure, "onFailure must not return null");
                this.zako.zad(statusOnFailure);
            } else if (zabw()) {
                this.zakp.onFailure(status);
            }
        }
    }

    @Override // com.google.android.gms.common.api.TransformedResult
    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.zadn) {
            boolean z = true;
            Preconditions.checkState(this.zakp == null, "Cannot call andFinally() twice.");
            if (this.zakn != null) {
                z = false;
            }
            Preconditions.checkState(z, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zakp = resultCallbacks;
            zabu();
        }
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public final void onResult(R r) {
        synchronized (this.zadn) {
            if (!r.getStatus().isSuccess()) {
                zad(r.getStatus());
                zab(r);
            } else if (this.zakn != null) {
                zacc.zabb().submit(new zacn(this, r));
            } else if (zabw()) {
                this.zakp.onSuccess(r);
            }
        }
    }

    @Override // com.google.android.gms.common.api.TransformedResult
    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        zacm<? extends Result> zacmVar;
        synchronized (this.zadn) {
            boolean z = true;
            Preconditions.checkState(this.zakn == null, "Cannot call then() twice.");
            if (this.zakp != null) {
                z = false;
            }
            Preconditions.checkState(z, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zakn = resultTransform;
            zacmVar = new zacm<>(this.zadp);
            this.zako = zacmVar;
            zabu();
        }
        return zacmVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zaa(PendingResult<?> pendingResult) {
        synchronized (this.zadn) {
            this.zakq = pendingResult;
            zabu();
        }
    }

    final void zabv() {
        this.zakp = null;
    }
}
