package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zacn implements Runnable {
    private final /* synthetic */ Result zaku;
    private final /* synthetic */ zacm zakv;

    zacn(zacm zacmVar, Result result) {
        this.zakv = zacmVar;
        this.zaku = result;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        try {
            BasePendingResult.zadm.set(true);
            this.zakv.zaks.sendMessage(this.zakv.zaks.obtainMessage(0, this.zakv.zakn.onSuccess(this.zaku)));
        } catch (RuntimeException e) {
            this.zakv.zaks.sendMessage(this.zakv.zaks.obtainMessage(1, e));
        } finally {
            BasePendingResult.zadm.set(false);
            zacm zacmVar = this.zakv;
            zacm.zab(this.zaku);
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zakv.zadp.get();
            if (googleApiClient != null) {
                googleApiClient.zab(this.zakv);
            }
        }
    }
}
