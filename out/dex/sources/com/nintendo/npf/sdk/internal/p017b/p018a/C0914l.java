package com.nintendo.npf.sdk.internal.p017b.p018a;

import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.l */
/* JADX INFO: compiled from: NotificationHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0914l extends C0906d implements InterfaceC0913k {
    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0913k
    /* JADX INFO: renamed from: a */
    public void mo1197a(BaaSUser baaSUser, C0918a.b bVar) {
        m1215a(String.format("%s/push_channels/%s/%s", "/notification/v1", baaSUser.getUserId(), baaSUser.getDeviceAccount()), m1187a(baaSUser), (Map<String, String>) null, true, bVar);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0913k
    /* JADX INFO: renamed from: a */
    public void mo1198a(BaaSUser baaSUser, JSONObject jSONObject, C0918a.b bVar) {
        m1219a(String.format("%s/push_channels/%s/%s", "/notification/v1", baaSUser.getUserId(), baaSUser.getDeviceAccount()), m1187a(baaSUser), (Map<String, String>) null, m1222a(jSONObject), true, bVar);
    }
}
