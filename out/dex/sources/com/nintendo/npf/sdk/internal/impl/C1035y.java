package com.nintendo.npf.sdk.internal.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.android.billingclient.api.SkuDetails;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.C0879d;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0940l;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.subscription.SubscriptionProduct;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.y */
/* JADX INFO: compiled from: SubscriptionProductImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1035y {

    /* JADX INFO: renamed from: a */
    private static final String f1744a = "y";

    /* JADX INFO: renamed from: b */
    private final C0940l f1745b = new C0940l();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1746c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.y$1, reason: invalid class name */
    /* JADX INFO: compiled from: SubscriptionProductImpl.java */
    class AnonymousClass1 implements C0879d.b {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ C0879d f1747a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ SubscriptionProduct.GetProductsCallback f1748b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ BaaSUser f1749c;

        /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.y$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SubscriptionProductImpl.java */
        class C12911 implements C0879d.c {
            C12911() {
            }

            @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.c
            /* JADX INFO: renamed from: a */
            public void mo1118a(NPFError nPFError) {
                if (nPFError == null) {
                    C0905c.m1185f().mo1199a(AnonymousClass1.this.f1749c, AbstractC0880e.m1122a(), new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.y.1.1.1
                        @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                        /* JADX INFO: renamed from: a */
                        public void mo1145a(JSONArray jSONArray, NPFError nPFError2) {
                            if (nPFError2 != null) {
                                AnonymousClass1.this.f1747a.m1109a();
                                AnonymousClass1.this.f1748b.onComplete(null, nPFError2);
                                return;
                            }
                            try {
                                final Map mapM1775b = C1035y.m1775b(C1035y.this.f1745b.m1263a(jSONArray));
                                AnonymousClass1.this.f1747a.m1114a(new ArrayList(mapM1775b.keySet()), new C0879d.f() { // from class: com.nintendo.npf.sdk.internal.impl.y.1.1.1.1
                                    @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.f
                                    /* JADX INFO: renamed from: a */
                                    public void mo1121a(List<SkuDetails> list, NPFError nPFError3) {
                                        AnonymousClass1.this.f1747a.m1109a();
                                        if (nPFError3 != null) {
                                            C0954d.m1387a("getProducts/getProductDetailsList", nPFError3);
                                            AnonymousClass1.this.f1748b.onComplete(null, nPFError3);
                                            return;
                                        }
                                        ArrayList arrayList = new ArrayList();
                                        for (SkuDetails skuDetails : list) {
                                            SubscriptionProduct subscriptionProduct = (SubscriptionProduct) mapM1775b.get(skuDetails.getSku());
                                            if (subscriptionProduct != null) {
                                                C1035y.m1776b(subscriptionProduct, skuDetails);
                                                arrayList.add(subscriptionProduct);
                                            }
                                        }
                                        AnonymousClass1.this.f1748b.onComplete(arrayList, null);
                                    }
                                });
                            } catch (JSONException e) {
                                AnonymousClass1.this.f1747a.m1109a();
                                AnonymousClass1.this.f1748b.onComplete(null, C1025o.m1658a(e));
                            }
                        }
                    });
                    return;
                }
                C0954d.m1387a("getProducts/checkSubscriptionsSupported", nPFError);
                AnonymousClass1.this.f1747a.m1109a();
                AnonymousClass1.this.f1748b.onComplete(null, nPFError);
            }
        }

        AnonymousClass1(C0879d c0879d, SubscriptionProduct.GetProductsCallback getProductsCallback, BaaSUser baaSUser) {
            this.f1747a = c0879d;
            this.f1748b = getProductsCallback;
            this.f1749c = baaSUser;
        }

        @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.b
        /* JADX INFO: renamed from: a */
        public void mo1117a() {
            this.f1747a.m1112a(new C12911());
        }
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1774a(String str) {
        return str.contains(".subs.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static Map<String, SubscriptionProduct> m1775b(List<SubscriptionProduct> list) {
        HashMap map = new HashMap();
        for (SubscriptionProduct subscriptionProduct : list) {
            if (m1774a(subscriptionProduct.getProductId())) {
                map.put(subscriptionProduct.getProductId(), subscriptionProduct);
            }
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static void m1776b(SubscriptionProduct subscriptionProduct, SkuDetails skuDetails) {
        subscriptionProduct.setTitle(skuDetails.getTitle());
        subscriptionProduct.setDescription(skuDetails.getDescription());
        subscriptionProduct.setSubscriptionPeriod(skuDetails.getSubscriptionPeriod());
        String priceCurrencyCode = skuDetails.getPriceCurrencyCode();
        long priceAmountMicros = skuDetails.getPriceAmountMicros();
        subscriptionProduct.setPrice(AbstractC0880e.m1124a(priceCurrencyCode, new BigDecimal(priceAmountMicros).movePointLeft(6)));
        subscriptionProduct.setPriceCurrencyCode(priceCurrencyCode);
        subscriptionProduct.setPriceAmountMicros(priceAmountMicros);
        subscriptionProduct.setFreeTrialPeriod(skuDetails.getFreeTrialPeriod());
        subscriptionProduct.setIntroductoryPricePeriod(skuDetails.getIntroductoryPricePeriod());
        subscriptionProduct.setIntroductoryPriceCycles(skuDetails.getIntroductoryPriceCycles());
        subscriptionProduct.setIntroductoryPrice(skuDetails.getIntroductoryPrice());
        if (TextUtils.isEmpty(skuDetails.getIntroductoryPriceAmountMicros())) {
            return;
        }
        try {
            subscriptionProduct.setIntroductoryPriceAmountMicros(Long.parseLong(skuDetails.getIntroductoryPriceAmountMicros()));
        } catch (NumberFormatException e) {
            C0955e.m1394b(f1744a, "Could not parse introductory price amount micros", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public JSONObject m1777a(SubscriptionProduct subscriptionProduct) {
        return this.f1745b.mo1259a(subscriptionProduct);
    }

    /* JADX INFO: renamed from: a */
    public void m1778a(Context context, @NonNull SubscriptionProduct.GetProductsCallback getProductsCallback) {
        C0955e.m1393b(f1744a, "getProducts is called");
        if (context == null) {
            C0955e.m1395c(f1744a, "Context is null");
            getProductsCallback.onComplete(null, C1025o.m1661d());
            return;
        }
        BaaSUser baaSUserM1665a = this.f1746c.mo1048b().m1665a();
        if (!this.f1746c.mo1050d().m1633b(baaSUserM1665a)) {
            getProductsCallback.onComplete(null, C1025o.m1656a());
        } else {
            C0879d c0879d = new C0879d();
            c0879d.m1111a(context, new AnonymousClass1(c0879d, getProductsCallback, baaSUserM1665a));
        }
    }
}
