package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zag<ResultT> extends zac {
    private final TaskCompletionSource<ResultT> zacm;
    private final TaskApiCall<Api.AnyClient, ResultT> zacq;
    private final StatusExceptionMapper zacr;

    public zag(int i, TaskApiCall<Api.AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        super(i);
        this.zacm = taskCompletionSource;
        this.zacq = taskApiCall;
        this.zacr = statusExceptionMapper;
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final void zaa(@NonNull Status status) {
        this.zacm.trySetException(this.zacr.getException(status));
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final void zaa(GoogleApiManager.zaa<?> zaaVar) throws DeadObjectException {
        try {
            this.zacq.doExecute(zaaVar.zaab(), this.zacm);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zaa(zab.zaa(e2));
        } catch (RuntimeException e3) {
            zaa(e3);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final void zaa(@NonNull zaab zaabVar, boolean z) {
        zaabVar.zaa(this.zacm, z);
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final void zaa(@NonNull RuntimeException runtimeException) {
        this.zacm.trySetException(runtimeException);
    }

    @Override // com.google.android.gms.common.api.internal.zac
    @Nullable
    public final Feature[] zab(GoogleApiManager.zaa<?> zaaVar) {
        return this.zacq.zabt();
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final boolean zac(GoogleApiManager.zaa<?> zaaVar) {
        return this.zacq.shouldAutoResolveMissingFeatures();
    }
}
