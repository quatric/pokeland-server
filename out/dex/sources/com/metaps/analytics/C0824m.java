package com.metaps.analytics;

import com.metaps.common.C0847a;
import com.metaps.common.C0859m;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.m */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class C0824m extends AbstractC0814c {

    /* JADX INFO: renamed from: f */
    private static final String f704f = "type";

    /* JADX INFO: renamed from: g */
    private static final String f705g = "content";

    /* JADX INFO: renamed from: h */
    private JSONObject f706h;

    /* JADX INFO: renamed from: i */
    private String f707i;

    protected C0824m(AbstractC0814c.a aVar, JSONObject jSONObject) {
        super(aVar);
        this.f706h = jSONObject;
        m765a(this.f706h.has("time_not_sync_with_server"));
    }

    /* JADX INFO: renamed from: b */
    protected static C0824m m799b(JSONObject jSONObject) throws JSONException {
        return new C0824m(AbstractC0814c.a.values()[jSONObject.getInt("type")], jSONObject.getJSONObject("content"));
    }

    /* JADX INFO: renamed from: l */
    private int m800l() {
        try {
            return this.f706h.getInt("event_seq");
        } catch (JSONException unused) {
            C0847a.m909b(C0824m.class.toString(), "Failed to get event seq from JSON");
            return 0;
        }
    }

    @Override // com.metaps.analytics.AbstractC0814c, java.lang.Comparable
    /* JADX INFO: renamed from: a */
    public int compareTo(AbstractC0814c abstractC0814c) {
        if (abstractC0814c instanceof C0824m) {
            return mo771e() == abstractC0814c.mo771e() ? m800l() - ((C0824m) abstractC0814c).m800l() : (int) (mo771e() - abstractC0814c.mo771e());
        }
        return -1;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: a */
    protected void mo694a(JSONObject jSONObject) throws JSONException {
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: d */
    protected String mo770d() {
        try {
            return this.f706h.getString("event_id");
        } catch (JSONException unused) {
            C0847a.m909b(C0824m.class.toString(), "Failed to get event id from JSON");
            return "";
        }
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: e */
    protected long mo771e() {
        try {
            return m776j() ? C0859m.m1035c(this.f706h.getLong("event_time")) : this.f706h.getLong("event_time");
        } catch (JSONException unused) {
            C0847a.m909b(C0824m.class.toString(), "Failed to get event time from JSON");
            return 0L;
        }
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: f */
    protected boolean mo772f() {
        return false;
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: g */
    protected void mo773g() {
        try {
            this.f706h.put("event_time", C0859m.m1035c(this.f706h.getLong("event_time")));
        } catch (JSONException e) {
            C0847a.m905a(C0824m.class.toString(), "Failed to update synchronized event time", e);
        }
    }

    @Override // com.metaps.analytics.AbstractC0814c
    /* JADX INFO: renamed from: i */
    protected JSONObject mo775i() throws JSONException {
        if (m776j()) {
            this.f706h.put("time_not_sync_with_server", m776j());
        } else {
            this.f706h.remove("time_not_sync_with_server");
        }
        return this.f706h;
    }

    /* JADX INFO: renamed from: k */
    protected JSONObject m801k() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.f606e.ordinal());
        jSONObject.put("content", this.f706h);
        return jSONObject;
    }
}
