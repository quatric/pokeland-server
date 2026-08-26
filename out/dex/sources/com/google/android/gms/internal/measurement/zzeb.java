package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzeb {
    int zzadp;
    int zzadq;
    private int zzadr;
    zzec zzads;
    private boolean zzadt;

    private zzeb() {
        this.zzadq = 100;
        this.zzadr = Integer.MAX_VALUE;
        this.zzadt = false;
    }

    static zzeb zza(byte[] bArr, int i, int i2, boolean z) {
        zzed zzedVar = new zzed(bArr, i, i2, false);
        try {
            zzedVar.zzaw(i2);
            return zzedVar;
        } catch (zzfi e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzaz(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long zzbm(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public static zzeb zzd(byte[] bArr, int i, int i2) {
        return zza(bArr, i, i2, false);
    }

    public abstract double readDouble() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract String readString() throws IOException;

    public abstract <T extends zzgi> T zza(zzgr<T> zzgrVar, zzel zzelVar) throws IOException;

    public abstract void zzat(int i) throws zzfi;

    public abstract boolean zzau(int i) throws IOException;

    public final int zzav(int i) {
        if (i >= 0) {
            int i2 = this.zzadq;
            this.zzadq = i;
            return i2;
        }
        StringBuilder sb = new StringBuilder(47);
        sb.append("Recursion limit cannot be negative: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public abstract int zzaw(int i) throws zzfi;

    public abstract void zzax(int i);

    public abstract void zzay(int i) throws IOException;

    public abstract int zzsg() throws IOException;

    public abstract long zzsh() throws IOException;

    public abstract long zzsi() throws IOException;

    public abstract int zzsj() throws IOException;

    public abstract long zzsk() throws IOException;

    public abstract int zzsl() throws IOException;

    public abstract boolean zzsm() throws IOException;

    public abstract String zzsn() throws IOException;

    public abstract zzdp zzso() throws IOException;

    public abstract int zzsp() throws IOException;

    public abstract int zzsq() throws IOException;

    public abstract int zzsr() throws IOException;

    public abstract long zzss() throws IOException;

    public abstract int zzst() throws IOException;

    public abstract long zzsu() throws IOException;

    abstract long zzsv() throws IOException;

    public abstract boolean zzsw() throws IOException;

    public abstract int zzsx();
}
