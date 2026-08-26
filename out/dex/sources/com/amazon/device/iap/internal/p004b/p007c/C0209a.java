package com.amazon.device.iap.internal.p004b.p007c;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.model.ProductBuilder;
import com.amazon.device.iap.internal.model.ProductDataResponseBuilder;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.internal.util.MetricsHelper;
import com.amazon.device.iap.model.Product;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.ProductType;
import com.amazon.venezia.command.SuccessResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.c.a */
/* JADX INFO: compiled from: GetItemDataCommandV2.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0209a extends AbstractC0211c {

    /* JADX INFO: renamed from: b */
    private static final String f172b = "a";

    public C0209a(C0218e c0218e, Set<String> set) {
        super(c0218e, "2.0", set);
    }

    /* JADX INFO: renamed from: a */
    private Product m333a(String str, Map map) throws IllegalArgumentException {
        JSONObject jSONObjectOptJSONObject;
        String str2 = (String) map.get(str);
        try {
            JSONObject jSONObject = new JSONObject(str2);
            ProductType productTypeValueOf = ProductType.valueOf(jSONObject.getString("itemType").toUpperCase());
            String string = jSONObject.getString("description");
            String strOptString = jSONObject.optString(FirebaseAnalytics.Param.PRICE, null);
            if (C0245d.m411a(strOptString) && (jSONObjectOptJSONObject = jSONObject.optJSONObject("priceJson")) != null) {
                Currency currency = Currency.getInstance(jSONObjectOptJSONObject.getString(FirebaseAnalytics.Param.CURRENCY));
                strOptString = currency.getSymbol() + new BigDecimal(jSONObjectOptJSONObject.getString("value"));
            }
            return new ProductBuilder().setSku(str).setProductType(productTypeValueOf).setDescription(string).setPrice(strOptString).setSmallIconUrl(jSONObject.getString("iconUrl")).setTitle(jSONObject.getString(C0856j.f955a)).setCoinsRewardAmount(jSONObject.optInt("coinsRewardAmount", 0)).build();
        } catch (JSONException unused) {
            throw new IllegalArgumentException("error in parsing json string" + str2);
        }
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws RemoteException, KiwiException {
        Map data = successResult.getData();
        C0246e.m412a(f172b, "data: " + data);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        HashMap map = new HashMap();
        for (String str : this.f174a) {
            if (data.containsKey(str)) {
                try {
                    map.put(str, m333a(str, data));
                } catch (IllegalArgumentException e) {
                    linkedHashSet.add(str);
                    String str2 = (String) data.get(str);
                    MetricsHelper.submitJsonParsingExceptionMetrics(m358c(), str2, f172b + ".onResult()");
                    C0246e.m414b(f172b, "Error parsing JSON for SKU " + str + ": " + e.getMessage());
                }
            } else {
                linkedHashSet.add(str);
            }
        }
        C0218e c0218eB = m355b();
        c0218eB.m342d().m348a(new ProductDataResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(ProductDataResponse.RequestStatus.SUCCESSFUL).setUnavailableSkus(linkedHashSet).setProductData(map).build());
        return true;
    }
}
