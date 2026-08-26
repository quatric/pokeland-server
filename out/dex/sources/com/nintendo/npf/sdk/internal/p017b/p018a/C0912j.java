package com.nintendo.npf.sdk.internal.p017b.p018a;

import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.Map;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.j */
/* JADX INFO: compiled from: InquiryHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0912j extends C0906d implements InterfaceC0911i {
    @Override // com.nintendo.npf.sdk.internal.p017b.p018a.InterfaceC0911i
    /* JADX INFO: renamed from: a */
    public void mo1196a(BaaSUser baaSUser, C0918a.b bVar) {
        m1215a(String.format("%s/users/%s", "/inquiry/v1", baaSUser.getUserId()), m1187a(baaSUser), (Map<String, String>) null, true, bVar);
    }
}
