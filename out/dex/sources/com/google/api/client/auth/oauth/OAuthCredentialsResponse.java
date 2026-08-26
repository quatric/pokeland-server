package com.google.api.client.auth.oauth;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class OAuthCredentialsResponse {

    @Key("oauth_callback_confirmed")
    public Boolean callbackConfirmed;

    @Key("oauth_token")
    public String token;

    @Key("oauth_token_secret")
    public String tokenSecret;
}
