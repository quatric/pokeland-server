package net.gree.unitywebview;

import android.webkit.JavascriptInterface;
import com.unity3d.player.UnityPlayer;

/* JADX INFO: compiled from: CWebViewPlugin.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class CWebViewPluginInterface {
    private String mGameObject;
    private CWebViewPlugin mPlugin;

    public CWebViewPluginInterface(CWebViewPlugin cWebViewPlugin, String str) {
        this.mPlugin = cWebViewPlugin;
        this.mGameObject = str;
    }

    @JavascriptInterface
    public void call(String str) {
        call("CallFromJS", str);
    }

    public void call(final String str, final String str2) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() { // from class: net.gree.unitywebview.CWebViewPluginInterface.1
            @Override // java.lang.Runnable
            public void run() {
                if (CWebViewPluginInterface.this.mPlugin.IsInitialized()) {
                    UnityPlayer.UnitySendMessage(CWebViewPluginInterface.this.mGameObject, str, str2);
                }
            }
        });
    }
}
