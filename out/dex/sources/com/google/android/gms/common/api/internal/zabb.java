package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zabb extends com.google.android.gms.internal.base.zal {
    private final /* synthetic */ zaaw zahg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zabb(zaaw zaawVar, Looper looper) {
        super(looper);
        this.zahg = zaawVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            this.zahg.zaav();
            return;
        }
        if (i == 2) {
            this.zahg.resume();
            return;
        }
        int i2 = message.what;
        StringBuilder sb = new StringBuilder(31);
        sb.append("Unknown message id: ");
        sb.append(i2);
        Log.w("GoogleApiClientImpl", sb.toString());
    }
}
