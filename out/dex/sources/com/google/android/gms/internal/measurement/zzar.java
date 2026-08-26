package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzar extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzas;
    private final /* synthetic */ zzl zzat;
    private final /* synthetic */ boolean zzbi;
    private final /* synthetic */ String zzx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzar(zzz zzzVar, String str, String str2, boolean z, zzl zzlVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzx = str;
        this.zzas = str2;
        this.zzbi = z;
        this.zzat = zzlVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.getUserProperties(this.zzx, this.zzas, this.zzbi, this.zzat);
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    protected final void zzk() {
        this.zzat.zzb((Bundle) null);
    }
}
