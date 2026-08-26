package com.nintendo.npf.sdk.internal.p017b.p018a;

import com.google.common.net.HttpHeaders;
import com.metaps.common.C0849c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Map;
import org.json.JSONArray;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.f */
/* JADX INFO: compiled from: BigdataHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0908f extends C0906d implements InterfaceC0907e {
    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0907e
    /* JADX INFO: renamed from: a */
    public void mo1188a(BaaSUser baaSUser, C0918a.b bVar) {
        m1215a(String.format("%s/analytics/events/config", "/bigdata/v1"), m1187a(baaSUser), (Map<String, String>) null, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0907e
    /* JADX INFO: renamed from: a */
    public void mo1189a(BaaSUser baaSUser, JSONArray jSONArray, C0918a.b bVar) {
        String str = String.format("%s/analytics/events", "/bigdata/v1");
        Map<String, String> mapA = m1187a(baaSUser);
        mapA.put(HttpHeaders.CONTENT_ENCODING, "gzip");
        m1217a(str, mapA, (Map<String, String>) null, m1224b(jSONArray), C0849c.f862b, true, bVar);
    }
}
