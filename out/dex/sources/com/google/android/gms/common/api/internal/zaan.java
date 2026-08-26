package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaan extends zaau {
    final /* synthetic */ zaak zagi;
    private final Map<Api.Client, zaam> zagk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaan(zaak zaakVar, Map<Api.Client, zaam> map) {
        super(zaakVar, null);
        this.zagi = zaakVar;
        this.zagk = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaau
    @WorkerThread
    public final void zaan() {
        GoogleApiAvailabilityCache googleApiAvailabilityCache = new GoogleApiAvailabilityCache(this.zagi.zaex);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zagk.keySet()) {
            if (!client.requiresGooglePlayServices() || this.zagk.get(client).zaeb) {
                arrayList2.add(client);
            } else {
                arrayList.add(client);
            }
        }
        int clientAvailability = -1;
        int i = 0;
        if (!arrayList.isEmpty()) {
            ArrayList arrayList3 = arrayList;
            int size = arrayList3.size();
            while (i < size) {
                Object obj = arrayList3.get(i);
                i++;
                clientAvailability = googleApiAvailabilityCache.getClientAvailability(this.zagi.mContext, (Api.Client) obj);
                if (clientAvailability != 0) {
                    break;
                }
            }
        } else {
            ArrayList arrayList4 = arrayList2;
            int size2 = arrayList4.size();
            while (i < size2) {
                Object obj2 = arrayList4.get(i);
                i++;
                clientAvailability = googleApiAvailabilityCache.getClientAvailability(this.zagi.mContext, (Api.Client) obj2);
                if (clientAvailability == 0) {
                    break;
                }
            }
        }
        if (clientAvailability != 0) {
            this.zagi.zafs.zaa(new zaao(this, this.zagi, new ConnectionResult(clientAvailability, null)));
            return;
        }
        if (this.zagi.zagc) {
            this.zagi.zaga.connect();
        }
        for (Api.Client client2 : this.zagk.keySet()) {
            zaam zaamVar = this.zagk.get(client2);
            if (!client2.requiresGooglePlayServices() || googleApiAvailabilityCache.getClientAvailability(this.zagi.mContext, client2) == 0) {
                client2.connect(zaamVar);
            } else {
                this.zagi.zafs.zaa(new zaap(this, this.zagi, zaamVar));
            }
        }
    }
}
