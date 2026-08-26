package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.audit.ProfanityWord;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0938j;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.s */
/* JADX INFO: compiled from: ProfanityWordImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1029s {

    /* JADX INFO: renamed from: a */
    private static final String f1674a = "s";

    /* JADX INFO: renamed from: b */
    private final C0938j f1675b = new C0938j();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1676c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1729a(List<ProfanityWord> list, @NonNull final ProfanityWord.CheckProfanityWordCallback checkProfanityWordCallback) {
        C0955e.m1393b(f1674a, "checkProfanityWord is called");
        BaaSUser baaSUserM1665a = this.f1676c.mo1048b().m1665a();
        if (!this.f1676c.mo1050d().m1633b(baaSUserM1665a)) {
            checkProfanityWordCallback.onComplete(null, C1025o.m1656a());
        } else if (list == null || list.size() == 0) {
            checkProfanityWordCallback.onComplete(null, C1025o.m1661d());
        } else {
            C0905c.m1180a().mo1179a(baaSUserM1665a, this.f1675b.m1264a((List) list), new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.s.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        checkProfanityWordCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        checkProfanityWordCallback.onComplete(C1029s.this.f1675b.m1263a(jSONArray), null);
                    } catch (JSONException e) {
                        checkProfanityWordCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        }
    }
}
