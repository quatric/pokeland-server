package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaco extends com.google.android.gms.internal.base.zal {
    private final /* synthetic */ zacm zakv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaco(zacm zacmVar, Looper looper) {
        super(looper);
        this.zakv = zacmVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            PendingResult<?> pendingResult = (PendingResult) message.obj;
            synchronized (this.zakv.zadn) {
                try {
                    if (pendingResult == null) {
                        this.zakv.zako.zad(new Status(13, "Transform returned null"));
                    } else if (pendingResult instanceof zacd) {
                        this.zakv.zako.zad(((zacd) pendingResult).getStatus());
                    } else {
                        this.zakv.zako.zaa(pendingResult);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        if (i == 1) {
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String strValueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", strValueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(strValueOf) : new String("Runtime exception on the transformation worker thread: "));
            throw runtimeException;
        }
        int i2 = message.what;
        StringBuilder sb = new StringBuilder(70);
        sb.append("TransformationResultHandler received unknown message type: ");
        sb.append(i2);
        Log.e("TransformedResultImpl", sb.toString());
    }
}
