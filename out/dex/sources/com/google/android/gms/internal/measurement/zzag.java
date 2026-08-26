package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzag extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ long zzba;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzag(zzz zzzVar, long j) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzba = j;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setSessionTimeoutDuration(this.zzba);
    }
}
