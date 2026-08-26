package com.nintendo.npf.sdk.internal.p016a;

import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0942n;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.j */
/* JADX INFO: compiled from: VirtualCurrencyBundleGetter.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0885j implements AbstractC0880e.a, InterfaceC0883h.b, C0918a.a {

    /* JADX INFO: renamed from: a */
    private static final String f1095a = "j";

    /* JADX INFO: renamed from: b */
    private final VirtualCurrencyBundle.RetrievingCallback f1096b;

    /* JADX INFO: renamed from: c */
    private JSONArray f1097c;

    /* JADX INFO: renamed from: d */
    private NPFError f1098d = null;

    /* JADX INFO: renamed from: e */
    private HashMap<String, List<VirtualCurrencyBundle>> f1099e = null;

    /* JADX INFO: renamed from: g */
    private final C0942n f1101g = new C0942n();

    /* JADX INFO: renamed from: h */
    private final InterfaceC0875a f1102h = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: f */
    private InterfaceC0883h f1100f = null;

    public C0885j(VirtualCurrencyBundle.RetrievingCallback retrievingCallback) {
        this.f1096b = retrievingCallback;
    }

    /* JADX INFO: renamed from: a */
    private void m1144a(Map<String, List<VirtualCurrencyBundle>> map, NPFError nPFError) {
        VirtualCurrencyBundle.RetrievingCallback retrievingCallback = this.f1096b;
        if (retrievingCallback != null) {
            retrievingCallback.onComplete(map, nPFError);
        }
        if (this.f1100f != null) {
            this.f1102h.mo1063q().mo1133d();
            this.f1100f = null;
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e.a
    /* JADX INFO: renamed from: a */
    public void mo1137a(InterfaceC0883h interfaceC0883h, int i) {
        try {
            this.f1100f = interfaceC0883h;
            this.f1098d = this.f1102h.mo1063q().mo1129a(i);
            if (this.f1098d != null) {
                C0954d.m1388a("purchase_error", "getAll#bindInAppBillingService#Error", this.f1098d);
                m1144a((Map<String, List<VirtualCurrencyBundle>>) null, this.f1098d);
                return;
            }
            C0955e.m1391a(f1095a, "Setup successful.");
            this.f1099e = new HashMap<>();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.f1097c.length(); i2++) {
                JSONArray jSONArray = this.f1097c.getJSONObject(i2).getJSONArray("items");
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    arrayList.add(jSONArray.getJSONObject(i3).getString("sku"));
                }
            }
            this.f1100f.mo1087a(arrayList, this);
        } catch (JSONException e) {
            m1144a(this.f1099e, C1025o.m1658a(e));
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h.b
    /* JADX INFO: renamed from: a */
    public void mo1139a(HashMap<String, JSONObject> map, NPFError nPFError) {
        try {
            if (nPFError != null) {
                C0954d.m1388a("purchase_error", "getAll#getSkuDetails", nPFError);
                m1144a((Map<String, List<VirtualCurrencyBundle>>) null, nPFError);
                return;
            }
            Map<String, List<VirtualCurrencyBundle>> mapM1278b = this.f1101g.m1278b(this.f1097c);
            this.f1099e = new HashMap<>();
            for (Map.Entry<String, List<VirtualCurrencyBundle>> entry : mapM1278b.entrySet()) {
                ArrayList arrayList = new ArrayList();
                for (VirtualCurrencyBundle virtualCurrencyBundle : entry.getValue()) {
                    if (map.containsKey(virtualCurrencyBundle.getSKU())) {
                        JSONObject jSONObject = map.get(virtualCurrencyBundle.getSKU());
                        virtualCurrencyBundle.setTitle(jSONObject.getString(C0856j.f955a));
                        virtualCurrencyBundle.setDetail(jSONObject.getString("description"));
                        virtualCurrencyBundle.setPrice(new BigDecimal(jSONObject.getString("price_amount_micros")).movePointLeft(6));
                        virtualCurrencyBundle.setPriceCode(jSONObject.getString("price_currency_code"));
                        virtualCurrencyBundle.setDisplayPrice(jSONObject.getString("display_price"));
                        arrayList.add(virtualCurrencyBundle);
                    }
                }
                this.f1099e.put(entry.getKey(), arrayList);
            }
        } catch (JSONException e) {
            this.f1098d = C1025o.m1658a(e);
        } finally {
            m1144a(this.f1099e, this.f1098d);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
    /* JADX INFO: renamed from: a */
    public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
        if (nPFError != null) {
            m1144a((Map<String, List<VirtualCurrencyBundle>>) null, nPFError);
            return;
        }
        if (jSONArray == null || jSONArray.length() == 0) {
            this.f1099e = new HashMap<>();
            m1144a(this.f1099e, (NPFError) null);
        } else if (!this.f1102h.mo1065s().m1335j()) {
            this.f1097c = jSONArray;
            this.f1102h.mo1063q().mo1130a(this);
        } else {
            try {
                m1144a(this.f1101g.m1278b(jSONArray), (NPFError) null);
            } catch (JSONException e) {
                m1144a((Map<String, List<VirtualCurrencyBundle>>) null, C1025o.m1658a(e));
            }
        }
    }
}
