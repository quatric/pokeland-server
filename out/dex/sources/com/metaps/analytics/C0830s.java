package com.metaps.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.metaps.common.C0847a;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.s */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0830s {

    /* JADX INFO: renamed from: a */
    private static final String f743a = "pkg_ver";

    /* JADX INFO: renamed from: b */
    private static final String f744b = "pkg_rev";

    /* JADX INFO: renamed from: c */
    private String f745c;

    /* JADX INFO: renamed from: d */
    private String f746d;

    public C0830s(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.f745c = packageInfo.versionName;
            this.f746d = String.valueOf(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            C0847a.m905a(C0830s.class.toString(), "Failed to get PackageInfo", e);
            this.f745c = "";
            this.f746d = "";
        }
    }

    /* JADX INFO: renamed from: a */
    public JSONObject m812a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(f743a, this.f745c);
        jSONObject.put(f744b, this.f746d);
        return jSONObject;
    }
}
