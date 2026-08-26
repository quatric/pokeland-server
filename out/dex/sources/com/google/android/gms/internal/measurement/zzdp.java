package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class zzdp implements Serializable, Iterable<Byte> {
    public static final zzdp zzadh = new zzdz(zzez.zzair);
    private static final zzdv zzadi;
    private static final Comparator<zzdp> zzadk;
    private int zzadj = 0;

    static {
        zzdo zzdoVar = null;
        zzadi = zzdi.zzrv() ? new zzdy(zzdoVar) : new zzdt(zzdoVar);
        zzadk = new zzdr();
    }

    zzdp() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zza(byte b) {
        return b & 255;
    }

    static zzdx zzas(int i) {
        return new zzdx(i, null);
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }

    public static zzdp zzb(byte[] bArr, int i, int i2) {
        zzb(i, i + i2, bArr.length);
        return new zzdz(zzadi.zzc(bArr, i, i2));
    }

    public static zzdp zzdq(String str) {
        return new zzdz(str.getBytes(zzez.UTF_8));
    }

    static zzdp zze(byte[] bArr) {
        return new zzdz(bArr);
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int iZza = this.zzadj;
        if (iZza == 0) {
            int size = size();
            iZza = zza(size, 0, size);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzadj = iZza;
        }
        return iZza;
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzdo(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
    }

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzdp zza(int i, int i2);

    protected abstract String zza(Charset charset);

    abstract void zza(zzdm zzdmVar) throws IOException;

    public abstract byte zzaq(int i);

    abstract byte zzar(int i);

    public final String zzsa() {
        return size() == 0 ? "" : zza(zzez.UTF_8);
    }

    public abstract boolean zzsb();

    protected final int zzsc() {
        return this.zzadj;
    }
}
