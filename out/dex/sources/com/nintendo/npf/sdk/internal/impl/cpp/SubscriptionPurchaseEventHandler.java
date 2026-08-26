package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.subscription.SubscriptionPurchase;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class SubscriptionPurchaseEventHandler implements SubscriptionPurchase.OwnershipsCallback, SubscriptionPurchase.PurchaseCallback, SubscriptionPurchase.PurchasesCallback {

    /* JADX INFO: renamed from: a */
    private long f1537a;

    /* JADX INFO: renamed from: b */
    private long f1538b;

    public SubscriptionPurchaseEventHandler() {
        this.f1537a = -1L;
        this.f1538b = -1L;
    }

    public SubscriptionPurchaseEventHandler(long j, long j2) {
        this.f1537a = -1L;
        this.f1538b = -1L;
        this.f1537a = j;
        this.f1538b = j2;
    }

    public static void getPurchases(long j, long j2) {
        SubscriptionPurchase.getPurchases(new SubscriptionPurchaseEventHandler(j, j2));
    }

    private static native void onOwnershipsCallback(long j, long j2, int i, long j3, String str);

    private static native void onPurchaseCallback(long j, long j2, String str);

    private static native void onPurchasesCallback(long j, long j2, String str, String str2);

    public static void openDeepLink(Activity activity, String str) {
        SubscriptionPurchase.openDeepLink(activity, str);
    }

    public static void openLink(Activity activity) {
        SubscriptionPurchase.openLink(activity);
    }

    public static void purchase(long j, long j2, Activity activity, String str) {
        SubscriptionPurchase.purchase(activity, str, new SubscriptionPurchaseEventHandler(j, j2));
    }

    public static void updateOwnerships(long j, long j2, Activity activity) {
        SubscriptionPurchase.updateOwnerships(activity, new SubscriptionPurchaseEventHandler(j, j2));
    }

    public static void updatePurchases(long j, long j2, Activity activity) {
        SubscriptionPurchase.updatePurchases(activity, new SubscriptionPurchaseEventHandler(j, j2));
    }

    @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.OwnershipsCallback
    public void onComplete(int i, long j, NPFError nPFError) {
        String string = null;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        onOwnershipsCallback(this.f1537a, this.f1538b, i, j, string);
    }

    @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchaseCallback
    public void onComplete(NPFError nPFError) {
        String string = null;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        onPurchaseCallback(this.f1537a, this.f1538b, string);
    }

    @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchasesCallback
    public void onComplete(List<SubscriptionPurchase> list, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (list != null) {
            try {
                string = NativeBridgeUtil.toJsonFromSubscriptionPurchases(list).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onPurchasesCallback(this.f1537a, this.f1538b, str2, string2);
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
        onPurchasesCallback(this.f1537a, this.f1538b, str2, string2);
    }
}
