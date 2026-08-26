package com.google.api.client.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class TokenResponse extends GenericJson {

    @Key("access_token")
    private String accessToken;

    @Key("expires_in")
    private Long expiresInSeconds;

    @Key("refresh_token")
    private String refreshToken;

    @Key
    private String scope;

    @Key("token_type")
    private String tokenType;

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public TokenResponse clone() {
        return (TokenResponse) super.clone();
    }

    public final String getAccessToken() {
        return this.accessToken;
    }

    public final Long getExpiresInSeconds() {
        return this.expiresInSeconds;
    }

    public final String getRefreshToken() {
        return this.refreshToken;
    }

    public final String getScope() {
        return this.scope;
    }

    public final String getTokenType() {
        return this.tokenType;
    }

    @Override // com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public TokenResponse set(String str, Object obj) {
        return (TokenResponse) super.set(str, obj);
    }

    public TokenResponse setAccessToken(String str) {
        this.accessToken = (String) Preconditions.checkNotNull(str);
        return this;
    }

    public TokenResponse setExpiresInSeconds(Long l) {
        this.expiresInSeconds = l;
        return this;
    }

    public TokenResponse setRefreshToken(String str) {
        this.refreshToken = str;
        return this;
    }

    public TokenResponse setScope(String str) {
        this.scope = str;
        return this;
    }

    public TokenResponse setTokenType(String str) {
        this.tokenType = (String) Preconditions.checkNotNull(str);
        return this;
    }
}
