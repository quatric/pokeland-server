package com.amazon.device.iap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.amazon.device.iap.internal.C0239d;
import com.amazon.device.iap.internal.util.C0246e;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class ResponseReceiver extends BroadcastReceiver {
    private static final String TAG = "ResponseReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            C0239d.m381d().m387a(context, intent);
        } catch (Exception e) {
            C0246e.m414b(TAG, "Error in onReceive: " + e);
        }
    }
}
