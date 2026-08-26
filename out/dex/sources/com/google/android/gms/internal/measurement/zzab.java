package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzab extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzau;
    private final /* synthetic */ String zzav;
    private final /* synthetic */ Bundle zzaw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzab(zzz zzzVar, String str, String str2, Bundle bundle) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzau = str;
        this.zzav = str2;
        this.zzaw = bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.clearConditionalUserProperty(this.zzau, this.zzav, this.zzaw);
    }
}
