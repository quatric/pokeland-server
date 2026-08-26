package com.nintendo.npf.sdk.internal.p017b.p020c;

import com.metaps.common.C0849c;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.c.d */
/* JADX INFO: compiled from: AccountHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0926d extends C0928f implements InterfaceC0925c {

    /* JADX INFO: renamed from: e */
    private static final String f1211e = "d";

    @Override // com.nintendo.npf.sdk.internal.p017b.p020c.InterfaceC0925c
    /* JADX INFO: renamed from: a */
    public void mo1252a(NintendoAccount nintendoAccount, String str, String str2, String str3, String str4, C0918a.b bVar) {
        Map<String, String> mapA = m1256a(nintendoAccount);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("userId", nintendoAccount.getNintendoAccountId());
            jSONObject.put("appName", str);
            jSONObject.put("market", str2);
            jSONObject.put(C0856j.f955a, str3);
            jSONObject.put("displayPrice", str4);
            m1217a("/api/1.0.0/email/send_purchased_to_parent", mapA, (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, true, bVar);
        } catch (JSONException e) {
            C0955e.m1394b(f1211e, "Failed making request JSON object", e);
            throw new IllegalArgumentException(e);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p020c.InterfaceC0925c
    /* JADX INFO: renamed from: a */
    public void mo1253a(String str, String str2, C0918a.b bVar) {
        try {
            m1217a("/connect/1.0.0/api/session_token", (Map<String, String>) null, (Map<String, String>) null, ((("client_id=" + URLEncoder.encode(this.f1213c, "UTF-8")) + "&session_token_code=" + URLEncoder.encode(str, "UTF-8")) + "&session_token_code_verifier=" + URLEncoder.encode(str2, "UTF-8")).getBytes(), "application/x-www-form-urlencoded", false, bVar);
        } catch (UnsupportedEncodingException e) {
            C0955e.m1394b(f1211e, "connect", e);
            throw new IllegalStateException(e);
        }
    }
}
