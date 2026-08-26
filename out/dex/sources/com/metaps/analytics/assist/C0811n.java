package com.metaps.analytics.assist;

import com.metaps.common.C0847a;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.n */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0811n {

    /* JADX INFO: renamed from: a */
    private static final String f575a = "impression_id";

    /* JADX INFO: renamed from: b */
    private static final String f576b = "type";

    /* JADX INFO: renamed from: c */
    private static final String f577c = "code";

    /* JADX INFO: renamed from: d */
    private static final String f578d = "creative_id";

    /* JADX INFO: renamed from: e */
    private static final String f579e = "creative_type";

    /* JADX INFO: renamed from: f */
    private static final String f580f = "creative_url";

    /* JADX INFO: renamed from: g */
    private static final String f581g = "deeplink_url";

    /* JADX INFO: renamed from: h */
    private String f582h;

    /* JADX INFO: renamed from: i */
    private String f583i;

    /* JADX INFO: renamed from: j */
    private int f584j;

    /* JADX INFO: renamed from: k */
    private String f585k;

    /* JADX INFO: renamed from: l */
    private int f586l;

    /* JADX INFO: renamed from: m */
    private int f587m;

    /* JADX INFO: renamed from: n */
    private String f588n;

    /* JADX INFO: renamed from: o */
    private String f589o;

    /* JADX INFO: renamed from: a */
    protected static C0811n m735a(String str, JSONObject jSONObject) {
        if (!jSONObject.has("type") || !jSONObject.has(f577c)) {
            return null;
        }
        try {
            C0811n c0811n = new C0811n();
            c0811n.m738a(str);
            if (jSONObject.has(f575a)) {
                c0811n.m738a(jSONObject.getString(f575a));
            }
            c0811n.m737a(jSONObject.getInt("type"));
            c0811n.m744c(jSONObject.getString(f577c));
            if (jSONObject.has(f578d)) {
                c0811n.m743c(jSONObject.getInt(f578d));
            }
            if (jSONObject.has(f580f)) {
                c0811n.m745d(jSONObject.getString(f580f));
            }
            if (jSONObject.has(f581g)) {
                c0811n.m748e(jSONObject.getString(f581g));
            }
            return c0811n;
        } catch (JSONException e) {
            C0847a.m905a(C0809l.class.toString(), "Failed to decode Promotion", e);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    protected String m736a() {
        return this.f582h;
    }

    /* JADX INFO: renamed from: a */
    protected void m737a(int i) {
        this.f584j = i;
    }

    /* JADX INFO: renamed from: a */
    protected void m738a(String str) {
        this.f582h = str;
    }

    /* JADX INFO: renamed from: b */
    protected String m739b() {
        return this.f583i;
    }

    /* JADX INFO: renamed from: b */
    protected void m740b(int i) {
        this.f587m = i;
    }

    /* JADX INFO: renamed from: b */
    protected void m741b(String str) {
        this.f583i = str;
    }

    /* JADX INFO: renamed from: c */
    protected void m742c() {
        this.f583i = UUID.randomUUID().toString();
    }

    /* JADX INFO: renamed from: c */
    protected void m743c(int i) {
        this.f586l = i;
    }

    /* JADX INFO: renamed from: c */
    protected void m744c(String str) {
        this.f585k = str;
    }

    /* JADX INFO: renamed from: d */
    protected void m745d(String str) {
        this.f588n = str;
    }

    /* JADX INFO: renamed from: d */
    protected boolean m746d() {
        return this.f583i != null;
    }

    /* JADX INFO: renamed from: e */
    protected int m747e() {
        return this.f584j;
    }

    /* JADX INFO: renamed from: e */
    protected void m748e(String str) {
        this.f589o = str;
    }

    /* JADX INFO: renamed from: f */
    protected String m749f() {
        return this.f585k;
    }

    /* JADX INFO: renamed from: g */
    protected int m750g() {
        return this.f587m;
    }

    /* JADX INFO: renamed from: h */
    protected int m751h() {
        return this.f586l;
    }

    /* JADX INFO: renamed from: i */
    protected String m752i() {
        return this.f588n;
    }

    /* JADX INFO: renamed from: j */
    protected String m753j() {
        return this.f589o;
    }

    /* JADX INFO: renamed from: k */
    protected JSONObject m754k() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", m747e());
        jSONObject.put(f577c, m749f());
        jSONObject.put(f579e, m750g());
        jSONObject.put(f578d, m751h());
        return jSONObject;
    }
}
