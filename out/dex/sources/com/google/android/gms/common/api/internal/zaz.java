package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaz implements OnCompleteListener<Map<zai<?>, String>> {
    private final /* synthetic */ zax zafh;

    private zaz(zax zaxVar) {
        this.zafh = zaxVar;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task<Map<zai<?>, String>> task) {
        this.zafh.zaen.lock();
        try {
            if (this.zafh.zafc) {
                if (task.isSuccessful()) {
                    this.zafh.zafd = new ArrayMap(this.zafh.zaet.size());
                    Iterator it = this.zafh.zaet.values().iterator();
                    while (it.hasNext()) {
                        this.zafh.zafd.put(((zaw) it.next()).zak(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zafh.zafa) {
                        this.zafh.zafd = new ArrayMap(this.zafh.zaet.size());
                        for (zaw zawVar : this.zafh.zaet.values()) {
                            Object objZak = zawVar.zak();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult(zawVar);
                            if (this.zafh.zaa((zaw<?>) zawVar, connectionResult)) {
                                this.zafh.zafd.put(objZak, new ConnectionResult(16));
                            } else {
                                this.zafh.zafd.put(objZak, connectionResult);
                            }
                        }
                    } else {
                        this.zafh.zafd = availabilityException.zaj();
                    }
                    this.zafh.zafg = this.zafh.zaaf();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zafh.zafd = Collections.emptyMap();
                    this.zafh.zafg = new ConnectionResult(8);
                }
                if (this.zafh.zafe != null) {
                    this.zafh.zafd.putAll(this.zafh.zafe);
                    this.zafh.zafg = this.zafh.zaaf();
                }
                if (this.zafh.zafg == null) {
                    this.zafh.zaad();
                    this.zafh.zaae();
                } else {
                    zax.zaa(this.zafh, false);
                    this.zafh.zaew.zac(this.zafh.zafg);
                }
                this.zafh.zaey.signalAll();
            }
        } finally {
            this.zafh.zaen.unlock();
        }
    }
}
