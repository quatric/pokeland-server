package net.gree.unitywebview;

import android.os.Bundle;
import com.unity3d.player.UnityPlayerActivity;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class CUnityPlayerActivity extends UnityPlayerActivity {
    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFormat(2);
        this.mUnityPlayer = new CUnityPlayer(this);
        setContentView(this.mUnityPlayer);
        this.mUnityPlayer.requestFocus();
    }
}
