package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzaz extends zzz.zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ com.google.android.gms.measurement.internal.zzgn zzbk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaz(zzz zzzVar, com.google.android.gms.measurement.internal.zzgn zzgnVar) {
        super(zzzVar);
        this.zzaa = zzzVar;
        this.zzbk = zzgnVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzz.zzb
    final void zzf() throws RemoteException {
        Pair pair;
        int i = 0;
        while (true) {
            if (i >= this.zzaa.zzaf.size()) {
                pair = null;
                break;
            } else {
                if (this.zzbk.equals(((Pair) this.zzaa.zzaf.get(i)).first)) {
                    pair = (Pair) this.zzaa.zzaf.get(i);
                    break;
                }
                i++;
            }
        }
        if (pair == null) {
            Log.w(this.zzaa.zzu, "OnEventListener had not been registered.");
        } else {
            this.zzaa.zzar.unregisterOnMeasurementEventListener((zzq) pair.second);
            this.zzaa.zzaf.remove(pair);
        }
    }
}
