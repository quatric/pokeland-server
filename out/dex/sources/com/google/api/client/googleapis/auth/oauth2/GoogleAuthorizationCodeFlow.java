package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GoogleAuthorizationCodeFlow extends AuthorizationCodeFlow {
    private final String accessType;
    private final String approvalPrompt;

    public static class Builder extends AuthorizationCodeFlow.Builder {
        String accessType;
        String approvalPrompt;

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, GoogleClientSecrets googleClientSecrets, Collection<String> collection) {
            super(BearerToken.authorizationHeaderAccessMethod(), httpTransport, jsonFactory, new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL), new ClientParametersAuthentication(googleClientSecrets.getDetails().getClientId(), googleClientSecrets.getDetails().getClientSecret()), googleClientSecrets.getDetails().getClientId(), GoogleOAuthConstants.AUTHORIZATION_SERVER_URL);
            setScopes(collection);
        }

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, String str, String str2, Collection<String> collection) {
            super(BearerToken.authorizationHeaderAccessMethod(), httpTransport, jsonFactory, new GenericUrl(GoogleOAuthConstants.TOKEN_SERVER_URL), new ClientParametersAuthentication(str, str2), str, GoogleOAuthConstants.AUTHORIZATION_SERVER_URL);
            setScopes(collection);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            return (Builder) super.addRefreshListener(credentialRefreshListener);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public GoogleAuthorizationCodeFlow build() {
            return new GoogleAuthorizationCodeFlow(this);
        }

        public final String getAccessType() {
            return this.accessType;
        }

        public final String getApprovalPrompt() {
            return this.approvalPrompt;
        }

        public Builder setAccessType(String str) {
            this.accessType = str;
            return this;
        }

        public Builder setApprovalPrompt(String str) {
            this.approvalPrompt = str;
            return this;
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setAuthorizationServerEncodedUrl(String str) {
            return (Builder) super.setAuthorizationServerEncodedUrl(str);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            return (Builder) super.setClientAuthentication(httpExecuteInterceptor);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setClientId(String str) {
            return (Builder) super.setClientId(str);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setCredentialCreatedListener(AuthorizationCodeFlow.CredentialCreatedListener credentialCreatedListener) {
            return (Builder) super.setCredentialCreatedListener(credentialCreatedListener);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public /* bridge */ /* synthetic */ AuthorizationCodeFlow.Builder setCredentialDataStore(DataStore dataStore) {
            return setCredentialDataStore((DataStore<StoredCredential>) dataStore);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setCredentialDataStore(DataStore<StoredCredential> dataStore) {
            return (Builder) super.setCredentialDataStore(dataStore);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        @Beta
        @Deprecated
        public Builder setCredentialStore(CredentialStore credentialStore) {
            return (Builder) super.setCredentialStore(credentialStore);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setDataStoreFactory(DataStoreFactory dataStoreFactory) throws IOException {
            return (Builder) super.setDataStoreFactory(dataStoreFactory);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder) super.setJsonFactory(jsonFactory);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setMethod(Credential.AccessMethod accessMethod) {
            return (Builder) super.setMethod(accessMethod);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public /* bridge */ /* synthetic */ AuthorizationCodeFlow.Builder setRefreshListeners(Collection collection) {
            return setRefreshListeners((Collection<CredentialRefreshListener>) collection);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            return (Builder) super.setRefreshListeners(collection);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setRequestInitializer(httpRequestInitializer);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public /* bridge */ /* synthetic */ AuthorizationCodeFlow.Builder setScopes(Collection collection) {
            return setScopes((Collection<String>) collection);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setScopes(Collection<String> collection) {
            Preconditions.checkState(!collection.isEmpty());
            return (Builder) super.setScopes(collection);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            return (Builder) super.setTokenServerUrl(genericUrl);
        }

        @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder) super.setTransport(httpTransport);
        }
    }

    protected GoogleAuthorizationCodeFlow(Builder builder) {
        super(builder);
        this.accessType = builder.accessType;
        this.approvalPrompt = builder.approvalPrompt;
    }

    public GoogleAuthorizationCodeFlow(HttpTransport httpTransport, JsonFactory jsonFactory, String str, String str2, Collection<String> collection) {
        this(new Builder(httpTransport, jsonFactory, str, str2, collection));
    }

    public final String getAccessType() {
        return this.accessType;
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow
    public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new GoogleAuthorizationCodeRequestUrl(getAuthorizationServerEncodedUrl(), getClientId(), "", getScopes()).setAccessType(this.accessType).setApprovalPrompt(this.approvalPrompt);
    }

    @Override // com.google.api.client.auth.oauth2.AuthorizationCodeFlow
    public GoogleAuthorizationCodeTokenRequest newTokenRequest(String str) {
        return new GoogleAuthorizationCodeTokenRequest(getTransport(), getJsonFactory(), getTokenServerEncodedUrl(), "", "", str, "").setClientAuthentication(getClientAuthentication()).setRequestInitializer(getRequestInitializer()).setScopes(getScopes());
    }
}
