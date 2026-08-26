package com.nintendo.npf.sdk.internal.p016a;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1022l;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.o */
/* JADX INFO: compiled from: VirtualCurrencyPromoCodeBundleListForRecoverPurchase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0890o implements C1022l.a {

    /* JADX INFO: renamed from: a */
    private VirtualCurrencyWallet.RetrievingCallback f1129a;

    /* JADX INFO: renamed from: b */
    private final InterfaceC0875a f1130b = InterfaceC0875a.a.m1072b();

    public C0890o(VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        this.f1129a = retrievingCallback;
    }

    @Override // com.nintendo.npf.sdk.internal.impl.C1022l.a
    /* JADX INFO: renamed from: a */
    public void mo1158a(List<PromoCodeBundle> list, NPFError nPFError) {
        if (nPFError != null) {
            VirtualCurrencyWallet.RetrievingCallback retrievingCallback = this.f1129a;
            if (retrievingCallback != null) {
                retrievingCallback.onComplete(null, nPFError);
            }
            this.f1130b.mo1049c().m1522c().onVirtualCurrencyPurchaseProcessError(nPFError);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<PromoCodeBundle> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSku());
        }
        C0886k.m1147a().m1151a(arrayList);
        C0886k.m1147a().m1150a(this.f1129a);
    }
}
