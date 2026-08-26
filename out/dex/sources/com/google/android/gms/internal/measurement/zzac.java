package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzac extends zzz.zzb {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzax;
    private final /* synthetic */ String zzay;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(zzz zzzVar, Activity activity, String str, String str2) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.val$activity = activity;
        this.zzax = str;
        this.zzay = str2;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setCurrentScreen(ObjectWrapper.wrap(this.val$activity), this.zzax, this.zzay, this.timestamp);
    }
}
