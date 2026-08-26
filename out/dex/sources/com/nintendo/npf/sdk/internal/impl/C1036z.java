package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0940l;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.subscription.SubscriptionProduct;
import com.nintendo.npf.sdk.user.BaaSUser;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.z */
/* JADX INFO: compiled from: SubscriptionProductMockImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1036z {

    /* JADX INFO: renamed from: a */
    private static final String f1755a = C1035y.class.getSimpleName();

    /* JADX INFO: renamed from: b */
    private final C0940l f1756b = new C0940l();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1757c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1780a(@NonNull final SubscriptionProduct.GetProductsCallback getProductsCallback) {
        C0955e.m1393b(f1755a, "getProducts is called");
        BaaSUser baaSUserM1665a = this.f1757c.mo1048b().m1665a();
        if (this.f1757c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1185f().mo1199a(baaSUserM1665a, "MOCK", new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.z.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        getProductsCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        getProductsCallback.onComplete(C1036z.this.f1756b.m1263a(jSONArray), null);
                    } catch (JSONException e) {
                        getProductsCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        } else {
            getProductsCallback.onComplete(null, C1025o.m1656a());
        }
    }
}
