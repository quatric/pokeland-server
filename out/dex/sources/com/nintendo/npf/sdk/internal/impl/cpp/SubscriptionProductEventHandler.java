package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.subscription.SubscriptionProduct;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class SubscriptionProductEventHandler implements SubscriptionProduct.GetProductsCallback {

    /* JADX INFO: renamed from: a */
    private long f1535a;

    /* JADX INFO: renamed from: b */
    private long f1536b;

    public SubscriptionProductEventHandler() {
        this.f1535a = -1L;
        this.f1536b = -1L;
    }

    public SubscriptionProductEventHandler(long j, long j2) {
        this.f1535a = -1L;
        this.f1536b = -1L;
        this.f1535a = j;
        this.f1536b = j2;
    }

    public static void getProducts(long j, long j2, Activity activity) {
        SubscriptionProduct.getProducts(activity, new SubscriptionProductEventHandler(j, j2));
    }

    private static native void onRetrieveCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.subscription.SubscriptionProduct.GetProductsCallback
    public void onComplete(List<SubscriptionProduct> list, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (list != null) {
            try {
                string = NativeBridgeUtil.toJsonFromSubscriptionProducts(list).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onRetrieveCallback(this.f1535a, this.f1536b, str2, string2);
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
        onRetrieveCallback(this.f1535a, this.f1536b, str2, string2);
    }
}
