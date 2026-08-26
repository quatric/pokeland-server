package com.amazon.device.iap;

import android.content.Context;
import android.util.Log;
import com.amazon.device.iap.internal.C0239d;
import com.amazon.device.iap.internal.C0240e;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.RequestId;
import java.util.Set;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class PurchasingService {
    public static final boolean IS_SANDBOX_MODE = C0240e.m393a();
    public static final String SDK_VERSION = "2.0.76.4";
    private static final String TAG = "PurchasingService";

    private PurchasingService() {
        Log.i(TAG, "In-App Purchasing SDK initializing. SDK Version 2.0.76.4, IS_SANDBOX_MODE: " + IS_SANDBOX_MODE);
    }

    public static RequestId getProductData(Set<String> set) {
        return C0239d.m381d().m385a(set);
    }

    public static RequestId getPurchaseUpdates(boolean z) {
        return C0239d.m381d().m386a(z);
    }

    public static RequestId getUserData() {
        return C0239d.m381d().m391c();
    }

    public static void notifyFulfillment(String str, FulfillmentResult fulfillmentResult) {
        C0239d.m381d().m389a(str, fulfillmentResult);
    }

    public static RequestId purchase(String str) {
        return C0239d.m381d().m384a(str);
    }

    public static void registerListener(Context context, PurchasingListener purchasingListener) {
        C0239d.m381d().m388a(context, purchasingListener);
    }
}
