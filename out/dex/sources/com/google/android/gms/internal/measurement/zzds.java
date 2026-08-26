package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzds extends zzdz {
    private final int zzadl;
    private final int zzadm;

    zzds(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzadl = i;
        this.zzadm = i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzdz, com.google.android.gms.internal.measurement.zzdp
    public final int size() {
        return this.zzadm;
    }

    @Override // com.google.android.gms.internal.measurement.zzdz, com.google.android.gms.internal.measurement.zzdp
    public final byte zzaq(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzado[this.zzadl + i];
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(22);
            sb.append("Index < 0: ");
            sb.append(i);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Index > length: ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(size);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    @Override // com.google.android.gms.internal.measurement.zzdz, com.google.android.gms.internal.measurement.zzdp
    final byte zzar(int i) {
        return this.zzado[this.zzadl + i];
    }

    @Override // com.google.android.gms.internal.measurement.zzdz
    protected final int zzsd() {
        return this.zzadl;
    }
}
