package com.nintendo.npf.sdk.internal.impl;

import android.os.Bundle;
import com.android.billingclient.util.BillingHelper;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h;
import com.nintendo.npf.sdk.internal.p021c.C0939k;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.promo.PromoCode;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.i */
/* JADX INFO: compiled from: PromoCodeImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C1019i implements AbstractC0880e.a, InterfaceC0883h.a {

    /* JADX INFO: renamed from: a */
    private static final String f1608a = "i";

    /* JADX INFO: renamed from: b */
    private boolean f1609b;

    /* JADX INFO: renamed from: c */
    private PromoCode.CheckRemainExchangePromotionPurchasedCallback f1610c;

    /* JADX INFO: renamed from: d */
    private InterfaceC0883h f1611d;

    /* JADX INFO: renamed from: e */
    private final InterfaceC0875a f1612e = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.i$a */
    /* JADX INFO: compiled from: PromoCodeImpl.java */
    private class a implements C1022l.a {

        /* JADX INFO: renamed from: b */
        private List<String> f1614b;

        /* JADX INFO: renamed from: c */
        private boolean f1615c;

        public a(List<String> list, boolean z) {
            this.f1614b = list;
            this.f1615c = z;
        }

        @Override // com.nintendo.npf.sdk.internal.impl.C1022l.a
        /* JADX INFO: renamed from: a */
        public void mo1158a(List<PromoCodeBundle> list, NPFError nPFError) {
            PromoCode.EventHandler eventHandlerM1730a;
            PromoCodeBundle next;
            if (nPFError != null) {
                C1019i.this.m1638a(null, null, nPFError);
                C1019i.this.m1640b();
                return;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (String str : this.f1614b) {
                Iterator<PromoCodeBundle> it = list.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!next.getSku().equals(str));
                if (next != null) {
                    arrayList.add(next);
                } else {
                    arrayList2.add(new C0939k.a(str, null));
                }
            }
            if (arrayList.size() != 0) {
                C1019i.this.m1638a(arrayList, arrayList2, null);
                C1019i.this.m1640b();
                return;
            }
            if (this.f1615c) {
                if (arrayList2.size() != 0 && (eventHandlerM1730a = C1019i.this.f1612e.mo1069w().m1730a()) != null) {
                    eventHandlerM1730a.onOthersNotificationSuccess(arrayList2);
                }
                C1019i.this.f1612e.mo1069w().m1735b();
            } else {
                C1019i.this.m1638a(new ArrayList(), arrayList2, null);
            }
            C1019i.this.m1640b();
        }
    }

    public C1019i(PromoCode.CheckRemainExchangePromotionPurchasedCallback checkRemainExchangePromotionPurchasedCallback, boolean z) {
        this.f1610c = null;
        this.f1611d = null;
        this.f1610c = checkRemainExchangePromotionPurchasedCallback;
        this.f1609b = z;
        this.f1611d = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1638a(List<PromoCodeBundle> list, List<PromoCodeBundle> list2, NPFError nPFError) {
        PromoCode.CheckRemainExchangePromotionPurchasedCallback checkRemainExchangePromotionPurchasedCallback = this.f1610c;
        if (checkRemainExchangePromotionPurchasedCallback != null) {
            checkRemainExchangePromotionPurchasedCallback.onComplete(list, nPFError);
        }
        PromoCode.EventHandler eventHandlerM1730a = this.f1612e.mo1069w().m1730a();
        if (this.f1609b && eventHandlerM1730a != null) {
            if (nPFError == null) {
                eventHandlerM1730a.onPromotionNotificationSuccess(list);
                if (list2 != null && list2.size() != 0) {
                    eventHandlerM1730a.onOthersNotificationSuccess(list2);
                }
            } else {
                eventHandlerM1730a.onPromotionNotoficationError(nPFError);
            }
        }
        if (this.f1609b) {
            this.f1612e.mo1069w().m1735b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1640b() {
        if (this.f1611d != null) {
            this.f1612e.mo1063q().mo1133d();
            this.f1611d = null;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1641a() {
        this.f1612e.mo1063q().mo1130a(this);
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h.a
    /* JADX INFO: renamed from: a */
    public void mo1138a(Bundle bundle, NPFError nPFError) {
        if (nPFError != null) {
            C0954d.m1388a("promocode_error", "checkRemainExchangePromotion#getPurchases#Error", nPFError);
            m1638a(null, null, nPFError);
            m1640b();
            return;
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
        if (stringArrayList != null && stringArrayList.size() != 0) {
            new C1022l(new a(stringArrayList, this.f1609b)).m1650a();
            return;
        }
        if (this.f1609b) {
            this.f1612e.mo1069w().m1735b();
        } else {
            m1638a(new ArrayList(), null, null);
        }
        m1640b();
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e.a
    /* JADX INFO: renamed from: a */
    public void mo1137a(InterfaceC0883h interfaceC0883h, int i) {
        this.f1611d = interfaceC0883h;
        if (this.f1612e.mo1063q().mo1131b(i)) {
            this.f1611d.mo1086a(this);
            return;
        }
        NPFError nPFErrorMo1129a = this.f1612e.mo1063q().mo1129a(i);
        if (this.f1609b && this.f1612e.mo1063q().mo1132c(i)) {
            this.f1612e.mo1069w().m1735b();
        } else {
            C0954d.m1388a("promocode_error", "checkRemainExchangePromotion#bindInAppBillingService#Error", nPFErrorMo1129a);
            m1638a(null, null, nPFErrorMo1129a);
        }
        m1640b();
    }
}
