package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Add missing generic type declarations: [A, ResultT] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zack<A, ResultT> extends TaskApiCall<A, ResultT> {
    private final /* synthetic */ TaskApiCall.Builder zakm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zack(TaskApiCall.Builder builder, Feature[] featureArr, boolean z) {
        super(featureArr, z);
        this.zakm = builder;
    }

    /* JADX WARN: Incorrect types in method signature: (TA;Lcom/google/android/gms/tasks/TaskCompletionSource<TResultT;>;)V */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zakm.zakl.accept(anyClient, taskCompletionSource);
    }
}
