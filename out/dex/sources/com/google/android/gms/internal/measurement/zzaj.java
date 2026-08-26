package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaj extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzbb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaj(zzz zzzVar, String str) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzbb = str;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.beginAdUnitExposure(this.zzbb, this.zzbt);
    }
}
