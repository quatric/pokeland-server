package com.metaps.analytics;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.e */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0816e extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f633f = "attributes";

    /* JADX INFO: renamed from: g */
    private JSONObject f634g;

    protected C0816e(JSONObject jSONObject) {
        super(AbstractC0814c.a.ATTRIBUTES);
        this.f634g = jSONObject;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f633f, this.f634g);
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: h */
    protected boolean mo774h() {
        return true;
    }
}
