package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Batch extends BasePendingResult<BatchResult> {
    private final Object mLock;
    private int zaaz;
    private boolean zaba;
    private boolean zabb;
    private final PendingResult<?>[] zabc;

    public static final class Builder {
        private List<PendingResult<?>> zabe = new ArrayList();
        private GoogleApiClient zabf;

        public Builder(GoogleApiClient googleApiClient) {
            this.zabf = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.zabe.size());
            this.zabe.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zabe, this.zabf, null);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zaaz = list.size();
        this.zabc = new PendingResult[this.zaaz];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.RESULT_SUCCESS, this.zabc));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult<?> pendingResult = list.get(i);
            this.zabc[i] = pendingResult;
            pendingResult.addStatusListener(new zaa(this));
        }
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zaa zaaVar) {
        this(list, googleApiClient);
    }

    static /* synthetic */ boolean zaa(Batch batch, boolean z) {
        batch.zabb = true;
        return true;
    }

    static /* synthetic */ int zab(Batch batch) {
        int i = batch.zaaz;
        batch.zaaz = i - 1;
        return i;
    }

    static /* synthetic */ boolean zab(Batch batch, boolean z) {
        batch.zaba = true;
        return true;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult, com.google.android.gms.common.api.PendingResult
    public final void cancel() {
        super.cancel();
        for (PendingResult<?> pendingResult : this.zabc) {
            pendingResult.cancel();
        }
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zabc);
    }
}
