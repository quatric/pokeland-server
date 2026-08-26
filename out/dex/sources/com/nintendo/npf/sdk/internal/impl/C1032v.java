package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.metaps.common.C0854h;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0920c;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.notification.PushNotificationChannel;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.v */
/* JADX INFO: compiled from: PushNotificationChannelImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1032v {

    /* JADX INFO: renamed from: a */
    private static final String f1686a = "v";

    /* JADX INFO: renamed from: b */
    private String f1687b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1688c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: b */
    private static String m1742b() {
        String str;
        int rawOffset = TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings();
        if (rawOffset < 0) {
            rawOffset = -rawOffset;
            str = "-";
        } else {
            str = "+";
        }
        return str + String.format(Locale.US, "%1$02d", Integer.valueOf(rawOffset / 3600000)) + ":" + String.format(Locale.US, "%1$02d", Integer.valueOf((rawOffset % 3600000) / 60000));
    }

    /* JADX INFO: renamed from: a */
    public void m1743a() {
        this.f1687b = null;
    }

    /* JADX INFO: renamed from: a */
    public void m1744a(@NonNull final PushNotificationChannel.GetDeviceTokenCallback getDeviceTokenCallback) {
        C0955e.m1393b(f1686a, "getDeviceToken is called");
        BaaSUser baaSUserM1665a = this.f1688c.mo1048b().m1665a();
        if (this.f1688c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1184e().mo1197a(baaSUserM1665a, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.v.2
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        getDeviceTokenCallback.onGetDeviceTokenCallbackComplete(null, nPFError);
                        return;
                    }
                    if (jSONObject != null) {
                        try {
                            C1032v.this.f1687b = jSONObject.getString("deviceToken");
                        } catch (JSONException e) {
                            getDeviceTokenCallback.onGetDeviceTokenCallbackComplete(null, C1025o.m1658a(e));
                            return;
                        }
                    }
                    getDeviceTokenCallback.onGetDeviceTokenCallbackComplete(C1032v.this.f1687b, null);
                }
            });
        } else {
            getDeviceTokenCallback.onGetDeviceTokenCallbackComplete(null, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1745a(final String str, @NonNull final PushNotificationChannel.RegisterDeviceTokenCallback registerDeviceTokenCallback) {
        C0955e.m1393b(f1686a, "registerDeviceToken is called");
        BaaSUser baaSUserM1665a = this.f1688c.mo1048b().m1665a();
        if (!this.f1688c.mo1050d().m1633b(baaSUserM1665a)) {
            registerDeviceTokenCallback.onRegisterDeviceTokenComplete(C1025o.m1656a());
            return;
        }
        if (str == null || str.isEmpty()) {
            registerDeviceTokenCallback.onRegisterDeviceTokenComplete(new C1025o(NPFError.ErrorType.PROCESS_CANCEL, 0, "argument error"));
            return;
        }
        if (str.equals(this.f1687b)) {
            registerDeviceTokenCallback.onRegisterDeviceTokenComplete(null);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("language", C0920c.m1227a());
            jSONObject2.put("zoneinfo", m1742b());
            jSONObject2.put("osName", C0854h.f926h);
            jSONObject2.put("osVersion", this.f1688c.mo1065s().m1344s());
            jSONObject2.put("appVersion", this.f1688c.mo1065s().m1339n());
            jSONObject.put("deviceAttributes", jSONObject2);
            jSONObject.put("deviceToken", str);
            jSONObject.put("market", NPFSDK.getMarket());
            C0905c.m1184e().mo1198a(baaSUserM1665a, jSONObject, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.v.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject3, NPFError nPFError) {
                    if (nPFError == null) {
                        C1032v.this.f1687b = str;
                    }
                    registerDeviceTokenCallback.onRegisterDeviceTokenComplete(nPFError);
                }
            });
        } catch (JSONException e) {
            registerDeviceTokenCallback.onRegisterDeviceTokenComplete(C1025o.m1658a(e));
        }
    }
}
