package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0939k;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.l */
/* JADX INFO: compiled from: GetPromoCodeBundleListImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1022l implements C0918a.a {

    /* JADX INFO: renamed from: a */
    private static final String f1624a = "l";

    /* JADX INFO: renamed from: c */
    private a f1626c;

    /* JADX INFO: renamed from: b */
    private final C0939k f1625b = new C0939k();

    /* JADX INFO: renamed from: d */
    private final InterfaceC0875a f1627d = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.l$a */
    /* JADX INFO: compiled from: GetPromoCodeBundleListImpl.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1158a(List<PromoCodeBundle> list, NPFError nPFError);
    }

    public C1022l(@NonNull a aVar) {
        this.f1626c = aVar;
    }

    /* JADX INFO: renamed from: a */
    private List<PromoCodeBundle> m1649a(JSONArray jSONArray) throws JSONException {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray3 = jSONArray.getJSONObject(i).getJSONArray("items");
            for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
                jSONArray2.put(jSONArray3.get(i2));
            }
        }
        List<PromoCodeBundle> listA = this.f1625b.m1263a(jSONArray2);
        C0955e.m1391a(f1624a, listA.toString());
        return listA;
    }

    /* JADX INFO: renamed from: a */
    public void m1650a() {
        C0955e.m1393b(f1624a, "exec is called");
        BaaSUser baaSUserM1665a = this.f1627d.mo1048b().m1665a();
        if (!this.f1627d.mo1050d().m1633b(baaSUserM1665a)) {
            this.f1626c.mo1158a(null, C1025o.m1656a());
        } else {
            C0905c.m1186g().m1211c(baaSUserM1665a, AbstractC0880e.m1122a(), this);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
    /* JADX INFO: renamed from: a */
    public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
        if (nPFError != null) {
            this.f1626c.mo1158a(null, nPFError);
            return;
        }
        try {
            this.f1626c.mo1158a(m1649a(jSONArray), null);
        } catch (JSONException e) {
            this.f1626c.mo1158a(null, C1025o.m1658a(e));
        }
    }
}
