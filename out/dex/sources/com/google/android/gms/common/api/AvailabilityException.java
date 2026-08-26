package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class AvailabilityException extends Exception {
    private final ArrayMap<zai<?>, ConnectionResult> zaay;

    public AvailabilityException(ArrayMap<zai<?>, ConnectionResult> arrayMap) {
        this.zaay = arrayMap;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends Api.ApiOptions> googleApi) {
        Object objZak = googleApi.zak();
        Preconditions.checkArgument(this.zaay.get(objZak) != null, "The given API was not part of the availability request.");
        return this.zaay.get(objZak);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (zai<?> zaiVar : this.zaay.keySet()) {
            ConnectionResult connectionResult = this.zaay.get(zaiVar);
            if (connectionResult.isSuccess()) {
                z = false;
            }
            String strZan = zaiVar.zan();
            String strValueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(strZan).length() + 2 + String.valueOf(strValueOf).length());
            sb.append(strZan);
            sb.append(": ");
            sb.append(strValueOf);
            arrayList.add(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        if (z) {
            sb2.append("None of the queried APIs are available. ");
        } else {
            sb2.append("Some of the queried APIs are unavailable. ");
        }
        sb2.append(TextUtils.join("; ", arrayList));
        return sb2.toString();
    }

    public final ArrayMap<zai<?>, ConnectionResult> zaj() {
        return this.zaay;
    }
}
