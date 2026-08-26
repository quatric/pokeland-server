package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchaseSummaryBySku;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.o */
/* JADX INFO: compiled from: VirtualCurrencyPurchaseSummaryBySkuMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0943o extends AbstractC0931c<VirtualCurrencyPurchaseSummaryBySku> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.o$a */
    /* JADX INFO: compiled from: VirtualCurrencyPurchaseSummaryBySkuMapper.java */
    private static class a extends VirtualCurrencyPurchaseSummaryBySku {
        private a(String str, int i, int i2, double d) {
            super(str, i, i2, d);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public VirtualCurrencyPurchaseSummaryBySku mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        return new a(jSONObject.getString("sku"), jSONObject.getInt("count"), jSONObject.getInt("purchasedVc"), jSONObject.getDouble("purchasedUsd"));
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(VirtualCurrencyPurchaseSummaryBySku virtualCurrencyPurchaseSummaryBySku) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
