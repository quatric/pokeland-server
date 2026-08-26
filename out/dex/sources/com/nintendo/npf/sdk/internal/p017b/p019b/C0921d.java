package com.nintendo.npf.sdk.internal.p017b.p019b;

import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.b.d */
/* JADX INFO: compiled from: JWT.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0921d {
    /* JADX INFO: renamed from: a */
    public static String m1242a(String str, String str2, String str3) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("alg", "HS256");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("iss", str3);
            jSONObject2.put("iat", Calendar.getInstance().getTimeInMillis() / 1000);
            jSONObject2.put("aud", "https://" + str);
            String str4 = Base64.encodeToString(jSONObject.toString().getBytes(), 10) + "." + Base64.encodeToString(jSONObject2.toString().getBytes(), 10);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str2.getBytes(), "HmacSHA256"));
            return str4 + "." + Base64.encodeToString(mac.doFinal(str4.getBytes()), 10);
        } catch (InvalidKeyException | NoSuchAlgorithmException | JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException("Invalid JSON format to generate JWT", e);
        }
    }
}
