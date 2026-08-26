package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzad implements Runnable {
    private final /* synthetic */ zzgh zzfe;
    private final /* synthetic */ zzaa zzff;

    zzad(zzaa zzaaVar, zzgh zzghVar) {
        this.zzff = zzaaVar;
        this.zzfe = zzghVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzfe.zzae();
        if (zzr.isMainThread()) {
            this.zzfe.zzaa().zza(this);
            return;
        }
        boolean zZzcp = this.zzff.zzcp();
        zzaa.zza(this.zzff, 0L);
        if (zZzcp) {
            this.zzff.run();
        }
    }
}
