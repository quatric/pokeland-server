package com.metaps.common;

import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.common.k */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0857k {

    /* JADX INFO: renamed from: a */
    private static final String f968a = "notification_id";

    /* JADX INFO: renamed from: b */
    private static final String f969b = "notification_sound";

    /* JADX INFO: renamed from: c */
    private static final String f970c = "large_icon_url";

    /* JADX INFO: renamed from: d */
    private static final String f971d = "channel_id";

    /* JADX INFO: renamed from: e */
    private static final String f972e = "custom_text";

    /* JADX INFO: renamed from: f */
    private JSONObject f973f;

    /* JADX INFO: renamed from: g */
    private String f974g;

    /* JADX INFO: renamed from: h */
    private boolean f975h;

    /* JADX INFO: renamed from: i */
    private boolean f976i;

    /* JADX INFO: renamed from: j */
    private String f977j;

    /* JADX INFO: renamed from: k */
    private String f978k;

    /* JADX INFO: renamed from: l */
    private String f979l;

    public C0857k(JSONObject jSONObject) {
        boolean z = false;
        this.f975h = false;
        this.f976i = false;
        if (jSONObject == null) {
            return;
        }
        this.f973f = jSONObject;
        this.f974g = jSONObject.optString(f968a, null);
        this.f976i = jSONObject.optBoolean(f969b, false);
        this.f977j = jSONObject.optString(f970c, null);
        this.f978k = jSONObject.optString(f971d, null);
        this.f979l = jSONObject.optString(f972e, null);
        String str = this.f974g;
        if (str != null && str != "") {
            z = true;
        }
        this.f975h = z;
    }

    /* JADX INFO: renamed from: a */
    public String m1011a() {
        return this.f974g;
    }

    /* JADX INFO: renamed from: b */
    public boolean m1012b() {
        return this.f975h;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1013c() {
        return this.f976i;
    }

    /* JADX INFO: renamed from: d */
    public String m1014d() {
        return this.f977j;
    }

    /* JADX INFO: renamed from: e */
    public String m1015e() {
        return this.f978k;
    }

    /* JADX INFO: renamed from: f */
    public String m1016f() {
        return this.f979l;
    }

    /* JADX INFO: renamed from: g */
    public String m1017g() {
        return this.f973f.toString();
    }
}
