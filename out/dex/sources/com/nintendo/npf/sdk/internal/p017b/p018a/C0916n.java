package com.nintendo.npf.sdk.internal.p017b.p018a;

import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.n */
/* JADX INFO: compiled from: SubscriptionHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0916n extends C0906d implements InterfaceC0915m {
    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0915m
    /* JADX INFO: renamed from: a */
    public void mo1199a(BaaSUser baaSUser, String str, C0918a.a aVar) {
        m1214a(String.format("%s/products/markets/%s", "/subs/v1", str), m1187a(baaSUser), (Map<String, String>) null, true, aVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0915m
    /* JADX INFO: renamed from: a */
    public void mo1200a(BaaSUser baaSUser, String str, String str2, JSONObject jSONObject, C0918a.b bVar) {
        m1217a(String.format("%s/purchases/markets/%s/products/%s/users/%s/ability", "/subs/v1", str, str2, baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0915m
    /* JADX INFO: renamed from: a */
    public void mo1201a(BaaSUser baaSUser, String str, JSONObject jSONObject, C0918a.a aVar) {
        m1218a(String.format("%s/purchases/markets/%s/users/%s", "/subs/v1", str, baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, m1222a(jSONObject), true, aVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0915m
    /* JADX INFO: renamed from: a */
    public void mo1202a(BaaSUser baaSUser, String str, JSONObject jSONObject, C0918a.b bVar) {
        m1217a(String.format("%s/purchases/markets/%s/users/%s", "/subs/v1", str, baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, m1222a(jSONObject), C0849c.f862b, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0915m
    /* JADX INFO: renamed from: b */
    public void mo1203b(BaaSUser baaSUser, String str, C0918a.a aVar) {
        m1214a(String.format("%s/purchases/markets/%s/users/%s", "/subs/v1", str, baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, true, aVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0915m
    /* JADX INFO: renamed from: b */
    public void mo1204b(BaaSUser baaSUser, String str, JSONObject jSONObject, C0918a.b bVar) {
        m1219a(String.format("%s/users/%s/markets/%s/ownerships", "/subs/v1", baaSUser.getUserId(), str), m1187a(baaSUser), (Map<String, String>) null, jSONObject.toString().getBytes(), true, bVar);
    }
}
