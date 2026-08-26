package com.nintendo.npf.sdk.notification;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PushNotificationChannel {

    public interface GetDeviceTokenCallback {
        void onGetDeviceTokenCallbackComplete(String str, NPFError nPFError);
    }

    public interface RegisterDeviceTokenCallback {
        void onRegisterDeviceTokenComplete(NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.notification.PushNotificationChannel$a */
    private static class C1045a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1777a = InterfaceC0875a.a.m1072b();
    }

    public static void getDeviceToken(final GetDeviceTokenCallback getDeviceTokenCallback) {
        C1045a.f1777a.mo1067u().m1744a(new GetDeviceTokenCallback() { // from class: com.nintendo.npf.sdk.notification.PushNotificationChannel.1
            @Override // com.nintendo.npf.sdk.notification.PushNotificationChannel.GetDeviceTokenCallback
            public void onGetDeviceTokenCallbackComplete(String str, NPFError nPFError) {
                GetDeviceTokenCallback getDeviceTokenCallback2 = getDeviceTokenCallback;
                if (getDeviceTokenCallback2 != null) {
                    getDeviceTokenCallback2.onGetDeviceTokenCallbackComplete(str, nPFError);
                }
            }
        });
    }

    public static void registerDeviceToken(String str, final RegisterDeviceTokenCallback registerDeviceTokenCallback) {
        C1045a.f1777a.mo1067u().m1745a(str, new RegisterDeviceTokenCallback() { // from class: com.nintendo.npf.sdk.notification.PushNotificationChannel.2
            @Override // com.nintendo.npf.sdk.notification.PushNotificationChannel.RegisterDeviceTokenCallback
            public void onRegisterDeviceTokenComplete(NPFError nPFError) {
                RegisterDeviceTokenCallback registerDeviceTokenCallback2 = registerDeviceTokenCallback;
                if (registerDeviceTokenCallback2 != null) {
                    registerDeviceTokenCallback2.onRegisterDeviceTokenComplete(nPFError);
                }
            }
        });
    }
}
