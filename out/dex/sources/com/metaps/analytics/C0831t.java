package com.metaps.analytics;

import android.content.Context;
import android.os.Build;
import com.metaps.common.C0854h;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.t */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0831t {

    /* JADX INFO: renamed from: a */
    private static final String f747a = "sdk";

    /* JADX INFO: renamed from: b */
    private static final String f748b = "os_name";

    /* JADX INFO: renamed from: c */
    private static final String f749c = "os_ver";

    /* JADX INFO: renamed from: d */
    private static final String f750d = "os_build";

    /* JADX INFO: renamed from: e */
    private static final String f751e = "device_id";

    /* JADX INFO: renamed from: f */
    private static final String f752f = "device_maker";

    /* JADX INFO: renamed from: g */
    private static final String f753g = "device_model";

    /* JADX INFO: renamed from: h */
    private static final String f754h = "pkg_id";

    /* JADX INFO: renamed from: i */
    private static final String f755i = "display_dpi";

    /* JADX INFO: renamed from: j */
    private static final String f756j = "display_width";

    /* JADX INFO: renamed from: k */
    private static final String f757k = "display_height";

    /* JADX INFO: renamed from: l */
    private static final String f758l = "locale";

    /* JADX INFO: renamed from: m */
    private static final String f759m = "timezone";

    /* JADX INFO: renamed from: n */
    private String f760n;

    /* JADX INFO: renamed from: o */
    private String f761o;

    /* JADX INFO: renamed from: p */
    private String f762p;

    /* JADX INFO: renamed from: q */
    private String f763q;

    /* JADX INFO: renamed from: r */
    private String f764r;

    public C0831t(Context context) {
        this.f760n = AnalyticsUtil.getDeviceId(context);
        this.f761o = context.getPackageName();
        this.f762p = String.valueOf(context.getResources().getDisplayMetrics().densityDpi);
        this.f763q = String.valueOf(context.getResources().getDisplayMetrics().widthPixels);
        this.f764r = String.valueOf(context.getResources().getDisplayMetrics().heightPixels);
    }

    /* JADX INFO: renamed from: a */
    public JSONObject m813a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(f747a, C0854h.m975c());
        jSONObject.put(f748b, C0854h.f926h);
        jSONObject.put(f749c, Build.VERSION.RELEASE);
        jSONObject.put(f750d, Build.ID);
        jSONObject.put(f751e, this.f760n);
        jSONObject.put(f752f, Build.MANUFACTURER);
        jSONObject.put(f753g, Build.MODEL);
        jSONObject.put(f754h, this.f761o);
        jSONObject.put(f755i, this.f762p);
        jSONObject.put(f756j, this.f763q);
        jSONObject.put(f757k, this.f764r);
        jSONObject.put(f758l, Locale.getDefault().toString());
        jSONObject.put(f759m, TimeZone.getDefault().getID());
        return jSONObject;
    }
}
