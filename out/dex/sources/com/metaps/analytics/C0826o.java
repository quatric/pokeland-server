package com.metaps.analytics;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.o */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0826o extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f716f = "spend";

    /* JADX INFO: renamed from: g */
    private static final String f717g = "category";

    /* JADX INFO: renamed from: h */
    private static final String f718h = "name";

    /* JADX INFO: renamed from: i */
    private static final String f719i = "value";

    /* JADX INFO: renamed from: j */
    private String f720j;

    /* JADX INFO: renamed from: k */
    private String f721k;

    /* JADX INFO: renamed from: l */
    private int f722l;

    protected C0826o(String str, String str2, int i) {
        super(AbstractC0814c.a.SPEND);
        this.f720j = str == null ? "" : str;
        this.f721k = str2 == null ? "" : str2;
        this.f722l = i;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(f717g, this.f720j);
        jSONObject2.put("name", this.f721k);
        jSONObject2.put("value", this.f722l);
        jSONObject.put(f716f, jSONObject2);
    }
}
