package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zacf implements Runnable {
    private final /* synthetic */ zace zakj;

    zacf(zace zaceVar) {
        this.zakj = zaceVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zakj.zaki.zag(new ConnectionResult(4));
    }
}
