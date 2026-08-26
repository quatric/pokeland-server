package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zabl implements Runnable {
    private final /* synthetic */ GoogleApiManager.zaa zaix;
    private final /* synthetic */ ConnectionResult zaiy;

    zabl(GoogleApiManager.zaa zaaVar, ConnectionResult connectionResult) {
        this.zaix = zaaVar;
        this.zaiy = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaix.onConnectionFailed(this.zaiy);
    }
}
