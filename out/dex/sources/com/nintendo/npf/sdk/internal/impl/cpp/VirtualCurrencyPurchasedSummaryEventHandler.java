package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyPurchasedSummaryEventHandler implements VirtualCurrencyPurchasedSummary.RetrievingCallback {

    /* JADX INFO: renamed from: a */
    private long f1543a;

    /* JADX INFO: renamed from: b */
    private long f1544b;

    public VirtualCurrencyPurchasedSummaryEventHandler() {
        this.f1543a = -1L;
        this.f1544b = -1L;
    }

    public VirtualCurrencyPurchasedSummaryEventHandler(long j, long j2) {
        this.f1543a = -1L;
        this.f1544b = -1L;
        this.f1543a = j;
        this.f1544b = j2;
    }

    public static void getAll(long j, long j2, int i) {
        VirtualCurrencyPurchasedSummary.getAll(i, new VirtualCurrencyPurchasedSummaryEventHandler(j, j2));
    }

    public static void getAllCache(long j, long j2, int i) {
        VirtualCurrencyPurchasedSummary.getAllCache(i, new VirtualCurrencyPurchasedSummaryEventHandler(j, j2));
    }

    private static native void onRetrieveCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.RetrievingCallback
    public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (map != null) {
            try {
                string = NativeBridgeUtil.toJsonFromVCPurchaseSummaries(map).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onRetrieveCallback(this.f1543a, this.f1544b, str2, string2);
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
        onRetrieveCallback(this.f1543a, this.f1544b, str2, string2);
    }
}
