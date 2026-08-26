package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzja implements Runnable {
    private final /* synthetic */ long zzcd;
    private final /* synthetic */ zziw zzsi;

    zzja(zziw zziwVar, long j) {
        this.zzsi = zziwVar;
        this.zzcd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzsi.zzab(this.zzcd);
    }
}
