package com.metaps.analytics;

import com.metaps.common.C0847a;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.v */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0833v {

    /* JADX INFO: renamed from: a */
    protected static final String f777a = "installed";

    /* JADX INFO: renamed from: b */
    private static final String f778b = "token";

    /* JADX INFO: renamed from: c */
    private static final String f779c = "adid";

    /* JADX INFO: renamed from: d */
    private static final String f780d = "ad_tracking_enabled";

    /* JADX INFO: renamed from: e */
    private static final String f781e = "device_token";

    /* JADX INFO: renamed from: f */
    private static final String f782f = "fq7";

    /* JADX INFO: renamed from: g */
    private static final String f783g = "fq30";

    /* JADX INFO: renamed from: h */
    private static final String f784h = "session";

    /* JADX INFO: renamed from: i */
    private static final String f785i = "purchase";

    /* JADX INFO: renamed from: j */
    private static final String f786j = "pur_per_cur";

    /* JADX INFO: renamed from: k */
    private String f787k;

    /* JADX INFO: renamed from: l */
    private String f788l;

    /* JADX INFO: renamed from: m */
    private boolean f789m;

    /* JADX INFO: renamed from: n */
    private String f790n;

    /* JADX INFO: renamed from: o */
    private long f791o;

    /* JADX INFO: renamed from: p */
    private int f792p = 0;

    /* JADX INFO: renamed from: q */
    private int f793q = 0;

    /* JADX INFO: renamed from: t */
    private List<b> f796t = new ArrayList();

    /* JADX INFO: renamed from: u */
    private String f797u = null;

    /* JADX INFO: renamed from: v */
    private String f798v = null;

    /* JADX INFO: renamed from: r */
    private c f794r = new c();

    /* JADX INFO: renamed from: s */
    private a f795s = new a();

    /* JADX INFO: renamed from: com.metaps.analytics.v$a */
    static final class a {

        /* JADX INFO: renamed from: a */
        private static final String f799a = "currency";

        /* JADX INFO: renamed from: b */
        private static final String f800b = "total_count";

        /* JADX INFO: renamed from: c */
        private static final String f801c = "total_price";

        /* JADX INFO: renamed from: d */
        private static final String f802d = "last_at";

        /* JADX INFO: renamed from: e */
        private static final String f803e = "last_price";

        /* JADX INFO: renamed from: f */
        private String f804f = "";

        /* JADX INFO: renamed from: g */
        private int f805g;

        /* JADX INFO: renamed from: h */
        private double f806h;

        /* JADX INFO: renamed from: i */
        private long f807i;

        /* JADX INFO: renamed from: j */
        private double f808j;

        protected a() {
        }

        /* JADX INFO: renamed from: a */
        protected static a m846a(JSONObject jSONObject) throws JSONException {
            a aVar = new a();
            if (jSONObject == null) {
                C0847a.m909b(a.class.toString(), "The jsonObject to build PurchaseTotal object was null");
            } else if (jSONObject.has(f800b)) {
                aVar.f804f = jSONObject.getString("currency");
                aVar.f805g = jSONObject.getInt(f800b);
                aVar.f806h = jSONObject.getDouble(f801c);
                aVar.f807i = jSONObject.getLong(f802d);
                aVar.f808j = jSONObject.getDouble(f803e);
            }
            return aVar;
        }

        /* JADX INFO: renamed from: a */
        protected String m847a() {
            return this.f804f;
        }

        /* JADX INFO: renamed from: a */
        protected void m848a(double d) {
            this.f806h = d;
        }

        /* JADX INFO: renamed from: a */
        protected void m849a(double d, String str, long j, b bVar) {
            this.f805g++;
            bVar.m861a(d);
            if (!this.f804f.equals(str)) {
                this.f804f = str;
            }
            this.f806h = bVar.m862b();
            this.f807i = j;
            this.f808j = d;
        }

        /* JADX INFO: renamed from: a */
        protected void m850a(int i) {
            this.f805g = i;
        }

        /* JADX INFO: renamed from: a */
        protected void m851a(long j) {
            this.f807i = j;
        }

        /* JADX INFO: renamed from: a */
        protected void m852a(String str) {
            this.f804f = str;
        }

        /* JADX INFO: renamed from: b */
        protected JSONObject m853b() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            if (this.f805g > 0) {
                jSONObject.put("currency", this.f804f);
                jSONObject.put(f800b, this.f805g);
                jSONObject.put(f801c, this.f806h);
                jSONObject.put(f802d, this.f807i);
                jSONObject.put(f803e, this.f808j);
            }
            return jSONObject;
        }

        /* JADX INFO: renamed from: b */
        protected void m854b(double d) {
            this.f808j = d;
        }

        /* JADX INFO: renamed from: c */
        protected int m855c() {
            return this.f805g;
        }

        /* JADX INFO: renamed from: d */
        protected double m856d() {
            return this.f806h;
        }

        /* JADX INFO: renamed from: e */
        protected long m857e() {
            return this.f807i;
        }

        /* JADX INFO: renamed from: f */
        protected double m858f() {
            return this.f808j;
        }
    }

    /* JADX INFO: renamed from: com.metaps.analytics.v$b */
    static final class b {

        /* JADX INFO: renamed from: a */
        private static final String f809a = "currency";

        /* JADX INFO: renamed from: b */
        private static final String f810b = "total_price";

        /* JADX INFO: renamed from: c */
        private String f811c;

        /* JADX INFO: renamed from: d */
        private double f812d;

        private b() {
        }

        protected b(String str) {
            this.f811c = str;
        }

        /* JADX INFO: renamed from: a */
        protected static b m859a(JSONObject jSONObject) throws JSONException {
            b bVar = new b();
            if (jSONObject != null) {
                bVar.f811c = jSONObject.getString("currency");
                bVar.f812d = jSONObject.getDouble(f810b);
            } else {
                C0847a.m909b(b.class.toString(), "The jsonObject to build PurchasesByCurrency object was null");
            }
            return bVar;
        }

        /* JADX INFO: renamed from: a */
        protected String m860a() {
            return this.f811c;
        }

        /* JADX INFO: renamed from: a */
        protected void m861a(double d) {
            this.f812d = new BigDecimal(String.valueOf(this.f812d)).add(new BigDecimal(String.valueOf(d))).doubleValue();
        }

        /* JADX INFO: renamed from: b */
        protected double m862b() {
            return this.f812d;
        }

        /* JADX INFO: renamed from: c */
        protected JSONObject m863c() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("currency", this.f811c);
            jSONObject.put(f810b, this.f812d);
            return jSONObject;
        }
    }

    /* JADX INFO: renamed from: com.metaps.analytics.v$c */
    static final class c {

        /* JADX INFO: renamed from: a */
        private static final String f813a = "total_count";

        /* JADX INFO: renamed from: b */
        private static final String f814b = "total_length";

        /* JADX INFO: renamed from: c */
        private static final String f815c = "last_at";

        /* JADX INFO: renamed from: d */
        private static final String f816d = "last_length";

        /* JADX INFO: renamed from: e */
        private int f817e;

        /* JADX INFO: renamed from: f */
        private long f818f;

        /* JADX INFO: renamed from: g */
        private long f819g;

        /* JADX INFO: renamed from: h */
        private long f820h;

        c() {
        }

        /* JADX INFO: renamed from: a */
        protected static c m864a(JSONObject jSONObject) throws JSONException {
            c cVar = new c();
            if (jSONObject == null) {
                C0847a.m909b(c.class.toString(), "The jsonObject to build SessionTotal object was null");
            } else if (jSONObject.has(f813a)) {
                cVar.f817e = jSONObject.getInt(f813a);
                cVar.f818f = jSONObject.getLong(f814b);
                cVar.f819g = jSONObject.getLong(f815c);
                cVar.f820h = jSONObject.getLong(f816d);
            }
            return cVar;
        }

        /* JADX INFO: renamed from: a */
        public JSONObject m865a() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            if (this.f817e > 0 || this.f818f > 0) {
                jSONObject.put(f813a, this.f817e);
                jSONObject.put(f814b, this.f818f);
                jSONObject.put(f815c, this.f819g);
                jSONObject.put(f816d, this.f820h);
            }
            return jSONObject;
        }

        /* JADX INFO: renamed from: a */
        protected void m866a(int i) {
            this.f817e += i;
        }

        /* JADX INFO: renamed from: a */
        protected void m867a(long j) {
            this.f818f = j;
        }

        /* JADX INFO: renamed from: a */
        protected void m868a(long j, long j2) {
            this.f818f += j;
            this.f820h = j;
            this.f819g = j2;
        }

        /* JADX INFO: renamed from: b */
        public int m869b() {
            return this.f817e;
        }

        /* JADX INFO: renamed from: b */
        protected void m870b(int i) {
            this.f817e = i;
        }

        /* JADX INFO: renamed from: b */
        protected void m871b(long j) {
            this.f819g = j;
        }

        /* JADX INFO: renamed from: c */
        public long m872c() {
            return this.f818f;
        }

        /* JADX INFO: renamed from: c */
        protected void m873c(long j) {
            this.f820h = j;
        }

        /* JADX INFO: renamed from: d */
        public long m874d() {
            return this.f819g;
        }

        /* JADX INFO: renamed from: e */
        public long m875e() {
            return this.f820h;
        }
    }

    /* JADX INFO: renamed from: a */
    public static C0833v m822a(JSONObject jSONObject) throws JSONException {
        C0833v c0833v = new C0833v();
        if (jSONObject != null) {
            c0833v.f787k = jSONObject.optString(f778b);
            c0833v.f788l = jSONObject.getString(f779c);
            c0833v.f789m = jSONObject.optInt(f780d) != 0;
            c0833v.f790n = jSONObject.optString(f781e);
            c0833v.f791o = jSONObject.getLong(f777a);
            c0833v.f792p = jSONObject.getInt(f782f);
            c0833v.f793q = jSONObject.getInt(f783g);
            c0833v.f794r = c.m864a(jSONObject.getJSONObject(f784h));
            c0833v.f795s = a.m846a(jSONObject.getJSONObject(f785i));
            c0833v.f796t = new ArrayList();
            JSONArray jSONArray = jSONObject.getJSONArray(f786j);
            for (int i = 0; i < jSONArray.length(); i++) {
                c0833v.f796t.add(b.m859a(jSONArray.getJSONObject(i)));
            }
        } else {
            C0847a.m909b(C0833v.class.toString(), "The jsonObject to build PartUser object was null");
        }
        return c0833v;
    }

    /* JADX INFO: renamed from: a */
    public c m823a() {
        return this.f794r;
    }

    /* JADX INFO: renamed from: a */
    public void m824a(double d, String str, long j) {
        b bVar;
        Iterator<b> it = this.f796t.iterator();
        do {
            if (!it.hasNext()) {
                bVar = null;
                break;
            }
            bVar = it.next();
        } while (!bVar.m860a().equals(str));
        if (bVar == null) {
            bVar = new b(str);
            this.f796t.add(bVar);
        }
        this.f795s.m849a(d, str, j, bVar);
    }

    /* JADX INFO: renamed from: a */
    public void m825a(int i) {
        if (this.f792p != i) {
            this.f797u = "fq7_" + this.f792p + "_" + i;
        }
        this.f792p = i;
    }

    /* JADX INFO: renamed from: a */
    public void m826a(long j) {
        this.f791o = j;
    }

    /* JADX INFO: renamed from: a */
    public void m827a(long j, long j2) {
        this.f794r.m868a(j, j2);
    }

    /* JADX INFO: renamed from: a */
    public void m828a(String str) {
        this.f787k = str;
    }

    /* JADX INFO: renamed from: a */
    public void m829a(boolean z) {
        this.f789m = z;
    }

    /* JADX INFO: renamed from: b */
    public a m830b() {
        return this.f795s;
    }

    /* JADX INFO: renamed from: b */
    public JSONObject m831b(boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(f778b, this.f787k);
        jSONObject.put(f779c, this.f788l);
        jSONObject.put(f780d, this.f789m ? 1 : 0);
        jSONObject.put(f781e, this.f790n);
        jSONObject.put(f777a, this.f791o);
        jSONObject.put(f782f, this.f792p);
        jSONObject.put(f783g, this.f793q);
        jSONObject.put(f784h, this.f794r.m865a());
        jSONObject.put(f785i, this.f795s.m853b());
        if (z) {
            JSONArray jSONArray = new JSONArray();
            Iterator<b> it = this.f796t.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().m863c());
            }
            jSONObject.put(f786j, jSONArray);
        }
        return jSONObject;
    }

    /* JADX INFO: renamed from: b */
    public void m832b(int i) {
        if (this.f793q != i) {
            this.f798v = "fq30_" + this.f793q + "_" + i;
        }
        this.f793q = i;
    }

    /* JADX INFO: renamed from: b */
    public void m833b(String str) {
        this.f788l = str;
    }

    /* JADX INFO: renamed from: c */
    public String m834c() {
        return this.f787k;
    }

    /* JADX INFO: renamed from: c */
    public void m835c(int i) {
        this.f794r.m866a(i);
    }

    /* JADX INFO: renamed from: c */
    public void m836c(String str) {
        this.f790n = str;
    }

    /* JADX INFO: renamed from: d */
    public String m837d() {
        return this.f788l;
    }

    /* JADX INFO: renamed from: d */
    public void m838d(String str) {
        this.f797u = str;
    }

    /* JADX INFO: renamed from: e */
    public String m839e() {
        return this.f790n;
    }

    /* JADX INFO: renamed from: e */
    public void m840e(String str) {
        this.f798v = str;
    }

    /* JADX INFO: renamed from: f */
    public long m841f() {
        return this.f791o;
    }

    /* JADX INFO: renamed from: g */
    public int m842g() {
        return this.f792p;
    }

    /* JADX INFO: renamed from: h */
    public int m843h() {
        return this.f793q;
    }

    /* JADX INFO: renamed from: i */
    public String m844i() {
        return this.f797u;
    }

    /* JADX INFO: renamed from: j */
    public String m845j() {
        return this.f798v;
    }
}
