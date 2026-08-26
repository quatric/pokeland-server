package com.metaps.analytics;

import com.metaps.common.C0847a;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Campaign {

    /* JADX INFO: renamed from: a */
    private static final String f356a = "attribution";

    /* JADX INFO: renamed from: b */
    private static final String f357b = "campaign";

    /* JADX INFO: renamed from: c */
    private static final String f358c = "network";

    /* JADX INFO: renamed from: d */
    private static final String f359d = "original_request";

    /* JADX INFO: renamed from: e */
    private static final String f360e = "click";

    /* JADX INFO: renamed from: f */
    private static final String f361f = "deeplink";

    /* JADX INFO: renamed from: g */
    private String f362g;

    /* JADX INFO: renamed from: h */
    private String f363h;

    /* JADX INFO: renamed from: i */
    private String f364i;

    /* JADX INFO: renamed from: j */
    private String f365j;

    private Campaign(JSONObject jSONObject) {
        this.f362g = jSONObject.optString("campaign");
        this.f363h = jSONObject.optString(f358c);
        this.f364i = jSONObject.optString(f359d);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(f360e);
        if (jSONObjectOptJSONObject != null) {
            this.f365j = jSONObjectOptJSONObject.optString(f361f);
        }
    }

    /* JADX INFO: renamed from: a */
    protected static Campaign m609a(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optBoolean(f356a, true)) {
                    return new Campaign(jSONObject);
                }
                return null;
            } catch (Exception e) {
                C0847a.m905a(C0817f.class.toString(), "Failed to parse Kochava attributes", e);
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    protected String m610a() {
        StringBuilder sb = new StringBuilder();
        sb.append("campaign:" + this.f362g + "\n");
        sb.append("network:" + this.f363h + "\n");
        sb.append("originalRequest:" + this.f364i + "\n");
        sb.append("deferredDeepLink:" + this.f365j + "\n");
        return sb.toString();
    }

    public String getDeferredDeepLink() {
        return this.f365j;
    }
}
