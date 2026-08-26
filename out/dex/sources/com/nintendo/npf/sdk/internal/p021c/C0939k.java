package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.k */
/* JADX INFO: compiled from: PromoCodeBundleMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0939k extends AbstractC0931c<PromoCodeBundle> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.k$a */
    /* JADX INFO: compiled from: PromoCodeBundleMapper.java */
    public static class a extends PromoCodeBundle {
        public a(String str, String str2) {
            super(str, str2);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public PromoCodeBundle mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        return new a(m1262a(jSONObject, "sku") ? jSONObject.getString("sku") : null, m1262a(jSONObject, "customAttribute") ? jSONObject.getString("customAttribute") : null);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(PromoCodeBundle promoCodeBundle) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
