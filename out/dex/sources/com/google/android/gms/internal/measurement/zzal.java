package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzal extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ com.google.android.gms.measurement.internal.zzgk zzbc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzal(zzz zzzVar, com.google.android.gms.measurement.internal.zzgk zzgkVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzbc = zzgkVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setEventInterceptor(new zzz.zza(this.zzbc));
    }
}
