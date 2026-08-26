package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhs implements Runnable {
    private final /* synthetic */ zzhr zzqy;
    private final /* synthetic */ zzhq zzqz;

    zzhs(zzhq zzhqVar, zzhr zzhrVar) {
        this.zzqz = zzhqVar;
        this.zzqy = zzhrVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzqz.zza(this.zzqy, false);
        zzhq zzhqVar = this.zzqz;
        zzhqVar.zzqo = null;
        zzhqVar.zzs().zza((zzhr) null);
    }
}
