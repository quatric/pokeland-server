package com.nintendo.npf.sdk.internal.p016a;

import android.content.SharedPreferences;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.math.BigDecimal;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.c */
/* JADX INFO: compiled from: BillingUtil.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0878c {
    /* JADX INFO: renamed from: a */
    public static JSONObject m1089a(String str, InterfaceC0875a interfaceC0875a) {
        SharedPreferences sharedPreferences = interfaceC0875a.mo1047a().getSharedPreferences("transactionData", 0);
        if (!sharedPreferences.contains(str)) {
            return null;
        }
        try {
            return new JSONObject(sharedPreferences.getString(str, null));
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1090a(String str, BigDecimal bigDecimal, String str2, String str3, String str4, InterfaceC0875a interfaceC0875a) {
        SharedPreferences.Editor editorEdit = interfaceC0875a.mo1047a().getSharedPreferences("transactionData", 0).edit();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sku", str);
            jSONObject.put(FirebaseAnalytics.Param.PRICE, bigDecimal);
            jSONObject.put("priceCode", str2);
            jSONObject.put("customAttribute", str3 != null ? str3 : JSONObject.NULL);
            jSONObject.put("purchaseProductInfo", str4 != null ? str4 : JSONObject.NULL);
            editorEdit.putString(str, jSONObject.toString());
            editorEdit.apply();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
