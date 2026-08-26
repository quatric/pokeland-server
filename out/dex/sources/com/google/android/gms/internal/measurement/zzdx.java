package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdx {
    private final byte[] buffer;
    private final zzee zzadn;

    private zzdx(int i) {
        this.buffer = new byte[i];
        this.zzadn = zzee.zzf(this.buffer);
    }

    /* synthetic */ zzdx(int i, zzdo zzdoVar) {
        this(i);
    }

    public final zzdp zzse() {
        this.zzadn.zzth();
        return new zzdz(this.buffer);
    }

    public final zzee zzsf() {
        return this.zzadn;
    }
}
