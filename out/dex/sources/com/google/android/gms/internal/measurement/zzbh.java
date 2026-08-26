package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzbh extends zzz.zzb {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ zzz.zzc zzbw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbh(zzz.zzc zzcVar, Activity activity) {
        super(zzz.this);
        this.zzbw = zzcVar;
        this.val$activity = activity;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        zzz.this.zzar.onActivityStopped(ObjectWrapper.wrap(this.val$activity), this.zzbt);
    }
}
