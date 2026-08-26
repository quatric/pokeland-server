package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@KeepForSdk
public class StatusCallback extends IStatusCallback.Stub {

    @KeepForSdk
    private final BaseImplementation.ResultHolder<Status> mResultHolder;

    @KeepForSdk
    public StatusCallback(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.mResultHolder = resultHolder;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    @KeepForSdk
    public void onResult(Status status) {
        this.mResultHolder.setResult(status);
    }
}
