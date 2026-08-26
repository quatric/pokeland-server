package com.unity3d.player;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class UnityWebRequest implements Runnable {

    /* JADX INFO: renamed from: k */
    private static final HostnameVerifier f1973k;

    /* JADX INFO: renamed from: a */
    private long f1974a;

    /* JADX INFO: renamed from: b */
    private String f1975b;

    /* JADX INFO: renamed from: c */
    private String f1976c;

    /* JADX INFO: renamed from: d */
    private Map f1977d;

    /* JADX INFO: renamed from: e */
    private boolean f1978e;

    /* JADX INFO: renamed from: f */
    private int f1979f;

    /* JADX INFO: renamed from: g */
    private long f1980g;

    /* JADX INFO: renamed from: h */
    private long f1981h;

    /* JADX INFO: renamed from: i */
    private boolean f1982i;

    /* JADX INFO: renamed from: j */
    private boolean f1983j;

    static {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
        f1973k = new HostnameVerifier() { // from class: com.unity3d.player.UnityWebRequest.1
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        };
    }

    UnityWebRequest(long j, String str, Map map, String str2, boolean z, int i) {
        this.f1974a = j;
        this.f1975b = str2;
        this.f1976c = str;
        this.f1977d = map;
        this.f1978e = z;
        this.f1979f = i;
    }

    static void clearCookieCache(String str, String str2) {
        CookieStore cookieStore;
        CookieHandler cookieHandler = CookieHandler.getDefault();
        if (cookieHandler == null || !(cookieHandler instanceof CookieManager) || (cookieStore = ((CookieManager) cookieHandler).getCookieStore()) == null) {
            return;
        }
        if (str == null) {
            cookieStore.removeAll();
            return;
        }
        try {
            URI uri = new URI(null, str, str2, null);
            List<HttpCookie> list = cookieStore.get(uri);
            if (list != null) {
                Iterator<HttpCookie> it = list.iterator();
                while (it.hasNext()) {
                    cookieStore.remove(uri, it.next());
                }
            }
        } catch (URISyntaxException unused) {
        }
    }

    private static native void contentLengthCallback(long j, int i);

    private static native boolean downloadCallback(long j, ByteBuffer byteBuffer, int i);

    private static native void errorCallback(long j, int i, String str);

    private boolean hasTimedOut() {
        return this.f1979f > 0 && System.currentTimeMillis() - this.f1980g >= ((long) this.f1979f);
    }

    private static native void headerCallback(long j, String str, String str2);

    private static native void responseCodeCallback(long j, int i);

    private void runSafe() {
        StringBuilder sb;
        String message;
        C1120b.b bVar;
        this.f1980g = System.currentTimeMillis();
        try {
            URL url = new URL(this.f1975b);
            URLConnection uRLConnectionOpenConnection = url.openConnection();
            uRLConnectionOpenConnection.setConnectTimeout(this.f1979f);
            uRLConnectionOpenConnection.setReadTimeout(this.f1979f);
            InputStream inputStream = null;
            if (uRLConnectionOpenConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
                if (this.f1978e) {
                    bVar = new C1120b.b() { // from class: com.unity3d.player.UnityWebRequest.2
                        @Override // com.unity3d.player.C1120b.b, javax.net.ssl.X509TrustManager
                        public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                            if (!UnityWebRequest.this.validateCertificateCallback((x509CertificateArr == null || x509CertificateArr.length <= 0) ? new byte[0] : x509CertificateArr[0].getEncoded())) {
                                throw new CertificateException();
                            }
                        }
                    };
                    httpsURLConnection.setHostnameVerifier(f1973k);
                } else {
                    bVar = null;
                }
                SSLSocketFactory sSLSocketFactoryM1923a = C1120b.m1923a(bVar);
                if (sSLSocketFactoryM1923a != null) {
                    httpsURLConnection.setSSLSocketFactory(sSLSocketFactoryM1923a);
                }
            }
            if (url.getProtocol().equalsIgnoreCase("file") && !url.getHost().isEmpty()) {
                malformattedUrlCallback("file:// must use an absolute path");
                return;
            }
            boolean z = uRLConnectionOpenConnection instanceof HttpURLConnection;
            int i = 0;
            if (z) {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                    httpURLConnection.setRequestMethod(this.f1976c);
                    httpURLConnection.setInstanceFollowRedirects(false);
                    if (this.f1981h > 0) {
                        if (this.f1983j) {
                            httpURLConnection.setChunkedStreamingMode(0);
                        } else {
                            httpURLConnection.setFixedLengthStreamingMode((int) this.f1981h);
                        }
                        if (this.f1982i) {
                            httpURLConnection.addRequestProperty(HttpHeaders.EXPECT, "100-continue");
                        }
                    }
                } catch (ProtocolException e) {
                    badProtocolCallback(e.toString());
                    return;
                }
            }
            Map map = this.f1977d;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    uRLConnectionOpenConnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                }
            }
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(131072);
            if (uploadCallback(null) > 0) {
                uRLConnectionOpenConnection.setDoOutput(true);
                try {
                    OutputStream outputStream = uRLConnectionOpenConnection.getOutputStream();
                    while (true) {
                        int iUploadCallback = uploadCallback(byteBufferAllocateDirect);
                        if (iUploadCallback <= 0) {
                            break;
                        }
                        if (hasTimedOut()) {
                            outputStream.close();
                            errorCallback(this.f1974a, 14, "WebRequest timed out.");
                            return;
                        }
                        outputStream.write(byteBufferAllocateDirect.array(), byteBufferAllocateDirect.arrayOffset(), iUploadCallback);
                    }
                } catch (Exception e2) {
                    errorCallback(e2.toString());
                    return;
                }
            }
            if (z) {
                try {
                    responseCodeCallback(((HttpURLConnection) uRLConnectionOpenConnection).getResponseCode());
                } catch (SocketTimeoutException e3) {
                    errorCallback(this.f1974a, 14, e3.toString());
                    return;
                } catch (UnknownHostException e4) {
                    unknownHostCallback(e4.toString());
                    return;
                } catch (SSLException e5) {
                    sslCannotConnectCallback(e5);
                    return;
                } catch (IOException e6) {
                    errorCallback(e6.toString());
                    return;
                }
            }
            Map<String, List<String>> headerFields = uRLConnectionOpenConnection.getHeaderFields();
            headerCallback(headerFields);
            if ((headerFields == null || !headerFields.containsKey("content-length")) && uRLConnectionOpenConnection.getContentLength() != -1) {
                headerCallback("content-length", String.valueOf(uRLConnectionOpenConnection.getContentLength()));
            }
            if ((headerFields == null || !headerFields.containsKey("content-type")) && uRLConnectionOpenConnection.getContentType() != null) {
                headerCallback("content-type", uRLConnectionOpenConnection.getContentType());
            }
            if (headerFields != null && headerFields.containsKey(HttpHeaders.SET_COOKIE) && CookieHandler.getDefault() != null && (CookieHandler.getDefault() instanceof CookieManager)) {
                CookieStore cookieStore = ((CookieManager) CookieHandler.getDefault()).getCookieStore();
                for (String str : headerFields.get(HttpHeaders.SET_COOKIE)) {
                    try {
                        HttpCookie httpCookie = HttpCookie.parse(str).get(i);
                        if (httpCookie.getPath() != null && !httpCookie.getPath().equals("") && (httpCookie.getDomain() == null || httpCookie.getDomain().equals(url.getHost()))) {
                            URI uri = new URI(url.getProtocol(), url.getHost(), httpCookie.getPath(), null);
                            httpCookie.setDomain(url.getHost());
                            cookieStore.add(uri, httpCookie);
                        }
                    } catch (IllegalArgumentException e7) {
                        sb = new StringBuilder("UnityWebRequest: error parsing cookie '");
                        sb.append(str);
                        sb.append("': ");
                        message = e7.getMessage();
                        sb.append(message);
                        C1125g.Log(6, sb.toString());
                    } catch (URISyntaxException e8) {
                        sb = new StringBuilder("UnityWebRequest: error constructing URI: ");
                        message = e8.getMessage();
                        sb.append(message);
                        C1125g.Log(6, sb.toString());
                    }
                    i = 0;
                }
            }
            contentLengthCallback(uRLConnectionOpenConnection.getContentLength());
            try {
                if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) uRLConnectionOpenConnection;
                    responseCodeCallback(httpURLConnection2.getResponseCode());
                    inputStream = httpURLConnection2.getErrorStream();
                }
                if (inputStream == null) {
                    inputStream = uRLConnectionOpenConnection.getInputStream();
                }
                ReadableByteChannel readableByteChannelNewChannel = Channels.newChannel(inputStream);
                while (true) {
                    int i2 = readableByteChannelNewChannel.read(byteBufferAllocateDirect);
                    if (i2 == -1) {
                        break;
                    }
                    if (hasTimedOut()) {
                        readableByteChannelNewChannel.close();
                        errorCallback(this.f1974a, 14, "WebRequest timed out.");
                        return;
                    } else if (!downloadCallback(byteBufferAllocateDirect, i2)) {
                        break;
                    } else {
                        byteBufferAllocateDirect.clear();
                    }
                }
                readableByteChannelNewChannel.close();
            } catch (SocketTimeoutException e9) {
                errorCallback(this.f1974a, 14, e9.toString());
            } catch (UnknownHostException e10) {
                unknownHostCallback(e10.toString());
            } catch (SSLException e11) {
                sslCannotConnectCallback(e11);
            } catch (IOException e12) {
                errorCallback(this.f1974a, 12, e12.toString());
            } catch (Exception e13) {
                errorCallback(e13.toString());
            }
        } catch (MalformedURLException e14) {
            malformattedUrlCallback(e14.toString());
        } catch (IOException e15) {
            errorCallback(e15.toString());
        }
    }

    private static native int uploadCallback(long j, ByteBuffer byteBuffer);

    private static native boolean validateCertificateCallback(long j, byte[] bArr);

    protected void badProtocolCallback(String str) {
        errorCallback(this.f1974a, 4, str);
    }

    protected void contentLengthCallback(int i) {
        contentLengthCallback(this.f1974a, i);
    }

    protected boolean downloadCallback(ByteBuffer byteBuffer, int i) {
        return downloadCallback(this.f1974a, byteBuffer, i);
    }

    protected void errorCallback(String str) {
        errorCallback(this.f1974a, 2, str);
    }

    protected void headerCallback(String str, String str2) {
        headerCallback(this.f1974a, str, str2);
    }

    protected void headerCallback(Map map) {
        if (map == null || map.size() == 0) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if (str == null) {
                str = "Status";
            }
            Iterator it = ((List) entry.getValue()).iterator();
            while (it.hasNext()) {
                headerCallback(str, (String) it.next());
            }
        }
    }

    protected void malformattedUrlCallback(String str) {
        errorCallback(this.f1974a, 5, str);
    }

    protected void responseCodeCallback(int i) {
        responseCodeCallback(this.f1974a, i);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            runSafe();
        } catch (Exception e) {
            errorCallback(e.toString());
        }
    }

    void setupTransferSettings(long j, boolean z, boolean z2) {
        this.f1981h = j;
        this.f1982i = z;
        this.f1983j = z2;
    }

    protected void sslCannotConnectCallback(SSLException sSLException) {
        int i;
        String string = sSLException.toString();
        for (Throwable cause = sSLException; cause != null; cause = cause.getCause()) {
            if (cause instanceof SSLKeyException) {
                i = 23;
            } else if ((cause instanceof SSLPeerUnverifiedException) || (cause instanceof CertPathValidatorException)) {
                i = 25;
            }
            errorCallback(this.f1974a, i, string);
        }
        i = 16;
        errorCallback(this.f1974a, i, string);
    }

    protected void unknownHostCallback(String str) {
        errorCallback(this.f1974a, 7, str);
    }

    protected int uploadCallback(ByteBuffer byteBuffer) {
        return uploadCallback(this.f1974a, byteBuffer);
    }

    protected boolean validateCertificateCallback(byte[] bArr) {
        return validateCertificateCallback(this.f1974a, bArr);
    }
}
