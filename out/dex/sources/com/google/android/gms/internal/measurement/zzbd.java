package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzbd extends zzz.zzb {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ zzz.zzc zzbw;
    private final /* synthetic */ Bundle zzbx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbd(zzz.zzc zzcVar, Activity activity, Bundle bundle) {
        super(zzz.this);
        this.zzbw = zzcVar;
        this.val$activity = activity;
        this.zzbx = bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        zzz.this.zzar.onActivityCreated(ObjectWrapper.wrap(this.val$activity), this.zzbx, this.zzbt);
    }
}
