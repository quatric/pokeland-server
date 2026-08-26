package com.nintendo.npf.sdk.internal.p024f;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.webkit.WebView;
import com.nintendo.npf.sdk.internal.p023e.C0955e;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.f.a */
/* JADX INFO: compiled from: SDKWebView.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0957a extends WebView {

    /* JADX INFO: renamed from: a */
    private static final String f1288a = "a";

    public C0957a(Context context) {
        super(context);
        m1401b(context);
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1400a(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    /* JADX INFO: renamed from: b */
    private void m1401b(Context context) {
        setVerticalScrollbarOverlay(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setLightTouchEnabled(true);
        getSettings().setDomStorageEnabled(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        if (m1400a(context)) {
            getSettings().setLoadWithOverviewMode(true);
            getSettings().setUseWideViewPort(true);
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        String str = "javascript:onOrientationScreen(\"" + configuration.orientation + "\");";
        C0955e.m1391a(f1288a, str);
        loadUrl(str);
    }
}
