package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzad extends zzz.zzb {
    private final /* synthetic */ String val$id;
    private final /* synthetic */ zzz zzaa;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzad(zzz zzzVar, String str) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.val$id = str;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setUserId(this.val$id, this.timestamp);
    }
}
