package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ClientParametersAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor {
    private final String clientId;
    private final String clientSecret;

    public ClientParametersAuthentication(String str, String str2) {
        this.clientId = (String) Preconditions.checkNotNull(str);
        this.clientSecret = str2;
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final String getClientSecret() {
        return this.clientSecret;
    }

    @Override // com.google.api.client.http.HttpRequestInitializer
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
    }

    @Override // com.google.api.client.http.HttpExecuteInterceptor
    public void intercept(HttpRequest httpRequest) throws IOException {
        Map<String, Object> mapMapOf = Data.mapOf(UrlEncodedContent.getContent(httpRequest).getData());
        mapMapOf.put("client_id", this.clientId);
        String str = this.clientSecret;
        if (str != null) {
            mapMapOf.put("client_secret", str);
        }
    }
}
