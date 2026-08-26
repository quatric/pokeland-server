package com.nintendo.npf.sdk.promo;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class PromoCodeBundle {
    private String customAttribute;
    private String sku;

    protected PromoCodeBundle(String str, String str2) {
        this.sku = str;
        this.customAttribute = str2;
    }

    public String getCustomAttribute() {
        return this.customAttribute;
    }

    public String getSku() {
        return this.sku;
    }
}
