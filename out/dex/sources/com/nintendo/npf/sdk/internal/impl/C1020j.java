package com.nintendo.npf.sdk.internal.impl;

import android.os.Bundle;
import com.android.billingclient.util.BillingHelper;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.promo.PromoCode;
import com.nintendo.npf.sdk.promo.PromoCodeBundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.j */
/* JADX INFO: compiled from: PromoCodeImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C1020j implements AbstractC0880e.a, InterfaceC0883h.a {

    /* JADX INFO: renamed from: a */
    private static final String f1616a = "j";

    /* JADX INFO: renamed from: b */
    private static boolean f1617b = false;

    /* JADX INFO: renamed from: c */
    private PromoCode.ExchangePromotionPurchasedCallback f1618c;

    /* JADX INFO: renamed from: d */
    private List<PromoCodeBundle> f1619d;

    /* JADX INFO: renamed from: f */
    private final InterfaceC0875a f1621f = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: e */
    private InterfaceC0883h f1620e = null;

    public C1020j(PromoCode.ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback, List<PromoCodeBundle> list) {
        this.f1618c = exchangePromotionPurchasedCallback;
        this.f1619d = list;
    }

    /* JADX INFO: renamed from: a */
    private void m1642a(Bundle bundle, List<PromoCodeBundle> list) {
        if (bundle == null) {
            m1645a((List<PromoCodeBundle>) null, new C1025o(NPFError.ErrorType.NPF_ERROR, -1, "Internal Error"));
            return;
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
        ArrayList<String> stringArrayList2 = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_PURCHASE_DATA_LIST);
        ArrayList<String> stringArrayList3 = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_SIGNATURE_LIST);
        ArrayList arrayList = new ArrayList();
        Iterator<PromoCodeBundle> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSku());
        }
        try {
            JSONArray jSONArray = new JSONArray();
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < stringArrayList2.size(); i++) {
                if (arrayList.contains(stringArrayList.get(i))) {
                    String str = stringArrayList2.get(i);
                    String str2 = stringArrayList3.get(i);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("data", str);
                    jSONObject.put("signature", str2);
                    jSONArray.put(jSONObject);
                }
            }
            C0955e.m1391a(f1616a, "orders : " + jSONArray2.toString());
            new C1033w(new C1033w.a() { // from class: com.nintendo.npf.sdk.internal.impl.j.1
                @Override // com.nintendo.npf.sdk.internal.impl.C1033w.a
                /* JADX INFO: renamed from: a */
                public void mo1648a(List<String> list2, List<String> list3, NPFError nPFError) {
                    if (nPFError != null) {
                        C1020j.this.m1645a((List<PromoCodeBundle>) null, nPFError);
                    } else {
                        C1020j.this.m1646a(list2, list3);
                    }
                }
            }).m1747a(jSONArray, jSONArray2);
        } catch (JSONException e) {
            e.printStackTrace();
            m1645a((List<PromoCodeBundle>) null, new C1025o(NPFError.ErrorType.NPF_ERROR, 500, e.getLocalizedMessage()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1645a(List<PromoCodeBundle> list, NPFError nPFError) {
        PromoCode.ExchangePromotionPurchasedCallback exchangePromotionPurchasedCallback = this.f1618c;
        if (exchangePromotionPurchasedCallback != null) {
            exchangePromotionPurchasedCallback.onComplete(list, nPFError);
        }
        if (this.f1620e != null) {
            this.f1621f.mo1063q().mo1133d();
            this.f1620e = null;
        }
        f1617b = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1646a(List<String> list, List<String> list2) {
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            int iMo1084a = this.f1620e.mo1084a(list.get(i));
            if (iMo1084a == 0) {
                arrayList.add(list2.get(i));
            }
            C0954d.m1388a("close_promo_receipt_response", String.format("exchangePromotionPurchased#consumePromoCodeTokenPurchase#result response_code: %d purchaseToken: %s", Integer.valueOf(iMo1084a), list.get(i)), null);
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str : arrayList) {
            for (PromoCodeBundle promoCodeBundle : this.f1619d) {
                if (promoCodeBundle.getSku().equals(str)) {
                    arrayList2.add(promoCodeBundle);
                    break;
                }
            }
        }
        m1645a(arrayList2, (NPFError) null);
    }

    /* JADX INFO: renamed from: a */
    public void m1647a() {
        if (!f1617b) {
            f1617b = true;
            this.f1621f.mo1063q().mo1130a(this);
        } else if (this.f1618c != null) {
            this.f1618c.onComplete(null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "exchangePromotionPurchased can't run multiply"));
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h.a
    /* JADX INFO: renamed from: a */
    public void mo1138a(Bundle bundle, NPFError nPFError) {
        if (nPFError != null) {
            C0954d.m1388a("promocode_error", "exchangePromotionPurchased#getPurchases#Error", nPFError);
            m1645a((List<PromoCodeBundle>) null, nPFError);
            return;
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
        if (stringArrayList == null || stringArrayList.size() == 0) {
            m1645a((List<PromoCodeBundle>) null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "not found untreated IAB receipt."));
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : stringArrayList) {
            for (PromoCodeBundle promoCodeBundle : this.f1619d) {
                if (promoCodeBundle.getSku().equals(str)) {
                    arrayList.add(promoCodeBundle);
                    break;
                }
            }
        }
        if (arrayList.size() == 0) {
            m1645a((List<PromoCodeBundle>) null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "not found untreated PromoCode IAB receipt."));
        } else {
            m1642a(bundle, arrayList);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e.a
    /* JADX INFO: renamed from: a */
    public void mo1137a(InterfaceC0883h interfaceC0883h, int i) {
        NPFError nPFErrorMo1129a = this.f1621f.mo1063q().mo1129a(i);
        if (nPFErrorMo1129a != null) {
            C0954d.m1388a("promocode_error", "exchangePromotionPurchased#bindInAppBillingService#Error", nPFErrorMo1129a);
            m1645a((List<PromoCodeBundle>) null, nPFErrorMo1129a);
        } else {
            C0955e.m1391a(f1616a, "Setup successful.");
            this.f1620e = interfaceC0883h;
            interfaceC0883h.mo1086a(this);
        }
    }
}
