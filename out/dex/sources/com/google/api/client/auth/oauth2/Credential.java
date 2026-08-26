package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Credential implements HttpExecuteInterceptor, HttpRequestInitializer, HttpUnsuccessfulResponseHandler {
    static final Logger LOGGER = Logger.getLogger(Credential.class.getName());
    private String accessToken;
    private final HttpExecuteInterceptor clientAuthentication;
    private final Clock clock;
    private Long expirationTimeMilliseconds;
    private final JsonFactory jsonFactory;
    private final Lock lock;
    private final AccessMethod method;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private String refreshToken;
    private final HttpRequestInitializer requestInitializer;
    private final String tokenServerEncodedUrl;
    private final HttpTransport transport;

    public interface AccessMethod {
        String getAccessTokenFromRequest(HttpRequest httpRequest);

        void intercept(HttpRequest httpRequest, String str) throws IOException;
    }

    public static class Builder {
        HttpExecuteInterceptor clientAuthentication;
        JsonFactory jsonFactory;
        final AccessMethod method;
        HttpRequestInitializer requestInitializer;
        GenericUrl tokenServerUrl;
        HttpTransport transport;
        Clock clock = Clock.SYSTEM;
        Collection<CredentialRefreshListener> refreshListeners = Lists.newArrayList();

        public Builder(AccessMethod accessMethod) {
            this.method = (AccessMethod) Preconditions.checkNotNull(accessMethod);
        }

        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            this.refreshListeners.add((CredentialRefreshListener) Preconditions.checkNotNull(credentialRefreshListener));
            return this;
        }

        public Credential build() {
            return new Credential(this);
        }

        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final AccessMethod getMethod() {
            return this.method;
        }

        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }

        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }

        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            this.clientAuthentication = httpExecuteInterceptor;
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = (Clock) Preconditions.checkNotNull(clock);
            return this;
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
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

        public Builder setTokenServerEncodedUrl(String str) {
            this.tokenServerUrl = str == null ? null : new GenericUrl(str);
            return this;
        }

        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            this.tokenServerUrl = genericUrl;
            return this;
        }

        public Builder setTransport(HttpTransport httpTransport) {
            this.transport = httpTransport;
            return this;
        }
    }

    public Credential(AccessMethod accessMethod) {
        this(new Builder(accessMethod));
    }

    protected Credential(Builder builder) {
        this.lock = new ReentrantLock();
        this.method = (AccessMethod) Preconditions.checkNotNull(builder.method);
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.tokenServerEncodedUrl = builder.tokenServerUrl == null ? null : builder.tokenServerUrl.build();
        this.clientAuthentication = builder.clientAuthentication;
        this.requestInitializer = builder.requestInitializer;
        this.refreshListeners = Collections.unmodifiableCollection(builder.refreshListeners);
        this.clock = (Clock) Preconditions.checkNotNull(builder.clock);
    }

    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.refreshToken == null) {
            return null;
        }
        return new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
    }

    public final String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        } finally {
            this.lock.unlock();
        }
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        } finally {
            this.lock.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final Long getExpiresInSeconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds == null ? null : Long.valueOf((this.expirationTimeMilliseconds.longValue() - this.clock.currentTimeMillis()) / 1000);
        } finally {
            this.lock.unlock();
        }
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final AccessMethod getMethod() {
        return this.method;
    }

    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }

    public final String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        } finally {
            this.lock.unlock();
        }
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    @Override // com.google.api.client.http.HttpUnsuccessfulResponseHandler
    public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean z) {
        boolean z2;
        boolean zFind;
        List<String> authenticateAsList = httpResponse.getHeaders().getAuthenticateAsList();
        boolean z3 = true;
        if (authenticateAsList == null) {
            z2 = false;
            zFind = false;
            break;
        }
        Iterator<String> it = authenticateAsList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                zFind = false;
                break;
            }
            String next = it.next();
            if (next.startsWith("Bearer ")) {
                zFind = BearerToken.INVALID_TOKEN_ERROR.matcher(next).find();
                z2 = true;
                break;
            }
        }
        if (!z2) {
            zFind = httpResponse.getStatusCode() == 401;
        }
        if (zFind) {
            try {
                this.lock.lock();
                try {
                    if (Objects.equal(this.accessToken, this.method.getAccessTokenFromRequest(httpRequest)) && !refreshToken()) {
                        z3 = false;
                    }
                    return z3;
                } finally {
                    this.lock.unlock();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "unable to refresh token", (Throwable) e);
            }
        }
        return false;
    }

    @Override // com.google.api.client.http.HttpRequestInitializer
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
        httpRequest.setUnsuccessfulResponseHandler(this);
    }

    @Override // com.google.api.client.http.HttpExecuteInterceptor
    public void intercept(HttpRequest httpRequest) throws IOException {
        this.lock.lock();
        try {
            Long expiresInSeconds = getExpiresInSeconds();
            if (this.accessToken == null || (expiresInSeconds != null && expiresInSeconds.longValue() <= 60)) {
                refreshToken();
                if (this.accessToken == null) {
                    return;
                }
            }
            this.method.intercept(httpRequest, this.accessToken);
        } finally {
            this.lock.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final boolean refreshToken() throws IOException {
        this.lock.lock();
        boolean z = true;
        try {
            try {
                TokenResponse tokenResponseExecuteRefreshToken = executeRefreshToken();
                if (tokenResponseExecuteRefreshToken != null) {
                    setFromTokenResponse(tokenResponseExecuteRefreshToken);
                    Iterator<CredentialRefreshListener> it = this.refreshListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onTokenResponse(this, tokenResponseExecuteRefreshToken);
                    }
                    this.lock.unlock();
                    return true;
                }
            } catch (TokenResponseException e) {
                if (400 > e.getStatusCode() || e.getStatusCode() >= 500) {
                    z = false;
                }
                if (e.getDetails() != null && z) {
                    setAccessToken(null);
                    setExpiresInSeconds(null);
                }
                Iterator<CredentialRefreshListener> it2 = this.refreshListeners.iterator();
                while (it2.hasNext()) {
                    it2.next().onTokenErrorResponse(this, e.getDetails());
                }
                if (z) {
                    throw e;
                }
            }
            this.lock.unlock();
            return false;
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    public Credential setAccessToken(String str) {
        this.lock.lock();
        try {
            this.accessToken = str;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setExpirationTimeMilliseconds(Long l) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = l;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public Credential setExpiresInSeconds(Long l) {
        return setExpirationTimeMilliseconds(l == null ? null : Long.valueOf(this.clock.currentTimeMillis() + (l.longValue() * 1000)));
    }

    public Credential setFromTokenResponse(TokenResponse tokenResponse) {
        setAccessToken(tokenResponse.getAccessToken());
        if (tokenResponse.getRefreshToken() != null) {
            setRefreshToken(tokenResponse.getRefreshToken());
        }
        setExpiresInSeconds(tokenResponse.getExpiresInSeconds());
        return this;
    }

    public Credential setRefreshToken(String str) {
        this.lock.lock();
        if (str != null) {
            try {
                Preconditions.checkArgument((this.jsonFactory == null || this.transport == null || this.clientAuthentication == null || this.tokenServerEncodedUrl == null) ? false : true, "Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
            } finally {
                this.lock.unlock();
            }
        }
        this.refreshToken = str;
        return this;
    }
}
