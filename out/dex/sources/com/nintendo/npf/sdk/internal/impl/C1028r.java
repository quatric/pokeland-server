package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0937i;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.OtherUser;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.r */
/* JADX INFO: compiled from: OtherUserImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1028r {

    /* JADX INFO: renamed from: a */
    private static final String f1669a = "r";

    /* JADX INFO: renamed from: b */
    private final C0937i f1670b = new C0937i();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1671c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1727a(List<String> list, @NonNull final OtherUser.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1669a, "getAsList is called");
        BaaSUser baaSUserM1665a = this.f1671c.mo1048b().m1665a();
        if (!this.f1671c.mo1050d().m1633b(baaSUserM1665a)) {
            retrievingCallback.onComplete(null, C1025o.m1656a());
            return;
        }
        if (list == null || list.size() == 0) {
            retrievingCallback.onComplete(null, new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "List is null or empty"));
        } else if (list.size() > 100) {
            retrievingCallback.onComplete(null, new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "List size cannot be over 100"));
        } else {
            C0905c.m1182c().mo1192a(baaSUserM1665a, list, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.r.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        retrievingCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        retrievingCallback.onComplete(C1028r.this.f1670b.m1265c(jSONObject), null);
                    } catch (JSONException e) {
                        retrievingCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        }
    }
}
