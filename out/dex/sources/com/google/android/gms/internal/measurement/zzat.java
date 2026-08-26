package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzat extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;
    private final /* synthetic */ Bundle zzbj;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzat(zzz zzzVar, Bundle bundle, zzl zzlVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzbj = bundle;
        this.zzat = zzlVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.performAction(this.zzbj, this.zzat, this.timestamp);
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    protected final void zzk() {
        this.zzat.zzb((Bundle) null);
    }
}
