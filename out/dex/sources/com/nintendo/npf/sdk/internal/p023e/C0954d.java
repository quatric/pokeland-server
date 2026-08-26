package com.nintendo.npf.sdk.internal.p023e;

import com.android.billingclient.api.BillingClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.analytics.Analytics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.e.d */
/* JADX INFO: compiled from: ReportInternalEvent.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0954d {
    /* JADX INFO: renamed from: a */
    public static void m1387a(String str, NPFError nPFError) {
        if (nPFError == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("origin", str);
            jSONObject.put("code", nPFError.getErrorCode());
            jSONObject.put("message", nPFError.getErrorMessage());
            Analytics.reportEvent("NPFAUDIT", BillingClient.SkuType.SUBS, null, jSONObject);
        } catch (JSONException unused) {
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1388a(String str, String str2, NPFError nPFError) {
        m1390c(str, str2, nPFError);
    }

    /* JADX INFO: renamed from: b */
    public static void m1389b(String str, String str2, NPFError nPFError) {
        m1390c(str, str2, nPFError);
    }

    /* JADX INFO: renamed from: c */
    private static void m1390c(String str, String str2, NPFError nPFError) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("report", str2);
            if (nPFError == null) {
                jSONObject.put("errorType", 0);
                jSONObject.put("errorCode", 0);
                jSONObject.put("errorMessage", FirebaseAnalytics.Param.SUCCESS);
            } else {
                jSONObject.put("errorType", nPFError.getErrorType().getInt());
                jSONObject.put("errorCode", nPFError.getErrorCode());
                jSONObject.put("errorMessage", nPFError.getErrorMessage());
            }
        } catch (JSONException unused) {
        }
        Analytics.reportEvent("NPFAUDIT", str, null, jSONObject);
    }
}
