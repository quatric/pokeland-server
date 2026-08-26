package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zacr implements IBinder.DeathRecipient, zacs {
    private final WeakReference<BasePendingResult<?>> zalb;
    private final WeakReference<com.google.android.gms.common.api.zac> zalc;
    private final WeakReference<IBinder> zald;

    private zacr(BasePendingResult<?> basePendingResult, com.google.android.gms.common.api.zac zacVar, IBinder iBinder) {
        this.zalc = new WeakReference<>(zacVar);
        this.zalb = new WeakReference<>(basePendingResult);
        this.zald = new WeakReference<>(iBinder);
    }

    /* synthetic */ zacr(BasePendingResult basePendingResult, com.google.android.gms.common.api.zac zacVar, IBinder iBinder, zacq zacqVar) {
        this(basePendingResult, null, iBinder);
    }

    private final void zaby() {
        BasePendingResult<?> basePendingResult = this.zalb.get();
        com.google.android.gms.common.api.zac zacVar = this.zalc.get();
        if (zacVar != null && basePendingResult != null) {
            zacVar.remove(basePendingResult.zam().intValue());
        }
        IBinder iBinder = this.zald.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        zaby();
    }

    @Override // com.google.android.gms.common.api.internal.zacs
    public final void zac(BasePendingResult<?> basePendingResult) {
        zaby();
    }
}
