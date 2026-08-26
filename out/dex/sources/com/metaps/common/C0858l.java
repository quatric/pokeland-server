package com.metaps.common;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.common.l */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0858l {

    /* JADX INFO: renamed from: a */
    public static final int f980a = 0;

    /* JADX INFO: renamed from: b */
    public static final String f981b = "time.retry";

    /* JADX INFO: renamed from: c */
    public static final String f982c = "time.retry.sleep";

    /* JADX INFO: renamed from: d */
    public static final String f983d = "time.retry.sleep.max";

    /* JADX INFO: renamed from: e */
    public static final String f984e = "time.retry.increase.coefficient";

    /* JADX INFO: renamed from: f */
    public static final String f985f = "time.location.timeout";

    /* JADX INFO: renamed from: h */
    private static final String f987h = "settings";

    /* JADX INFO: renamed from: i */
    private static final String f988i = "urls";

    /* JADX INFO: renamed from: j */
    private static final String f989j = "black_list";

    /* JADX INFO: renamed from: k */
    private static final String f990k = "Unix-Time";

    /* JADX INFO: renamed from: m */
    private Map<String, String> f992m = new HashMap();

    /* JADX INFO: renamed from: n */
    private Map<String, String> f993n = new HashMap();

    /* JADX INFO: renamed from: o */
    private List<String> f994o = new ArrayList();

    /* JADX INFO: renamed from: p */
    private boolean f995p = false;

    /* JADX INFO: renamed from: q */
    private final int f996q;

    /* JADX INFO: renamed from: g */
    protected static final Map<String, String> f986g = new HashMap<String, String>() { // from class: com.metaps.common.l.1

        /* JADX INFO: renamed from: a */
        private static final long f997a = 1;

        {
            put(C0858l.f981b, String.valueOf(86400));
            put(C0858l.f982c, String.valueOf(60));
            put(C0858l.f983d, String.valueOf(600));
            put(C0858l.f984e, String.valueOf(20));
            put(C0858l.f985f, String.valueOf(60));
        }
    };

    /* JADX INFO: renamed from: l */
    private static SparseArray<C0858l> f991l = new SparseArray<>();

    private C0858l(int i) {
        this.f996q = i;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C0858l m1018a(int i) {
        if (f991l.get(i) != null) {
            return f991l.get(i);
        }
        C0858l c0858l = new C0858l(i);
        f991l.put(i, c0858l);
        return c0858l;
    }

    /* JADX INFO: renamed from: a */
    private void m1019a(Map<String, List<String>> map) {
        if (map.containsKey(f990k)) {
            try {
                C0859m.m1032a((long) (Double.parseDouble(map.get(f990k).get(0)) * 1000.0d));
            } catch (NumberFormatException e) {
                C0847a.m905a(C0858l.class.toString(), "Failed to decode Unix-Time from response header", e);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1020a(JSONObject jSONObject) throws JSONException {
        m1022a(jSONObject, f988i, this.f993n);
        m1022a(jSONObject, f987h, this.f992m);
        m1021a(jSONObject, f989j, this.f994o);
    }

    /* JADX INFO: renamed from: a */
    private void m1021a(JSONObject jSONObject, String str, List<String> list) throws JSONException {
        if (jSONObject == null || !jSONObject.has(str)) {
            return;
        }
        JSONArray jSONArray = jSONObject.getJSONArray(str);
        for (int i = 0; i < jSONArray.length(); i++) {
            list.add(jSONArray.getString(i));
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1022a(JSONObject jSONObject, String str, Map<String, String> map) throws JSONException {
        if (jSONObject == null || !jSONObject.has(str)) {
            return;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, jSONObject2.getString(next));
            C0847a.m903a(getClass().toString(), next + " -> " + map.get(next));
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized C0858l m1023a(C0849c c0849c, String str) {
        return m1024a(c0849c, str, false);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public synchronized C0858l m1024a(C0849c c0849c, String str, boolean z) {
        String string;
        String str2;
        if (m1026a() && !z) {
            return this;
        }
        String strM970a = null;
        try {
            if (this.f996q == 0) {
                strM970a = C0854h.m970a(str);
            }
            C0849c.a aVarM918a = c0849c.m918a(strM970a, "application/x-www-form-urlencoded");
            if (aVarM918a.f871f != 200) {
                C0847a.m909b(C0858l.class.toString(), "Error while fetching remote settings : " + aVarM918a.f871f + " - " + aVarM918a.f873h);
            } else {
                if ("451".equals(aVarM918a.f874i)) {
                    C0847a.m911c("Unavailable to use Analytics SDK. code: " + aVarM918a.f874i);
                    C0854h.m972a(true);
                    return this;
                }
                synchronized (this) {
                    m1020a(aVarM918a.f876k);
                    m1019a(aVarM918a.f872g);
                    this.f995p = true;
                }
            }
        } catch (C0848b e) {
            e = e;
            string = C0858l.class.toString();
            str2 = "Failed to fetch remote settings";
            C0847a.m905a(string, str2, e);
        } catch (JSONException e2) {
            e = e2;
            string = C0858l.class.toString();
            str2 = "Failed to decode remote settings";
            C0847a.m905a(string, str2, e);
        }
        return this;
    }

    /* JADX INFO: renamed from: a */
    public String m1025a(String str) {
        String str2 = this.f993n.get(str);
        return (str2 == null || str2.length() == 0) ? "" : str2;
    }

    /* JADX INFO: renamed from: a */
    public boolean m1026a() {
        boolean z;
        synchronized (this) {
            z = this.f995p;
        }
        return z;
    }

    /* JADX INFO: renamed from: b */
    public boolean m1027b(String str) {
        return this.f994o.contains(str);
    }

    /* JADX INFO: renamed from: c */
    public String m1028c(String str) {
        return this.f992m.get(str) != null ? this.f992m.get(str) : f986g.get(str);
    }

    /* JADX INFO: renamed from: d */
    public long m1029d(String str) {
        try {
            return Long.parseLong(m1028c(str));
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    /* JADX INFO: renamed from: e */
    public int m1030e(String str) {
        try {
            return Integer.parseInt(m1028c(str));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }
}
