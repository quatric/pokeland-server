package com.nintendo.npf.sdk.internal.p021c;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.n */
/* JADX INFO: compiled from: VirtualCurrencyBundleMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0942n extends AbstractC0931c<VirtualCurrencyBundle> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.n$a */
    /* JADX INFO: compiled from: VirtualCurrencyBundleMapper.java */
    private static class a extends VirtualCurrencyBundle {
        private a(String str, String str2, BigDecimal bigDecimal, String str3, String str4, String str5, BigDecimal bigDecimal2, String str6, int i, int i2, String str7) {
            super(str, str2, bigDecimal, str3, str4, str5, bigDecimal2, str6, i, i2, str7);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public VirtualCurrencyBundle mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("sku");
        String string2 = m1262a(jSONObject, C0856j.f955a) ? jSONObject.getString(C0856j.f955a) : null;
        BigDecimal bigDecimal = m1262a(jSONObject, FirebaseAnalytics.Param.PRICE) ? new BigDecimal(jSONObject.getString(FirebaseAnalytics.Param.PRICE)) : null;
        String string3 = m1262a(jSONObject, "priceCode") ? jSONObject.getString("priceCode") : null;
        return new a(string, string2, bigDecimal, string3, AbstractC0880e.m1124a(string3, bigDecimal), jSONObject.getString("detail"), new BigDecimal(jSONObject.getString("usdPrice")), jSONObject.getString("virtualCurrencyName"), jSONObject.getInt("amount"), jSONObject.getInt("extraAmount"), m1262a(jSONObject, "customAttribute") ? jSONObject.getString("customAttribute") : null);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(VirtualCurrencyBundle virtualCurrencyBundle) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /* JADX INFO: renamed from: b */
    public Map<String, List<VirtualCurrencyBundle>> m1278b(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            ArrayList arrayList = new ArrayList();
            String string = jSONObject.getString("virtualCurrencyName");
            JSONArray jSONArray2 = jSONObject.getJSONArray("items");
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                arrayList.add(mo1260b(jSONArray2.getJSONObject(i2)));
            }
            map.put(string, arrayList);
        }
        return map;
    }
}
