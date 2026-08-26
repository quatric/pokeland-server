package com.metaps.analytics.assist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.metaps.common.C0847a;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.d */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractC0801d extends RelativeLayout {

    /* JADX INFO: renamed from: a */
    protected static final String f439a = "assist_type";

    /* JADX INFO: renamed from: b */
    protected static final String f440b = "creative_type";

    /* JADX INFO: renamed from: c */
    protected static final String f441c = "html";

    /* JADX INFO: renamed from: d */
    protected static final String f442d = "ads";

    /* JADX INFO: renamed from: e */
    protected static final String f443e = "promotions";

    /* JADX INFO: renamed from: f */
    protected static final String f444f = "settings";

    /* JADX INFO: renamed from: g */
    protected static final String f445g = "hw_accelerated";

    /* JADX INFO: renamed from: h */
    protected final AppSpot f446h;

    /* JADX INFO: renamed from: i */
    private final String f447i;

    /* JADX INFO: renamed from: j */
    private WebView f448j;

    /* JADX INFO: renamed from: k */
    private boolean f449k;

    /* JADX INFO: renamed from: l */
    private boolean f450l;

    public AbstractC0801d(Context context, AppSpot appSpot, String str, JSONObject jSONObject) throws JSONException {
        super(context);
        this.f449k = true;
        setVisibility(4);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(f444f);
        if (jSONObjectOptJSONObject != null) {
            m673a(jSONObjectOptJSONObject);
        }
        this.f446h = appSpot;
        this.f447i = mo672a(str, jSONObject);
        if (this.f447i == null) {
            this.f450l = false;
            return;
        }
        this.f448j = m676c();
        this.f448j.loadDataWithBaseURL(null, this.f447i, "text/html", "utf-8", null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(appSpot.f386e, appSpot.f385d);
        layoutParams.addRule(13);
        addView(this.f448j, layoutParams);
        if (appSpot.f390i != null) {
            setOnClickListener(appSpot.f390i);
        }
        this.f450l = true;
    }

    /* JADX INFO: renamed from: a */
    protected String mo672a(String str, JSONObject jSONObject) throws JSONException {
        return str;
    }

    /* JADX INFO: renamed from: a */
    protected void m673a(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.has(f445g)) {
            this.f449k = jSONObject.optBoolean(f445g, true);
        }
    }

    /* JADX INFO: renamed from: a */
    protected boolean m674a() {
        return getWindowVisibility() != 8;
    }

    /* JADX INFO: renamed from: b */
    protected abstract void mo675b();

    /* JADX INFO: renamed from: c */
    protected WebView m676c() {
        final WebView webView = new WebView(getContext());
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, getJsObjectName());
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        if (!this.f449k && Build.VERSION.SDK_INT >= 11) {
            webView.setLayerType(1, null);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        webView.setWebChromeClient(new WebChromeClient() { // from class: com.metaps.analytics.assist.d.1

            /* JADX INFO: renamed from: c */
            private View f453c;

            /* JADX INFO: renamed from: d */
            private WebChromeClient.CustomViewCallback f454d;

            @Override // android.webkit.WebChromeClient
            public void onConsoleMessage(String str, int i, String str2) {
                C0847a.m903a(getClass().toString(), "JS console : [" + str + "] at line " + i);
            }

            @Override // android.webkit.WebChromeClient
            public void onHideCustomView() {
                super.onHideCustomView();
                AbstractC0801d.this.removeView(this.f453c);
                this.f454d.onCustomViewHidden();
                webView.setVisibility(0);
                this.f453c = null;
                this.f454d = null;
            }

            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                super.onShowCustomView(view, customViewCallback);
                this.f453c = view;
                this.f454d = customViewCallback;
                webView.setVisibility(8);
                AbstractC0801d.this.addView(view);
            }
        });
        webView.setWebViewClient(new WebViewClient() { // from class: com.metaps.analytics.assist.d.2
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                AbstractC0801d.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                if (AbstractC0801d.this.f446h.f389h) {
                    return true;
                }
                AbstractC0801d.this.f446h.m662a(AppSpotListener.DismissReason.OPEN_URL);
                return true;
            }
        });
        return webView;
    }

    @JavascriptInterface
    public void dismiss() {
        this.f446h.m662a(AppSpotListener.DismissReason.CLOSE_BUTTON);
    }

    abstract String getJsObjectName();

    public boolean isLoaded() {
        return this.f450l;
    }
}
