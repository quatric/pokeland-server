package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zaat implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ zaak zagi;

    private zaat(zaak zaakVar) {
        this.zagi = zaakVar;
    }

    /* synthetic */ zaat(zaak zaakVar, zaal zaalVar) {
        this(zaakVar);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public final void onConnected(Bundle bundle) {
        this.zagi.zaga.zaa(new zaar(this.zagi));
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zagi.zaen.lock();
        try {
            if (this.zagi.zad(connectionResult)) {
                this.zagi.zaar();
                this.zagi.zaap();
            } else {
                this.zagi.zae(connectionResult);
            }
        } finally {
            this.zagi.zaen.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public final void onConnectionSuspended(int i) {
    }
}
