package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzbg extends zzz.zzb {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ zzl zzat;
    private final /* synthetic */ zzz.zzc zzbw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbg(zzz.zzc zzcVar, Activity activity, zzl zzlVar) {
        super(zzz.this);
        this.zzbw = zzcVar;
        this.val$activity = activity;
        this.zzat = zzlVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        zzz.this.zzar.onActivitySaveInstanceState(ObjectWrapper.wrap(this.val$activity), this.zzat, this.zzbt);
    }
}
