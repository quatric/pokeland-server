package com.nintendo.npf.sdk.internal.impl;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.promo.PromoCode;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import java.util.List;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.k */
/* JADX INFO: compiled from: PromoCodeImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C1021k implements C1022l.a {

    /* JADX INFO: renamed from: a */
    private PromoCode.ExchangePromotionPurchasedCallback f1623a;

    public C1021k(PromoCode.ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback) {
        this.f1623a = null;
        this.f1623a = exchangePromotionPurchasedCallback;
    }

    @Override // com.nintendo.npf.sdk.internal.impl.C1022l.a
    /* JADX INFO: renamed from: a */
    public void mo1158a(List<PromoCodeBundle> list, NPFError nPFError) {
        if (nPFError == null) {
            new C1020j(this.f1623a, list).m1647a();
            return;
        }
        PromoCode.ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback = this.f1623a;
        if (exchangePromotionPurchasedCallback != null) {
            exchangePromotionPurchasedCallback.onComplete(null, nPFError);
        }
    }
}
