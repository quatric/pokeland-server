package com.nintendo.npf.sdk.internal.p021c;

import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.mynintendo.MissionStatus;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.g */
/* JADX INFO: compiled from: MissionStatusMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0935g extends AbstractC0931c<MissionStatus> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.g$a */
    /* JADX INFO: compiled from: MissionStatusMapper.java */
    private static class a extends MissionStatus {
        private a(String str, String str2, String str3, String str4, int i, boolean z, Integer num, int i2, Integer num2, boolean z2, Long l, Map<String, Long> map) {
            super(str, str2, str3, str4, i, z, num, i2, num2, z2, l, map);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public MissionStatus mo1260b(JSONObject jSONObject) throws JSONException {
        long jLongValue;
        if (jSONObject == null) {
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("mission");
        String str = "id";
        String string = jSONObject2.getString("id");
        String string2 = jSONObject2.getString("key");
        String string3 = jSONObject2.getString(C0856j.f955a);
        String string4 = jSONObject2.getString("description");
        JSONObject jSONObject3 = jSONObject2.getJSONObject("points");
        int i = jSONObject3.getInt(jSONObject3.keys().next());
        boolean zEquals = jSONObject.getString("state").equals("completed");
        int i2 = jSONObject.getInt("numberOfCompletions");
        int i3 = jSONObject2.getInt("totalSteps");
        int i4 = jSONObject.getInt("currentSteps");
        boolean z = jSONObject.getBoolean("limited");
        if (z && m1262a(jSONObject, "limitEndsAt")) {
            jLongValue = jSONObject.getLong("limitEndsAt");
        } else {
            Long l = 0L;
            jLongValue = l.longValue();
        }
        JSONArray jSONArray = jSONObject.getJSONArray("gifts");
        HashMap map = new HashMap();
        int i5 = 0;
        while (i5 < jSONArray.length()) {
            JSONObject jSONObject4 = jSONArray.getJSONObject(i5);
            map.put(jSONObject4.getString(str), Long.valueOf(jSONObject4.getLong("expiresAt")));
            i5++;
            jSONArray = jSONArray;
            str = str;
        }
        return new a(string, string2, string3, string4, i, zEquals, Integer.valueOf(i2), i3, Integer.valueOf(i4), z, Long.valueOf(jLongValue), map);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(MissionStatus missionStatus) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
