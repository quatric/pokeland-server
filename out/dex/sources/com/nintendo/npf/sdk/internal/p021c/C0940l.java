package com.nintendo.npf.sdk.internal.p021c;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p023e.C0951a;
import com.nintendo.npf.sdk.subscription.SubscriptionProduct;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.l */
/* JADX INFO: compiled from: SubscriptionProductMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0940l extends AbstractC0931c<SubscriptionProduct> {

    /* JADX INFO: renamed from: a */
    private final InterfaceC0875a f1220a = InterfaceC0875a.a.m1072b();

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public SubscriptionProduct mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        SubscriptionProduct subscriptionProduct = new SubscriptionProduct();
        if (AbstractC0931c.m1262a(jSONObject, "subscriptionId")) {
            subscriptionProduct.setSubscriptionId(jSONObject.getString("subscriptionId"));
            if (AbstractC0931c.m1262a(jSONObject, "productId")) {
                subscriptionProduct.setProductId(jSONObject.getString("productId"));
                if (AbstractC0931c.m1262a(jSONObject, "startsAt")) {
                    subscriptionProduct.setStartsAt(jSONObject.getLong("startsAt"));
                    if (AbstractC0931c.m1262a(jSONObject, "endsAt")) {
                        subscriptionProduct.setEndsAt(jSONObject.getLong("endsAt"));
                        if (AbstractC0931c.m1262a(jSONObject, "attributes")) {
                            subscriptionProduct.setAttributes(C0951a.m1384a(jSONObject.getJSONObject("attributes")));
                            if (this.f1220a.mo1065s().m1335j()) {
                                if (subscriptionProduct.getAttributes().containsKey("period")) {
                                    subscriptionProduct.setSubscriptionPeriod(subscriptionProduct.getAttributes().get("period"));
                                }
                                if (subscriptionProduct.getAttributes().containsKey("freeTrialPeriod")) {
                                    subscriptionProduct.setFreeTrialPeriod(subscriptionProduct.getAttributes().get("freeTrialPeriod"));
                                }
                                if (subscriptionProduct.getAttributes().containsKey("introductoryOfferPeriod")) {
                                    subscriptionProduct.setIntroductoryPricePeriod(subscriptionProduct.getAttributes().get("introductoryOfferPeriod"));
                                }
                                if (subscriptionProduct.getAttributes().containsKey("introductoryOfferCycles")) {
                                    subscriptionProduct.setIntroductoryPriceCycles(subscriptionProduct.getAttributes().get("introductoryOfferCycles"));
                                }
                            }
                        }
                        return subscriptionProduct;
                    }
                }
            }
        }
        return null;
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(SubscriptionProduct subscriptionProduct) {
        if (subscriptionProduct == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("subscriptionId", subscriptionProduct.getSubscriptionId());
            jSONObject.put("productId", subscriptionProduct.getProductId());
            jSONObject.put("startsAt", subscriptionProduct.getStartsAt());
            jSONObject.put("endsAt", subscriptionProduct.getEndsAt());
            if (subscriptionProduct.getAttributes() != null) {
                jSONObject.put("attributes", new JSONObject(subscriptionProduct.getAttributes()));
            }
            jSONObject.put(C0856j.f955a, subscriptionProduct.getTitle());
            jSONObject.put("description", subscriptionProduct.getDescription());
            jSONObject.put("subscriptionPeriod", subscriptionProduct.getSubscriptionPeriod());
            jSONObject.put(FirebaseAnalytics.Param.PRICE, subscriptionProduct.getPrice());
            jSONObject.put("priceCurrencyCode", subscriptionProduct.getPriceCurrencyCode());
            jSONObject.put("priceAmountMicros", subscriptionProduct.getPriceAmountMicros());
            jSONObject.put("freeTrialPeriod", subscriptionProduct.getFreeTrialPeriod());
            jSONObject.put("introductoryPricePeriod", subscriptionProduct.getIntroductoryPricePeriod());
            jSONObject.put("introductoryPriceCycles", subscriptionProduct.getIntroductoryPriceCycles());
            jSONObject.put("introductoryPrice", subscriptionProduct.getIntroductoryPrice());
            jSONObject.put("introductoryPriceAmountMicros", subscriptionProduct.getIntroductoryPriceAmountMicros());
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }
}
