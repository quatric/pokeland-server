package com.metaps.analytics;

import com.metaps.common.C0847a;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.u */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0832u {

    /* JADX INFO: renamed from: a */
    private static final String f765a = "latitude";

    /* JADX INFO: renamed from: b */
    private static final String f766b = "longitude";

    /* JADX INFO: renamed from: c */
    private static final String f767c = "altitude";

    /* JADX INFO: renamed from: d */
    private static final String f768d = "time";

    /* JADX INFO: renamed from: e */
    private static final String f769e = "accuracy";

    /* JADX INFO: renamed from: f */
    private static final String f770f = "provider";

    /* JADX INFO: renamed from: g */
    private double f771g = 0.0d;

    /* JADX INFO: renamed from: h */
    private double f772h = 0.0d;

    /* JADX INFO: renamed from: i */
    private double f773i = 0.0d;

    /* JADX INFO: renamed from: j */
    private long f774j = 0;

    /* JADX INFO: renamed from: k */
    private float f775k = 0.0f;

    /* JADX INFO: renamed from: l */
    private String f776l = "";

    /* JADX INFO: renamed from: a */
    public static C0832u m814a(JSONObject jSONObject) throws JSONException {
        C0832u c0832u = new C0832u();
        if (jSONObject != null) {
            c0832u.f771g = jSONObject.getDouble(f765a);
            c0832u.f772h = jSONObject.getDouble(f766b);
            c0832u.f773i = jSONObject.getDouble(f767c);
            c0832u.f774j = jSONObject.getLong(f768d);
            c0832u.f775k = (float) jSONObject.getDouble(f769e);
            c0832u.f776l = jSONObject.getString(f770f);
        } else {
            C0847a.m909b(C0832u.class.toString(), "The jsonObject to build PartLocation object was null");
        }
        return c0832u;
    }

    /* JADX INFO: renamed from: a */
    public JSONObject m815a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(f765a, this.f771g);
        jSONObject.put(f766b, this.f772h);
        jSONObject.put(f767c, this.f773i);
        jSONObject.put(f768d, this.f774j);
        jSONObject.put(f769e, this.f775k);
        jSONObject.put(f770f, this.f776l);
        return jSONObject;
    }

    /* JADX INFO: renamed from: a */
    public void m816a(double d) {
        this.f771g = d;
    }

    /* JADX INFO: renamed from: a */
    public void m817a(float f) {
        this.f775k = f;
    }

    /* JADX INFO: renamed from: a */
    public void m818a(long j) {
        this.f774j = j;
    }

    /* JADX INFO: renamed from: a */
    public void m819a(String str) {
        this.f776l = str;
    }

    /* JADX INFO: renamed from: b */
    public void m820b(double d) {
        this.f772h = d;
    }

    /* JADX INFO: renamed from: c */
    public void m821c(double d) {
        this.f773i = d;
    }
}
