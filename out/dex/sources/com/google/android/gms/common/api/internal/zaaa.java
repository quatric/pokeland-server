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
final class zaaa implements OnCompleteListener<Map<zai<?>, String>> {
    private final /* synthetic */ zax zafh;
    private SignInConnectionListener zafi;

    zaaa(zax zaxVar, SignInConnectionListener signInConnectionListener) {
        this.zafh = zaxVar;
        this.zafi = signInConnectionListener;
    }

    final void cancel() {
        this.zafi.onComplete();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task<Map<zai<?>, String>> task) {
        this.zafh.zaen.lock();
        try {
            if (!this.zafh.zafc) {
                this.zafi.onComplete();
                this.zafh.zaen.unlock();
                return;
            }
            if (task.isSuccessful()) {
                this.zafh.zafe = new ArrayMap(this.zafh.zaeu.size());
                Iterator it = this.zafh.zaeu.values().iterator();
                while (it.hasNext()) {
                    this.zafh.zafe.put(((zaw) it.next()).zak(), ConnectionResult.RESULT_SUCCESS);
                }
            } else if (task.getException() instanceof AvailabilityException) {
                AvailabilityException availabilityException = (AvailabilityException) task.getException();
                if (this.zafh.zafa) {
                    this.zafh.zafe = new ArrayMap(this.zafh.zaeu.size());
                    for (zaw zawVar : this.zafh.zaeu.values()) {
                        Object objZak = zawVar.zak();
                        ConnectionResult connectionResult = availabilityException.getConnectionResult(zawVar);
                        if (this.zafh.zaa((zaw<?>) zawVar, connectionResult)) {
                            this.zafh.zafe.put(objZak, new ConnectionResult(16));
                        } else {
                            this.zafh.zafe.put(objZak, connectionResult);
                        }
                    }
                } else {
                    this.zafh.zafe = availabilityException.zaj();
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                this.zafh.zafe = Collections.emptyMap();
            }
            if (this.zafh.isConnected()) {
                this.zafh.zafd.putAll(this.zafh.zafe);
                if (this.zafh.zaaf() == null) {
                    this.zafh.zaad();
                    this.zafh.zaae();
                    this.zafh.zaey.signalAll();
                }
            }
            this.zafi.onComplete();
            this.zafh.zaen.unlock();
        } catch (Throwable th) {
            this.zafh.zaen.unlock();
            throw th;
        }
    }
}
