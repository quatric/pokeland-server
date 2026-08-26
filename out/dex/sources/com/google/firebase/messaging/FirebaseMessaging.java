package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class FirebaseMessaging {
    public static final String INSTANCE_ID_SCOPE = "FCM";
    private static final Pattern zzdw = Pattern.compile("[a-zA-Z0-9-_.~%]{1,900}");
    private static FirebaseMessaging zzdx;
    private final FirebaseInstanceId zzdm;

    private FirebaseMessaging(FirebaseInstanceId firebaseInstanceId) {
        this.zzdm = firebaseInstanceId;
    }

    public static synchronized FirebaseMessaging getInstance() {
        if (zzdx == null) {
            zzdx = new FirebaseMessaging(FirebaseInstanceId.getInstance());
        }
        return zzdx;
    }

    public boolean isAutoInitEnabled() {
        return this.zzdm.zzq();
    }

    public void send(RemoteMessage remoteMessage) {
        if (TextUtils.isEmpty(remoteMessage.getTo())) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Context applicationContext = FirebaseApp.getInstance().getApplicationContext();
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        Intent intent2 = new Intent();
        intent2.setPackage("com.google.example.invalidpackage");
        intent.putExtra("app", PendingIntent.getBroadcast(applicationContext, 0, intent2, 0));
        intent.setPackage("com.google.android.gms");
        intent.putExtras(remoteMessage.zzee);
        applicationContext.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    public void setAutoInitEnabled(boolean z) {
        this.zzdm.zzb(z);
    }

    public Task<Void> subscribeToTopic(String str) {
        if (str != null && str.startsWith("/topics/")) {
            Log.w("FirebaseMessaging", "Format /topics/topic-name is deprecated. Only 'topic-name' should be used in subscribeToTopic.");
            str = str.substring(8);
        }
        if (str != null && zzdw.matcher(str).matches()) {
            FirebaseInstanceId firebaseInstanceId = this.zzdm;
            String strValueOf = String.valueOf(str);
            return firebaseInstanceId.zza(strValueOf.length() != 0 ? "S!".concat(strValueOf) : new String("S!"));
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 78);
        sb.append("Invalid topic name: ");
        sb.append(str);
        sb.append(" does not match the allowed format [a-zA-Z0-9-_.~%]{1,900}");
        throw new IllegalArgumentException(sb.toString());
    }

    public Task<Void> unsubscribeFromTopic(String str) {
        if (str != null && str.startsWith("/topics/")) {
            Log.w("FirebaseMessaging", "Format /topics/topic-name is deprecated. Only 'topic-name' should be used in unsubscribeFromTopic.");
            str = str.substring(8);
        }
        if (str != null && zzdw.matcher(str).matches()) {
            FirebaseInstanceId firebaseInstanceId = this.zzdm;
            String strValueOf = String.valueOf(str);
            return firebaseInstanceId.zza(strValueOf.length() != 0 ? "U!".concat(strValueOf) : new String("U!"));
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 78);
        sb.append("Invalid topic name: ");
        sb.append(str);
        sb.append(" does not match the allowed format [a-zA-Z0-9-_.~%]{1,900}");
        throw new IllegalArgumentException(sb.toString());
    }
}
