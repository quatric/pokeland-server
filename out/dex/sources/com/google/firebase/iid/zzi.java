package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.GuardedBy;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzi implements ServiceConnection {
    private final Context zzag;
    private final Intent zzah;
    private final ScheduledExecutorService zzai;
    private final Queue<zze> zzaj;
    private zzg zzak;

    @GuardedBy("this")
    private boolean zzal;

    public zzi(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0, new NamedThreadFactory("Firebase-FirebaseInstanceIdServiceConnection")));
    }

    @VisibleForTesting
    private zzi(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.zzaj = new ArrayDeque();
        this.zzal = false;
        this.zzag = context.getApplicationContext();
        this.zzah = new Intent(str).setPackage(this.zzag.getPackageName());
        this.zzai = scheduledExecutorService;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private final synchronized void zzf() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "flush queue called");
        }
        while (!this.zzaj.isEmpty()) {
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "found intent to be delivered");
            }
            if (this.zzak == null || !this.zzak.isBinderAlive()) {
                if (Log.isLoggable("EnhancedIntentService", 3)) {
                    boolean z = !this.zzal;
                    StringBuilder sb = new StringBuilder(39);
                    sb.append("binder is dead. start connection? ");
                    sb.append(z);
                    Log.d("EnhancedIntentService", sb.toString());
                }
                if (!this.zzal) {
                    this.zzal = true;
                    try {
                        if (ConnectionTracker.getInstance().bindService(this.zzag, this.zzah, this, 65)) {
                            return;
                        } else {
                            Log.e("EnhancedIntentService", "binding to the service failed");
                        }
                    } catch (SecurityException e) {
                        Log.e("EnhancedIntentService", "Exception while binding the service", e);
                    }
                    this.zzal = false;
                    zzg();
                }
                return;
            }
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "binder is alive, sending the intent.");
            }
            this.zzak.zza(this.zzaj.poll());
        }
    }

    @GuardedBy("this")
    private final void zzg() {
        while (!this.zzaj.isEmpty()) {
            this.zzaj.poll().finish();
        }
    }

    @Override // android.content.ServiceConnection
    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            String strValueOf = String.valueOf(componentName);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 20);
            sb.append("onServiceConnected: ");
            sb.append(strValueOf);
            Log.d("EnhancedIntentService", sb.toString());
        }
        this.zzal = false;
        if (iBinder instanceof zzg) {
            this.zzak = (zzg) iBinder;
            zzf();
            return;
        }
        String strValueOf2 = String.valueOf(iBinder);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 28);
        sb2.append("Invalid service connection: ");
        sb2.append(strValueOf2);
        Log.e("EnhancedIntentService", sb2.toString());
        zzg();
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            String strValueOf = String.valueOf(componentName);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 23);
            sb.append("onServiceDisconnected: ");
            sb.append(strValueOf);
            Log.d("EnhancedIntentService", sb.toString());
        }
        zzf();
    }

    public final synchronized void zza(Intent intent, BroadcastReceiver.PendingResult pendingResult) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
        }
        this.zzaj.add(new zze(intent, pendingResult, this.zzai));
        zzf();
    }
}
