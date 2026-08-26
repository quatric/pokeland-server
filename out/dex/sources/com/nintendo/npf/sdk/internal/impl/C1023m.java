package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.inquiry.InquiryStatus;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0933e;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.m */
/* JADX INFO: compiled from: InquiryStatusImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1023m {

    /* JADX INFO: renamed from: a */
    private static final String f1628a = "m";

    /* JADX INFO: renamed from: b */
    private final C0933e f1629b = new C0933e();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1630c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1652a(@NonNull final InquiryStatus.CheckCallback checkCallback) {
        C0955e.m1393b(f1628a, "check is called");
        BaaSUser baaSUserM1665a = this.f1630c.mo1048b().m1665a();
        if (this.f1630c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1183d().mo1196a(baaSUserM1665a, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.m.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        checkCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        checkCallback.onComplete(C1023m.this.f1629b.mo1260b(jSONObject), null);
                    } catch (JSONException e) {
                        checkCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        } else {
            checkCallback.onComplete(null, C1025o.m1656a());
        }
    }
}
