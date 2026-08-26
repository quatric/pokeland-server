package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zaw<O extends Api.ApiOptions> extends GoogleApi<O> {
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zacd;
    private final Api.Client zaeq;
    private final zaq zaer;
    private final ClientSettings zaes;

    public zaw(@NonNull Context context, Api<O> api, Looper looper, @NonNull Api.Client client, @NonNull zaq zaqVar, ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder) {
        super(context, api, looper);
        this.zaeq = client;
        this.zaer = zaqVar;
        this.zaes = clientSettings;
        this.zacd = abstractClientBuilder;
        this.zabm.zaa(this);
    }

    @Override // com.google.android.gms.common.api.GoogleApi
    public final Api.Client zaa(Looper looper, GoogleApiManager.zaa<O> zaaVar) {
        this.zaer.zaa(zaaVar);
        return this.zaeq;
    }

    @Override // com.google.android.gms.common.api.GoogleApi
    public final zace zaa(Context context, Handler handler) {
        return new zace(context, handler, this.zaes, this.zacd);
    }

    public final Api.Client zaab() {
        return this.zaeq;
    }
}
