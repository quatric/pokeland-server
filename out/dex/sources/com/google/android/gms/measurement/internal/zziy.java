package com.google.android.gms.measurement.internal;

import android.support.annotation.WorkerThread;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zziy extends zzaa {
    private final /* synthetic */ zziw zzsi;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zziy(zziw zziwVar, zzgh zzghVar) {
        super(zzghVar);
        this.zzsi = zziwVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaa
    @WorkerThread
    public final void run() {
        this.zzsi.zzjc();
    }
}
