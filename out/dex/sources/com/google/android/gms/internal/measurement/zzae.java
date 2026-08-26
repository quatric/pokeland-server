package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzae extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzae(zzz zzzVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.resetAnalyticsData(this.timestamp);
    }
}
