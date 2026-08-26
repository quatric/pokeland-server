package com.unity3d.player;

import android.os.Build;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: renamed from: com.unity3d.player.b */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C1120b extends SSLSocketFactory {

    /* JADX INFO: renamed from: c */
    private static volatile SSLSocketFactory f2024c;

    /* JADX INFO: renamed from: d */
    private static volatile X509TrustManager f2025d;

    /* JADX INFO: renamed from: e */
    private static final Object f2026e = new Object[0];

    /* JADX INFO: renamed from: f */
    private static final Object f2027f = new Object[0];

    /* JADX INFO: renamed from: g */
    private static final boolean f2028g;

    /* JADX INFO: renamed from: a */
    private final SSLSocketFactory f2029a;

    /* JADX INFO: renamed from: b */
    private final a f2030b;

    /* JADX INFO: renamed from: com.unity3d.player.b$a */
    class a implements HandshakeCompletedListener {
        @Override // javax.net.ssl.HandshakeCompletedListener
        public final void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
            SSLSession session = handshakeCompletedEvent.getSession();
            session.getCipherSuite();
            session.getProtocol();
            try {
                session.getPeerPrincipal().getName();
            } catch (SSLPeerUnverifiedException unused) {
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.b$b */
    public static abstract class b implements X509TrustManager {

        /* JADX INFO: renamed from: a */
        protected X509TrustManager f2031a = C1120b.m1926c();

        @Override // javax.net.ssl.X509TrustManager
        public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            this.f2031a.checkClientTrusted(x509CertificateArr, str);
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            this.f2031a.checkServerTrusted(x509CertificateArr, str);
        }

        @Override // javax.net.ssl.X509TrustManager
        public final X509Certificate[] getAcceptedIssuers() {
            return this.f2031a.getAcceptedIssuers();
        }
    }

    static {
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 20) {
            z = true;
        }
        f2028g = z;
    }

    private C1120b(b[] bVarArr) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, bVarArr, null);
        this.f2029a = sSLContext.getSocketFactory();
        this.f2030b = null;
    }

    /* JADX INFO: renamed from: a */
    private Socket m1922a(Socket socket) {
        if (socket != null && (socket instanceof SSLSocket)) {
            if (f2028g) {
                SSLSocket sSLSocket = (SSLSocket) socket;
                sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
            }
            a aVar = this.f2030b;
            if (aVar != null) {
                ((SSLSocket) socket).addHandshakeCompletedListener(aVar);
            }
        }
        return socket;
    }

    /* JADX INFO: renamed from: a */
    public static SSLSocketFactory m1923a(b bVar) {
        try {
            return bVar == null ? m1925b() : new C1120b(new b[]{bVar});
        } catch (Exception e) {
            C1125g.Log(5, "CustomSSLSocketFactory: Failed to create SSLSocketFactory (" + e.getMessage() + ")");
            return null;
        }
    }

    /* JADX INFO: renamed from: b */
    private static SSLSocketFactory m1925b() {
        synchronized (f2026e) {
            if (f2024c != null) {
                return f2024c;
            }
            C1120b c1120b = new C1120b(null);
            f2024c = c1120b;
            return c1120b;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: c */
    public static X509TrustManager m1926c() {
        synchronized (f2027f) {
            if (f2025d != null) {
                return f2025d;
            }
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                    if (trustManager instanceof X509TrustManager) {
                        X509TrustManager x509TrustManager = (X509TrustManager) trustManager;
                        f2025d = x509TrustManager;
                        return x509TrustManager;
                    }
                }
            } catch (Exception e) {
                C1125g.Log(5, "CustomSSLSocketFactory: Failed to find X509TrustManager (" + e.getMessage() + ")");
            }
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket() {
        return m1922a(this.f2029a.createSocket());
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(String str, int i) {
        return m1922a(this.f2029a.createSocket(str, i));
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        return m1922a(this.f2029a.createSocket(str, i, inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(InetAddress inetAddress, int i) {
        return m1922a(this.f2029a.createSocket(inetAddress, i));
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        return m1922a(this.f2029a.createSocket(inetAddress, i, inetAddress2, i2));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final Socket createSocket(Socket socket, String str, int i, boolean z) {
        return m1922a(this.f2029a.createSocket(socket, str, i, z));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final String[] getDefaultCipherSuites() {
        return this.f2029a.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final String[] getSupportedCipherSuites() {
        return this.f2029a.getSupportedCipherSuites();
    }
}
