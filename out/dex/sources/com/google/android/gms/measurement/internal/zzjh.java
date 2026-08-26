package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zzjh extends zzje {
    private boolean zzdh;

    zzjh(zzjg zzjgVar) {
        super(zzjgVar);
        this.zzkz.zzb(this);
    }

    public final void initialize() {
        if (this.zzdh) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzbk();
        this.zzkz.zzjs();
        this.zzdh = true;
    }

    final boolean isInitialized() {
        return this.zzdh;
    }

    protected final void zzbi() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    protected abstract boolean zzbk();
}
