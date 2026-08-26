package com.google.android.gms.measurement.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zzaa {
    private static volatile Handler handler;
    private final zzgh zzev;
    private final Runnable zzew;
    private volatile long zzex;

    zzaa(zzgh zzghVar) {
        Preconditions.checkNotNull(zzghVar);
        this.zzev = zzghVar;
        this.zzew = new zzad(this, zzghVar);
    }

    private final Handler getHandler() {
        Handler handler2;
        if (handler != null) {
            return handler;
        }
        synchronized (zzaa.class) {
            if (handler == null) {
                handler = new com.google.android.gms.internal.measurement.zzh(this.zzev.getContext().getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }

    static /* synthetic */ long zza(zzaa zzaaVar, long j) {
        zzaaVar.zzex = 0L;
        return 0L;
    }

    final void cancel() {
        this.zzex = 0L;
        getHandler().removeCallbacks(this.zzew);
    }

    public abstract void run();

    public final boolean zzcp() {
        return this.zzex != 0;
    }

    public final void zzv(long j) {
        cancel();
        if (j >= 0) {
            this.zzex = this.zzev.zzx().currentTimeMillis();
            if (getHandler().postDelayed(this.zzew, j)) {
                return;
            }
            this.zzev.zzab().zzgk().zza("Failed to schedule delayed post. time", Long.valueOf(j));
        }
    }
}
