package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaq extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzbe;
    private final /* synthetic */ Object zzbf;
    private final /* synthetic */ int zzbd = 5;
    private final /* synthetic */ Object zzbg = null;
    private final /* synthetic */ Object zzbh = null;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaq(zzz zzzVar, boolean z, int i, String str, Object obj, Object obj2, Object obj3) {
        super(false);
        this.zzaa = zzzVar;
        this.zzbe = str;
        this.zzbf = obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        this.zzaa.zzar.logHealthData(this.zzbd, this.zzbe, ObjectWrapper.wrap(this.zzbf), ObjectWrapper.wrap(this.zzbg), ObjectWrapper.wrap(this.zzbh));
    }
}
