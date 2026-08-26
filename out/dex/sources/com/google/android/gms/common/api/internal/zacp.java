package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zacp {
    public static final Status zakw = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zakx = new BasePendingResult[0];
    private final Map<Api.AnyClientKey<?>, Api.Client> zagy;

    @VisibleForTesting
    final Set<BasePendingResult<?>> zaky = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zacs zakz = new zacq(this);

    public zacp(Map<Api.AnyClientKey<?>, Api.Client> map) {
        this.zagy = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void release() {
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.zaky.toArray(zakx)) {
            com.google.android.gms.common.api.zac zacVar = null;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            Object[] objArr3 = 0;
            Object[] objArr4 = 0;
            com.google.android.gms.common.api.zac zacVar2 = null;
            basePendingResult.zaa((zacs) null);
            if (basePendingResult.zam() != null) {
                basePendingResult.setResultCallback(null);
                IBinder serviceBrokerBinder = this.zagy.get(((BaseImplementation.ApiMethodImpl) basePendingResult).getClientKey()).getServiceBrokerBinder();
                if (basePendingResult.isReady()) {
                    basePendingResult.zaa(new zacr(basePendingResult, objArr4 == true ? 1 : 0, serviceBrokerBinder, objArr3 == true ? 1 : 0));
                } else if (serviceBrokerBinder == null || !serviceBrokerBinder.isBinderAlive()) {
                    basePendingResult.zaa((zacs) null);
                    basePendingResult.cancel();
                    zacVar.remove(basePendingResult.zam().intValue());
                } else {
                    zacr zacrVar = new zacr(basePendingResult, objArr2 == true ? 1 : 0, serviceBrokerBinder, objArr == true ? 1 : 0);
                    basePendingResult.zaa(zacrVar);
                    try {
                        serviceBrokerBinder.linkToDeath(zacrVar, 0);
                    } catch (RemoteException unused) {
                        basePendingResult.cancel();
                        zacVar2.remove(basePendingResult.zam().intValue());
                    }
                }
                this.zaky.remove(basePendingResult);
            } else if (basePendingResult.zat()) {
                this.zaky.remove(basePendingResult);
            }
        }
    }

    final void zab(BasePendingResult<? extends Result> basePendingResult) {
        this.zaky.add(basePendingResult);
        basePendingResult.zaa(this.zakz);
    }

    public final void zabx() {
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.zaky.toArray(zakx)) {
            basePendingResult.zab(zakw);
        }
    }
}
