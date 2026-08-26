package com.metaps.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.messaging.MessageForwardingService;
import com.metaps.common.C0847a;
import com.metaps.common.C0856j;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FcmReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(MessageForwardingService.ACTION_REMOTE_INTENT)) {
            try {
                Intent intent2 = new Intent(context, (Class<?>) PushNotificationListenerService.class);
                intent2.putExtras(intent.getExtras());
                context.startService(intent2);
            } catch (Exception e) {
                C0847a.m908b("Failed to start PushNotificationListenerService: " + e.getMessage());
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                new Thread() { // from class: com.metaps.analytics.FcmReceiver.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        C0856j.m992a(context).m1010a(context, intent);
                        countDownLatch.countDown();
                    }
                }.start();
                try {
                    countDownLatch.await();
                } catch (InterruptedException e2) {
                    C0847a.m905a(FcmReceiver.class.toString(), "separateInit process failed", e2);
                }
            }
        }
    }
}
