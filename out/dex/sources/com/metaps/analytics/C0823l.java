package com.metaps.analytics;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.l */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0823l extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f702f = "referrer";

    /* JADX INFO: renamed from: g */
    private String f703g;

    protected C0823l(String str) {
        super(AbstractC0814c.a.REFERRER);
        this.f703g = str;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f702f, this.f703g);
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: h */
    protected boolean mo774h() {
        return true;
    }
}
