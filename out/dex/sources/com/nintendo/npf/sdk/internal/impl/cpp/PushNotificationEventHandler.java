package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.notification.PushNotificationChannel;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PushNotificationEventHandler implements PushNotificationChannel.GetDeviceTokenCallback, PushNotificationChannel.RegisterDeviceTokenCallback {

    /* JADX INFO: renamed from: a */
    private long f1533a;

    /* JADX INFO: renamed from: b */
    private long f1534b;

    public PushNotificationEventHandler(long j, long j2) {
        this.f1533a = -1L;
        this.f1534b = -1L;
        this.f1533a = j;
        this.f1534b = j2;
    }

    public static void getDeviceToken(long j, long j2) {
        PushNotificationChannel.getDeviceToken(new PushNotificationEventHandler(j, j2));
    }

    private static native void onGetDeviceTokenCompleteCallback(long j, long j2, String str, String str2);

    private static native void onRegisterDeviceTokenCompleteCallback(long j, long j2, String str);

    public static void registerDeviceToken(long j, long j2, String str) {
        PushNotificationChannel.registerDeviceToken(str, new PushNotificationEventHandler(j, j2));
    }

    @Override // com.nintendo.npf.sdk.notification.PushNotificationChannel.GetDeviceTokenCallback
    public void onGetDeviceTokenCallbackComplete(String str, NPFError nPFError) {
        String string = null;
        String str2 = str != null ? str : null;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        onGetDeviceTokenCompleteCallback(this.f1533a, this.f1534b, str2, string);
    }

    @Override // com.nintendo.npf.sdk.notification.PushNotificationChannel.RegisterDeviceTokenCallback
    public void onRegisterDeviceTokenComplete(NPFError nPFError) {
        String string = null;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        onRegisterDeviceTokenCompleteCallback(this.f1533a, this.f1534b, string);
    }
}
