package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class zzem extends BroadcastReceiver {

    @VisibleForTesting
    private static final String zzky = "com.google.android.gms.measurement.internal.zzem";
    private final zzjg zzkz;
    private boolean zzla;
    private boolean zzlb;

    zzem(zzjg zzjgVar) {
        Preconditions.checkNotNull(zzjgVar);
        this.zzkz = zzjgVar;
    }

    @Override // android.content.BroadcastReceiver
    @MainThread
    public void onReceive(Context context, Intent intent) {
        this.zzkz.zzjj();
        String action = intent.getAction();
        this.zzkz.zzab().zzgs().zza("NetworkBroadcastReceiver received action", action);
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            this.zzkz.zzab().zzgn().zza("NetworkBroadcastReceiver received unknown action", action);
            return;
        }
        boolean zZzgv = this.zzkz.zzjf().zzgv();
        if (this.zzlb != zZzgv) {
            this.zzlb = zZzgv;
            this.zzkz.zzaa().zza(new zzep(this, zZzgv));
        }
    }

    @WorkerThread
    public final void unregister() {
        this.zzkz.zzjj();
        this.zzkz.zzaa().zzo();
        this.zzkz.zzaa().zzo();
        if (this.zzla) {
            this.zzkz.zzab().zzgs().zzao("Unregistering connectivity change receiver");
            this.zzla = false;
            this.zzlb = false;
            try {
                this.zzkz.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzkz.zzab().zzgk().zza("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    @WorkerThread
    public final void zzha() {
        this.zzkz.zzjj();
        this.zzkz.zzaa().zzo();
        if (this.zzla) {
            return;
        }
        this.zzkz.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.zzlb = this.zzkz.zzjf().zzgv();
        this.zzkz.zzab().zzgs().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzlb));
        this.zzla = true;
    }
}
