package com.nintendo.npf.sdk.vcm;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyPurchaseSummaryBySku {
    private int count;
    private int purchasedAmount;
    private double purchasedUSD;
    private String sku;

    protected VirtualCurrencyPurchaseSummaryBySku(String str, int i, int i2, double d) {
        this.sku = str;
        this.count = i;
        this.purchasedAmount = i2;
        this.purchasedUSD = d;
    }

    public int getCount() {
        return this.count;
    }

    public int getPurchasedAmount() {
        return this.purchasedAmount;
    }

    public double getPurchasedUSD() {
        return this.purchasedUSD;
    }

    public String getSKU() {
        return this.sku;
    }
}
