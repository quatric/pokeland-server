package com.nintendo.npf.sdk.internal.p017b.p018a;

import com.google.common.net.HttpHeaders;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.a.d */
/* JADX INFO: compiled from: BaasHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0906d extends C0918a {
    /* JADX INFO: renamed from: a */
    protected Map<String, String> m1187a(final BaaSUser baaSUser) {
        return new HashMap<String, String>() { // from class: com.nintendo.npf.sdk.internal.b.a.d.1
            {
                put(HttpHeaders.AUTHORIZATION, "Bearer " + baaSUser.getAccessToken());
            }
        };
    }
}
