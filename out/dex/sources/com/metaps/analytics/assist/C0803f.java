package com.metaps.analytics.assist;

import android.content.Context;
import android.net.Uri;
import com.metaps.analytics.AnalyticsUtil;
import com.metaps.analytics.C0830s;
import com.metaps.analytics.C0831t;
import com.metaps.analytics.C0832u;
import com.metaps.analytics.C0833v;
import com.metaps.common.C0847a;
import com.metaps.common.C0849c;
import com.metaps.common.C0853g;
import com.metaps.common.C0854h;
import com.metaps.common.C0858l;
import com.metaps.common.C0859m;
import com.metaps.common.Metaps;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.assist.f */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0803f {

    /* JADX INFO: renamed from: A */
    private static final String f457A = "customs";

    /* JADX INFO: renamed from: B */
    private static final String f458B = "category";

    /* JADX INFO: renamed from: C */
    private static final String f459C = "name";

    /* JADX INFO: renamed from: D */
    private static final String f460D = "value";

    /* JADX INFO: renamed from: E */
    private static final String f461E = "inactive_duration";

    /* JADX INFO: renamed from: F */
    private static C0803f f462F = null;

    /* JADX INFO: renamed from: a */
    private static final String f463a = "assist_content";

    /* JADX INFO: renamed from: b */
    private static final String f464b = "house_ad_click_action";

    /* JADX INFO: renamed from: c */
    private static final String f465c = "{app_key}";

    /* JADX INFO: renamed from: d */
    private static final String f466d = "{spot_code}";

    /* JADX INFO: renamed from: e */
    private static final String f467e = "req_time";

    /* JADX INFO: renamed from: f */
    private static final String f468f = "user";

    /* JADX INFO: renamed from: g */
    private static final String f469g = "info";

    /* JADX INFO: renamed from: h */
    private static final String f470h = "app";

    /* JADX INFO: renamed from: i */
    private static final String f471i = "location";

    /* JADX INFO: renamed from: j */
    private static final String f472j = "device_id";

    /* JADX INFO: renamed from: k */
    private static final String f473k = "device_id_type";

    /* JADX INFO: renamed from: l */
    private static final String f474l = "google_aid";

    /* JADX INFO: renamed from: m */
    private static final String f475m = "android_id";

    /* JADX INFO: renamed from: n */
    private static final String f476n = "os_name";

    /* JADX INFO: renamed from: o */
    private static final String f477o = "pkg_id";

    /* JADX INFO: renamed from: p */
    private static final String f478p = "locale";

    /* JADX INFO: renamed from: q */
    private static final String f479q = "sdk";

    /* JADX INFO: renamed from: r */
    private static final String f480r = "app_spot_type";

    /* JADX INFO: renamed from: s */
    private static final String f481s = "targets_count";

    /* JADX INFO: renamed from: t */
    private static final String f482t = "impression_id";

    /* JADX INFO: renamed from: u */
    private static final String f483u = "click_id";

    /* JADX INFO: renamed from: v */
    private static final String f484v = "target_code";

    /* JADX INFO: renamed from: w */
    private static final String f485w = "target_type";

    /* JADX INFO: renamed from: x */
    private static final String f486x = "spot_code";

    /* JADX INFO: renamed from: y */
    private static final String f487y = "fill_empty";

    /* JADX INFO: renamed from: z */
    private static final String f488z = "attributes";

    /* JADX INFO: renamed from: G */
    private C0858l f489G;

    /* JADX INFO: renamed from: I */
    private JSONArray f491I;

    /* JADX INFO: renamed from: H */
    private Object f490H = new Object();

    /* JADX INFO: renamed from: J */
    private long f492J = -1;

    private C0803f() {
        m684c();
    }

    /* JADX INFO: renamed from: a */
    private String m681a(Context context, String str, String str2, C0809l c0809l) {
        String str3;
        String adId = AnalyticsUtil.getAdId(context);
        if (adId == null || adId.length() == 0) {
            C0847a.m911c("Failed to get Google Advertising Id");
            adId = AnalyticsUtil.getDeviceId(context);
            str3 = f475m;
        } else {
            str3 = f474l;
        }
        Uri.Builder builderAppendQueryParameter = Uri.parse(str).buildUpon().appendQueryParameter(f472j, adId).appendQueryParameter(f473k, str3).appendQueryParameter(f477o, context.getPackageName()).appendQueryParameter(f478p, Locale.getDefault().toString()).appendQueryParameter(f476n, C0854h.f926h).appendQueryParameter(f479q, C0854h.m975c()).appendQueryParameter(f482t, c0809l.m722i()).appendQueryParameter(f483u, c0809l.m727n()).appendQueryParameter(f484v, c0809l.m702b()).appendQueryParameter(f485w, String.valueOf(c0809l.m696a())).appendQueryParameter(f486x, str2);
        if (c0809l.m724k()) {
            C0847a.m903a(getClass().toString(), "Default houseAd is used to fill empty.");
            builderAppendQueryParameter.appendQueryParameter(f487y, String.valueOf(1));
        }
        return builderAppendQueryParameter.build().toString();
    }

    /* JADX INFO: renamed from: a */
    protected static void m682a() {
        f462F = new C0803f();
    }

    /* JADX INFO: renamed from: b */
    public static synchronized C0803f m683b() {
        if (f462F == null) {
            m682a();
        }
        return f462F;
    }

    /* JADX INFO: renamed from: c */
    private void m684c() {
        new Thread() { // from class: com.metaps.analytics.assist.f.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (C0803f.this.f490H) {
                    C0803f.this.f489G = C0858l.m1018a(0).m1023a(new C0849c(), Metaps.getApplicationId());
                }
            }
        }.start();
    }

    /* JADX INFO: renamed from: a */
    protected String m685a(Context context, AppSpotType appSpotType, AppSpotConfig appSpotConfig) throws JSONException {
        C0853g c0853gM936a = C0853g.m936a(context);
        JSONObject jSONObjectM966i = c0853gM936a.m966i();
        C0833v c0833vM951a = c0853gM936a.m951a(false);
        C0831t c0831t = new C0831t(context);
        C0830s c0830s = new C0830s(context);
        C0832u c0832uM958b = c0853gM936a.m958b();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(f467e, C0859m.m1033b());
        jSONObject.put(f468f, c0833vM951a.m831b(false));
        jSONObject.put(f469g, c0831t.m813a());
        jSONObject.put(f470h, c0830s.m812a());
        jSONObject.put("location", c0832uM958b.m815a());
        jSONObject.put(f480r, appSpotType.getName());
        jSONObject.put(f481s, appSpotType == AppSpotType.ICON ? appSpotConfig.m666b() : 1);
        if (jSONObjectM966i != null) {
            jSONObject.put(f488z, jSONObjectM966i);
        }
        JSONArray jSONArray = this.f491I;
        if (jSONArray != null) {
            jSONObject.put(f457A, jSONArray);
        }
        long j = this.f492J;
        if (j >= 0) {
            jSONObject.put(f461E, j);
        }
        return jSONObject.toString();
    }

    /* JADX INFO: renamed from: a */
    protected String m686a(Context context, String str, C0809l c0809l) {
        String strM1025a;
        C0858l c0858l = this.f489G;
        if (c0858l == null || (strM1025a = c0858l.m1025a(f464b)) == null) {
            return null;
        }
        return m681a(context, strM1025a.replace(f465c, Metaps.getApplicationId()), str, c0809l);
    }

    /* JADX INFO: renamed from: a */
    protected String m687a(String str) {
        String strM1025a;
        C0858l c0858l = this.f489G;
        if (c0858l == null || (strM1025a = c0858l.m1025a(f463a)) == null) {
            return null;
        }
        return strM1025a.replace(f465c, Metaps.getApplicationId()).replace(f466d, str);
    }

    /* JADX INFO: renamed from: a */
    public void m688a(long j) {
        if (this.f492J >= 0) {
            return;
        }
        if (j < 0) {
            j = 0;
        }
        this.f492J = j;
    }

    /* JADX INFO: renamed from: a */
    public void m689a(String str, String str2, int i) {
        if (this.f491I == null) {
            this.f491I = new JSONArray();
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(f458B, str);
            jSONObject.put("name", str2);
            jSONObject.put("value", i);
            this.f491I.put(jSONObject);
        } catch (JSONException unused) {
            C0847a.m909b(getClass().toString(), "Failed to add cached custom events.");
        }
    }
}
