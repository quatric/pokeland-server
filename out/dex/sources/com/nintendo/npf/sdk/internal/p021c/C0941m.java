package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.subscription.SubscriptionPurchase;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.m */
/* JADX INFO: compiled from: SubscriptionPurchaseMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0941m extends AbstractC0931c<SubscriptionPurchase> {
    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public SubscriptionPurchase mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        SubscriptionPurchase subscriptionPurchase = new SubscriptionPurchase();
        if (AbstractC0931c.m1262a(jSONObject, "subscriptionId")) {
            subscriptionPurchase.setSubscriptionId(jSONObject.getString("subscriptionId"));
            if (AbstractC0931c.m1262a(jSONObject, "productId")) {
                subscriptionPurchase.setProductId(jSONObject.getString("productId"));
                if (AbstractC0931c.m1262a(jSONObject, "startsAt")) {
                    subscriptionPurchase.setStartsAt(jSONObject.getLong("startsAt"));
                    if (AbstractC0931c.m1262a(jSONObject, "endsAt")) {
                        subscriptionPurchase.setEndsAt(jSONObject.getLong("endsAt"));
                        if (AbstractC0931c.m1262a(jSONObject, "inFreeTrialPeriod")) {
                            subscriptionPurchase.setInFreeTrialPeriod(Boolean.valueOf(jSONObject.getBoolean("inFreeTrialPeriod")));
                        }
                        if (AbstractC0931c.m1262a(jSONObject, "revokedAt")) {
                            subscriptionPurchase.setRevokedAt(jSONObject.getLong("revokedAt"));
                        }
                        return subscriptionPurchase;
                    }
                }
            }
        }
        return null;
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(SubscriptionPurchase subscriptionPurchase) {
        if (subscriptionPurchase == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("subscriptionId", subscriptionPurchase.getSubscriptionId());
            jSONObject.put("productId", subscriptionPurchase.getProductId());
            jSONObject.put("startsAt", subscriptionPurchase.getStartsAt());
            jSONObject.put("endsAt", subscriptionPurchase.getEndsAt());
            jSONObject.put("inFreeTrialPeriod", subscriptionPurchase.getInFreeTrialPeriod());
            jSONObject.put("revokedAt", subscriptionPurchase.getRevokedAt());
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX INFO: renamed from: d */
    public List m1276d(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            throw new JSONException("Missing mandatory field(s)");
        }
        if (!m1262a(jSONObject, "result")) {
            throw new JSONException("Missing mandatory field(s)");
        }
        int i = jSONObject.getInt("result");
        if (m1262a(jSONObject, "allowedSince")) {
            return Arrays.asList(Integer.valueOf(i), Long.valueOf(jSONObject.getLong("allowedSince")));
        }
        throw new JSONException("Missing mandatory field(s)");
    }
}
