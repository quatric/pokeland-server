package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import com.google.api.services.pubsub.Pubsub;
import java.io.IOException;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractGoogleClient {
    static final Logger logger = Logger.getLogger(AbstractGoogleClient.class.getName());
    private final String applicationName;
    private final String batchPath;
    private final GoogleClientRequestInitializer googleClientRequestInitializer;
    private final ObjectParser objectParser;
    private final HttpRequestFactory requestFactory;
    private final String rootUrl;
    private final String servicePath;
    private final boolean suppressPatternChecks;
    private final boolean suppressRequiredParameterChecks;

    public static abstract class Builder {
        String applicationName;
        String batchPath;
        GoogleClientRequestInitializer googleClientRequestInitializer;
        HttpRequestInitializer httpRequestInitializer;
        final ObjectParser objectParser;
        String rootUrl;
        String servicePath;
        boolean suppressPatternChecks;
        boolean suppressRequiredParameterChecks;
        final HttpTransport transport;

        protected Builder(HttpTransport httpTransport, String str, String str2, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
            this.transport = (HttpTransport) Preconditions.checkNotNull(httpTransport);
            this.objectParser = objectParser;
            setRootUrl(str);
            setServicePath(str2);
            this.httpRequestInitializer = httpRequestInitializer;
        }

        public abstract AbstractGoogleClient build();

        public final String getApplicationName() {
            return this.applicationName;
        }

        public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
            return this.googleClientRequestInitializer;
        }

        public final HttpRequestInitializer getHttpRequestInitializer() {
            return this.httpRequestInitializer;
        }

        public ObjectParser getObjectParser() {
            return this.objectParser;
        }

        public final String getRootUrl() {
            return this.rootUrl;
        }

        public final String getServicePath() {
            return this.servicePath;
        }

        public final boolean getSuppressPatternChecks() {
            return this.suppressPatternChecks;
        }

        public final boolean getSuppressRequiredParameterChecks() {
            return this.suppressRequiredParameterChecks;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setApplicationName(String str) {
            this.applicationName = str;
            return this;
        }

        public Builder setBatchPath(String str) {
            this.batchPath = str;
            return this;
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            this.googleClientRequestInitializer = googleClientRequestInitializer;
            return this;
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            this.httpRequestInitializer = httpRequestInitializer;
            return this;
        }

        public Builder setRootUrl(String str) {
            this.rootUrl = AbstractGoogleClient.normalizeRootUrl(str);
            return this;
        }

        public Builder setServicePath(String str) {
            this.servicePath = AbstractGoogleClient.normalizeServicePath(str);
            return this;
        }

        public Builder setSuppressAllChecks(boolean z) {
            return setSuppressPatternChecks(true).setSuppressRequiredParameterChecks(true);
        }

        public Builder setSuppressPatternChecks(boolean z) {
            this.suppressPatternChecks = z;
            return this;
        }

        public Builder setSuppressRequiredParameterChecks(boolean z) {
            this.suppressRequiredParameterChecks = z;
            return this;
        }
    }

    protected AbstractGoogleClient(Builder builder) {
        this.googleClientRequestInitializer = builder.googleClientRequestInitializer;
        this.rootUrl = normalizeRootUrl(builder.rootUrl);
        this.servicePath = normalizeServicePath(builder.servicePath);
        this.batchPath = builder.batchPath;
        if (Strings.isNullOrEmpty(builder.applicationName)) {
            logger.warning("Application name is not set. Call Builder#setApplicationName.");
        }
        this.applicationName = builder.applicationName;
        this.requestFactory = builder.httpRequestInitializer == null ? builder.transport.createRequestFactory() : builder.transport.createRequestFactory(builder.httpRequestInitializer);
        this.objectParser = builder.objectParser;
        this.suppressPatternChecks = builder.suppressPatternChecks;
        this.suppressRequiredParameterChecks = builder.suppressRequiredParameterChecks;
    }

    static String normalizeRootUrl(String str) {
        Preconditions.checkNotNull(str, "root URL cannot be null.");
        if (str.endsWith("/")) {
            return str;
        }
        return str + "/";
    }

    static String normalizeServicePath(String str) {
        Preconditions.checkNotNull(str, "service path cannot be null");
        if (str.length() == 1) {
            Preconditions.checkArgument("/".equals(str), "service path must equal \"/\" if it is of length 1.");
            return "";
        }
        if (str.length() <= 0) {
            return str;
        }
        if (!str.endsWith("/")) {
            str = str + "/";
        }
        return str.startsWith("/") ? str.substring(1) : str;
    }

    public final BatchRequest batch() {
        return batch(null);
    }

    public final BatchRequest batch(HttpRequestInitializer httpRequestInitializer) {
        BatchRequest batchRequest = new BatchRequest(getRequestFactory().getTransport(), httpRequestInitializer);
        if (Strings.isNullOrEmpty(this.batchPath)) {
            batchRequest.setBatchUrl(new GenericUrl(getRootUrl() + Pubsub.DEFAULT_BATCH_PATH));
        } else {
            batchRequest.setBatchUrl(new GenericUrl(getRootUrl() + this.batchPath));
        }
        return batchRequest;
    }

    public final String getApplicationName() {
        return this.applicationName;
    }

    public final String getBaseUrl() {
        return this.rootUrl + this.servicePath;
    }

    public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
        return this.googleClientRequestInitializer;
    }

    public ObjectParser getObjectParser() {
        return this.objectParser;
    }

    public final HttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public final String getRootUrl() {
        return this.rootUrl;
    }

    public final String getServicePath() {
        return this.servicePath;
    }

    public final boolean getSuppressPatternChecks() {
        return this.suppressPatternChecks;
    }

    public final boolean getSuppressRequiredParameterChecks() {
        return this.suppressRequiredParameterChecks;
    }

    protected void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        if (getGoogleClientRequestInitializer() != null) {
            getGoogleClientRequestInitializer().initialize(abstractGoogleClientRequest);
        }
    }
}
