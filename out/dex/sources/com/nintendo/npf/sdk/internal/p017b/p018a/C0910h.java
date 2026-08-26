package com.nintendo.npf.sdk.internal.p017b.p018a;

import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.h */
/* JADX INFO: compiled from: CoreHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0910h extends C0906d implements InterfaceC0909g {
    /* JADX INFO: renamed from: b */
    private JSONArray m1195b(BaaSUser baaSUser) {
        JSONArray jSONArray = new JSONArray();
        try {
            String nickname = !TextUtils.isEmpty(baaSUser.getNickname()) ? baaSUser.getNickname() : "";
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("op", "replace");
            jSONObject.put("path", "/nickname");
            jSONObject.put("value", nickname);
            jSONArray.put(jSONObject);
            String country = TextUtils.isEmpty(baaSUser.getCountry()) ? "" : baaSUser.getCountry();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("op", "replace");
            jSONObject2.put("path", "/country");
            jSONObject2.put("value", country);
            jSONArray.put(jSONObject2);
            String lowerCase = EnvironmentCompat.MEDIA_UNKNOWN;
            if (baaSUser.getGender() != null) {
                lowerCase = baaSUser.getGender().toString().toLowerCase();
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("op", "replace");
            jSONObject3.put("path", "/gender");
            jSONObject3.put("value", lowerCase);
            jSONArray.put(jSONObject3);
            String str = String.format(Locale.US, "%04d-%02d-%02d", baaSUser.getBirthdayYear(), baaSUser.getBirthdayMonth(), baaSUser.getBirthdayDay());
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("op", "replace");
            jSONObject4.put("path", "/birthday");
            jSONObject4.put("value", str);
            jSONArray.put(jSONObject4);
        } catch (JSONException unused) {
        }
        return jSONArray;
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0909g
    /* JADX INFO: renamed from: a */
    public void mo1190a(BaaSUser baaSUser, C0918a.b bVar) {
        m1223b(String.format("%s/users/%s", "/core/v1", baaSUser.getUserId()), m1187a(baaSUser), null, m1221a(m1195b(baaSUser)), true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0909g
    /* JADX INFO: renamed from: a */
    public void mo1191a(BaaSUser baaSUser, String str, C0918a.b bVar) {
        m1217a(String.format("%s/users/%s/link", "/core/v1", baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, String.format("idp=nintendoAccount&idToken=%s", str).getBytes(), "application/x-www-form-urlencoded", true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0909g
    /* JADX INFO: renamed from: a */
    public void mo1192a(BaaSUser baaSUser, List<String> list, C0918a.b bVar) {
        String str = String.format("%s/users", "/core/v1");
        Map<String, String> mapA = m1187a(baaSUser);
        HashMap map = new HashMap();
        map.put("embed_link_userinfo", "1");
        if (list != null && !list.isEmpty()) {
            map.put("filter.id.$in", TextUtils.join(",", list));
        }
        m1215a(str, mapA, (Map<String, String>) map, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0909g
    /* JADX INFO: renamed from: a */
    public void mo1193a(JSONObject jSONObject, C0918a.b bVar) {
        m1217a(String.format("%s/gateway/sdk/login", "/core/v1"), (Map<String, String>) null, (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, false, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0909g
    /* JADX INFO: renamed from: b */
    public void mo1194b(JSONObject jSONObject, C0918a.b bVar) {
        m1217a(String.format("%s/gateway/sdk/federation", "/core/v1"), (Map<String, String>) null, (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, false, bVar);
    }
}
