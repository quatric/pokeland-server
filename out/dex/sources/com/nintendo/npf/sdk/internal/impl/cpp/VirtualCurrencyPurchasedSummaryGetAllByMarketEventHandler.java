package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyPurchasedSummaryGetAllByMarketEventHandler implements VirtualCurrencyPurchasedSummary.GetAllByMarketCallback {

    /* JADX INFO: renamed from: a */
    private long f1545a;

    /* JADX INFO: renamed from: b */
    private long f1546b;

    public VirtualCurrencyPurchasedSummaryGetAllByMarketEventHandler() {
        this.f1545a = -1L;
        this.f1546b = -1L;
    }

    public VirtualCurrencyPurchasedSummaryGetAllByMarketEventHandler(long j, long j2) {
        this.f1545a = -1L;
        this.f1546b = -1L;
        this.f1545a = j;
        this.f1546b = j2;
    }

    public static void getAllByMarket(long j, long j2, int i, String str) {
        VirtualCurrencyPurchasedSummary.getAllByMarket(i, str, new VirtualCurrencyPurchasedSummaryGetAllByMarketEventHandler(j, j2));
    }

    public static void getAllCacheByMarket(long j, long j2, int i, String str) {
        VirtualCurrencyPurchasedSummary.getAllCacheByMarket(i, str, new VirtualCurrencyPurchasedSummaryGetAllByMarketEventHandler(j, j2));
    }

    private static native void onGetAllByMarketCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
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
                onGetAllByMarketCallback(this.f1545a, this.f1546b, str2, string2);
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
        onGetAllByMarketCallback(this.f1545a, this.f1546b, str2, string2);
    }
}
