package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaam implements BaseGmsClient.ConnectionProgressReportCallbacks {
    private final Api<?> mApi;
    private final boolean zaeb;
    private final WeakReference<zaak> zagj;

    public zaam(zaak zaakVar, Api<?> api, boolean z) {
        this.zagj = new WeakReference<>(zaakVar);
        this.mApi = api;
        this.zaeb = z;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
    public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
        zaak zaakVar = this.zagj.get();
        if (zaakVar == null) {
            return;
        }
        Preconditions.checkState(Looper.myLooper() == zaakVar.zafs.zaed.getLooper(), "onReportServiceBinding must be called on the GoogleApiClient handler thread");
        zaakVar.zaen.lock();
        try {
            if (zaakVar.zac(0)) {
                if (!connectionResult.isSuccess()) {
                    zaakVar.zab(connectionResult, this.mApi, this.zaeb);
                }
                if (zaakVar.zaao()) {
                    zaakVar.zaap();
                }
            }
        } finally {
            zaakVar.zaen.unlock();
        }
    }
}
