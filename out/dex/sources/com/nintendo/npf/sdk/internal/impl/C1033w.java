package com.nintendo.npf.sdk.internal.impl;

import android.util.Pair;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.w */
/* JADX INFO: compiled from: PromoCodeImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C1033w implements C0918a.b {

    /* JADX INFO: renamed from: a */
    private static final String f1694a = "w";

    /* JADX INFO: renamed from: b */
    private a f1695b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1696c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: d */
    private final C1026p f1697d = this.f1696c.mo1048b();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.w$a */
    /* JADX INFO: compiled from: PromoCodeImpl.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1648a(List<String> list, List<String> list2, NPFError nPFError);
    }

    public C1033w(a aVar) {
        this.f1695b = aVar;
    }

    /* JADX INFO: renamed from: a */
    private Pair<List<String>, List<String>> m1746a(JSONObject jSONObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        JSONArray jSONArray = jSONObject.getJSONArray("transactions");
        C0955e.m1391a(f1694a, "transactions size : " + jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            if (jSONObject2.getString("type").equalsIgnoreCase("promotion")) {
                String string = jSONObject2.getJSONObject("extras").getString("token");
                C0955e.m1391a(f1694a, "token : " + string);
                arrayList.add(string);
                arrayList2.add(jSONObject2.getJSONObject("extras").getString("sku"));
            }
        }
        C0955e.m1391a(f1694a, "purchase token size : " + arrayList.size());
        return new Pair<>(arrayList, arrayList2);
    }

    /* JADX INFO: renamed from: a */
    public void m1747a(JSONArray jSONArray, JSONArray jSONArray2) {
        C0955e.m1393b(f1694a, "exec is called");
        BaaSUser baaSUserM1665a = this.f1697d.m1665a();
        if (!this.f1696c.mo1050d().m1633b(baaSUserM1665a)) {
            this.f1695b.mo1648a(null, null, C1025o.m1656a());
            return;
        }
        C0905c.m1186g().m1209a(baaSUserM1665a, AbstractC0880e.m1122a(), AbstractC0880e.m1125a(jSONArray, jSONArray2), this);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
    /* JADX INFO: renamed from: a */
    public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
        if (nPFError != null) {
            this.f1695b.mo1648a(null, null, nPFError);
            return;
        }
        try {
            Pair<List<String>, List<String>> pairM1746a = m1746a(jSONObject);
            this.f1695b.mo1648a((List) pairM1746a.first, (List) pairM1746a.second, null);
        } catch (JSONException e) {
            this.f1695b.mo1648a(null, null, C1025o.m1658a(e));
        }
    }
}
