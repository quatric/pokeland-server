package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public class GoogleIdTokenVerifier extends IdTokenVerifier {
    private final GooglePublicKeysManager publicKeys;

    @Beta
    public static class Builder extends IdTokenVerifier.Builder {
        GooglePublicKeysManager publicKeys;

        public Builder(GooglePublicKeysManager googlePublicKeysManager) {
            this.publicKeys = (GooglePublicKeysManager) Preconditions.checkNotNull(googlePublicKeysManager);
            setIssuers((Collection<String>) Arrays.asList("accounts.google.com", "https://accounts.google.com"));
        }

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory) {
            this(new GooglePublicKeysManager(httpTransport, jsonFactory));
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public GoogleIdTokenVerifier build() {
            return new GoogleIdTokenVerifier(this);
        }

        public final JsonFactory getJsonFactory() {
            return this.publicKeys.getJsonFactory();
        }

        public final GooglePublicKeysManager getPublicCerts() {
            return this.publicKeys;
        }

        @Deprecated
        public final String getPublicCertsEncodedUrl() {
            return this.publicKeys.getPublicCertsEncodedUrl();
        }

        public final HttpTransport getTransport() {
            return this.publicKeys.getTransport();
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public Builder setAcceptableTimeSkewSeconds(long j) {
            return (Builder) super.setAcceptableTimeSkewSeconds(j);
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public /* bridge */ /* synthetic */ IdTokenVerifier.Builder setAudience(Collection collection) {
            return setAudience((Collection<String>) collection);
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public Builder setAudience(Collection<String> collection) {
            return (Builder) super.setAudience(collection);
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public Builder setIssuer(String str) {
            return (Builder) super.setIssuer(str);
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public /* bridge */ /* synthetic */ IdTokenVerifier.Builder setIssuers(Collection collection) {
            return setIssuers((Collection<String>) collection);
        }

        @Override // com.google.api.client.auth.openidconnect.IdTokenVerifier.Builder
        public Builder setIssuers(Collection<String> collection) {
            return (Builder) super.setIssuers(collection);
        }

        @Deprecated
        public Builder setPublicCertsEncodedUrl(String str) {
            this.publicKeys = new GooglePublicKeysManager.Builder(getTransport(), getJsonFactory()).setPublicCertsEncodedUrl(str).setClock(this.publicKeys.getClock()).build();
            return this;
        }
    }

    protected GoogleIdTokenVerifier(Builder builder) {
        super(builder);
        this.publicKeys = builder.publicKeys;
    }

    public GoogleIdTokenVerifier(GooglePublicKeysManager googlePublicKeysManager) {
        this(new Builder(googlePublicKeysManager));
    }

    public GoogleIdTokenVerifier(HttpTransport httpTransport, JsonFactory jsonFactory) {
        this(new Builder(httpTransport, jsonFactory));
    }

    @Deprecated
    public final long getExpirationTimeMilliseconds() {
        return this.publicKeys.getExpirationTimeMilliseconds();
    }

    public final JsonFactory getJsonFactory() {
        return this.publicKeys.getJsonFactory();
    }

    @Deprecated
    public final String getPublicCertsEncodedUrl() {
        return this.publicKeys.getPublicCertsEncodedUrl();
    }

    @Deprecated
    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        return this.publicKeys.getPublicKeys();
    }

    public final GooglePublicKeysManager getPublicKeysManager() {
        return this.publicKeys;
    }

    public final HttpTransport getTransport() {
        return this.publicKeys.getTransport();
    }

    @Deprecated
    public GoogleIdTokenVerifier loadPublicCerts() throws GeneralSecurityException, IOException {
        this.publicKeys.refresh();
        return this;
    }

    public GoogleIdToken verify(String str) throws GeneralSecurityException, IOException {
        GoogleIdToken googleIdToken = GoogleIdToken.parse(getJsonFactory(), str);
        if (verify(googleIdToken)) {
            return googleIdToken;
        }
        return null;
    }

    public boolean verify(GoogleIdToken googleIdToken) throws GeneralSecurityException, IOException {
        if (!super.verify((IdToken) googleIdToken)) {
            return false;
        }
        Iterator<PublicKey> it = this.publicKeys.getPublicKeys().iterator();
        while (it.hasNext()) {
            if (googleIdToken.verifySignature(it.next())) {
                return true;
            }
        }
        return false;
    }
}
