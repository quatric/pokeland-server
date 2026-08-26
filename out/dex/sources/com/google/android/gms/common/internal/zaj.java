package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaj implements PendingResult.StatusListener {
    private final /* synthetic */ PendingResult zaou;
    private final /* synthetic */ TaskCompletionSource zaov;
    private final /* synthetic */ PendingResultUtil.ResultConverter zaow;
    private final /* synthetic */ PendingResultUtil.zaa zaox;

    zaj(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, PendingResultUtil.ResultConverter resultConverter, PendingResultUtil.zaa zaaVar) {
        this.zaou = pendingResult;
        this.zaov = taskCompletionSource;
        this.zaow = resultConverter;
        this.zaox = zaaVar;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        if (!status.isSuccess()) {
            this.zaov.setException(this.zaox.zaf(status));
        } else {
            this.zaov.setResult(this.zaow.convert(this.zaou.await(0L, TimeUnit.MILLISECONDS)));
        }
    }
}
