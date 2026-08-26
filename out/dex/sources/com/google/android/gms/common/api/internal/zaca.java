package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Add missing generic type declarations: [A, L] */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaca<A, L> extends RegisterListenerMethod<A, L> {
    private final /* synthetic */ RegistrationMethods.Builder zakg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zaca(RegistrationMethods.Builder builder, ListenerHolder listenerHolder, Feature[] featureArr, boolean z) {
        super(listenerHolder, featureArr, z);
        this.zakg = builder;
    }

    /* JADX WARN: Incorrect types in method signature: (TA;Lcom/google/android/gms/tasks/TaskCompletionSource<Ljava/lang/Void;>;)V */
    @Override // com.google.android.gms.common.api.internal.RegisterListenerMethod
    protected final void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zakg.zaka.accept(anyClient, taskCompletionSource);
    }
}
