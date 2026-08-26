package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzba extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ Bundle zzbj;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzba(zzz zzzVar, Bundle bundle) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzbj = bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setConditionalUserProperty(this.zzbj, this.timestamp);
    }
}
