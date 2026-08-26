package com.google.api.client.googleapis.compute;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class ComputeCredential extends Credential {
    public static final String TOKEN_SERVER_ENCODED_URL = OAuth2Utils.getMetadataServerUrl() + "/computeMetadata/v1/instance/service-accounts/default/token";

    @Beta
    public static class Builder extends Credential.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory) {
            super(BearerToken.authorizationHeaderAccessMethod());
            setTransport(httpTransport);
            setJsonFactory(jsonFactory);
            setTokenServerEncodedUrl(ComputeCredential.TOKEN_SERVER_ENCODED_URL);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            return (Builder) super.addRefreshListener(credentialRefreshListener);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public ComputeCredential build() {
            return new ComputeCredential(this);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            Preconditions.checkArgument(httpExecuteInterceptor == null);
            return this;
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder) super.setJsonFactory((JsonFactory) Preconditions.checkNotNull(jsonFactory));
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public /* bridge */ /* synthetic */ Credential.Builder setRefreshListeners(Collection collection) {
            return setRefreshListeners((Collection<CredentialRefreshListener>) collection);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            return (Builder) super.setRefreshListeners(collection);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setRequestInitializer(httpRequestInitializer);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setTokenServerEncodedUrl(String str) {
            return (Builder) super.setTokenServerEncodedUrl((String) Preconditions.checkNotNull(str));
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            return (Builder) super.setTokenServerUrl((GenericUrl) Preconditions.checkNotNull(genericUrl));
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder) super.setTransport((HttpTransport) Preconditions.checkNotNull(httpTransport));
        }
    }

    protected ComputeCredential(Builder builder) {
        super(builder);
    }

    public ComputeCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
        this(new Builder(httpTransport, jsonFactory));
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    protected TokenResponse executeRefreshToken() throws IOException {
        HttpRequest httpRequestBuildGetRequest = getTransport().createRequestFactory().buildGetRequest(new GenericUrl(getTokenServerEncodedUrl()));
        httpRequestBuildGetRequest.setParser(new JsonObjectParser(getJsonFactory()));
        httpRequestBuildGetRequest.getHeaders().set("Metadata-Flavor", "Google");
        return (TokenResponse) httpRequestBuildGetRequest.execute().parseAs(TokenResponse.class);
    }
}
