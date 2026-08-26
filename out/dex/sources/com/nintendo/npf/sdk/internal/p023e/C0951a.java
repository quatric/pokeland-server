package com.nintendo.npf.sdk.internal.p023e;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.e.a */
/* JADX INFO: compiled from: JSONUtil.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0951a {
    /* JADX INFO: renamed from: a */
    public static Map<String, String> m1384a(JSONObject jSONObject) throws JSONException {
        HashMap map = new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (m1385a(jSONObject, next)) {
                map.put(next, jSONObject.getString(next));
            }
        }
        return map;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1385a(JSONObject jSONObject, String str) throws JSONException {
        return jSONObject.has(str) && !jSONObject.isNull(str);
    }
}
