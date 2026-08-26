package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzah extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ long zzba;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzah(zzz zzzVar, long j) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzba = j;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setMinimumSessionDuration(this.zzba);
    }
}
