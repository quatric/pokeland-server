package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class zag<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zai> {
    public zag(GoogleApiClient googleApiClient) {
        super(Common.API, googleApiClient);
    }
}
