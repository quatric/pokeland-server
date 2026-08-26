package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class zzdz extends zzdw {
    protected final byte[] zzado;

    zzdz(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException();
        }
        this.zzado = bArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdp) || size() != ((zzdp) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzdz)) {
            return obj.equals(this);
        }
        zzdz zzdzVar = (zzdz) obj;
        int iZzsc = zzsc();
        int iZzsc2 = zzdzVar.zzsc();
        if (iZzsc == 0 || iZzsc2 == 0 || iZzsc == iZzsc2) {
            return zza(zzdzVar, 0, size());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    public int size() {
        return this.zzado.length;
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    protected final int zza(int i, int i2, int i3) {
        return zzez.zza(i, this.zzado, zzsd(), i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    public final zzdp zza(int i, int i2) {
        int iZzb = zzb(0, i2, size());
        return iZzb == 0 ? zzdp.zzadh : new zzds(this.zzado, zzsd(), iZzb);
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    protected final String zza(Charset charset) {
        return new String(this.zzado, zzsd(), size(), charset);
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    final void zza(zzdm zzdmVar) throws IOException {
        zzdmVar.zza(this.zzado, zzsd(), size());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.internal.measurement.zzdw
    final boolean zza(zzdp zzdpVar, int i, int i2) {
        if (i2 > zzdpVar.size()) {
            int size = size();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i2 > zzdpVar.size()) {
            int size2 = zzdpVar.size();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (!(zzdpVar instanceof zzdz)) {
            return zzdpVar.zza(0, i2).equals(zza(0, i2));
        }
        zzdz zzdzVar = (zzdz) zzdpVar;
        byte[] bArr = this.zzado;
        byte[] bArr2 = zzdzVar.zzado;
        int iZzsd = zzsd() + i2;
        int iZzsd2 = zzsd();
        int iZzsd3 = zzdzVar.zzsd();
        while (iZzsd2 < iZzsd) {
            if (bArr[iZzsd2] != bArr2[iZzsd3]) {
                return false;
            }
            iZzsd2++;
            iZzsd3++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    public byte zzaq(int i) {
        return this.zzado[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    byte zzar(int i) {
        return this.zzado[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzdp
    public final boolean zzsb() {
        int iZzsd = zzsd();
        return zzhy.zzf(this.zzado, iZzsd, size() + iZzsd);
    }

    protected int zzsd() {
        return 0;
    }
}
