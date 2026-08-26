package com.nintendo.npf.sdk.internal.p021c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.c */
/* JADX INFO: compiled from: BaseMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractC0931c<T> {
    /* JADX INFO: renamed from: a */
    public static boolean m1262a(JSONObject jSONObject, String str) {
        return jSONObject.has(str) && !jSONObject.isNull(str);
    }

    /* JADX INFO: renamed from: a */
    public List<T> m1263a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            T tMo1260b = mo1260b(jSONArray.getJSONObject(i));
            if (tMo1260b != null) {
                arrayList.add(tMo1260b);
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: a */
    public JSONArray m1264a(List<T> list) {
        if (list == null) {
            return new JSONArray();
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            JSONObject jSONObjectMo1259a = mo1259a(it.next());
            if (jSONObjectMo1259a != null) {
                jSONArray.put(jSONObjectMo1259a);
            }
        }
        return jSONArray;
    }

    /* JADX INFO: renamed from: a */
    public abstract JSONObject mo1259a(T t);

    /* JADX INFO: renamed from: b */
    public abstract T mo1260b(JSONObject jSONObject) throws JSONException;

    /* JADX INFO: renamed from: c */
    public List<T> m1265c(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null && m1262a(jSONObject, "items")) {
            return m1263a(jSONObject.getJSONArray("items"));
        }
        return Collections.emptyList();
    }
}
