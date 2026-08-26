package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class BaaSUserSwitchEventHandler implements BaaSUser.SwitchByNintendoAccountCallback {

    /* JADX INFO: renamed from: a */
    private long f1509a;

    /* JADX INFO: renamed from: b */
    private long f1510b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.cpp.BaaSUserSwitchEventHandler$a */
    private static class C1008a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1511a = InterfaceC0875a.a.m1072b();
    }

    public BaaSUserSwitchEventHandler() {
        this.f1509a = -1L;
        this.f1510b = -1L;
    }

    public BaaSUserSwitchEventHandler(long j, long j2) {
        this.f1509a = -1L;
        this.f1510b = -1L;
        this.f1509a = j;
        this.f1510b = j2;
    }

    private static native void onSwitchBaaSUserCallback(long j, long j2, String str, String str2, String str3, String str4, String str5);

    public static void retryPendingSwitchByNintendoAccount2(long j, long j2, Activity activity) {
        C1008a.f1511a.mo1050d().m1628a(C1008a.f1511a.mo1048b().m1665a(), new BaaSUserSwitchEventHandler(j, j2));
    }

    public static void switchByNintendoAccount(long j, long j2, Activity activity, byte[] bArr) {
        C1008a.f1511a.mo1050d().m1626a(C1008a.f1511a.mo1048b().m1665a(), activity, NintendoAccountEventHandler.parseScope(new String(bArr)), new BaaSUserSwitchEventHandler(j, j2));
    }

    public static void switchByNintendoAccount2(long j, long j2, Activity activity, byte[] bArr) {
        C1008a.f1511a.mo1050d().m1632b(C1008a.f1511a.mo1048b().m1665a(), activity, NintendoAccountEventHandler.parseScope(new String(bArr)), new BaaSUserSwitchEventHandler(j, j2));
    }

    @Override // com.nintendo.npf.sdk.user.BaaSUser.SwitchByNintendoAccountCallback
    public void onComplete(String str, String str2, NintendoAccount nintendoAccount, NPFError nPFError) {
        String string;
        String string2;
        String string3 = null;
        try {
            string = NativeBridgeUtil.toJsonFromBaaSUser(C1008a.f1511a.mo1048b().m1665a()).toString();
            if (nintendoAccount != null) {
                try {
                    string2 = NativeBridgeUtil.toJsonFromNintendoAccount(nintendoAccount).toString();
                } catch (JSONException e) {
                    e = e;
                    string2 = null;
                    e.printStackTrace();
                    onSwitchBaaSUserCallback(this.f1509a, this.f1510b, str, str2, string, string2, string3);
                }
            } else {
                string2 = null;
            }
            if (nPFError != null) {
                try {
                    string3 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                }
            }
        } catch (JSONException e3) {
            e = e3;
            string = null;
            string2 = null;
        }
        onSwitchBaaSUserCallback(this.f1509a, this.f1510b, str, str2, string, string2, string3);
    }
}
