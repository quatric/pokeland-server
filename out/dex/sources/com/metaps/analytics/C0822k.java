package com.metaps.analytics;

import com.metaps.common.C0857k;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.k */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0822k extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f700f = "notification_id";

    /* JADX INFO: renamed from: g */
    private String f701g;

    protected C0822k(C0857k c0857k) {
        super(AbstractC0814c.a.READ_NOTIFICATION);
        this.f701g = c0857k.m1011a();
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f700f, this.f701g);
    }
}
