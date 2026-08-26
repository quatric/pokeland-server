package com.metaps.analytics.assist;

import com.metaps.analytics.AbstractC0814c;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.j */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0807j extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f513f = "spot_code";

    /* JADX INFO: renamed from: g */
    private static final String f514g = "impression_id";

    /* JADX INFO: renamed from: h */
    private static final String f515h = "click_id";

    /* JADX INFO: renamed from: i */
    private static final String f516i = "target";

    /* JADX INFO: renamed from: j */
    private String f517j;

    /* JADX INFO: renamed from: k */
    private C0811n f518k;

    public C0807j(String str, C0811n c0811n) {
        super(AbstractC0814c.a.PROMOTION_CLICK);
        this.f517j = str;
        this.f518k = c0811n;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    public void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f513f, this.f517j);
        C0811n c0811n = this.f518k;
        if (c0811n != null) {
            jSONObject.put(f514g, c0811n.m736a());
            jSONObject.put(f515h, this.f518k.m739b());
            jSONObject.put(f516i, this.f518k.m754k());
        }
    }
}
