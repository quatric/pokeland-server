package com.nintendo.npf.sdk.internal.p017b.p020c;

import com.google.common.net.HttpHeaders;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.b.c.f */
/* JADX INFO: compiled from: NaHttpClient.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0928f extends C0918a {

    /* JADX INFO: renamed from: c */
    protected String f1213c;

    /* JADX INFO: renamed from: d */
    protected boolean f1214d;

    /* JADX INFO: renamed from: a */
    protected Map<String, String> m1256a(final NintendoAccount nintendoAccount) {
        return new HashMap<String, String>() { // from class: com.nintendo.npf.sdk.internal.b.c.f.1
            {
                put(HttpHeaders.AUTHORIZATION, "Bearer " + nintendoAccount.getAccessToken());
            }
        };
    }

    /* JADX INFO: renamed from: a */
    public void m1257a(boolean z, String str, String str2, boolean z2) {
        super.m1220a(z, str);
        this.f1213c = str2;
        this.f1214d = z2;
    }
}
