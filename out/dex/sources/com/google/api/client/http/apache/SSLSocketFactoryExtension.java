package com.google.api.client.http.apache;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class SSLSocketFactoryExtension extends SSLSocketFactory {
    private final javax.net.ssl.SSLSocketFactory socketFactory;

    SSLSocketFactoryExtension(SSLContext sSLContext) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        super((KeyStore) null);
        this.socketFactory = sSLContext.getSocketFactory();
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        return this.socketFactory.createSocket();
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        SSLSocket sSLSocket = (SSLSocket) this.socketFactory.createSocket(socket, str, i, z);
        getHostnameVerifier().verify(str, sSLSocket);
        return sSLSocket;
    }
}
