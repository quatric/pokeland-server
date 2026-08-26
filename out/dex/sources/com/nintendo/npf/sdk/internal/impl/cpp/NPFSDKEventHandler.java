package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Application;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.analytics.Analytics;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NPFSDKEventHandler implements NPFSDK.EventHandler, BaaSUser.AuthorizationCallback {

    /* JADX INFO: renamed from: a */
    private long f1517a;

    /* JADX INFO: renamed from: b */
    private long f1518b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.cpp.NPFSDKEventHandler$a */
    private static class C1010a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1519a = InterfaceC0875a.a.m1072b();
    }

    public NPFSDKEventHandler() {
        this.f1517a = -1L;
        this.f1518b = -1L;
    }

    public NPFSDKEventHandler(long j, long j2) {
        this.f1517a = -1L;
        this.f1518b = -1L;
        this.f1517a = j;
        this.f1518b = j2;
    }

    public static String getAppVersion() {
        return C1010a.f1519a.mo1065s().m1339n();
    }

    public static String getDeviceName() {
        return C1010a.f1519a.mo1065s().m1345t();
    }

    public static String getMarket() {
        return NativeBridgeUtil.getMarket();
    }

    public static String getRuntimeOSVersion() {
        return NativeBridgeUtil.getRuntimeOSVersion();
    }

    public static String getTargetedOS() {
        return NativeBridgeUtil.getTargetedOS();
    }

    public static String getTimeZone() {
        return C1010a.f1519a.mo1065s().m1350y();
    }

    public static int getTimeZoneOffsetMin() {
        return NativeBridgeUtil.getTimeZoneOffsetMin();
    }

    public static void init(Application application, int i) {
        NPFSDK.init(application, new NPFSDKEventHandler());
        if (i > 0) {
            C1010a.f1519a.mo1049c().m1517a(new BaaSUser.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.cpp.NPFSDKEventHandler.1
                @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
                public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
                }
            });
        }
    }

    public static boolean isSuspended() {
        return Analytics.isSuspended();
    }

    private static native void onAuthorizationCallback(long j, long j2, String str, String str2);

    private static native void onBaaSAuthError(String str);

    private static native void onBaaSAuthStart(String str);

    private static native void onBaaSAuthUpdate(String str);

    private static native void onNintendoAccountAuthError(String str);

    private static native void onPendingAuthorizationByNintendoAccount2Jni();

    private static native void onPendingSwitchByNintendoAccount2Jni();

    private static native void onVirtualCurrencyPurchaseProcessError(String str);

    private static native void onVirtualCurrencyPurchaseProcessSuccess(String str);

    public static void reportEvent(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        try {
            Analytics.reportEvent(new String(bArr), new String(bArr2), new JSONObject(new String(bArr3)), new JSONObject(new String(bArr4)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void resume() {
        Analytics.resume();
    }

    public static void retryBaaSAuth(long j, long j2) {
        NPFSDK.retryBaaSAuth(new NPFSDKEventHandler(j, j2));
    }

    public static void retryBaaSAuth(long j, long j2, byte[] bArr, byte[] bArr2) {
        NPFSDK.retryBaaSAuth(new String(bArr), new String(bArr2), new NPFSDKEventHandler(j, j2));
    }

    public static void suspend() {
        Analytics.suspend();
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onBaaSAuthError(NPFError nPFError) {
        try {
            onBaaSAuthError(NativeBridgeUtil.toJsonFromNPFError(nPFError).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onBaaSAuthStart() {
        onBaaSAuthStart("");
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onBaaSAuthUpdate(BaaSUser baaSUser) {
        try {
            onBaaSAuthUpdate(NativeBridgeUtil.toJsonFromBaaSUser(baaSUser).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.nintendo.npf.sdk.user.BaaSUser.AuthorizationCallback
    public void onComplete(BaaSUser baaSUser, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (baaSUser != null) {
            try {
                string = NativeBridgeUtil.toJsonFromBaaSUser(baaSUser).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onAuthorizationCallback(this.f1517a, this.f1518b, str2, string2);
            }
        } else {
            string = null;
        }
        if (nPFError != null) {
            try {
                string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e2) {
                str = string;
                e = e2;
                e.printStackTrace();
                str2 = str;
            }
        }
        str2 = string;
        onAuthorizationCallback(this.f1517a, this.f1518b, str2, string2);
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onNintendoAccountAuthError(NPFError nPFError) {
        try {
            onNintendoAccountAuthError(NativeBridgeUtil.toJsonFromNPFError(nPFError).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onPendingAuthorizationByNintendoAccount2() {
        onPendingAuthorizationByNintendoAccount2Jni();
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onPendingSwitchByNintendoAccount2() {
        onPendingSwitchByNintendoAccount2Jni();
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onVirtualCurrencyPurchaseProcessError(NPFError nPFError) {
        try {
            onVirtualCurrencyPurchaseProcessError(NativeBridgeUtil.toJsonFromNPFError(nPFError).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.EventHandler
    public void onVirtualCurrencyPurchaseProcessSuccess(Map<String, VirtualCurrencyWallet> map) {
        try {
            onVirtualCurrencyPurchaseProcessSuccess(NativeBridgeUtil.toJsonFromVCWallets(map).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
