package com.metaps.analytics;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.g */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0818g extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f646f = "custom";

    /* JADX INFO: renamed from: g */
    private static final String f647g = "category";

    /* JADX INFO: renamed from: h */
    private static final String f648h = "name";

    /* JADX INFO: renamed from: i */
    private static final String f649i = "value";

    /* JADX INFO: renamed from: j */
    private String f650j;

    /* JADX INFO: renamed from: k */
    private String f651k;

    /* JADX INFO: renamed from: l */
    private int f652l;

    protected C0818g(String str, String str2, int i) {
        super(AbstractC0814c.a.CUSTOM);
        this.f650j = str == null ? "" : str;
        this.f651k = str2 == null ? "" : str2;
        this.f652l = i;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(f647g, this.f650j);
        jSONObject2.put("name", this.f651k);
        jSONObject2.put("value", this.f652l);
        jSONObject.put(f646f, jSONObject2);
    }
}
