package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class AuthorizationRequestUrl extends GenericUrl {

    @Key("client_id")
    private String clientId;

    @Key("redirect_uri")
    private String redirectUri;

    @Key("response_type")
    private String responseTypes;

    @Key("scope")
    private String scopes;

    @Key
    private String state;

    public AuthorizationRequestUrl(String str, String str2, Collection<String> collection) {
        super(str);
        Preconditions.checkArgument(getFragment() == null);
        setClientId(str2);
        setResponseTypes(collection);
    }

    @Override // com.google.api.client.http.GenericUrl, com.google.api.client.util.GenericData, java.util.AbstractMap
    public AuthorizationRequestUrl clone() {
        return (AuthorizationRequestUrl) super.clone();
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final String getRedirectUri() {
        return this.redirectUri;
    }

    public final String getResponseTypes() {
        return this.responseTypes;
    }

    public final String getScopes() {
        return this.scopes;
    }

    public final String getState() {
        return this.state;
    }

    @Override // com.google.api.client.http.GenericUrl, com.google.api.client.util.GenericData
    public AuthorizationRequestUrl set(String str, Object obj) {
        return (AuthorizationRequestUrl) super.set(str, obj);
    }

    public AuthorizationRequestUrl setClientId(String str) {
        this.clientId = (String) Preconditions.checkNotNull(str);
        return this;
    }

    public AuthorizationRequestUrl setRedirectUri(String str) {
        this.redirectUri = str;
        return this;
    }

    public AuthorizationRequestUrl setResponseTypes(Collection<String> collection) {
        this.responseTypes = Joiner.m459on(' ').join(collection);
        return this;
    }

    public AuthorizationRequestUrl setScopes(Collection<String> collection) {
        this.scopes = (collection == null || !collection.iterator().hasNext()) ? null : Joiner.m459on(' ').join(collection);
        return this;
    }

    public AuthorizationRequestUrl setState(String str) {
        this.state = str;
        return this;
    }
}
