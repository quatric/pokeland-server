package com.metaps.analytics;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.j */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0821j extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f693f = "purchase";

    /* JADX INFO: renamed from: g */
    private static final String f694g = "product_id";

    /* JADX INFO: renamed from: h */
    private static final String f695h = "product_price";

    /* JADX INFO: renamed from: i */
    private static final String f696i = "product_price_currency";

    /* JADX INFO: renamed from: j */
    private String f697j;

    /* JADX INFO: renamed from: k */
    private double f698k;

    /* JADX INFO: renamed from: l */
    private String f699l;

    protected C0821j(String str, double d, String str2) {
        super(AbstractC0814c.a.PURCHASE);
        this.f697j = str;
        this.f698k = d;
        this.f699l = str2;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo764a(C0833v c0833v) {
        c0833v.m824a(this.f698k, this.f699l, mo771e());
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(f694g, this.f697j);
        jSONObject2.put(f695h, this.f698k);
        jSONObject2.put(f696i, this.f699l);
        jSONObject.put(f693f, jSONObject2);
    }
}
