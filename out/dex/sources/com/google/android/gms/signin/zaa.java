package com.google.android.gms.signin;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.signin.internal.SignInClientImpl;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class zaa {
    private static final Api.ClientKey<SignInClientImpl> CLIENT_KEY = new Api.ClientKey<>();

    @ShowFirstParty
    private static final Api.ClientKey<SignInClientImpl> zarp = new Api.ClientKey<>();
    public static final Api.AbstractClientBuilder<SignInClientImpl, SignInOptions> zapg = new zab();
    private static final Api.AbstractClientBuilder<SignInClientImpl, Object> zarq = new zac();
    private static final Scope zar = new Scope(Scopes.PROFILE);
    private static final Scope zas = new Scope("email");
    public static final Api<SignInOptions> API = new Api<>("SignIn.API", zapg, CLIENT_KEY);
    private static final Api<Object> zarr = new Api<>("SignIn.INTERNAL_API", zarq, zarp);
}
