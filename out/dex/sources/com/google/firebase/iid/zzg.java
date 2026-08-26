package com.google.firebase.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzg extends Binder {
    private final zzc zzae;

    zzg(zzc zzcVar) {
        this.zzae = zzcVar;
    }

    public final void zza(zze zzeVar) {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Binding only allowed within app");
        }
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "service received new intent via bind strategy");
        }
        if (this.zzae.zzc(zzeVar.intent)) {
            zzeVar.finish();
            return;
        }
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "intent being queued for bg execution");
        }
        this.zzae.zzt.execute(new zzf(this, zzeVar));
    }
}
