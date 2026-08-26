package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzac {

    @GuardedBy("MessengerIpcClient.class")
    private static zzac zzby;
    private final Context zzag;
    private final ScheduledExecutorService zzbz;

    @GuardedBy("this")
    private zzae zzca = new zzae(this);

    @GuardedBy("this")
    private int zzcb = 1;

    @VisibleForTesting
    private zzac(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzbz = scheduledExecutorService;
        this.zzag = context.getApplicationContext();
    }

    private final synchronized <T> Task<T> zza(zzaj<T> zzajVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(zzajVar);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 9);
            sb.append("Queueing ");
            sb.append(strValueOf);
            Log.d("MessengerIpcClient", sb.toString());
        }
        if (!this.zzca.zzb(zzajVar)) {
            this.zzca = new zzae(this);
            this.zzca.zzb(zzajVar);
        }
        return zzajVar.zzcl.getTask();
    }

    public static synchronized zzac zzc(Context context) {
        if (zzby == null) {
            zzby = new zzac(context, com.google.android.gms.internal.firebase_messaging.zza.zza().zza(1, new NamedThreadFactory("MessengerIpcClient"), com.google.android.gms.internal.firebase_messaging.zzf.zze));
        }
        return zzby;
    }

    private final synchronized int zzx() {
        int i;
        i = this.zzcb;
        this.zzcb = i + 1;
        return i;
    }

    public final Task<Void> zza(int i, Bundle bundle) {
        return zza(new zzak(zzx(), 2, bundle));
    }

    public final Task<Bundle> zzb(int i, Bundle bundle) {
        return zza(new zzal(zzx(), 1, bundle));
    }
}
