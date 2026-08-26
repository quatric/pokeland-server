package com.nintendo.npf.sdk.internal.p017b.p018a;

import android.text.TextUtils;
import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.o */
/* JADX INFO: compiled from: VcmHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0917o extends C0906d {
    /* JADX INFO: renamed from: a */
    public void m1205a(BaaSUser baaSUser, C0918a.b bVar) {
        m1215a(String.format("%s/users/%s/ability", "/vcm/v1", baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, true, bVar);
    }

    /* JADX INFO: renamed from: a */
    public void m1206a(BaaSUser baaSUser, String str, C0918a.a aVar) {
        m1214a(String.format("%s/users/%s/markets/%s/wallets", "/vcm/v1", baaSUser.getUserId(), str), m1187a(baaSUser), (Map<String, String>) null, true, aVar);
    }

    /* JADX INFO: renamed from: a */
    public void m1207a(BaaSUser baaSUser, String str, String str2, String str3, C0918a.a aVar) {
        String str4 = String.format("%s/users/%s/markets/%s/%s", "/vcm/v1", baaSUser.getUserId(), str.toUpperCase(), str3);
        Map<String, String> mapA = m1187a(baaSUser);
        HashMap map = new HashMap();
        map.put("timezone", str2);
        m1214a(str4, mapA, (Map<String, String>) map, true, aVar);
    }

    /* JADX INFO: renamed from: a */
    public void m1208a(BaaSUser baaSUser, String str, Set<String> set, C0918a.a aVar) {
        String str2 = String.format("%s/users/%s/markets/%s/transactions", "/vcm/v1", baaSUser.getUserId(), str);
        Map<String, String> mapA = m1187a(baaSUser);
        HashMap map = new HashMap();
        if (set != null && !set.isEmpty()) {
            map.put("filter.extras.orderId.$in", TextUtils.join(",", set));
        }
        m1214a(str2, mapA, (Map<String, String>) map, true, aVar);
    }

    /* JADX INFO: renamed from: a */
    public void m1209a(BaaSUser baaSUser, String str, JSONObject jSONObject, C0918a.b bVar) {
        m1217a(String.format("%s/users/%s/markets/%s/transactions", "/vcm/v1", baaSUser.getUserId(), str), m1187a(baaSUser), (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, true, bVar);
    }

    /* JADX INFO: renamed from: b */
    public void m1210b(BaaSUser baaSUser, String str, C0918a.a aVar) {
        m1214a(String.format("%s/markets/%s/bundles", "/vcm/v1", str), m1187a(baaSUser), (Map<String, String>) null, true, aVar);
    }

    /* JADX INFO: renamed from: c */
    public void m1211c(BaaSUser baaSUser, String str, C0918a.a aVar) {
        m1214a(String.format("%s/markets/%s/promo_bundles", "/vcm/v1", str), m1187a(baaSUser), (Map<String, String>) null, true, aVar);
    }
}
