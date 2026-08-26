package com.metaps.analytics.assist;

import android.content.Context;
import com.metaps.common.C0847a;
import com.metaps.common.C0860n;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.l */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0809l {

    /* JADX INFO: renamed from: a */
    protected static final int f525a = 1;

    /* JADX INFO: renamed from: b */
    protected static final int f526b = 2;

    /* JADX INFO: renamed from: c */
    protected static final int f527c = 1;

    /* JADX INFO: renamed from: d */
    protected static final int f528d = 2;

    /* JADX INFO: renamed from: e */
    protected static final int f529e = 3;

    /* JADX INFO: renamed from: f */
    private static final String f530f = "impression_id";

    /* JADX INFO: renamed from: g */
    private static final String f531g = "type";

    /* JADX INFO: renamed from: h */
    private static final String f532h = "code";

    /* JADX INFO: renamed from: i */
    private static final String f533i = "conversion_action";

    /* JADX INFO: renamed from: j */
    private static final String f534j = "detect_via";

    /* JADX INFO: renamed from: k */
    private static final String f535k = "creative_type";

    /* JADX INFO: renamed from: l */
    private static final String f536l = "creative_id";

    /* JADX INFO: renamed from: m */
    private static final String f537m = "creative_url";

    /* JADX INFO: renamed from: n */
    private static final String f538n = "detect_value";

    /* JADX INFO: renamed from: o */
    private static final String f539o = "creation_time";

    /* JADX INFO: renamed from: p */
    private static final String f540p = "default";

    /* JADX INFO: renamed from: q */
    private static final String f541q = "device_id";

    /* JADX INFO: renamed from: r */
    private static final String f542r = "device_id_type";

    /* JADX INFO: renamed from: s */
    private static final String f543s = "os_name";

    /* JADX INFO: renamed from: t */
    private static final String f544t = "pkg_id";

    /* JADX INFO: renamed from: u */
    private static final String f545u = "imp";

    /* JADX INFO: renamed from: v */
    private static final String f546v = "click";

    /* JADX INFO: renamed from: w */
    private static final String f547w = "spot_code";

    /* JADX INFO: renamed from: x */
    private static final String f548x = "locale";

    /* JADX INFO: renamed from: y */
    private static final String f549y = "sdk";

    /* JADX INFO: renamed from: z */
    private static final String f550z = "fill_empty";

    /* JADX INFO: renamed from: A */
    private int f551A;

    /* JADX INFO: renamed from: B */
    private String f552B;

    /* JADX INFO: renamed from: C */
    private int f553C;

    /* JADX INFO: renamed from: D */
    private int f554D;

    /* JADX INFO: renamed from: E */
    private int f555E;

    /* JADX INFO: renamed from: F */
    private int f556F;

    /* JADX INFO: renamed from: G */
    private String f557G;

    /* JADX INFO: renamed from: H */
    private String f558H;

    /* JADX INFO: renamed from: I */
    private String f559I;

    /* JADX INFO: renamed from: J */
    private long f560J;

    /* JADX INFO: renamed from: K */
    private boolean f561K;

    /* JADX INFO: renamed from: L */
    private boolean f562L;

    /* JADX INFO: renamed from: M */
    private String f563M;

    /* JADX INFO: renamed from: N */
    private String f564N;

    /* JADX INFO: renamed from: O */
    private String f565O;

    /* JADX INFO: renamed from: P */
    private String f566P;

    private C0809l() {
    }

    /* JADX INFO: renamed from: a */
    protected static C0809l m695a(String str, JSONObject jSONObject) {
        if (!jSONObject.has("type") || !jSONObject.has(f532h)) {
            return null;
        }
        try {
            C0809l c0809l = new C0809l();
            c0809l.m712d(str);
            if (jSONObject.has(f530f)) {
                c0809l.m712d(jSONObject.getString(f530f));
            }
            c0809l.m697a(jSONObject.getInt("type"));
            c0809l.m699a(jSONObject.getString(f532h));
            if (jSONObject.has(f536l)) {
                c0809l.m708c(jSONObject.getInt(f536l));
            }
            if (jSONObject.has(f533i)) {
                c0809l.m711d(jSONObject.getInt(f533i));
            }
            if (jSONObject.has(f534j)) {
                c0809l.m714e(jSONObject.getInt(f534j));
            }
            if (jSONObject.has(f537m)) {
                c0809l.m704b(jSONObject.getString(f537m));
            }
            if (jSONObject.has(f538n)) {
                c0809l.m709c(jSONObject.getString(f538n));
            }
            c0809l.m698a(jSONObject.has(f539o) ? jSONObject.getLong(f539o) : System.currentTimeMillis() / 1000);
            if (jSONObject.has(f546v)) {
                c0809l.m715e(jSONObject.getString(f546v));
            }
            if (jSONObject.has(f541q)) {
                c0809l.m717f(jSONObject.getString(f541q));
            }
            if (jSONObject.has(f542r)) {
                c0809l.m719g(jSONObject.getString(f542r));
            }
            if (jSONObject.has(f547w)) {
                c0809l.m721h(jSONObject.getString(f547w));
            }
            if (jSONObject.has(f540p) && jSONObject.getInt(f540p) == 1) {
                c0809l.m700a(true);
            }
            if (jSONObject.has(f550z)) {
                c0809l.m705b(true);
            }
            return c0809l;
        } catch (JSONException e) {
            C0847a.m905a(C0809l.class.toString(), "Failed to decode Ad", e);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    protected int m696a() {
        return this.f551A;
    }

    /* JADX INFO: renamed from: a */
    protected void m697a(int i) {
        this.f551A = i;
    }

    /* JADX INFO: renamed from: a */
    protected void m698a(long j) {
        this.f560J = j;
    }

    /* JADX INFO: renamed from: a */
    protected void m699a(String str) {
        this.f552B = str;
    }

    /* JADX INFO: renamed from: a */
    protected void m700a(boolean z) {
        this.f561K = z;
    }

    /* JADX INFO: renamed from: a */
    protected boolean m701a(Context context) {
        return m713e() == 2 && !C0860n.m1040a(context, m720h());
    }

    /* JADX INFO: renamed from: b */
    protected String m702b() {
        return this.f552B;
    }

    /* JADX INFO: renamed from: b */
    protected void m703b(int i) {
        this.f553C = i;
    }

    /* JADX INFO: renamed from: b */
    protected void m704b(String str) {
        this.f557G = str;
    }

    /* JADX INFO: renamed from: b */
    protected void m705b(boolean z) {
        this.f562L = z;
    }

    /* JADX INFO: renamed from: b */
    protected boolean m706b(Context context) {
        return m713e() == 1 && C0860n.m1040a(context, m720h());
    }

    /* JADX INFO: renamed from: c */
    protected int m707c() {
        return this.f553C;
    }

    /* JADX INFO: renamed from: c */
    protected void m708c(int i) {
        this.f554D = i;
    }

    /* JADX INFO: renamed from: c */
    protected void m709c(String str) {
        this.f558H = str;
    }

    /* JADX INFO: renamed from: d */
    protected int m710d() {
        return this.f554D;
    }

    /* JADX INFO: renamed from: d */
    protected void m711d(int i) {
        this.f555E = i;
    }

    /* JADX INFO: renamed from: d */
    protected void m712d(String str) {
        this.f559I = str;
    }

    /* JADX INFO: renamed from: e */
    protected int m713e() {
        return this.f555E;
    }

    /* JADX INFO: renamed from: e */
    protected void m714e(int i) {
        this.f556F = i;
    }

    /* JADX INFO: renamed from: e */
    protected void m715e(String str) {
        this.f563M = str;
    }

    /* JADX INFO: renamed from: f */
    protected int m716f() {
        return this.f556F;
    }

    /* JADX INFO: renamed from: f */
    protected void m717f(String str) {
        this.f564N = str;
    }

    /* JADX INFO: renamed from: g */
    protected String m718g() {
        return this.f557G;
    }

    /* JADX INFO: renamed from: g */
    protected void m719g(String str) {
        this.f565O = str;
    }

    /* JADX INFO: renamed from: h */
    protected String m720h() {
        return this.f558H;
    }

    /* JADX INFO: renamed from: h */
    protected void m721h(String str) {
        this.f566P = str;
    }

    /* JADX INFO: renamed from: i */
    protected String m722i() {
        return this.f559I;
    }

    /* JADX INFO: renamed from: j */
    protected boolean m723j() {
        return this.f561K;
    }

    /* JADX INFO: renamed from: k */
    protected boolean m724k() {
        return this.f562L;
    }

    /* JADX INFO: renamed from: l */
    protected void m725l() {
        this.f563M = UUID.randomUUID().toString();
    }

    /* JADX INFO: renamed from: m */
    protected boolean m726m() {
        return this.f563M != null;
    }

    /* JADX INFO: renamed from: n */
    protected String m727n() {
        return this.f563M;
    }

    /* JADX INFO: renamed from: o */
    protected String m728o() {
        return this.f564N;
    }

    /* JADX INFO: renamed from: p */
    protected String m729p() {
        return this.f565O;
    }

    /* JADX INFO: renamed from: q */
    protected String m730q() {
        return this.f566P;
    }

    /* JADX INFO: renamed from: r */
    protected JSONObject m731r() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", m696a());
        jSONObject.put(f532h, m702b());
        jSONObject.put(f535k, m707c());
        jSONObject.put(f536l, m710d());
        jSONObject.put(f533i, m713e());
        jSONObject.put(f534j, m716f());
        return jSONObject;
    }
}
