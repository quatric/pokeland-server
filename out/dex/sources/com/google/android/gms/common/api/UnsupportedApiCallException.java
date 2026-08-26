package com.google.android.gms.common.api;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class UnsupportedApiCallException extends UnsupportedOperationException {
    private final Feature zzas;

    @KeepForSdk
    public UnsupportedApiCallException(Feature feature) {
        this.zzas = feature;
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        String strValueOf = String.valueOf(this.zzas);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 8);
        sb.append("Missing ");
        sb.append(strValueOf);
        return sb.toString();
    }
}
