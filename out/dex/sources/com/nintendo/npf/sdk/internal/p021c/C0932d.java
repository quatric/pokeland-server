package com.nintendo.npf.sdk.internal.p021c;

import com.deploygate.service.DeployGateEvent;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.d */
/* JADX INFO: compiled from: CapabilitiesMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0932d extends AbstractC0931c<C0947b> {

    /* JADX INFO: renamed from: a */
    private int f1217a;

    public C0932d(int i) {
        this.f1217a = i;
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0947b mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        String string = m1262a(jSONObject, "baasHost") ? jSONObject.getString("baasHost") : null;
        boolean z = m1262a(jSONObject, "printLog") ? jSONObject.getBoolean("printLog") : false;
        boolean z2 = m1262a(jSONObject, "debugLog") ? jSONObject.getBoolean("debugLog") : false;
        String string2 = m1262a(jSONObject, "clientId") ? jSONObject.getString("clientId") : null;
        String string3 = m1262a(jSONObject, "basicAuthUser") ? jSONObject.getString("basicAuthUser") : null;
        String string4 = m1262a(jSONObject, "basicAuthPass") ? jSONObject.getString("basicAuthPass") : null;
        boolean z3 = m1262a(jSONObject, "purchaseMock") ? jSONObject.getBoolean("purchaseMock") : false;
        String upperCase = (!m1262a(jSONObject, "marketForSandbox") || jSONObject.getString("marketForSandbox").isEmpty()) ? null : jSONObject.getString("marketForSandbox").toUpperCase();
        String string5 = m1262a(jSONObject, "accountHost") ? jSONObject.getString("accountHost") : null;
        String string6 = m1262a(jSONObject, "accountApiHost") ? jSONObject.getString("accountApiHost") : null;
        String string7 = m1262a(jSONObject, "pointProgramHost") ? jSONObject.getString("pointProgramHost") : null;
        int i = m1262a(jSONObject, "sessionUpdateInterval") ? jSONObject.getInt("sessionUpdateInterval") : 0;
        boolean z4 = m1262a(jSONObject, "useHttp") ? jSONObject.getBoolean("useHttp") : false;
        int i2 = this.f1217a;
        if (m1262a(jSONObject, "readTimeout")) {
            i2 = jSONObject.getInt("readTimeout");
        }
        return new C0947b(string, z, z2, string2, string3, string4, z3, upperCase, string5, string6, string7, i, z4, i2, m1262a(jSONObject, "requestTimeout") ? jSONObject.getInt("requestTimeout") : this.f1217a);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(C0947b c0947b) {
        if (c0947b == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("baasHost", c0947b.m1317a());
            jSONObject.put("sandbox", c0947b.m1312C());
            jSONObject.put("printLog", c0947b.m1326b());
            jSONObject.put("debugLog", c0947b.m1328c());
            jSONObject.put("clientId", c0947b.m1329d());
            jSONObject.put("basicAuthUser", c0947b.m1333h());
            jSONObject.put("basicAuthPass", c0947b.m1334i());
            jSONObject.put("purchaseMock", c0947b.m1335j());
            jSONObject.put("marketForSandbox", c0947b.m1313D());
            jSONObject.put("accountHost", c0947b.m1330e());
            jSONObject.put("accountApiHost", c0947b.m1331f());
            jSONObject.put("pointProgramHost", c0947b.m1332g());
            jSONObject.put("sessionUpdateInterval", c0947b.m1314E());
            jSONObject.put("useHttp", c0947b.m1315F());
            jSONObject.put("readTimeout", c0947b.m1342q());
            jSONObject.put("requestTimeout", c0947b.m1343r());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(DeployGateEvent.EXTRA_SDK_VERSION, c0947b.m1340o());
            jSONObject2.put("buildType", "release");
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("packageName", c0947b.m1337l());
            jSONObject3.put("signatureSHA1", c0947b.m1338m());
            jSONObject3.put("appVersion", c0947b.m1339n());
            JSONObject jSONObjectM1351z = c0947b.m1351z();
            jSONObjectM1351z.remove("appVersion");
            jSONObjectM1351z.remove(DeployGateEvent.EXTRA_SDK_VERSION);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("npf", jSONObject);
            jSONObject4.put("sdk", jSONObject2);
            jSONObject4.put("application", jSONObject3);
            jSONObject4.put("device", jSONObjectM1351z);
            return jSONObject4;
        } catch (JSONException unused) {
            return null;
        }
    }
}
