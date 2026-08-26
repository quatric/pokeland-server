package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1022l;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.n */
/* JADX INFO: compiled from: VirtualCurrencyPromoCodeBundleListForPurchase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0889n implements C1022l.a {

    /* JADX INFO: renamed from: a */
    private Activity f1124a;

    /* JADX INFO: renamed from: b */
    private VirtualCurrencyBundle f1125b;

    /* JADX INFO: renamed from: c */
    private VirtualCurrencyWallet.RetrievingCallback f1126c;

    /* JADX INFO: renamed from: d */
    private String f1127d;

    /* JADX INFO: renamed from: e */
    private final InterfaceC0875a f1128e = InterfaceC0875a.a.m1072b();

    public C0889n(Activity activity, VirtualCurrencyBundle virtualCurrencyBundle, VirtualCurrencyWallet.RetrievingCallback retrievingCallback, String str) {
        this.f1124a = activity;
        this.f1125b = virtualCurrencyBundle;
        this.f1126c = retrievingCallback;
        this.f1127d = str;
    }

    @Override // com.nintendo.npf.sdk.internal.impl.C1022l.a
    /* JADX INFO: renamed from: a */
    public void mo1158a(List<PromoCodeBundle> list, NPFError nPFError) {
        if (nPFError != null) {
            VirtualCurrencyWallet.RetrievingCallback retrievingCallback = this.f1126c;
            if (retrievingCallback != null) {
                retrievingCallback.onComplete(null, nPFError);
            }
            this.f1128e.mo1049c().m1522c().onVirtualCurrencyPurchaseProcessError(nPFError);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<PromoCodeBundle> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSku());
        }
        C0886k.m1147a().m1151a(arrayList);
        C0886k.m1147a().m1149a(this.f1124a, this.f1125b, this.f1127d, this.f1126c);
    }
}
