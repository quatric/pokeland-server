package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zze implements Runnable {
    private final /* synthetic */ zza zzcc;
    private final /* synthetic */ long zzcd;

    zze(zza zzaVar, long j) {
        this.zzcc = zzaVar;
        this.zzcd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzcc.zzd(this.zzcd);
    }
}
