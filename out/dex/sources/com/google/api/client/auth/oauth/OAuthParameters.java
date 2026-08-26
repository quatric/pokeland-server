package com.google.api.client.auth.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.util.Beta;
import com.google.api.client.util.escape.PercentEscaper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import kotlin.text.Typography;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
public final class OAuthParameters implements HttpExecuteInterceptor, HttpRequestInitializer {
    public String callback;
    public String consumerKey;
    public String nonce;
    public String realm;
    public String signature;
    public String signatureMethod;
    public OAuthSigner signer;
    public String timestamp;
    public String token;
    public String verifier;
    public String version;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final PercentEscaper ESCAPER = new PercentEscaper("-_.~", false);

    private void appendParameter(StringBuilder sb, String str, String str2) {
        if (str2 != null) {
            sb.append(' ');
            sb.append(escape(str));
            sb.append("=\"");
            sb.append(escape(str2));
            sb.append("\",");
        }
    }

    public static String escape(String str) {
        return ESCAPER.escape(str);
    }

    private void putParameter(TreeMap<String, String> treeMap, String str, Object obj) {
        treeMap.put(escape(str), obj == null ? null : escape(obj.toString()));
    }

    private void putParameterIfValueNotNull(TreeMap<String, String> treeMap, String str, String str2) {
        if (str2 != null) {
            putParameter(treeMap, str, str2);
        }
    }

    public void computeNonce() {
        this.nonce = Long.toHexString(Math.abs(RANDOM.nextLong()));
    }

    public void computeSignature(String str, GenericUrl genericUrl) throws GeneralSecurityException {
        OAuthSigner oAuthSigner = this.signer;
        String signatureMethod = oAuthSigner.getSignatureMethod();
        this.signatureMethod = signatureMethod;
        TreeMap<String, String> treeMap = new TreeMap<>();
        putParameterIfValueNotNull(treeMap, "oauth_callback", this.callback);
        putParameterIfValueNotNull(treeMap, "oauth_consumer_key", this.consumerKey);
        putParameterIfValueNotNull(treeMap, "oauth_nonce", this.nonce);
        putParameterIfValueNotNull(treeMap, "oauth_signature_method", signatureMethod);
        putParameterIfValueNotNull(treeMap, "oauth_timestamp", this.timestamp);
        putParameterIfValueNotNull(treeMap, "oauth_token", this.token);
        putParameterIfValueNotNull(treeMap, "oauth_verifier", this.verifier);
        putParameterIfValueNotNull(treeMap, "oauth_version", this.version);
        for (Map.Entry<String, Object> entry : genericUrl.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                String key = entry.getKey();
                if (value instanceof Collection) {
                    Iterator it = ((Collection) value).iterator();
                    while (it.hasNext()) {
                        putParameter(treeMap, key, it.next());
                    }
                } else {
                    putParameter(treeMap, key, value);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry2 : treeMap.entrySet()) {
            if (z) {
                z = false;
            } else {
                sb.append(Typography.amp);
            }
            sb.append(entry2.getKey());
            String value2 = entry2.getValue();
            if (value2 != null) {
                sb.append('=');
                sb.append(value2);
            }
        }
        String string = sb.toString();
        GenericUrl genericUrl2 = new GenericUrl();
        String scheme = genericUrl.getScheme();
        genericUrl2.setScheme(scheme);
        genericUrl2.setHost(genericUrl.getHost());
        genericUrl2.setPathParts(genericUrl.getPathParts());
        int port = genericUrl.getPort();
        if (("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) {
            port = -1;
        }
        genericUrl2.setPort(port);
        this.signature = oAuthSigner.computeSignature(escape(str) + Typography.amp + escape(genericUrl2.build()) + Typography.amp + escape(string));
    }

    public void computeTimestamp() {
        this.timestamp = Long.toString(System.currentTimeMillis() / 1000);
    }

    public String getAuthorizationHeader() {
        StringBuilder sb = new StringBuilder("OAuth");
        appendParameter(sb, "realm", this.realm);
        appendParameter(sb, "oauth_callback", this.callback);
        appendParameter(sb, "oauth_consumer_key", this.consumerKey);
        appendParameter(sb, "oauth_nonce", this.nonce);
        appendParameter(sb, "oauth_signature", this.signature);
        appendParameter(sb, "oauth_signature_method", this.signatureMethod);
        appendParameter(sb, "oauth_timestamp", this.timestamp);
        appendParameter(sb, "oauth_token", this.token);
        appendParameter(sb, "oauth_verifier", this.verifier);
        appendParameter(sb, "oauth_version", this.version);
        return sb.substring(0, sb.length() - 1);
    }

    @Override // com.google.api.client.http.HttpRequestInitializer
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
    }

    @Override // com.google.api.client.http.HttpExecuteInterceptor
    public void intercept(HttpRequest httpRequest) throws IOException {
        computeNonce();
        computeTimestamp();
        try {
            computeSignature(httpRequest.getRequestMethod(), httpRequest.getUrl());
            httpRequest.getHeaders().setAuthorization(getAuthorizationHeader());
        } catch (GeneralSecurityException e) {
            IOException iOException = new IOException();
            iOException.initCause(e);
            throw iOException;
        }
    }
}
