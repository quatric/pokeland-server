package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaba implements ResultCallback<Status> {
    private final /* synthetic */ zaaw zahg;
    private final /* synthetic */ StatusPendingResult zahi;
    private final /* synthetic */ boolean zahj;
    private final /* synthetic */ GoogleApiClient zahk;

    zaba(zaaw zaawVar, StatusPendingResult statusPendingResult, boolean z, GoogleApiClient googleApiClient) {
        this.zahg = zaawVar;
        this.zahi = statusPendingResult;
        this.zahj = z;
        this.zahk = googleApiClient;
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        Storage.getInstance(this.zahg.mContext).zaf();
        if (status.isSuccess() && this.zahg.isConnected()) {
            this.zahg.reconnect();
        }
        this.zahi.setResult(status);
        if (this.zahj) {
            this.zahk.disconnect();
        }
    }
}
