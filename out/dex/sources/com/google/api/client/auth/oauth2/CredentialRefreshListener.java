package com.google.api.client.auth.oauth2;

import java.io.IOException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public interface CredentialRefreshListener {
    void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException;

    void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException;
}
