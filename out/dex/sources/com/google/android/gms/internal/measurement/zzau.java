package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzau extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ com.google.android.gms.measurement.internal.zzgn zzbk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzau(zzz zzzVar, com.google.android.gms.measurement.internal.zzgn zzgnVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzbk = zzgnVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        for (int i = 0; i < this.zzaa.zzaf.size(); i++) {
            if (this.zzbk.equals(((Pair) this.zzaa.zzaf.get(i)).first)) {
                Log.w(this.zzaa.zzu, "OnEventListener already registered.");
                return;
            }
        }
        zzz.zzd zzdVar = new zzz.zzd(this.zzbk);
        this.zzaa.zzaf.add(new Pair(this.zzbk, zzdVar));
        this.zzaa.zzar.registerOnMeasurementEventListener(zzdVar);
    }
}
