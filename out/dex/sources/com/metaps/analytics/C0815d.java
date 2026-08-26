package com.metaps.analytics;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.d */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0815d extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f628f = "action";

    /* JADX INFO: renamed from: g */
    private static final String f629g = "name";

    /* JADX INFO: renamed from: h */
    private static final String f630h = "value";

    /* JADX INFO: renamed from: i */
    private String f631i;

    /* JADX INFO: renamed from: j */
    private String f632j;

    protected C0815d(String str) {
        super(AbstractC0814c.a.ACTION);
        this.f631i = str == null ? "" : str;
    }

    protected C0815d(String str, String str2) {
        super(AbstractC0814c.a.ACTION);
        this.f631i = str == null ? "" : str;
        this.f632j = str2 == null ? new JSONObject().toString() : str2;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("name", this.f631i);
        String str = this.f632j;
        if (str != null) {
            jSONObject2.put("value", str);
        }
        jSONObject.put(f628f, jSONObject2);
    }
}
