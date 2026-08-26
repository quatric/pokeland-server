package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchaseSummaryBySku;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.p */
/* JADX INFO: compiled from: VirtualCurrencyPurchasedSummaryMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0944p extends AbstractC0931c<VirtualCurrencyPurchasedSummary> {

    /* JADX INFO: renamed from: a */
    private final C0943o f1221a = new C0943o();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.p$a */
    /* JADX INFO: compiled from: VirtualCurrencyPurchasedSummaryMapper.java */
    private static class a extends VirtualCurrencyPurchasedSummary {
        protected a(String str, double d, int i, Map<String, VirtualCurrencyPurchaseSummaryBySku> map, double d2, int i2, Map<String, VirtualCurrencyPurchaseSummaryBySku> map2, double d3, int i3, Map<String, VirtualCurrencyPurchaseSummaryBySku> map3) {
            super(str, d, i, map, d2, i2, map2, d3, i3, map3);
        }
    }

    /* JADX INFO: renamed from: d */
    private Map<String, VirtualCurrencyPurchaseSummaryBySku> m1280d(JSONObject jSONObject) throws JSONException {
        HashMap map = new HashMap();
        if (jSONObject == null) {
            return map;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            VirtualCurrencyPurchaseSummaryBySku virtualCurrencyPurchaseSummaryBySkuMo1260b = this.f1221a.mo1260b(jSONObject.getJSONObject(next));
            if (virtualCurrencyPurchaseSummaryBySkuMo1260b != null) {
                map.put(next, virtualCurrencyPurchaseSummaryBySkuMo1260b);
            }
        }
        return map;
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public VirtualCurrencyPurchasedSummary mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("virtualCurrencyName");
        JSONObject jSONObject2 = jSONObject.getJSONObject("lifetime");
        double d = jSONObject2.getDouble("purchasedUsd");
        int i = jSONObject2.getInt("purchasedVc");
        Map<String, VirtualCurrencyPurchaseSummaryBySku> map = jSONObject2.isNull("purchasesBySku") ? new HashMap<>() : m1280d(jSONObject2.getJSONObject("purchasesBySku"));
        JSONObject jSONObject3 = jSONObject.getJSONObject("thisDay");
        double d2 = jSONObject3.getDouble("purchasedUsd");
        int i2 = jSONObject3.getInt("purchasedVc");
        Map<String, VirtualCurrencyPurchaseSummaryBySku> map2 = jSONObject3.isNull("purchasesBySku") ? new HashMap<>() : m1280d(jSONObject3.getJSONObject("purchasesBySku"));
        JSONObject jSONObject4 = jSONObject.getJSONObject("thisMonth");
        return new a(string, d, i, map, d2, i2, map2, jSONObject4.getDouble("purchasedUsd"), jSONObject4.getInt("purchasedVc"), jSONObject4.isNull("purchasesBySku") ? new HashMap<>() : m1280d(jSONObject4.getJSONObject("purchasesBySku")));
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(VirtualCurrencyPurchasedSummary virtualCurrencyPurchasedSummary) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
