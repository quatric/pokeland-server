package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzdt implements zzdv {
    private zzdt() {
    }

    /* synthetic */ zzdt(zzdo zzdoVar) {
        this();
    }

    @Override // com.google.android.gms.internal.measurement.zzdv
    public final byte[] zzc(byte[] bArr, int i, int i2) {
        return Arrays.copyOfRange(bArr, i, i2 + i);
    }
}
