package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzbb extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzbn;
    private final /* synthetic */ boolean zzbp;
    private final /* synthetic */ Object zzbr;
    private final /* synthetic */ String zzx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbb(zzz zzzVar, String str, String str2, Object obj, boolean z) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzx = str;
        this.zzbn = str2;
        this.zzbr = obj;
        this.zzbp = z;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.setUserProperty(this.zzx, this.zzbn, ObjectWrapper.wrap(this.zzbr), this.zzbp, this.timestamp);
    }
}
