package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zziy {
    final int tag;
    final byte[] zzado;

    zziy(int i, byte[] bArr) {
        this.tag = i;
        this.zzado = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zziy)) {
            return false;
        }
        zziy zziyVar = (zziy) obj;
        return this.tag == zziyVar.tag && Arrays.equals(this.zzado, zziyVar.zzado);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzado);
    }
}
