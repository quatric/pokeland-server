package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaa extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzas;
    private final /* synthetic */ zzl zzat;
    private final /* synthetic */ String zzx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaa(zzz zzzVar, String str, String str2, zzl zzlVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzx = str;
        this.zzas = str2;
        this.zzat = zzlVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.getConditionalUserProperties(this.zzx, this.zzas, this.zzat);
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    protected final void zzk() {
        this.zzat.zzb((Bundle) null);
    }
}
