package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0945q;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.ae */
/* JADX INFO: compiled from: VirtualCurrencyWalletImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1003ae {

    /* JADX INFO: renamed from: a */
    private static final String f1490a = VirtualCurrencyWallet.class.getSimpleName();

    /* JADX INFO: renamed from: b */
    private final C0945q f1491b = new C0945q();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1492c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1562a(@NonNull final VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1490a, "getAll is called");
        BaaSUser baaSUserM1665a = this.f1492c.mo1048b().m1665a();
        if (!this.f1492c.mo1050d().m1633b(baaSUserM1665a)) {
            retrievingCallback.onComplete(null, C1025o.m1656a());
        } else {
            C0905c.m1186g().m1206a(baaSUserM1665a, AbstractC0880e.m1122a(), new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.ae.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        retrievingCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        List<VirtualCurrencyWallet> listA = C1003ae.this.f1491b.m1263a(jSONArray);
                        HashMap map = new HashMap();
                        for (VirtualCurrencyWallet virtualCurrencyWallet : listA) {
                            map.put(virtualCurrencyWallet.getVirtualCurrencyName(), virtualCurrencyWallet);
                        }
                        retrievingCallback.onComplete(map, null);
                    } catch (JSONException e) {
                        retrievingCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        }
    }
}
