package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.MainThread;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zzex implements ServiceConnection {
    private final String packageName;
    final /* synthetic */ zzeu zzmt;

    zzex(zzeu zzeuVar, String str) {
        this.zzmt = zzeuVar;
        this.packageName = str;
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            this.zzmt.zzj.zzab().zzgn().zzao("Install Referrer connection returned with null binder");
            return;
        }
        try {
            com.google.android.gms.internal.measurement.zzf zzfVarZza = com.google.android.gms.internal.measurement.zze.zza(iBinder);
            if (zzfVarZza == null) {
                this.zzmt.zzj.zzab().zzgn().zzao("Install Referrer Service implementation was not found");
            } else {
                this.zzmt.zzj.zzab().zzgq().zzao("Install Referrer Service connected");
                this.zzmt.zzj.zzaa().zza(new zzew(this, zzfVarZza, this));
            }
        } catch (Exception e) {
            this.zzmt.zzj.zzab().zzgn().zza("Exception occurred while calling Install Referrer API", e);
        }
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        this.zzmt.zzj.zzab().zzgq().zzao("Install Referrer Service disconnected");
    }
}
