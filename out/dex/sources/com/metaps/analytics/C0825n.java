package com.metaps.analytics;

import com.metaps.common.C0847a;
import com.metaps.common.C0849c;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.n */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0825n extends AbstractC0814c {

    /* JADX INFO: renamed from: h */
    private static final String f708h = "duration";

    /* JADX INFO: renamed from: i */
    private static final String f709i = "serial_session_id";

    /* JADX INFO: renamed from: j */
    private static final String f710j = "push_notification_enabled";

    /* JADX INFO: renamed from: k */
    private static final String f711k = "current_page";

    /* JADX INFO: renamed from: f */
    protected boolean f712f;

    /* JADX INFO: renamed from: g */
    protected String f713g;

    /* JADX INFO: renamed from: l */
    private long f714l;

    /* JADX INFO: renamed from: m */
    private String f715m;

    protected C0825n(long j, String str, boolean z, String str2) {
        super(AbstractC0814c.a.SESSION);
        this.f714l = j;
        this.f715m = str;
        this.f712f = z;
        this.f713g = str2;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f708h, this.f714l);
        jSONObject.put(f709i, this.f715m);
        jSONObject.put(f710j, this.f712f ? 1 : 0);
        String str = this.f713g;
        if (str != null) {
            jSONObject.put(f711k, str);
        }
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected boolean mo766a(C0833v c0833v, C0849c.a aVar) {
        c0833v.m827a(this.f714l, mo771e());
        if (aVar.f876k == null || !aVar.f876k.has("session_incr_value")) {
            return false;
        }
        try {
            c0833v.m835c(aVar.f876k.getInt("session_incr_value"));
            return false;
        } catch (JSONException e) {
            C0847a.m905a(C0817f.class.toString(), "Failed to get session_incr_value", e);
            return false;
        }
    }
}
