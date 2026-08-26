package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0944p;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.ad */
/* JADX INFO: compiled from: VirtualCurrencyPurchasedSummaryImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1002ad {

    /* JADX INFO: renamed from: a */
    private static final String f1480a = VirtualCurrencyPurchasedSummary.class.getSimpleName();

    /* JADX INFO: renamed from: b */
    private static final String[] f1481b = {"GOOGLE", "APPLE", "AMAZON"};

    /* JADX INFO: renamed from: c */
    private final C0944p f1482c = new C0944p();

    /* JADX INFO: renamed from: d */
    private final InterfaceC0875a f1483d = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    private static String m1554a(int i) {
        if (i == 0) {
            return "Z";
        }
        return (i < 0 ? "-" : "+") + String.format(Locale.US, "%1$02d", Integer.valueOf(Math.abs(i) / 60)) + ":" + String.format(Locale.US, "%1$02d", Integer.valueOf(Math.abs(i) % 60));
    }

    /* JADX INFO: renamed from: a */
    private void m1555a(String str, int i, String str2, final VirtualCurrencyPurchasedSummary.GetAllByMarketCallback getAllByMarketCallback) {
        BaaSUser baaSUserM1665a = this.f1483d.mo1048b().m1665a();
        if (!this.f1483d.mo1050d().m1633b(baaSUserM1665a)) {
            getAllByMarketCallback.onComplete(null, C1025o.m1656a());
        } else if (!m1556a(str2)) {
            getAllByMarketCallback.onComplete(null, new C1025o(NPFError.ErrorType.NPF_ERROR, 410, "Unsupported market"));
        } else {
            C0905c.m1186g().m1207a(baaSUserM1665a, str2, m1554a(i), str, new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.ad.3
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        getAllByMarketCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        List<VirtualCurrencyPurchasedSummary> listA = C1002ad.this.f1482c.m1263a(jSONArray);
                        HashMap map = new HashMap();
                        for (VirtualCurrencyPurchasedSummary virtualCurrencyPurchasedSummary : listA) {
                            map.put(virtualCurrencyPurchasedSummary.getVirtualCurrencyName(), virtualCurrencyPurchasedSummary);
                        }
                        getAllByMarketCallback.onComplete(map, null);
                    } catch (JSONException e) {
                        getAllByMarketCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    private static boolean m1556a(String str) {
        for (String str2 : f1481b) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: a */
    public void m1557a(int i, @NonNull final VirtualCurrencyPurchasedSummary.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1480a, "getAll is called");
        m1558a(i, AbstractC0880e.m1122a(), new VirtualCurrencyPurchasedSummary.GetAllByMarketCallback() { // from class: com.nintendo.npf.sdk.internal.impl.ad.1
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
            public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
                retrievingCallback.onComplete(map, nPFError);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1558a(int i, String str, @NonNull VirtualCurrencyPurchasedSummary.GetAllByMarketCallback getAllByMarketCallback) {
        C0955e.m1393b(f1480a, "getAllByMarket is called");
        m1555a("transaction_histories", i, str, getAllByMarketCallback);
    }

    /* JADX INFO: renamed from: b */
    public void m1559b(int i, @NonNull final VirtualCurrencyPurchasedSummary.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1480a, "getAllCache is called");
        m1560b(i, AbstractC0880e.m1122a(), new VirtualCurrencyPurchasedSummary.GetAllByMarketCallback() { // from class: com.nintendo.npf.sdk.internal.impl.ad.2
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyPurchasedSummary.GetAllByMarketCallback
            public void onComplete(Map<String, VirtualCurrencyPurchasedSummary> map, NPFError nPFError) {
                retrievingCallback.onComplete(map, nPFError);
            }
        });
    }

    /* JADX INFO: renamed from: b */
    public void m1560b(int i, String str, @NonNull VirtualCurrencyPurchasedSummary.GetAllByMarketCallback getAllByMarketCallback) {
        C0955e.m1393b(f1480a, "getAllCacheByMarket is called");
        m1555a("transaction_histories_cached", i, str, getAllByMarketCallback);
    }
}
