package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Log;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zze {
    final Intent intent;
    private boolean zzaa = false;
    private final ScheduledFuture<?> zzab;
    private final BroadcastReceiver.PendingResult zzz;

    zze(final Intent intent, BroadcastReceiver.PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.intent = intent;
        this.zzz = pendingResult;
        this.zzab = scheduledExecutorService.schedule(new Runnable(this, intent) { // from class: com.google.firebase.iid.zzd
            private final zze zzx;
            private final Intent zzy;

            {
                this.zzx = this;
                this.zzy = intent;
            }

            @Override // java.lang.Runnable
            public final void run() {
                zze zzeVar = this.zzx;
                String action = this.zzy.getAction();
                StringBuilder sb = new StringBuilder(String.valueOf(action).length() + 61);
                sb.append("Service took too long to process intent: ");
                sb.append(action);
                sb.append(" App may get closed.");
                Log.w("EnhancedIntentService", sb.toString());
                zzeVar.finish();
            }
        }, 9000L, TimeUnit.MILLISECONDS);
    }

    final synchronized void finish() {
        if (!this.zzaa) {
            this.zzz.finish();
            this.zzab.cancel(false);
            this.zzaa = true;
        }
    }
}
