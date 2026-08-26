package com.nintendo.npf.sdk.subscription;

import android.content.Context;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class SubscriptionProduct {
    private Map<String, String> attributes;
    private String description;
    private long endsAt;
    private String freeTrialPeriod;
    private String introductoryPrice;
    private long introductoryPriceAmountMicros;
    private String introductoryPriceCycles;
    private String introductoryPricePeriod;
    private String price;
    private long priceAmountMicros;
    private String priceCurrencyCode;
    private String productId;
    private long startsAt;
    private String subscriptionId;
    private String subscriptionPeriod;
    private String title;

    public interface GetProductsCallback {
        void onComplete(List<SubscriptionProduct> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.subscription.SubscriptionProduct$a */
    private static class C1051a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1783a = InterfaceC0875a.a.m1072b();
    }

    public static void getProducts(Context context, final GetProductsCallback getProductsCallback) {
        GetProductsCallback getProductsCallback2 = new GetProductsCallback() { // from class: com.nintendo.npf.sdk.subscription.SubscriptionProduct.1
            @Override // com.nintendo.npf.sdk.subscription.SubscriptionProduct.GetProductsCallback
            public void onComplete(List<SubscriptionProduct> list, NPFError nPFError) {
                GetProductsCallback getProductsCallback3 = getProductsCallback;
                if (getProductsCallback3 != null) {
                    getProductsCallback3.onComplete(list, nPFError);
                }
            }
        };
        if (C1051a.f1783a.mo1065s().m1335j()) {
            C1051a.f1783a.mo1055i().m1780a(getProductsCallback2);
        } else {
            C1051a.f1783a.mo1053g().m1778a(context, getProductsCallback2);
        }
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public String getDescription() {
        return this.description;
    }

    public long getEndsAt() {
        return this.endsAt;
    }

    public String getFreeTrialPeriod() {
        return this.freeTrialPeriod;
    }

    public String getIntroductoryPrice() {
        return this.introductoryPrice;
    }

    public long getIntroductoryPriceAmountMicros() {
        return this.introductoryPriceAmountMicros;
    }

    public String getIntroductoryPriceCycles() {
        return this.introductoryPriceCycles;
    }

    public String getIntroductoryPricePeriod() {
        return this.introductoryPricePeriod;
    }

    public String getPrice() {
        return this.price;
    }

    public long getPriceAmountMicros() {
        return this.priceAmountMicros;
    }

    public String getPriceCurrencyCode() {
        return this.priceCurrencyCode;
    }

    public String getProductId() {
        return this.productId;
    }

    public long getStartsAt() {
        return this.startsAt;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    public String getSubscriptionPeriod() {
        return this.subscriptionPeriod;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAttributes(Map<String, String> map) {
        this.attributes = Collections.unmodifiableMap(map);
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setEndsAt(long j) {
        this.endsAt = j;
    }

    public void setFreeTrialPeriod(String str) {
        this.freeTrialPeriod = str;
    }

    public void setIntroductoryPrice(String str) {
        this.introductoryPrice = str;
    }

    public void setIntroductoryPriceAmountMicros(long j) {
        this.introductoryPriceAmountMicros = j;
    }

    public void setIntroductoryPriceCycles(String str) {
        this.introductoryPriceCycles = str;
    }

    public void setIntroductoryPricePeriod(String str) {
        this.introductoryPricePeriod = str;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public void setPriceAmountMicros(long j) {
        this.priceAmountMicros = j;
    }

    public void setPriceCurrencyCode(String str) {
        this.priceCurrencyCode = str;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public void setStartsAt(long j) {
        this.startsAt = j;
    }

    public void setSubscriptionId(String str) {
        this.subscriptionId = str;
    }

    public void setSubscriptionPeriod(String str) {
        this.subscriptionPeriod = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public JSONObject toJSON() {
        return C1051a.f1783a.mo1053g().m1777a(this);
    }
}
