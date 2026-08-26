package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.SignInOptions;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zace extends com.google.android.gms.signin.internal.zac implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zakh = com.google.android.gms.signin.zaa.zapg;
    private final Context mContext;
    private final Handler mHandler;
    private Set<Scope> mScopes;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zaau;
    private ClientSettings zaes;
    private com.google.android.gms.signin.zad zaga;
    private zach zaki;

    @WorkerThread
    public zace(Context context, Handler handler, @NonNull ClientSettings clientSettings) {
        this(context, handler, clientSettings, zakh);
    }

    @WorkerThread
    public zace(Context context, Handler handler, @NonNull ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder) {
        this.mContext = context;
        this.mHandler = handler;
        this.zaes = (ClientSettings) Preconditions.checkNotNull(clientSettings, "ClientSettings must not be null");
        this.mScopes = clientSettings.getRequiredScopes();
        this.zaau = abstractClientBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zac(com.google.android.gms.signin.internal.zaj zajVar) {
        ConnectionResult connectionResult = zajVar.getConnectionResult();
        if (connectionResult.isSuccess()) {
            ResolveAccountResponse resolveAccountResponseZacw = zajVar.zacw();
            ConnectionResult connectionResult2 = resolveAccountResponseZacw.getConnectionResult();
            if (!connectionResult2.isSuccess()) {
                String strValueOf = String.valueOf(connectionResult2);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 48);
                sb.append("Sign-in succeeded with resolve account failure: ");
                sb.append(strValueOf);
                Log.wtf("SignInCoordinator", sb.toString(), new Exception());
                this.zaki.zag(connectionResult2);
                this.zaga.disconnect();
                return;
            }
            this.zaki.zaa(resolveAccountResponseZacw.getAccountAccessor(), this.mScopes);
        } else {
            this.zaki.zag(connectionResult);
        }
        this.zaga.disconnect();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zaga.zaa(this);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zaki.zag(connectionResult);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zaga.disconnect();
    }

    @WorkerThread
    public final void zaa(zach zachVar) {
        com.google.android.gms.signin.zad zadVar = this.zaga;
        if (zadVar != null) {
            zadVar.disconnect();
        }
        this.zaes.setClientSessionId(Integer.valueOf(System.identityHashCode(this)));
        Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder = this.zaau;
        Context context = this.mContext;
        Looper looper = this.mHandler.getLooper();
        ClientSettings clientSettings = this.zaes;
        this.zaga = (com.google.android.gms.signin.zad) abstractClientBuilder.buildClient(context, looper, clientSettings, clientSettings.getSignInOptions(), this, this);
        this.zaki = zachVar;
        Set<Scope> set = this.mScopes;
        if (set == null || set.isEmpty()) {
            this.mHandler.post(new zacf(this));
        } else {
            this.zaga.connect();
        }
    }

    @Override // com.google.android.gms.signin.internal.zac, com.google.android.gms.signin.internal.zad
    @BinderThread
    public final void zab(com.google.android.gms.signin.internal.zaj zajVar) {
        this.mHandler.post(new zacg(this, zajVar));
    }

    public final com.google.android.gms.signin.zad zabq() {
        return this.zaga;
    }

    public final void zabs() {
        com.google.android.gms.signin.zad zadVar = this.zaga;
        if (zadVar != null) {
            zadVar.disconnect();
        }
    }
}
