package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.promo.PromoCode;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.t */
/* JADX INFO: compiled from: PromoCodeImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1030t {

    /* JADX INFO: renamed from: a */
    private static final String f1679a = "t";

    /* JADX INFO: renamed from: c */
    private PromoCode.EventHandler f1681c;

    /* JADX INFO: renamed from: b */
    private final InterfaceC0875a f1680b = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: d */
    private boolean f1682d = false;

    /* JADX INFO: renamed from: a */
    public PromoCode.EventHandler m1730a() {
        return this.f1681c;
    }

    /* JADX INFO: renamed from: a */
    public void m1731a(@NonNull PromoCode.CheckRemainExchangePromotionPurchasedCallback checkRemainExchangePromotionPurchasedCallback) {
        C0955e.m1393b(f1679a, "checkRemainExchangePromotionPurchased is called");
        if (!this.f1680b.mo1050d().m1633b(this.f1680b.mo1048b().m1665a())) {
            checkRemainExchangePromotionPurchasedCallback.onComplete(null, C1025o.m1656a());
        } else if (this.f1680b.mo1065s().m1335j()) {
            checkRemainExchangePromotionPurchasedCallback.onComplete(new ArrayList(), null);
        } else {
            new C1019i(checkRemainExchangePromotionPurchasedCallback, false).m1641a();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1732a(PromoCode.EventHandler eventHandler) {
        this.f1681c = eventHandler;
    }

    /* JADX INFO: renamed from: a */
    public void m1733a(@NonNull PromoCode.ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback) {
        C0955e.m1393b(f1679a, "exchangePromotionPurchased is called");
        if (!this.f1680b.mo1050d().m1633b(this.f1680b.mo1048b().m1665a())) {
            exchangePromotionPurchasedCallback.onComplete(null, C1025o.m1656a());
        } else if (this.f1680b.mo1065s().m1335j()) {
            exchangePromotionPurchasedCallback.onComplete(null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "not found untreated IAB receipt."));
        } else {
            new C1022l(new C1021k(exchangePromotionPurchasedCallback)).m1650a();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1734a(boolean z, NPFError nPFError) {
        if (this.f1680b.mo1065s().m1335j()) {
            return;
        }
        InterfaceC0875a interfaceC0875aM1072b = InterfaceC0875a.a.m1072b();
        if (interfaceC0875aM1072b.mo1049c() == null || !interfaceC0875aM1072b.mo1049c().m1519a()) {
            return;
        }
        if (nPFError != null) {
            PromoCode.EventHandler eventHandler = this.f1681c;
            if (eventHandler != null) {
                eventHandler.onPromotionNotoficationError(nPFError);
                return;
            }
            return;
        }
        if (this.f1682d) {
            return;
        }
        this.f1682d = true;
        new C1019i(null, true).m1641a();
    }

    /* JADX INFO: renamed from: b */
    public void m1735b() {
        this.f1682d = false;
    }
}
