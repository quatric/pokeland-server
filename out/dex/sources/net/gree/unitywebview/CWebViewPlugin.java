package net.gree.unitywebview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.common.net.HttpHeaders;
import com.metaps.common.UnityWrapper;
import com.unity3d.player.UnityPlayer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class CWebViewPlugin {
    private static FrameLayout layout;
    private boolean canGoBack;
    private boolean canGoForward;
    private Hashtable<String, String> mCustomHeaders;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private WebView mWebView;
    private CWebViewPluginInterface mWebViewPlugin;
    private String mWebViewUA;
    private int progress;

    public static boolean IsWebViewAvailable() {
        final Activity activity = UnityPlayer.currentActivity;
        FutureTask futureTask = new FutureTask(new Callable<Boolean>() { // from class: net.gree.unitywebview.CWebViewPlugin.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                boolean z;
                try {
                    new WebView(activity);
                    z = true;
                } catch (Exception unused) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
        activity.runOnUiThread(futureTask);
        try {
            return ((Boolean) futureTask.get()).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public void AddCustomHeader(String str, String str2) {
        Hashtable<String, String> hashtable = this.mCustomHeaders;
        if (hashtable == null) {
            return;
        }
        hashtable.put(str, str2);
    }

    public void ClearCookies() {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
            return;
        }
        CookieSyncManager cookieSyncManagerCreateInstance = CookieSyncManager.createInstance(UnityPlayer.currentActivity);
        cookieSyncManagerCreateInstance.startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
        cookieSyncManagerCreateInstance.stopSync();
        cookieSyncManagerCreateInstance.sync();
    }

    public void ClearCustomHeader() {
        Hashtable<String, String> hashtable = this.mCustomHeaders;
        if (hashtable == null) {
            return;
        }
        hashtable.clear();
    }

    public void Destroy() {
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.4
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                if (CWebViewPlugin.this.mGlobalLayoutListener != null) {
                    activity.getWindow().getDecorView().getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(CWebViewPlugin.this.mGlobalLayoutListener);
                    CWebViewPlugin.this.mGlobalLayoutListener = null;
                }
                CWebViewPlugin.this.mWebView.stopLoading();
                CWebViewPlugin.layout.removeView(CWebViewPlugin.this.mWebView);
                CWebViewPlugin.this.mWebView.destroy();
                CWebViewPlugin.this.mWebView = null;
            }
        });
    }

    public void EvaluateJS(final String str) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.7
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    CWebViewPlugin.this.mWebView.evaluateJavascript(str, null);
                    return;
                }
                CWebViewPlugin.this.mWebView.loadUrl("javascript:" + URLEncoder.encode(str));
            }
        });
    }

    public String GetCookies(String str) {
        return CookieManager.getInstance().getCookie(str);
    }

    public String GetCustomHeaderValue(String str) {
        Hashtable<String, String> hashtable = this.mCustomHeaders;
        if (hashtable != null && hashtable.containsKey(str)) {
            return this.mCustomHeaders.get(str);
        }
        return null;
    }

    public void GoBack() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.8
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                CWebViewPlugin.this.mWebView.goBack();
            }
        });
    }

    public void GoForward() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.9
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                CWebViewPlugin.this.mWebView.goForward();
            }
        });
    }

    public void Init(final String str, final boolean z, final String str2) {
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.2
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView != null) {
                    return;
                }
                CWebViewPlugin.this.mCustomHeaders = new Hashtable();
                final WebView webView = new WebView(activity);
                if (Build.VERSION.SDK_INT >= 19) {
                    try {
                        if ((activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0).flags & 2) != 0) {
                            WebView.setWebContentsDebuggingEnabled(true);
                        }
                    } catch (Exception unused) {
                    }
                }
                webView.setVisibility(8);
                webView.setFocusable(true);
                webView.setFocusableInTouchMode(true);
                webView.setWebChromeClient(new WebChromeClient() { // from class: net.gree.unitywebview.CWebViewPlugin.2.1
                    View videoView;

                    @Override // android.webkit.WebChromeClient
                    public void onHideCustomView() {
                        super.onHideCustomView();
                        if (CWebViewPlugin.layout != null) {
                            CWebViewPlugin.layout.removeView(this.videoView);
                            CWebViewPlugin.layout.setBackgroundColor(0);
                            this.videoView = null;
                        }
                    }

                    @Override // android.webkit.WebChromeClient
                    public void onPermissionRequest(PermissionRequest permissionRequest) {
                        String[] resources = permissionRequest.getResources();
                        for (String str3 : resources) {
                            if (str3.equals("android.webkit.resource.VIDEO_CAPTURE") || str3.equals("android.webkit.resource.AUDIO_CAPTURE")) {
                                permissionRequest.grant(resources);
                                return;
                            }
                        }
                    }

                    @Override // android.webkit.WebChromeClient
                    public void onProgressChanged(WebView webView2, int i) {
                        CWebViewPlugin.this.progress = i;
                    }

                    @Override // android.webkit.WebChromeClient
                    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                        super.onShowCustomView(view, customViewCallback);
                        if (CWebViewPlugin.layout != null) {
                            this.videoView = view;
                            CWebViewPlugin.layout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                            CWebViewPlugin.layout.addView(this.videoView);
                        }
                    }
                });
                CWebViewPlugin.this.mWebViewPlugin = new CWebViewPluginInterface(this, str);
                webView.setWebViewClient(new WebViewClient() { // from class: net.gree.unitywebview.CWebViewPlugin.2.2
                    @Override // android.webkit.WebViewClient
                    public void onLoadResource(WebView webView2, String str3) {
                        CWebViewPlugin.this.canGoBack = webView.canGoBack();
                        CWebViewPlugin.this.canGoForward = webView.canGoForward();
                    }

                    @Override // android.webkit.WebViewClient
                    public void onPageFinished(WebView webView2, String str3) {
                        CWebViewPlugin.this.canGoBack = webView.canGoBack();
                        CWebViewPlugin.this.canGoForward = webView.canGoForward();
                        CWebViewPlugin.this.mWebViewPlugin.call("CallOnLoaded", str3);
                    }

                    @Override // android.webkit.WebViewClient
                    public void onPageStarted(WebView webView2, String str3, Bitmap bitmap) {
                        CWebViewPlugin.this.canGoBack = webView.canGoBack();
                        CWebViewPlugin.this.canGoForward = webView.canGoForward();
                        CWebViewPlugin.this.mWebViewPlugin.call("CallOnStarted", str3);
                    }

                    @Override // android.webkit.WebViewClient
                    public void onReceivedError(WebView webView2, int i, String str3, String str4) {
                        webView.loadUrl("about:blank");
                        CWebViewPlugin.this.canGoBack = webView.canGoBack();
                        CWebViewPlugin.this.canGoForward = webView.canGoForward();
                        CWebViewPlugin.this.mWebViewPlugin.call("CallOnError", i + "\t" + str3 + "\t" + str4);
                    }

                    @Override // android.webkit.WebViewClient
                    public void onReceivedHttpError(WebView webView2, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                        CWebViewPlugin.this.canGoBack = webView.canGoBack();
                        CWebViewPlugin.this.canGoForward = webView.canGoForward();
                        CWebViewPlugin.this.mWebViewPlugin.call("CallOnHttpError", Integer.toString(webResourceResponse.getStatusCode()));
                    }

                    @Override // android.webkit.WebViewClient
                    public WebResourceResponse shouldInterceptRequest(WebView webView2, String str3) {
                        if (CWebViewPlugin.this.mCustomHeaders == null || CWebViewPlugin.this.mCustomHeaders.isEmpty()) {
                            return super.shouldInterceptRequest(webView2, str3);
                        }
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
                            httpURLConnection.setRequestProperty(HttpHeaders.USER_AGENT, CWebViewPlugin.this.mWebViewUA);
                            for (Map.Entry entry : CWebViewPlugin.this.mCustomHeaders.entrySet()) {
                                httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                            }
                            httpURLConnection.connect();
                            return new WebResourceResponse(httpURLConnection.getContentType().split(";", 2)[0], httpURLConnection.getContentEncoding(), httpURLConnection.getInputStream());
                        } catch (Exception unused2) {
                            return super.shouldInterceptRequest(webView2, str3);
                        }
                    }

                    @Override // android.webkit.WebViewClient
                    public boolean shouldOverrideUrlLoading(WebView webView2, String str3) {
                        CWebViewPlugin.this.canGoBack = webView.canGoBack();
                        CWebViewPlugin.this.canGoForward = webView.canGoForward();
                        if (str3.startsWith("http://") || str3.startsWith("https://") || str3.startsWith("file://") || str3.startsWith("javascript:")) {
                            return false;
                        }
                        if (str3.startsWith("unity:")) {
                            CWebViewPlugin.this.mWebViewPlugin.call("CallFromJS", str3.substring(6));
                            return true;
                        }
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str3));
                        if (activity.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                            webView2.getContext().startActivity(intent);
                        }
                        return true;
                    }
                });
                webView.addJavascriptInterface(CWebViewPlugin.this.mWebViewPlugin, UnityWrapper.PLATFORM);
                WebSettings settings = webView.getSettings();
                String str3 = str2;
                if (str3 != null && str3.length() > 0) {
                    settings.setUserAgentString(str2);
                }
                CWebViewPlugin.this.mWebViewUA = settings.getUserAgentString();
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setDisplayZoomControls(false);
                settings.setLoadWithOverviewMode(true);
                settings.setUseWideViewPort(true);
                settings.setJavaScriptEnabled(true);
                if (Build.VERSION.SDK_INT >= 16) {
                    settings.setAllowUniversalAccessFromFileURLs(true);
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    settings.setMediaPlaybackRequiresUserGesture(false);
                }
                settings.setDatabaseEnabled(true);
                settings.setDomStorageEnabled(true);
                settings.setDatabasePath(webView.getContext().getDir("databases", 0).getPath());
                if (z) {
                    webView.setBackgroundColor(0);
                }
                if (CWebViewPlugin.layout == null || CWebViewPlugin.layout.getParent() != activity.findViewById(android.R.id.content)) {
                    FrameLayout unused2 = CWebViewPlugin.layout = new FrameLayout(activity);
                    activity.addContentView(CWebViewPlugin.layout, new ViewGroup.LayoutParams(-1, -1));
                    CWebViewPlugin.layout.setFocusable(true);
                    CWebViewPlugin.layout.setFocusableInTouchMode(true);
                }
                CWebViewPlugin.layout.addView(webView, new FrameLayout.LayoutParams(-1, -1, 0));
                CWebViewPlugin.this.mWebView = webView;
            }
        });
        final View rootView = activity.getWindow().getDecorView().getRootView();
        this.mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: net.gree.unitywebview.CWebViewPlugin.3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height;
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
                try {
                    Point point = new Point();
                    defaultDisplay.getSize(point);
                    height = point.y;
                } catch (NoSuchMethodError unused) {
                    height = defaultDisplay.getHeight();
                }
                if (rootView.getRootView().getHeight() - (rect.bottom - rect.top) > height / 3) {
                    UnityPlayer.UnitySendMessage(str, "SetKeyboardVisible", "true");
                } else {
                    UnityPlayer.UnitySendMessage(str, "SetKeyboardVisible", "false");
                }
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    public boolean IsInitialized() {
        return this.mWebView != null;
    }

    public void LoadHTML(final String str, final String str2) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.6
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                CWebViewPlugin.this.mWebView.loadDataWithBaseURL(str2, str, "text/html", "UTF8", null);
            }
        });
    }

    public void LoadURL(final String str) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.5
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                if (CWebViewPlugin.this.mCustomHeaders == null || CWebViewPlugin.this.mCustomHeaders.isEmpty()) {
                    CWebViewPlugin.this.mWebView.loadUrl(str);
                } else {
                    CWebViewPlugin.this.mWebView.loadUrl(str, CWebViewPlugin.this.mCustomHeaders);
                }
            }
        });
    }

    public void OnApplicationPause(final boolean z) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.12
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                if (z) {
                    CWebViewPlugin.this.mWebView.onPause();
                    CWebViewPlugin.this.mWebView.pauseTimers();
                } else {
                    CWebViewPlugin.this.mWebView.onResume();
                    CWebViewPlugin.this.mWebView.resumeTimers();
                }
            }
        });
    }

    public void RemoveCustomHeader(String str) {
        Hashtable<String, String> hashtable = this.mCustomHeaders;
        if (hashtable != null && hashtable.containsKey(str)) {
            this.mCustomHeaders.remove(str);
        }
    }

    public void SetMargins(int i, int i2, int i3, int i4) {
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1, 0);
        layoutParams.setMargins(i, i2, i3, i4);
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.10
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                CWebViewPlugin.this.mWebView.setLayoutParams(layoutParams);
            }
        });
    }

    public void SetVisibility(final boolean z) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPlugin.11
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPlugin.this.mWebView == null) {
                    return;
                }
                if (!z) {
                    CWebViewPlugin.this.mWebView.setVisibility(8);
                    return;
                }
                CWebViewPlugin.this.mWebView.setVisibility(0);
                CWebViewPlugin.layout.requestFocus();
                CWebViewPlugin.this.mWebView.requestFocus();
            }
        });
    }
}
