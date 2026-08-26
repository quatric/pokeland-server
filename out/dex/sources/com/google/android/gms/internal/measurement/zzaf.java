package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaf extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ boolean zzaz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaf(zzz zzzVar, boolean z) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzaz = z;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setMeasurementEnabled(this.zzaz, this.timestamp);
    }
}
