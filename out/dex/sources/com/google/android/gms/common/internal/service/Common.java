package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class Common {

    @KeepForSdk
    public static final Api.ClientKey<zai> CLIENT_KEY = new Api.ClientKey<>();
    private static final Api.AbstractClientBuilder<zai, Api.ApiOptions.NoOptions> zapg = new zab();

    @KeepForSdk
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Common.API", zapg, CLIENT_KEY);
    public static final zac zaph = new zad();
}
