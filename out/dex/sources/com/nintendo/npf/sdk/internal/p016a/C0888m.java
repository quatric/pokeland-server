package com.nintendo.npf.sdk.internal.p016a;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.C1022l;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.m */
/* JADX INFO: compiled from: VirtualCurrencyPromoCodeBundleListForCheckUnProcessedPurchase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0888m implements C1022l.a {

    /* JADX INFO: renamed from: a */
    private VirtualCurrencyBundle.UnprocessedPurchaseCallback f1123a;

    public C0888m(VirtualCurrencyBundle.UnprocessedPurchaseCallback unprocessedPurchaseCallback) {
        this.f1123a = unprocessedPurchaseCallback;
    }

    @Override // com.nintendo.npf.sdk.internal.impl.C1022l.a
    /* JADX INFO: renamed from: a */
    public void mo1158a(List<PromoCodeBundle> list, NPFError nPFError) {
        if (nPFError != null) {
            VirtualCurrencyBundle.UnprocessedPurchaseCallback unprocessedPurchaseCallback = this.f1123a;
            if (unprocessedPurchaseCallback != null) {
                unprocessedPurchaseCallback.onComplete(null, nPFError);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<PromoCodeBundle> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSku());
        }
        C0887l c0887l = new C0887l(this.f1123a);
        c0887l.m1157a(arrayList);
        c0887l.m1156a();
    }
}
