package com.google.android.gms.measurement.internal;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zzge extends zzgf {
    private boolean zzdh;

    zzge(zzfj zzfjVar) {
        super(zzfjVar);
        this.zzj.zzb(this);
    }

    public final void initialize() {
        if (this.zzdh) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (zzbk()) {
            return;
        }
        this.zzj.zzid();
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

    public final void zzbj() {
        if (this.zzdh) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzbl();
        this.zzj.zzid();
        this.zzdh = true;
    }

    protected abstract boolean zzbk();

    protected void zzbl() {
    }
}
