package com.google.api.client.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class HttpTransport {
    static final Logger LOGGER = Logger.getLogger(HttpTransport.class.getName());
    private static final String[] SUPPORTED_METHODS = {HttpMethods.DELETE, "GET", "POST", HttpMethods.PUT};

    static {
        Arrays.sort(SUPPORTED_METHODS);
    }

    HttpRequest buildRequest() {
        return new HttpRequest(this, null);
    }

    protected abstract LowLevelHttpRequest buildRequest(String str, String str2) throws IOException;

    public final HttpRequestFactory createRequestFactory() {
        return createRequestFactory(null);
    }

    public final HttpRequestFactory createRequestFactory(HttpRequestInitializer httpRequestInitializer) {
        return new HttpRequestFactory(this, httpRequestInitializer);
    }

    public void shutdown() throws IOException {
    }

    public boolean supportsMethod(String str) throws IOException {
        return Arrays.binarySearch(SUPPORTED_METHODS, str) >= 0;
    }
}
