package com.nintendo.npf.sdk.analytics;

import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Analytics {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.analytics.Analytics$a */
    private static class C0869a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1013a = InterfaceC0875a.a.m1072b();
    }

    private Analytics() {
    }

    public static boolean isSuspended() {
        return C0869a.f1013a.mo1056j().m1594c();
    }

    public static void reportEvent(String str, String str2, JSONObject jSONObject, JSONObject jSONObject2) {
        C0869a.f1013a.mo1056j().m1586a(str, str2, jSONObject, jSONObject2);
    }

    public static void resume() {
        C0869a.f1013a.mo1056j().m1590b();
    }

    public static void suspend() {
        C0869a.f1013a.mo1056j().m1583a();
    }
}
