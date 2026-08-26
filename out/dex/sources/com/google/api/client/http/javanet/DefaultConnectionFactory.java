package com.google.api.client.http.javanet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class DefaultConnectionFactory implements ConnectionFactory {
    private final Proxy proxy;

    public DefaultConnectionFactory() {
        this(null);
    }

    public DefaultConnectionFactory(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override // com.google.api.client.http.javanet.ConnectionFactory
    public HttpURLConnection openConnection(URL url) throws IOException {
        Proxy proxy = this.proxy;
        return (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
    }
}
