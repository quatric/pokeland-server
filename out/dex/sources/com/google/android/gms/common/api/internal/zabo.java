package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zabo implements Runnable {
    private final /* synthetic */ ConnectionResult zaiy;
    private final /* synthetic */ GoogleApiManager.zac zajf;

    zabo(GoogleApiManager.zac zacVar, ConnectionResult connectionResult) {
        this.zajf = zacVar;
        this.zaiy = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!this.zaiy.isSuccess()) {
            ((GoogleApiManager.zaa) GoogleApiManager.this.zaih.get(this.zajf.zafp)).onConnectionFailed(this.zaiy);
            return;
        }
        GoogleApiManager.zac.zaa(this.zajf, true);
        if (this.zajf.zain.requiresSignIn()) {
            this.zajf.zabr();
            return;
        }
        try {
            this.zajf.zain.getRemoteService(null, Collections.emptySet());
        } catch (SecurityException unused) {
            ((GoogleApiManager.zaa) GoogleApiManager.this.zaih.get(this.zajf.zafp)).onConnectionFailed(new ConnectionResult(10));
        }
    }
}
