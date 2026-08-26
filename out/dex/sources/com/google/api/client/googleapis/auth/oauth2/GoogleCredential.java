package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class GoogleCredential extends Credential {
    static final String SERVICE_ACCOUNT_FILE_TYPE = "service_account";
    static final String USER_FILE_TYPE = "authorized_user";

    @Beta
    private static DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider();
    private String serviceAccountId;
    private PrivateKey serviceAccountPrivateKey;
    private String serviceAccountPrivateKeyId;
    private String serviceAccountProjectId;
    private Collection<String> serviceAccountScopes;
    private String serviceAccountUser;

    public static class Builder extends Credential.Builder {
        String serviceAccountId;
        PrivateKey serviceAccountPrivateKey;
        String serviceAccountPrivateKeyId;
        String serviceAccountProjectId;
        Collection<String> serviceAccountScopes;
        String serviceAccountUser;

        public Builder() {
            super(BearerToken.authorizationHeaderAccessMethod());
            setTokenServerEncodedUrl(GoogleOAuthConstants.TOKEN_SERVER_URL);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            return (Builder) super.addRefreshListener(credentialRefreshListener);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public GoogleCredential build() {
            return new GoogleCredential(this);
        }

        public final String getServiceAccountId() {
            return this.serviceAccountId;
        }

        public final PrivateKey getServiceAccountPrivateKey() {
            return this.serviceAccountPrivateKey;
        }

        @Beta
        public final String getServiceAccountPrivateKeyId() {
            return this.serviceAccountPrivateKeyId;
        }

        public final String getServiceAccountProjectId() {
            return this.serviceAccountProjectId;
        }

        public final Collection<String> getServiceAccountScopes() {
            return this.serviceAccountScopes;
        }

        public final String getServiceAccountUser() {
            return this.serviceAccountUser;
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            return (Builder) super.setClientAuthentication(httpExecuteInterceptor);
        }

        public Builder setClientSecrets(GoogleClientSecrets googleClientSecrets) {
            GoogleClientSecrets.Details details = googleClientSecrets.getDetails();
            setClientAuthentication((HttpExecuteInterceptor) new ClientParametersAuthentication(details.getClientId(), details.getClientSecret()));
            return this;
        }

        public Builder setClientSecrets(String str, String str2) {
            setClientAuthentication((HttpExecuteInterceptor) new ClientParametersAuthentication(str, str2));
            return this;
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setClock(Clock clock) {
            return (Builder) super.setClock(clock);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder) super.setJsonFactory(jsonFactory);
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

        public Builder setServiceAccountId(String str) {
            this.serviceAccountId = str;
            return this;
        }

        public Builder setServiceAccountPrivateKey(PrivateKey privateKey) {
            this.serviceAccountPrivateKey = privateKey;
            return this;
        }

        public Builder setServiceAccountPrivateKeyFromP12File(File file) throws GeneralSecurityException, IOException {
            this.serviceAccountPrivateKey = SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), new FileInputStream(file), "notasecret", "privatekey", "notasecret");
            return this;
        }

        @Beta
        public Builder setServiceAccountPrivateKeyFromPemFile(File file) throws GeneralSecurityException, IOException {
            this.serviceAccountPrivateKey = SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(PemReader.readFirstSectionAndClose(new FileReader(file), "PRIVATE KEY").getBase64DecodedBytes()));
            return this;
        }

        @Beta
        public Builder setServiceAccountPrivateKeyId(String str) {
            this.serviceAccountPrivateKeyId = str;
            return this;
        }

        public Builder setServiceAccountProjectId(String str) {
            this.serviceAccountProjectId = str;
            return this;
        }

        public Builder setServiceAccountScopes(Collection<String> collection) {
            this.serviceAccountScopes = collection;
            return this;
        }

        public Builder setServiceAccountUser(String str) {
            this.serviceAccountUser = str;
            return this;
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setTokenServerEncodedUrl(String str) {
            return (Builder) super.setTokenServerEncodedUrl(str);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            return (Builder) super.setTokenServerUrl(genericUrl);
        }

        @Override // com.google.api.client.auth.oauth2.Credential.Builder
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder) super.setTransport(httpTransport);
        }
    }

    public GoogleCredential() {
        this(new Builder());
    }

    protected GoogleCredential(Builder builder) {
        super(builder);
        if (builder.serviceAccountPrivateKey == null) {
            Preconditions.checkArgument(builder.serviceAccountId == null && builder.serviceAccountScopes == null && builder.serviceAccountUser == null);
            return;
        }
        this.serviceAccountId = (String) Preconditions.checkNotNull(builder.serviceAccountId);
        this.serviceAccountProjectId = builder.serviceAccountProjectId;
        this.serviceAccountScopes = builder.serviceAccountScopes == null ? Collections.emptyList() : Collections.unmodifiableCollection(builder.serviceAccountScopes);
        this.serviceAccountPrivateKey = builder.serviceAccountPrivateKey;
        this.serviceAccountPrivateKeyId = builder.serviceAccountPrivateKeyId;
        this.serviceAccountUser = builder.serviceAccountUser;
    }

    @Beta
    public static GoogleCredential fromStream(InputStream inputStream) throws IOException {
        return fromStream(inputStream, Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }

    @Beta
    public static GoogleCredential fromStream(InputStream inputStream, HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(httpTransport);
        Preconditions.checkNotNull(jsonFactory);
        GenericJson genericJson = (GenericJson) new JsonObjectParser(jsonFactory).parseAndClose(inputStream, OAuth2Utils.UTF_8, GenericJson.class);
        String str = (String) genericJson.get("type");
        if (str == null) {
            throw new IOException("Error reading credentials from stream, 'type' field not specified.");
        }
        if (USER_FILE_TYPE.equals(str)) {
            return fromStreamUser(genericJson, httpTransport, jsonFactory);
        }
        if (SERVICE_ACCOUNT_FILE_TYPE.equals(str)) {
            return fromStreamServiceAccount(genericJson, httpTransport, jsonFactory);
        }
        throw new IOException(String.format("Error reading credentials from stream, 'type' value '%s' not recognized. Expecting '%s' or '%s'.", str, USER_FILE_TYPE, SERVICE_ACCOUNT_FILE_TYPE));
    }

    @Beta
    private static GoogleCredential fromStreamServiceAccount(GenericJson genericJson, HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        String str = (String) genericJson.get("client_id");
        String str2 = (String) genericJson.get("client_email");
        String str3 = (String) genericJson.get("private_key");
        String str4 = (String) genericJson.get("private_key_id");
        if (str == null || str2 == null || str3 == null || str4 == null) {
            throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        }
        Builder serviceAccountPrivateKeyId = new Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setServiceAccountId(str2).setServiceAccountScopes(Collections.emptyList()).setServiceAccountPrivateKey(privateKeyFromPkcs8(str3)).setServiceAccountPrivateKeyId(str4);
        String str5 = (String) genericJson.get("token_uri");
        if (str5 != null) {
            serviceAccountPrivateKeyId.setTokenServerEncodedUrl(str5);
        }
        String str6 = (String) genericJson.get("project_id");
        if (str6 != null) {
            serviceAccountPrivateKeyId.setServiceAccountProjectId(str6);
        }
        return serviceAccountPrivateKeyId.build();
    }

    @Beta
    private static GoogleCredential fromStreamUser(GenericJson genericJson, HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        String str = (String) genericJson.get("client_id");
        String str2 = (String) genericJson.get("client_secret");
        String str3 = (String) genericJson.get("refresh_token");
        if (str == null || str2 == null || str3 == null) {
            throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        }
        GoogleCredential googleCredentialBuild = new Builder().setClientSecrets(str, str2).setTransport(httpTransport).setJsonFactory(jsonFactory).build();
        googleCredentialBuild.setRefreshToken(str3);
        googleCredentialBuild.refreshToken();
        return googleCredentialBuild;
    }

    @Beta
    public static GoogleCredential getApplicationDefault() throws IOException {
        return getApplicationDefault(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }

    @Beta
    public static GoogleCredential getApplicationDefault(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(httpTransport);
        Preconditions.checkNotNull(jsonFactory);
        return defaultCredentialProvider.getDefaultCredential(httpTransport, jsonFactory);
    }

    @Beta
    private static PrivateKey privateKeyFromPkcs8(String str) throws IOException {
        PemReader.Section firstSectionAndClose = PemReader.readFirstSectionAndClose(new StringReader(str), "PRIVATE KEY");
        if (firstSectionAndClose == null) {
            throw new IOException("Invalid PKCS8 data.");
        }
        try {
            return SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(firstSectionAndClose.getBase64DecodedBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), e));
        }
    }

    @Beta
    public GoogleCredential createScoped(Collection<String> collection) {
        return this.serviceAccountPrivateKey == null ? this : new Builder().setServiceAccountPrivateKey(this.serviceAccountPrivateKey).setServiceAccountPrivateKeyId(this.serviceAccountPrivateKeyId).setServiceAccountId(this.serviceAccountId).setServiceAccountProjectId(this.serviceAccountProjectId).setServiceAccountUser(this.serviceAccountUser).setServiceAccountScopes(collection).setTokenServerEncodedUrl(getTokenServerEncodedUrl()).setTransport(getTransport()).setJsonFactory(getJsonFactory()).setClock(getClock()).build();
    }

    @Beta
    public boolean createScopedRequired() {
        if (this.serviceAccountPrivateKey == null) {
            return false;
        }
        Collection<String> collection = this.serviceAccountScopes;
        return collection == null || collection.isEmpty();
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    @Beta
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.serviceAccountPrivateKey == null) {
            return super.executeRefreshToken();
        }
        JsonWebSignature.Header header = new JsonWebSignature.Header();
        header.setAlgorithm("RS256");
        header.setType("JWT");
        header.setKeyId(this.serviceAccountPrivateKeyId);
        JsonWebToken.Payload payload = new JsonWebToken.Payload();
        long jCurrentTimeMillis = getClock().currentTimeMillis();
        payload.setIssuer(this.serviceAccountId);
        payload.setAudience(getTokenServerEncodedUrl());
        long j = jCurrentTimeMillis / 1000;
        payload.setIssuedAtTimeSeconds(Long.valueOf(j));
        payload.setExpirationTimeSeconds(Long.valueOf(j + 3600));
        payload.setSubject(this.serviceAccountUser);
        payload.put("scope", (Object) Joiner.m459on(' ').join(this.serviceAccountScopes));
        try {
            String strSignUsingRsaSha256 = JsonWebSignature.signUsingRsaSha256(this.serviceAccountPrivateKey, getJsonFactory(), header, payload);
            TokenRequest tokenRequest = new TokenRequest(getTransport(), getJsonFactory(), new GenericUrl(getTokenServerEncodedUrl()), "urn:ietf:params:oauth:grant-type:jwt-bearer");
            tokenRequest.put("assertion", (Object) strSignUsingRsaSha256);
            return tokenRequest.execute();
        } catch (GeneralSecurityException e) {
            IOException iOException = new IOException();
            iOException.initCause(e);
            throw iOException;
        }
    }

    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }

    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }

    @Beta
    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }

    public final String getServiceAccountProjectId() {
        return this.serviceAccountProjectId;
    }

    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }

    public final String getServiceAccountScopesAsString() {
        if (this.serviceAccountScopes == null) {
            return null;
        }
        return Joiner.m459on(' ').join(this.serviceAccountScopes);
    }

    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    public GoogleCredential setAccessToken(String str) {
        return (GoogleCredential) super.setAccessToken(str);
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    public GoogleCredential setExpirationTimeMilliseconds(Long l) {
        return (GoogleCredential) super.setExpirationTimeMilliseconds(l);
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    public GoogleCredential setExpiresInSeconds(Long l) {
        return (GoogleCredential) super.setExpiresInSeconds(l);
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    public GoogleCredential setFromTokenResponse(TokenResponse tokenResponse) {
        return (GoogleCredential) super.setFromTokenResponse(tokenResponse);
    }

    @Override // com.google.api.client.auth.oauth2.Credential
    public GoogleCredential setRefreshToken(String str) {
        if (str != null) {
            Preconditions.checkArgument((getJsonFactory() == null || getTransport() == null || getClientAuthentication() == null) ? false : true, "Please use the Builder and call setJsonFactory, setTransport and setClientSecrets");
        }
        return (GoogleCredential) super.setRefreshToken(str);
    }
}
