package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GoogleTokenResponse extends TokenResponse {

    @Key("id_token")
    private String idToken;

    @Override // com.google.api.client.auth.oauth2.TokenResponse, com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData, java.util.AbstractMap
    public GoogleTokenResponse clone() {
        return (GoogleTokenResponse) super.clone();
    }

    @Beta
    public final String getIdToken() {
        return this.idToken;
    }

    @Beta
    public GoogleIdToken parseIdToken() throws IOException {
        return GoogleIdToken.parse(getFactory(), getIdToken());
    }

    @Override // com.google.api.client.auth.oauth2.TokenResponse, com.google.api.client.json.GenericJson, com.google.api.client.util.GenericData
    public GoogleTokenResponse set(String str, Object obj) {
        return (GoogleTokenResponse) super.set(str, obj);
    }

    @Override // com.google.api.client.auth.oauth2.TokenResponse
    public GoogleTokenResponse setAccessToken(String str) {
        return (GoogleTokenResponse) super.setAccessToken(str);
    }

    @Override // com.google.api.client.auth.oauth2.TokenResponse
    public GoogleTokenResponse setExpiresInSeconds(Long l) {
        return (GoogleTokenResponse) super.setExpiresInSeconds(l);
    }

    @Beta
    public GoogleTokenResponse setIdToken(String str) {
        this.idToken = (String) Preconditions.checkNotNull(str);
        return this;
    }

    @Override // com.google.api.client.auth.oauth2.TokenResponse
    public GoogleTokenResponse setRefreshToken(String str) {
        return (GoogleTokenResponse) super.setRefreshToken(str);
    }

    @Override // com.google.api.client.auth.oauth2.TokenResponse
    public GoogleTokenResponse setScope(String str) {
        return (GoogleTokenResponse) super.setScope(str);
    }

    @Override // com.google.api.client.auth.oauth2.TokenResponse
    public GoogleTokenResponse setTokenType(String str) {
        return (GoogleTokenResponse) super.setTokenType(str);
    }
}
