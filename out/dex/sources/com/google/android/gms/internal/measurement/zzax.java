package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzax extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;
    private final /* synthetic */ int zzbl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzax(zzz zzzVar, zzl zzlVar, int i) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzat = zzlVar;
        this.zzbl = i;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.getTestFlag(this.zzat, this.zzbl);
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    protected final void zzk() {
        this.zzat.zzb((Bundle) null);
    }
}
