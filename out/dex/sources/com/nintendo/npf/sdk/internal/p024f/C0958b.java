package com.nintendo.npf.sdk.internal.p024f;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.b */
/* JADX INFO: compiled from: SDKWebViewClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0958b extends WebViewClient {

    /* JADX INFO: renamed from: a */
    private static final String f1289a = "b";

    /* JADX INFO: renamed from: d */
    private DialogC0959c f1292d;

    /* JADX INFO: renamed from: e */
    private boolean f1293e;

    /* JADX INFO: renamed from: f */
    private boolean f1294f;

    /* JADX INFO: renamed from: c */
    private boolean f1291c = false;

    /* JADX INFO: renamed from: g */
    private final InterfaceC0875a f1295g = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: b */
    private Timer f1290b = new Timer();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.b$a */
    /* JADX INFO: compiled from: SDKWebViewClient.java */
    private class a extends TimerTask {

        /* JADX INFO: renamed from: a */
        Activity f1296a;

        a(Activity activity) {
            this.f1296a = activity;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            this.f1296a.runOnUiThread(new Runnable() { // from class: com.nintendo.npf.sdk.internal.f.b.a.1
                @Override // java.lang.Runnable
                public void run() {
                    if (C0958b.this.f1291c) {
                        return;
                    }
                    NPFError.ErrorType errorType = NPFError.ErrorType.NETWORK_ERROR;
                    C0955e.m1395c(C0958b.f1289a, "Timeout occurs while getting web content");
                    C0958b.this.f1295g.mo1058l().m1442a(new C1025o(errorType, 0, "Timeout occurs while getting web content"));
                }
            });
        }
    }

    public C0958b(Activity activity, DialogC0959c dialogC0959c, boolean z) {
        this.f1292d = dialogC0959c;
        this.f1293e = z;
        this.f1290b.schedule(new a(activity), 20000L);
    }

    /* JADX INFO: renamed from: a */
    public void m1405a() {
        Timer timer = this.f1290b;
        if (timer != null) {
            timer.cancel();
            this.f1290b = null;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1406a(boolean z) {
        this.f1294f = z;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        C0955e.m1391a(f1289a, "onPageFinished : " + str);
        m1405a();
        this.f1295g.mo1058l().m1440a();
        this.f1291c = true;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        if (this.f1291c) {
            return;
        }
        NPFError.ErrorType errorType = NPFError.ErrorType.NETWORK_ERROR;
        String str3 = str2 + " | " + i + " | " + str;
        C0955e.m1395c(f1289a, str3);
        this.f1295g.mo1058l().m1442a(new C1025o(errorType, 0, str3));
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        httpAuthHandler.proceed(this.f1295g.mo1065s().m1333h(), this.f1295g.mo1065s().m1334i());
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        onReceivedError(webView, HttpStatusCodes.STATUS_CODE_UNAUTHORIZED, "SSL certification error", sslError.getUrl());
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        C0955e.m1391a(f1289a, "url: " + str);
        Uri uri = Uri.parse(str);
        String scheme = uri.getScheme();
        C0955e.m1391a(f1289a, "scheme: " + scheme);
        if (!scheme.equals("npf" + this.f1295g.mo1065s().m1329d())) {
            if (scheme.indexOf("http") != 0) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.putExtra("com.android.browser.application_id", this.f1292d.getContext().getPackageName());
            this.f1292d.getContext().startActivity(intent);
            return true;
        }
        String host = uri.getHost();
        C0955e.m1391a(f1289a, "method: " + host);
        byte b = -1;
        switch (host.hashCode()) {
            case -2083282955:
                if (host.equals("launchBrowser")) {
                    b = 3;
                }
                break;
            case -121617663:
                if (host.equals("closeWebView")) {
                    b = 0;
                }
                break;
            case 109627663:
                if (host.equals("sound")) {
                    b = 2;
                }
                break;
            case 1475610601:
                if (host.equals("authorize")) {
                    b = 1;
                }
                break;
        }
        if (b == 0) {
            this.f1292d.m1419a(true, true);
        } else if (b != 1) {
            if (b != 2 && b == 3) {
                try {
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(new JSONObject(uri.getQueryParameter("params")).getString(ImagesContract.URL)));
                    intent2.putExtra("com.android.browser.application_id", this.f1292d.getContext().getPackageName());
                    if (this.f1292d.getContext().getPackageManager().queryIntentActivities(intent2, 0).size() > 0) {
                        this.f1292d.getContext().startActivity(intent2);
                    } else {
                        C0955e.m1391a(f1289a, "Browser is not available");
                    }
                } catch (JSONException unused) {
                }
            }
        } else if (!this.f1294f) {
            this.f1294f = true;
            if (uri.getPath() != null) {
                uri.getPath().length();
            }
            this.f1295g.mo1058l().m1443a(webView.getUrl());
            this.f1292d.m1419a(false, true);
        }
        C0955e.m1391a(f1289a, "shouldOverrideUrlLoading: true");
        return true;
    }
}
