package com.metaps.analytics;

import com.metaps.common.C0847a;
import com.metaps.common.C0849c;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.f */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0817f extends AbstractC0814c {

    /* JADX INFO: renamed from: l */
    private static final String f635l = "duration";

    /* JADX INFO: renamed from: m */
    private static final String f636m = "serial_session_id";

    /* JADX INFO: renamed from: n */
    private static final String f637n = "push_notification_enabled";

    /* JADX INFO: renamed from: o */
    private static final String f638o = "current_page";

    /* JADX INFO: renamed from: p */
    private static final String f639p = "deep_link_url";

    /* JADX INFO: renamed from: f */
    protected long f640f;

    /* JADX INFO: renamed from: g */
    protected String f641g;

    /* JADX INFO: renamed from: h */
    protected boolean f642h;

    /* JADX INFO: renamed from: i */
    protected String f643i;

    /* JADX INFO: renamed from: j */
    protected String f644j;

    /* JADX INFO: renamed from: k */
    protected long f645k;

    protected C0817f(long j, String str, boolean z, String str2, String str3) {
        super(AbstractC0814c.a.BOOTUP);
        this.f640f = j;
        this.f641g = str;
        this.f642h = z;
        this.f643i = str2;
        this.f644j = str3;
        this.f645k = System.currentTimeMillis();
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
        jSONObject.put(f635l, this.f640f);
        jSONObject.put(f636m, this.f641g);
        jSONObject.put(f637n, this.f642h ? 1 : 0);
        String str = this.f643i;
        if (str != null) {
            jSONObject.put(f638o, str);
        }
        String str2 = this.f644j;
        if (str2 != null) {
            jSONObject.put(f639p, str2);
        }
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected boolean mo766a(C0833v c0833v, C0849c.a aVar) {
        int i;
        if (aVar.f876k == null || !aVar.f876k.has("session_incr_value")) {
            i = 0;
        } else {
            try {
                i = aVar.f876k.getInt("session_incr_value");
                try {
                    c0833v.m835c(i);
                } catch (JSONException e) {
                    e = e;
                    C0847a.m905a(C0817f.class.toString(), "Failed to get session_incr_value", e);
                }
            } catch (JSONException e2) {
                e = e2;
                i = 0;
            }
        }
        return i > 0;
    }

    /* JADX INFO: renamed from: k */
    protected long m777k() {
        return this.f645k;
    }
}
