package com.nintendo.npf.sdk.internal.p016a;

import android.util.Base64;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.internal.p023e.C0956f;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.e */
/* JADX INFO: compiled from: IABUtil.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public abstract class AbstractC0880e {

    /* JADX INFO: renamed from: c */
    private static final String f1080c = "e";

    /* JADX INFO: renamed from: d */
    private static String f1081d = "GOOGLE";

    /* JADX INFO: renamed from: a */
    protected InterfaceC0883h f1082a;

    /* JADX INFO: renamed from: b */
    protected a f1083b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.e$a */
    /* JADX INFO: compiled from: IABUtil.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1137a(InterfaceC0883h interfaceC0883h, int i);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.e$b */
    /* JADX INFO: compiled from: IABUtil.java */
    private static class b {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1084a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: a */
    public static String m1122a() {
        return f1081d;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public static String m1123a(String str, String str2, BigDecimal bigDecimal) {
        try {
            String str3 = b.f1084a.mo1048b().m1665a().getUserId() + str + str2 + bigDecimal.stripTrailingZeros().toPlainString() + C0956f.m1397a(b.f1084a.mo1065s().m1337l().getBytes(), 600, 8, "HmacSHA1");
            C0955e.m1391a(f1080c, "baseString : " + str3);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str3.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArrDigest) {
                String hexString = Integer.toHexString(b2 & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    public static String m1124a(String str, BigDecimal bigDecimal) {
        String str2 = str + bigDecimal;
        try {
            Currency currency = Currency.getInstance(str);
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault());
            currencyInstance.setCurrency(currency);
            currencyInstance.setMaximumFractionDigits(currency.getDefaultFractionDigits());
            currencyInstance.setMinimumFractionDigits(currency.getDefaultFractionDigits());
            return currencyInstance.format(bigDecimal);
        } catch (IllegalArgumentException e) {
            C0955e.m1395c(f1080c, "Error creating display price: " + e.toString());
            return str2;
        }
    }

    /* JADX INFO: renamed from: a */
    public static JSONObject m1125a(JSONArray jSONArray, JSONArray jSONArray2) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("type", "purchase");
            String strEncodeToString = "";
            if (jSONArray != null && jSONArray.length() > 0) {
                C0955e.m1391a(f1080c, "receipt : " + jSONArray.toString());
                strEncodeToString = Base64.encodeToString(jSONArray.toString().getBytes(), 2);
                C0955e.m1391a(f1080c, "encodedReceipt : " + strEncodeToString);
            }
            jSONObject2.put("receipt", strEncodeToString);
            if (jSONArray2 != null) {
                jSONObject2.put("orders", jSONArray2);
            }
            jSONObject.put("extras", jSONObject2);
            return jSONObject;
        } catch (JSONException e) {
            C0955e.m1394b(f1080c, "Failed making request JSON object", e);
            throw new IllegalArgumentException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1126a(String str) {
        f1081d = str;
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1127b() {
        return f1081d.equals("GOOGLE");
    }

    /* JADX INFO: renamed from: c */
    public static boolean m1128c() {
        return f1081d.equals("AMAZON");
    }

    /* JADX INFO: renamed from: a */
    public NPFError mo1129a(int i) {
        return null;
    }

    /* JADX INFO: renamed from: a */
    public void mo1130a(a aVar) {
        if (aVar != null) {
            aVar.mo1137a(null, -1);
        }
    }

    /* JADX INFO: renamed from: b */
    public boolean mo1131b(int i) {
        return false;
    }

    /* JADX INFO: renamed from: c */
    public boolean mo1132c(int i) {
        return false;
    }

    /* JADX INFO: renamed from: d */
    public void mo1133d() {
    }

    /* JADX INFO: renamed from: e */
    public int mo1134e() {
        return 0;
    }

    /* JADX INFO: renamed from: f */
    public int mo1135f() {
        return -1;
    }

    /* JADX INFO: renamed from: g */
    public int mo1136g() {
        return -2;
    }
}
