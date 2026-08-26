package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzao extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzao(zzz zzzVar, zzl zzlVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzat = zzlVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.getCurrentScreenClass(this.zzat);
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    protected final void zzk() {
        this.zzat.zzb((Bundle) null);
    }
}
