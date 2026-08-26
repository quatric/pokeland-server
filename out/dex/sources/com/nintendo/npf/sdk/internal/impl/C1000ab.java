package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0941m;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.subscription.SubscriptionPurchase;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.ab */
/* JADX INFO: compiled from: SubscriptionPurchaseMockImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1000ab {

    /* JADX INFO: renamed from: a */
    private static final String f1464a = "ab";

    /* JADX INFO: renamed from: b */
    private final C0941m f1465b = new C0941m();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1466c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1542a() {
        C0955e.m1393b(f1464a, "openLink is called");
        C0955e.m1395c(f1464a, "Not supported");
    }

    /* JADX INFO: renamed from: a */
    public void m1543a(@NonNull final SubscriptionPurchase.OwnershipsCallback ownershipsCallback) {
        C0955e.m1393b(f1464a, "updateOwnerships is called");
        BaaSUser baaSUserM1665a = this.f1466c.mo1048b().m1665a();
        if (this.f1466c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1185f().mo1204b(baaSUserM1665a, "MOCK", new JSONObject(), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.ab.3
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        ownershipsCallback.onComplete(-1, -1L, nPFError);
                        return;
                    }
                    try {
                        List listM1276d = C1000ab.this.f1465b.m1276d(jSONObject);
                        ownershipsCallback.onComplete(((Integer) listM1276d.get(0)).intValue(), ((Long) listM1276d.get(1)).longValue(), null);
                    } catch (JSONException e) {
                        ownershipsCallback.onComplete(-1, -1L, C1025o.m1658a(e));
                    }
                }
            });
        } else {
            ownershipsCallback.onComplete(-1, -1L, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1544a(@NonNull final SubscriptionPurchase.PurchasesCallback purchasesCallback) {
        C0955e.m1393b(f1464a, "getPurchases is called");
        BaaSUser baaSUserM1665a = this.f1466c.mo1048b().m1665a();
        if (this.f1466c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1185f().mo1203b(baaSUserM1665a, "MOCK", new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.ab.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        purchasesCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        purchasesCallback.onComplete(C1000ab.this.f1465b.m1263a(jSONArray), null);
                    } catch (JSONException e) {
                        purchasesCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        } else {
            purchasesCallback.onComplete(null, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1545a(String str) {
        C0955e.m1393b(f1464a, "openDeepLink is called");
        C0955e.m1395c(f1464a, "Not supported");
    }

    /* JADX INFO: renamed from: a */
    public void m1546a(final String str, @NonNull final SubscriptionPurchase.PurchaseCallback purchaseCallback) {
        C0955e.m1393b(f1464a, "purchase is called");
        if (TextUtils.isEmpty(str)) {
            C0955e.m1395c(f1464a, "Product id is null or empty");
            purchaseCallback.onComplete(C1025o.m1661d());
            return;
        }
        final BaaSUser baaSUserM1665a = this.f1466c.mo1048b().m1665a();
        if (this.f1466c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1185f().mo1200a(baaSUserM1665a, "MOCK", str, new JSONObject(), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.ab.4
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        purchaseCallback.onComplete(nPFError);
                        return;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("productId", str);
                    } catch (JSONException e) {
                        C0955e.m1394b(C1000ab.f1464a, "makeReceipt", e);
                    }
                    C0905c.m1185f().mo1202a(baaSUserM1665a, "MOCK", jSONObject2, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.ab.4.1
                        @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                        /* JADX INFO: renamed from: a */
                        public void mo1143a(JSONObject jSONObject3, NPFError nPFError2) {
                            if (nPFError2 != null) {
                                purchaseCallback.onComplete(nPFError2);
                            } else {
                                purchaseCallback.onComplete(null);
                            }
                        }
                    });
                }
            });
        } else {
            purchaseCallback.onComplete(C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1547b(@NonNull final SubscriptionPurchase.PurchasesCallback purchasesCallback) {
        C0955e.m1393b(f1464a, "updatePurchases is called");
        BaaSUser baaSUserM1665a = this.f1466c.mo1048b().m1665a();
        if (this.f1466c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1185f().mo1201a(baaSUserM1665a, "MOCK", new JSONObject(), new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.ab.2
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        purchasesCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        purchasesCallback.onComplete(C1000ab.this.f1465b.m1263a(jSONArray), null);
                    } catch (JSONException e) {
                        purchasesCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        } else {
            purchasesCallback.onComplete(null, C1025o.m1656a());
        }
    }
}
