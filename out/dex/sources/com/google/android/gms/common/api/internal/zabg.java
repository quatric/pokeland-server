package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zabg extends com.google.android.gms.internal.base.zal {
    private final /* synthetic */ zabe zahu;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zabg(zabe zabeVar, Looper looper) {
        super(looper);
        this.zahu = zabeVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            ((zabf) message.obj).zac(this.zahu);
            return;
        }
        if (i == 2) {
            throw ((RuntimeException) message.obj);
        }
        int i2 = message.what;
        StringBuilder sb = new StringBuilder(31);
        sb.append("Unknown message id: ");
        sb.append(i2);
        Log.w("GACStateManager", sb.toString());
    }
}
