package com.google.android.gms.common.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GoogleApiAvailabilityCache {
    private final SparseIntArray zaor;
    private GoogleApiAvailabilityLight zaos;

    public GoogleApiAvailabilityCache() {
        this(GoogleApiAvailability.getInstance());
    }

    public GoogleApiAvailabilityCache(@NonNull GoogleApiAvailabilityLight googleApiAvailabilityLight) {
        this.zaor = new SparseIntArray();
        Preconditions.checkNotNull(googleApiAvailabilityLight);
        this.zaos = googleApiAvailabilityLight;
    }

    public void flush() {
        this.zaor.clear();
    }

    public int getClientAvailability(@NonNull Context context, @NonNull Api.Client client) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(client);
        if (!client.requiresGooglePlayServices()) {
            return 0;
        }
        int minApkVersion = client.getMinApkVersion();
        int iIsGooglePlayServicesAvailable = this.zaor.get(minApkVersion, -1);
        if (iIsGooglePlayServicesAvailable != -1) {
            return iIsGooglePlayServicesAvailable;
        }
        for (int i = 0; i < this.zaor.size(); i++) {
            int iKeyAt = this.zaor.keyAt(i);
            if (iKeyAt > minApkVersion && this.zaor.get(iKeyAt) == 0) {
                iIsGooglePlayServicesAvailable = 0;
                break;
            }
        }
        if (iIsGooglePlayServicesAvailable == -1) {
            iIsGooglePlayServicesAvailable = this.zaos.isGooglePlayServicesAvailable(context, minApkVersion);
        }
        this.zaor.put(minApkVersion, iIsGooglePlayServicesAvailable);
        return iIsGooglePlayServicesAvailable;
    }
}
