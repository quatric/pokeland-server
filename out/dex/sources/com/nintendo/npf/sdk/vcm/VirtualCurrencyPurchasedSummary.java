package com.nintendo.npf.sdk.vcm;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyPurchasedSummary {
    private int lifeTimePurchasedAmount;
    private double lifeTimePurchasedUSD;
    private Map<String, VirtualCurrencyPurchaseSummaryBySku> lifeTimePurchasesBySKU;
    private int thisDayPurchasedAmount;
    private double thisDayPurchasedUSD;
    private Map<String, VirtualCurrencyPurchaseSummaryBySku> thisDayPurchasesBySKU;
    private int thisMonthPurchasedAmount;
    private double thisMonthPurchasedUSD;
    private Map<String, VirtualCurrencyPurchaseSummaryBySku> thisMonthPurchasesBySKU;
    private String virtualCurrencyName;

    public interface GetAllByMarketCallback {
        void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError);
    }

    public interface RetrievingCallback {
        void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary$a */
    private static class C1077a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1831a = InterfaceC0875a.a.m1072b();
    }

    protected VirtualCurrencyPurchasedSummary(String str, double d, int i, Map<String, VirtualCurrencyPurchaseSummaryBySku> map, double d2, int i2, Map<String, VirtualCurrencyPurchaseSummaryBySku> map2, double d3, int i3, Map<String, VirtualCurrencyPurchaseSummaryBySku> map3) {
        this.virtualCurrencyName = str;
        this.lifeTimePurchasedUSD = d;
        this.lifeTimePurchasedAmount = i;
        this.lifeTimePurchasesBySKU = map;
        this.thisDayPurchasedUSD = d2;
        this.thisDayPurchasedAmount = i2;
        this.thisDayPurchasesBySKU = map2;
        this.thisMonthPurchasedUSD = d3;
        this.thisMonthPurchasedAmount = i3;
        this.thisMonthPurchasesBySKU = map3;
    }

    public static void getAll(int i, final RetrievingCallback retrievingCallback) {
        C1077a.f1831a.mo1060n().m1557a(i, new RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.1
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.RetrievingCallback
            public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
                RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public static void getAllByMarket(int i, String str, final GetAllByMarketCallback getAllByMarketCallback) {
        C1077a.f1831a.mo1060n().m1558a(i, str, new GetAllByMarketCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.2
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
            public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
                GetAllByMarketCallback getAllByMarketCallback2 = getAllByMarketCallback;
                if (getAllByMarketCallback2 != null) {
                    getAllByMarketCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public static void getAllCache(int i, final RetrievingCallback retrievingCallback) {
        C1077a.f1831a.mo1060n().m1559b(i, new RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.3
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.RetrievingCallback
            public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
                RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public static void getAllCacheByMarket(int i, String str, final GetAllByMarketCallback getAllByMarketCallback) {
        C1077a.f1831a.mo1060n().m1560b(i, str, new GetAllByMarketCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.4
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
            public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
                GetAllByMarketCallback getAllByMarketCallback2 = getAllByMarketCallback;
                if (getAllByMarketCallback2 != null) {
                    getAllByMarketCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public int getLifeTimePurchasedAmount() {
        return this.lifeTimePurchasedAmount;
    }

    public double getLifeTimePurchasedUSD() {
        return this.lifeTimePurchasedUSD;
    }

    public Map<String, VirtualCurrencyPurchaseSummaryBySku> getLifeTimePurchasesBySKU() {
        return this.lifeTimePurchasesBySKU;
    }

    public int getThisDayPurchasedAmount() {
        return this.thisDayPurchasedAmount;
    }

    public double getThisDayPurchasedUSD() {
        return this.thisDayPurchasedUSD;
    }

    public Map<String, VirtualCurrencyPurchaseSummaryBySku> getThisDayPurchasesBySKU() {
        return this.thisDayPurchasesBySKU;
    }

    public int getThisMonthPurchasedAmount() {
        return this.thisMonthPurchasedAmount;
    }

    public double getThisMonthPurchasedUSD() {
        return this.thisMonthPurchasedUSD;
    }

    public Map<String, VirtualCurrencyPurchaseSummaryBySku> getThisMonthPurchasesBySKU() {
        return this.thisMonthPurchasesBySKU;
    }

    public String getVirtualCurrencyName() {
        return this.virtualCurrencyName;
    }
}
