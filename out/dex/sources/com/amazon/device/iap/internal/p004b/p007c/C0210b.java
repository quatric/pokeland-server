package com.amazon.device.iap.internal.p004b.p007c;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.model.ProductBuilder;
import com.amazon.device.iap.internal.model.ProductDataResponseBuilder;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.Product;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.ProductType;
import com.amazon.venezia.command.SuccessResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.c.b */
/* JADX INFO: compiled from: GetItemDataCommandV1.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0210b extends AbstractC0211c {

    /* JADX INFO: renamed from: b */
    private static final String f173b = "b";

    public C0210b(C0218e c0218e, Set<String> set) {
        super(c0218e, "1.0", set);
    }

    /* JADX INFO: renamed from: a */
    private Product m334a(String str, Map map) throws IllegalArgumentException {
        String str2 = (String) map.get(str);
        try {
            JSONObject jSONObject = new JSONObject(str2);
            ProductType productTypeValueOf = ProductType.valueOf(jSONObject.getString("itemType").toUpperCase());
            String string = jSONObject.getString("description");
            String strOptString = jSONObject.optString(FirebaseAnalytics.Param.PRICE);
            return new ProductBuilder().setSku(str).setProductType(productTypeValueOf).setDescription(string).setPrice(strOptString).setSmallIconUrl(jSONObject.getString("iconUrl")).setTitle(jSONObject.getString(C0856j.f955a)).setCoinsRewardAmount(jSONObject.optInt("coinsRewardAmount", 0)).build();
        } catch (JSONException unused) {
            throw new IllegalArgumentException("error in parsing json string" + str2);
        }
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws RemoteException, KiwiException {
        Map data = successResult.getData();
        C0246e.m412a(f173b, "data: " + data);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        HashMap map = new HashMap();
        for (String str : this.f174a) {
            if (data.containsKey(str)) {
                try {
                    map.put(str, m334a(str, data));
                } catch (IllegalArgumentException e) {
                    linkedHashSet.add(str);
                    C0246e.m414b(f173b, "Error parsing JSON for SKU " + str + ": " + e.getMessage());
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
