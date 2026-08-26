package com.metaps.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.metaps.common.C0847a;
import com.metaps.common.C0856j;
import com.metaps.common.C0857k;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ReadReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(C0856j.f958d)) {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            launchIntentForPackage.addFlags(536870912);
            C0857k c0857kM993a = C0856j.m993a(intent);
            if (c0857kM993a == null || !c0857kM993a.m1012b()) {
                C0847a.m911c("ReadReceiver#onReceive Notification params is invalid.");
            } else {
                C0785a.m617a(c0857kM993a);
                C0856j.m994a(c0857kM993a.m1016f());
            }
            context.startActivity(launchIntentForPackage);
        }
    }
}
