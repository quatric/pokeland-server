package com.nintendo.npf.sdk.internal.p017b.p018a;

import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Map;
import org.json.JSONArray;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.b */
/* JADX INFO: compiled from: AuditHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0904b extends C0906d implements InterfaceC0903a {
    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0903a
    /* JADX INFO: renamed from: a */
    public void mo1179a(BaaSUser baaSUser, JSONArray jSONArray, C0918a.a aVar) {
        m1216a(String.format("%s/profanity_inspect", "/audit/v1"), m1187a(baaSUser), (Map<String, String>) null, m1221a(jSONArray), C0849c.f862b, true, aVar);
    }
}
