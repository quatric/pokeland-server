package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GoogleBrowserClientRequestUrl extends BrowserClientRequestUrl {

    @Key("approval_prompt")
    private String approvalPrompt;

    public GoogleBrowserClientRequestUrl(GoogleClientSecrets googleClientSecrets, String str, Collection<String> collection) {
        this(googleClientSecrets.getDetails().getClientId(), str, collection);
    }

    public GoogleBrowserClientRequestUrl(String str, String str2, Collection<String> collection) {
        super(GoogleOAuthConstants.AUTHORIZATION_SERVER_URL, str);
        setRedirectUri(str2);
        setScopes(collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl, com.google.api.client.http.GenericUrl, com.google.api.client.util.GenericData, java.util.AbstractMap
    public GoogleBrowserClientRequestUrl clone() {
        return (GoogleBrowserClientRequestUrl) super.clone();
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl, com.google.api.client.http.GenericUrl, com.google.api.client.util.GenericData
    public GoogleBrowserClientRequestUrl set(String str, Object obj) {
        return (GoogleBrowserClientRequestUrl) super.set(str, obj);
    }

    public GoogleBrowserClientRequestUrl setApprovalPrompt(String str) {
        this.approvalPrompt = str;
        return this;
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public GoogleBrowserClientRequestUrl setClientId(String str) {
        return (GoogleBrowserClientRequestUrl) super.setClientId(str);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public GoogleBrowserClientRequestUrl setRedirectUri(String str) {
        return (GoogleBrowserClientRequestUrl) super.setRedirectUri(str);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public /* bridge */ /* synthetic */ AuthorizationRequestUrl setResponseTypes(Collection collection) {
        return setResponseTypes((Collection<String>) collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public /* bridge */ /* synthetic */ BrowserClientRequestUrl setResponseTypes(Collection collection) {
        return setResponseTypes((Collection<String>) collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public GoogleBrowserClientRequestUrl setResponseTypes(Collection<String> collection) {
        return (GoogleBrowserClientRequestUrl) super.setResponseTypes(collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public /* bridge */ /* synthetic */ AuthorizationRequestUrl setScopes(Collection collection) {
        return setScopes((Collection<String>) collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public /* bridge */ /* synthetic */ BrowserClientRequestUrl setScopes(Collection collection) {
        return setScopes((Collection<String>) collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public GoogleBrowserClientRequestUrl setScopes(Collection<String> collection) {
        Preconditions.checkArgument(collection.iterator().hasNext());
        return (GoogleBrowserClientRequestUrl) super.setScopes(collection);
    }

    @Override // com.google.api.client.auth.oauth2.BrowserClientRequestUrl, com.google.api.client.auth.oauth2.AuthorizationRequestUrl
    public GoogleBrowserClientRequestUrl setState(String str) {
        return (GoogleBrowserClientRequestUrl) super.setState(str);
    }
}
