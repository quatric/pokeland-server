package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Activity;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyTransaction;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyBundleEventHandler implements VirtualCurrencyBundle.RetrievingCallback, VirtualCurrencyBundle.UnprocessedPurchaseCallback {

    /* JADX INFO: renamed from: c */
    private static Map<String, VirtualCurrencyBundle> f1539c = new ConcurrentHashMap();

    /* JADX INFO: renamed from: a */
    private long f1540a;

    /* JADX INFO: renamed from: b */
    private long f1541b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.cpp.VirtualCurrencyBundleEventHandler$a */
    private static class C1013a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1542a = InterfaceC0875a.a.m1072b();
    }

    public VirtualCurrencyBundleEventHandler() {
        this.f1540a = -1L;
        this.f1541b = -1L;
    }

    public VirtualCurrencyBundleEventHandler(long j, long j2) {
        this.f1540a = -1L;
        this.f1541b = -1L;
        this.f1540a = j;
        this.f1541b = j2;
    }

    public static void checkUnprocessedPurchase(long j, long j2) {
        C1013a.f1542a.mo1059m().m1551a((VirtualCurrencyBundle.UnprocessedPurchaseCallback) new VirtualCurrencyBundleEventHandler(j, j2));
    }

    public static void getAll(long j, long j2) {
        C1013a.f1542a.mo1059m().m1550a((VirtualCurrencyBundle.RetrievingCallback) new VirtualCurrencyBundleEventHandler(j, j2));
    }

    private static native void onRetrieveCallback(long j, long j2, String str, String str2);

    private static native void onUnprocessedPurchaseCallback(long j, long j2, String str, String str2);

    public static void purchase(Activity activity, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        String str = new String(bArr) + "_" + new String(bArr2);
        if (f1539c.containsKey(str)) {
            if (bArr3 == null) {
                f1539c.get(str).purchase(activity, new VirtualCurrencyBundlePurchasedEventHandler());
                return;
            } else {
                f1539c.get(str).purchaseProductInfo(activity, new VirtualCurrencyBundlePurchasedEventHandler(), new String(bArr3));
                return;
            }
        }
        try {
            VirtualCurrencyBundlePurchasedEventHandler.onRetrieveCallback(null, NativeBridgeUtil.toJsonFromNPFError(new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "Can't find the vc bundle : " + new String(str))).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void recoverPurchased() {
        C1013a.f1542a.mo1059m().m1552a(new VirtualCurrencyBundlePurchasedEventHandler());
    }

    @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.UnprocessedPurchaseCallback
    public void onComplete(List<VirtualCurrencyTransaction> list, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (list != null) {
            try {
                string = NativeBridgeUtil.toJsonFromVCTransactions(list).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onUnprocessedPurchaseCallback(this.f1540a, this.f1541b, str2, string2);
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
        onUnprocessedPurchaseCallback(this.f1540a, this.f1541b, str2, string2);
    }

    @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.RetrievingCallback
    public void onComplete(Map<String, List<VirtualCurrencyBundle>> map, NPFError nPFError) {
        String string;
        String string2 = null;
        if (map != null) {
            try {
                string = NativeBridgeUtil.toJsonFromVCBundles(map).toString();
                try {
                    for (String str : map.keySet()) {
                        for (VirtualCurrencyBundle virtualCurrencyBundle : map.get(str)) {
                            f1539c.put(str + "_" + virtualCurrencyBundle.getSKU(), virtualCurrencyBundle);
                        }
                    }
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (JSONException e2) {
                e = e2;
                string = null;
                e.printStackTrace();
            }
        } else {
            string = null;
        }
        if (nPFError != null) {
            string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
        }
        onRetrieveCallback(this.f1540a, this.f1541b, string, string2);
    }
}
