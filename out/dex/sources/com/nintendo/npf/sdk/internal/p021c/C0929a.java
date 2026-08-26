package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.internal.p022d.C0946a;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.a */
/* JADX INFO: compiled from: AnalyticsConfigMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0929a extends AbstractC0931c<C0946a> {
    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0946a mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        C0946a c0946a = new C0946a();
        if (m1262a(jSONObject, "mode")) {
            c0946a.m1286a(C0946a.a.m1304a(jSONObject.getString("mode")));
        }
        if (m1262a(jSONObject, "expirationTime")) {
            c0946a.m1285a(jSONObject.getLong("expirationTime"));
        }
        if (m1262a(jSONObject, "applicationId")) {
            c0946a.m1287a(jSONObject.getString("applicationId"));
        }
        if (m1262a(jSONObject, "immediateReporting")) {
            c0946a.m1288a(jSONObject.getBoolean("immediateReporting"));
        }
        if (m1262a(jSONObject, "reportingPeriod")) {
            c0946a.m1284a(jSONObject.getInt("reportingPeriod"));
        }
        if (m1262a(jSONObject, "accessToken")) {
            c0946a.m1290b(jSONObject.getString("accessToken"));
        }
        if (m1262a(jSONObject, "topic")) {
            c0946a.m1292c(jSONObject.getString("topic"));
        }
        if (m1262a(jSONObject, "country")) {
            c0946a.m1293d(jSONObject.getString("country"));
        }
        if (m1262a(jSONObject, "region")) {
            c0946a.m1296e(jSONObject.getString("region"));
        }
        if (m1262a(jSONObject, "city")) {
            c0946a.m1298f(jSONObject.getString("city"));
        }
        return c0946a;
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(C0946a c0946a) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
