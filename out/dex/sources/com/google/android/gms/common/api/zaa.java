package com.google.android.gms.common.api;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaa implements PendingResult.StatusListener {
    private final /* synthetic */ Batch zabd;

    zaa(Batch batch) {
        this.zabd = batch;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        synchronized (this.zabd.mLock) {
            if (this.zabd.isCanceled()) {
                return;
            }
            if (status.isCanceled()) {
                Batch.zaa(this.zabd, true);
            } else if (!status.isSuccess()) {
                Batch.zab(this.zabd, true);
            }
            Batch.zab(this.zabd);
            if (this.zabd.zaaz == 0) {
                if (this.zabd.zabb) {
                    super/*com.google.android.gms.common.api.internal.BasePendingResult*/.cancel();
                } else {
                    this.zabd.setResult(new BatchResult(this.zabd.zaba ? new Status(13) : Status.RESULT_SUCCESS, this.zabd.zabc));
                }
            }
        }
    }
}
