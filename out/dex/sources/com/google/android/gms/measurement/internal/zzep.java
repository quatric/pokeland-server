package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzep implements Runnable {
    private final /* synthetic */ boolean zzmf;
    private final /* synthetic */ zzem zzmg;

    zzep(zzem zzemVar, boolean z) {
        this.zzmg = zzemVar;
        this.zzmf = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzmg.zzkz.zzj(this.zzmf);
    }
}
