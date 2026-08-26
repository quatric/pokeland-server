package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class AuthorizationCodeFlow {
    private final String authorizationServerEncodedUrl;
    private final HttpExecuteInterceptor clientAuthentication;
    private final String clientId;
    private final Clock clock;
    private final CredentialCreatedListener credentialCreatedListener;

    @Beta
    private final DataStore<StoredCredential> credentialDataStore;

    @Beta
    @Deprecated
    private final CredentialStore credentialStore;
    private final JsonFactory jsonFactory;
    private final Credential.AccessMethod method;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private final HttpRequestInitializer requestInitializer;
    private final Collection<String> scopes;
    private final String tokenServerEncodedUrl;
    private final HttpTransport transport;

    public static class Builder {
        String authorizationServerEncodedUrl;
        HttpExecuteInterceptor clientAuthentication;
        String clientId;
        CredentialCreatedListener credentialCreatedListener;

        @Beta
        DataStore<StoredCredential> credentialDataStore;

        @Beta
        @Deprecated
        CredentialStore credentialStore;
        JsonFactory jsonFactory;
        Credential.AccessMethod method;
        HttpRequestInitializer requestInitializer;
        GenericUrl tokenServerUrl;
        HttpTransport transport;
        Collection<String> scopes = Lists.newArrayList();
        Clock clock = Clock.SYSTEM;
        Collection<CredentialRefreshListener> refreshListeners = Lists.newArrayList();

        public Builder(Credential.AccessMethod accessMethod, HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, HttpExecuteInterceptor httpExecuteInterceptor, String str, String str2) {
            setMethod(accessMethod);
            setTransport(httpTransport);
            setJsonFactory(jsonFactory);
            setTokenServerUrl(genericUrl);
            setClientAuthentication(httpExecuteInterceptor);
            setClientId(str);
            setAuthorizationServerEncodedUrl(str2);
        }

        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            this.refreshListeners.add((CredentialRefreshListener) Preconditions.checkNotNull(credentialRefreshListener));
            return this;
        }

        public AuthorizationCodeFlow build() {
            return new AuthorizationCodeFlow(this);
        }

        public final String getAuthorizationServerEncodedUrl() {
            return this.authorizationServerEncodedUrl;
        }

        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }

        public final String getClientId() {
            return this.clientId;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public final CredentialCreatedListener getCredentialCreatedListener() {
            return this.credentialCreatedListener;
        }

        @Beta
        public final DataStore<StoredCredential> getCredentialDataStore() {
            return this.credentialDataStore;
        }

        @Beta
        @Deprecated
        public final CredentialStore getCredentialStore() {
            return this.credentialStore;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final Credential.AccessMethod getMethod() {
            return this.method;
        }

        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }

        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }

        public final Collection<String> getScopes() {
            return this.scopes;
        }

        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setAuthorizationServerEncodedUrl(String str) {
            this.authorizationServerEncodedUrl = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            this.clientAuthentication = httpExecuteInterceptor;
            return this;
        }

        public Builder setClientId(String str) {
            this.clientId = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = (Clock) Preconditions.checkNotNull(clock);
            return this;
        }

        public Builder setCredentialCreatedListener(CredentialCreatedListener credentialCreatedListener) {
            this.credentialCreatedListener = credentialCreatedListener;
            return this;
        }

        @Beta
        public Builder setCredentialDataStore(DataStore<StoredCredential> dataStore) {
            Preconditions.checkArgument(this.credentialStore == null);
            this.credentialDataStore = dataStore;
            return this;
        }

        @Beta
        @Deprecated
        public Builder setCredentialStore(CredentialStore credentialStore) {
            Preconditions.checkArgument(this.credentialDataStore == null);
            this.credentialStore = credentialStore;
            return this;
        }

        @Beta
        public Builder setDataStoreFactory(DataStoreFactory dataStoreFactory) throws IOException {
            return setCredentialDataStore(StoredCredential.getDefaultDataStore(dataStoreFactory));
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(jsonFactory);
            return this;
        }

        public Builder setMethod(Credential.AccessMethod accessMethod) {
            this.method = (Credential.AccessMethod) Preconditions.checkNotNull(accessMethod);
            return this;
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            this.refreshListeners = (Collection) Preconditions.checkNotNull(collection);
            return this;
        }

        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            this.requestInitializer = httpRequestInitializer;
            return this;
        }

        public Builder setScopes(Collection<String> collection) {
            this.scopes = (Collection) Preconditions.checkNotNull(collection);
            return this;
        }

        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            this.tokenServerUrl = (GenericUrl) Preconditions.checkNotNull(genericUrl);
            return this;
        }

        public Builder setTransport(HttpTransport httpTransport) {
            this.transport = (HttpTransport) Preconditions.checkNotNull(httpTransport);
            return this;
        }
    }

    public interface CredentialCreatedListener {
        void onCredentialCreated(Credential credential, TokenResponse tokenResponse) throws IOException;
    }

    protected AuthorizationCodeFlow(Builder builder) {
        this.method = (Credential.AccessMethod) Preconditions.checkNotNull(builder.method);
        this.transport = (HttpTransport) Preconditions.checkNotNull(builder.transport);
        this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(builder.jsonFactory);
        this.tokenServerEncodedUrl = ((GenericUrl) Preconditions.checkNotNull(builder.tokenServerUrl)).build();
        this.clientAuthentication = builder.clientAuthentication;
        this.clientId = (String) Preconditions.checkNotNull(builder.clientId);
        this.authorizationServerEncodedUrl = (String) Preconditions.checkNotNull(builder.authorizationServerEncodedUrl);
        this.requestInitializer = builder.requestInitializer;
        this.credentialStore = builder.credentialStore;
        this.credentialDataStore = builder.credentialDataStore;
        this.scopes = Collections.unmodifiableCollection(builder.scopes);
        this.clock = (Clock) Preconditions.checkNotNull(builder.clock);
        this.credentialCreatedListener = builder.credentialCreatedListener;
        this.refreshListeners = Collections.unmodifiableCollection(builder.refreshListeners);
    }

    public AuthorizationCodeFlow(Credential.AccessMethod accessMethod, HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, HttpExecuteInterceptor httpExecuteInterceptor, String str, String str2) {
        this(new Builder(accessMethod, httpTransport, jsonFactory, genericUrl, httpExecuteInterceptor, str, str2));
    }

    private Credential newCredential(String str) {
        Credential.Builder clock = new Credential.Builder(this.method).setTransport(this.transport).setJsonFactory(this.jsonFactory).setTokenServerEncodedUrl(this.tokenServerEncodedUrl).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).setClock(this.clock);
        DataStore<StoredCredential> dataStore = this.credentialDataStore;
        if (dataStore != null) {
            clock.addRefreshListener(new DataStoreCredentialRefreshListener(str, dataStore));
        } else {
            CredentialStore credentialStore = this.credentialStore;
            if (credentialStore != null) {
                clock.addRefreshListener(new CredentialStoreRefreshListener(str, credentialStore));
            }
        }
        clock.getRefreshListeners().addAll(this.refreshListeners);
        return clock.build();
    }

    public Credential createAndStoreCredential(TokenResponse tokenResponse, String str) throws IOException {
        Credential fromTokenResponse = newCredential(str).setFromTokenResponse(tokenResponse);
        CredentialStore credentialStore = this.credentialStore;
        if (credentialStore != null) {
            credentialStore.store(str, fromTokenResponse);
        }
        DataStore<StoredCredential> dataStore = this.credentialDataStore;
        if (dataStore != null) {
            dataStore.set(str, new StoredCredential(fromTokenResponse));
        }
        CredentialCreatedListener credentialCreatedListener = this.credentialCreatedListener;
        if (credentialCreatedListener != null) {
            credentialCreatedListener.onCredentialCreated(fromTokenResponse, tokenResponse);
        }
        return fromTokenResponse;
    }

    public final String getAuthorizationServerEncodedUrl() {
        return this.authorizationServerEncodedUrl;
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final Clock getClock() {
        return this.clock;
    }

    @Beta
    public final DataStore<StoredCredential> getCredentialDataStore() {
        return this.credentialDataStore;
    }

    @Beta
    @Deprecated
    public final CredentialStore getCredentialStore() {
        return this.credentialStore;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final Credential.AccessMethod getMethod() {
        return this.method;
    }

    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final Collection<String> getScopes() {
        return this.scopes;
    }

    public final String getScopesAsString() {
        return Joiner.m459on(' ').join(this.scopes);
    }

    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public Credential loadCredential(String str) throws IOException {
        if (Strings.isNullOrEmpty(str)) {
            return null;
        }
        if (this.credentialDataStore == null && this.credentialStore == null) {
            return null;
        }
        Credential credentialNewCredential = newCredential(str);
        DataStore<StoredCredential> dataStore = this.credentialDataStore;
        if (dataStore != null) {
            StoredCredential storedCredential = (StoredCredential) dataStore.get(str);
            if (storedCredential == null) {
                return null;
            }
            credentialNewCredential.setAccessToken(storedCredential.getAccessToken());
            credentialNewCredential.setRefreshToken(storedCredential.getRefreshToken());
            credentialNewCredential.setExpirationTimeMilliseconds(storedCredential.getExpirationTimeMilliseconds());
        } else if (!this.credentialStore.load(str, credentialNewCredential)) {
            return null;
        }
        return credentialNewCredential;
    }

    public AuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new AuthorizationCodeRequestUrl(this.authorizationServerEncodedUrl, this.clientId).setScopes(this.scopes);
    }

    public AuthorizationCodeTokenRequest newTokenRequest(String str) {
        return new AuthorizationCodeTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), str).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).setScopes(this.scopes);
    }
}
