package com.google.firebase;

import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.messaging.MessageForwardingService;
import com.unity3d.player.UnityPlayerActivity;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class MessagingUnityPlayerActivity extends UnityPlayerActivity {
    private static final String EXTRA_FROM = "google.message_id";
    private static final String EXTRA_MESSAGE_ID_KEY = "google.message_id";
    private static final String EXTRA_MESSAGE_ID_KEY_SERVER = "message_id";

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        if (this.mUnityPlayer != null) {
            this.mUnityPlayer.quit();
            this.mUnityPlayer = null;
        }
        super.onCreate(bundle);
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        String string = extras.getString("google.message_id");
        String string2 = extras.getString("google.message_id");
        if (string2 == null) {
            string2 = extras.getString(EXTRA_MESSAGE_ID_KEY_SERVER);
        }
        if (string != null && string2 != null) {
            Intent intent2 = new Intent(this, (Class<?>) MessageForwardingService.class);
            intent2.setAction(MessageForwardingService.ACTION_REMOTE_INTENT);
            intent2.putExtras(intent);
            intent2.setData(intent.getData());
            startService(intent2);
        }
        setIntent(intent);
    }
}
