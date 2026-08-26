package com.nintendo.npf.sdk.vcm;

import android.app.Activity;
import android.support.annotation.RestrictTo;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyBundle {
    private int amount;
    private String customAttribute;
    private String detail;
    private String displayPrice;
    private int extraAmount;
    private BigDecimal price;
    private String priceCode;
    private String sku;
    private String title;
    private BigDecimal usdPrice;
    private String virtualCurrencyName;

    public interface RetrievingCallback {
        void onComplete(Map<String, List<VirtualCurrencyBundle>> map, NPFError nPFError);
    }

    public interface UnprocessedPurchaseCallback {
        void onComplete(List<VirtualCurrencyTransaction> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle$a */
    private static class C1072a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1826a = InterfaceC0875a.a.m1072b();
    }

    protected VirtualCurrencyBundle(String str, String str2, BigDecimal bigDecimal, String str3, String str4, String str5, BigDecimal bigDecimal2, String str6, int i, int i2, String str7) {
        this.sku = str;
        this.title = str2;
        this.price = bigDecimal;
        this.priceCode = str3;
        this.displayPrice = str4;
        this.detail = str5;
        this.usdPrice = bigDecimal2;
        this.virtualCurrencyName = str6;
        this.amount = i;
        this.extraAmount = i2;
        this.customAttribute = str7;
    }

    public static void checkUnprocessedPurchase(final UnprocessedPurchaseCallback unprocessedPurchaseCallback) {
        C1072a.f1826a.mo1059m().m1551a(new UnprocessedPurchaseCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.5
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.UnprocessedPurchaseCallback
            public void onComplete(List<VirtualCurrencyTransaction> list, NPFError nPFError) {
                UnprocessedPurchaseCallback unprocessedPurchaseCallback2 = unprocessedPurchaseCallback;
                if (unprocessedPurchaseCallback2 != null) {
                    unprocessedPurchaseCallback2.onComplete(list, nPFError);
                }
            }
        });
    }

    public static void getAll(final RetrievingCallback retrievingCallback) {
        C1072a.f1826a.mo1059m().m1550a(new RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.1
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.RetrievingCallback
            public void onComplete(Map<String, List<VirtualCurrencyBundle>> map, NPFError nPFError) {
                RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public static void recoverPurchased(final VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C1072a.f1826a.mo1059m().m1552a(new VirtualCurrencyWallet.RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.4
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
            public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
                VirtualCurrencyWallet.RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public int getAmount() {
        return this.amount;
    }

    public String getCustomAttribute() {
        return this.customAttribute;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getDisplayPrice() {
        return this.displayPrice;
    }

    public int getExtraAmount() {
        return this.extraAmount;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getPriceCode() {
        return this.priceCode;
    }

    public String getSKU() {
        return this.sku;
    }

    public String getTitle() {
        return this.title;
    }

    public BigDecimal getUsdPrice() {
        return this.usdPrice;
    }

    public String getVirtualCurrencyName() {
        return this.virtualCurrencyName;
    }

    public void purchase(Activity activity, final VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C1072a.f1826a.mo1059m().m1548a(activity, this, new VirtualCurrencyWallet.RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.2
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
            public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
                VirtualCurrencyWallet.RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    public void purchaseProductInfo(Activity activity, final VirtualCurrencyWallet.RetrievingCallback retrievingCallback, String str) {
        C1072a.f1826a.mo1059m().m1549a(activity, this, str, new VirtualCurrencyWallet.RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle.3
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
            public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
                VirtualCurrencyWallet.RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDetail(String str) {
        this.detail = str;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDisplayPrice(String str) {
        this.displayPrice = str;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setPrice(BigDecimal bigDecimal) {
        this.price = bigDecimal;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setPriceCode(String str) {
        this.priceCode = str;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setTitle(String str) {
        this.title = str;
    }
}
