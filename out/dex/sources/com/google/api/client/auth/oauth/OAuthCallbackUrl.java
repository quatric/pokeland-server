package com.google.api.client.auth.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class OAuthCallbackUrl extends GenericUrl {

    @Key("oauth_token")
    public String token;

    @Key("oauth_verifier")
    public String verifier;

    public OAuthCallbackUrl(String str) {
        super(str);
    }
}
