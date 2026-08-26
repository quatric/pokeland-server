package com.metaps.analytics.assist;

import com.metaps.analytics.AbstractC0814c;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.h */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0805h extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f499f = "spot_code";

    /* JADX INFO: renamed from: g */
    private static final String f500g = "impression_id";

    /* JADX INFO: renamed from: h */
    private static final String f501h = "click_id";

    /* JADX INFO: renamed from: i */
    private static final String f502i = "fill_empty";

    /* JADX INFO: renamed from: j */
    private static final String f503j = "target";

    /* JADX INFO: renamed from: k */
    private String f504k;

    /* JADX INFO: renamed from: l */
    private C0809l f505l;

    public C0805h(String str, C0809l c0809l) {
        super(AbstractC0814c.a.HOUSE_AD_CLICK);
        this.f504k = str;
        this.f505l = c0809l;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    public void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f499f, this.f504k);
        C0809l c0809l = this.f505l;
        if (c0809l != null) {
            jSONObject.put(f500g, c0809l.m722i());
            jSONObject.put(f501h, this.f505l.m727n());
            if (this.f505l.m724k()) {
                jSONObject.put(f502i, 1);
            }
            jSONObject.put(f503j, this.f505l.m731r());
        }
    }
}
