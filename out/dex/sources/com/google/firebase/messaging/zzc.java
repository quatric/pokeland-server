package com.google.firebase.messaging;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Tasks;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzc {
    private final Context zzag;
    private final Bundle zzcm;
    private final Executor zzdy;
    private final zzb zzdz;

    public zzc(Context context, Bundle bundle, Executor executor) {
        this.zzdy = executor;
        this.zzag = context;
        this.zzcm = bundle;
        this.zzdz = new zzb(context, context.getPackageName());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x005e A[EDGE_INSN: B:20:0x005e->B:21:0x005f BREAK  A[LOOP:0: B:13:0x0046->B:42:?]] */
    final boolean zzas() {
        boolean z;
        if ("1".equals(zzb.zza(this.zzcm, "gcm.n.noui"))) {
            return true;
        }
        if (!((KeyguardManager) this.zzag.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            if (!PlatformVersion.isAtLeastLollipop()) {
                SystemClock.sleep(10L);
            }
            int iMyPid = Process.myPid();
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.zzag.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses == null) {
                z = false;
                break;
            }
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (true) {
                if (it.hasNext()) {
                    ActivityManager.RunningAppProcessInfo next = it.next();
                    if (next.pid == iMyPid) {
                        if (next.importance == 100) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                break;
            }
        }
        z = false;
        break;
        if (z) {
            return false;
        }
        zzd zzdVarZzo = zzd.zzo(zzb.zza(this.zzcm, "gcm.n.image"));
        if (zzdVarZzo != null) {
            zzdVarZzo.zza(this.zzdy);
        }
        zza zzaVarZzf = this.zzdz.zzf(this.zzcm);
        NotificationCompat.Builder builder = zzaVarZzf.zzds;
        if (zzdVarZzo != null) {
            try {
                Bitmap bitmap = (Bitmap) Tasks.await(zzdVarZzo.getTask(), 5L, TimeUnit.SECONDS);
                builder.setLargeIcon(bitmap);
                builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));
            } catch (InterruptedException unused) {
                Log.w("FirebaseMessaging", "Interrupted while downloading image, showing notification without it");
                zzdVarZzo.close();
                Thread.currentThread().interrupt();
            } catch (ExecutionException unused2) {
            } catch (TimeoutException unused3) {
                Log.w("FirebaseMessaging", "Failed to download image in time, showing notification without it");
                zzdVarZzo.close();
            }
        }
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            Log.d("FirebaseMessaging", "Showing notification");
        }
        ((NotificationManager) this.zzag.getSystemService("notification")).notify(zzaVarZzf.tag, 0, zzaVarZzf.zzds.build());
        return true;
    }
}
