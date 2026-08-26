package com.nintendo.npf.sdk.subscription;

import android.app.Activity;
import android.content.Context;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class SubscriptionPurchase {
    private long endsAt;
    private boolean inFreeTrialPeriod;
    private String productId;
    private long revokedAt;
    private long startsAt;
    private String subscriptionId;

    public interface OwnershipsCallback {
        void onComplete(int i, long j, NPFError nPFError);
    }

    public interface PurchaseCallback {
        void onComplete(NPFError nPFError);
    }

    public interface PurchasesCallback {
        void onComplete(List<SubscriptionPurchase> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.subscription.SubscriptionPurchase$a */
    private static class C1056a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1788a = InterfaceC0875a.a.m1072b();
    }

    public static void getPurchases(final PurchasesCallback purchasesCallback) {
        PurchasesCallback purchasesCallback2 = new PurchasesCallback() { // from class: com.nintendo.npf.sdk.subscription.SubscriptionPurchase.1
            @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchasesCallback
            public void onComplete(List<SubscriptionPurchase> list, NPFError nPFError) {
                PurchasesCallback purchasesCallback3 = purchasesCallback;
                if (purchasesCallback3 != null) {
                    purchasesCallback3.onComplete(list, nPFError);
                }
            }
        };
        if (C1056a.f1788a.mo1065s().m1335j()) {
            C1056a.f1788a.mo1054h().m1544a(purchasesCallback2);
        } else {
            C1056a.f1788a.mo1052f().m1539a(purchasesCallback2);
        }
    }

    public static void openDeepLink(Activity activity, String str) {
        if (C1056a.f1788a.mo1065s().m1335j()) {
            C1056a.f1788a.mo1054h().m1545a(str);
        } else {
            C1056a.f1788a.mo1052f().m1535a(activity, str);
        }
    }

    public static void openLink(Activity activity) {
        if (C1056a.f1788a.mo1065s().m1335j()) {
            C1056a.f1788a.mo1054h().m1542a();
        } else {
            C1056a.f1788a.mo1052f().m1534a(activity);
        }
    }

    public static void purchase(Activity activity, String str, final PurchaseCallback purchaseCallback) {
        PurchaseCallback purchaseCallback2 = new PurchaseCallback() { // from class: com.nintendo.npf.sdk.subscription.SubscriptionPurchase.4
            @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchaseCallback
            public void onComplete(NPFError nPFError) {
                PurchaseCallback purchaseCallback3 = purchaseCallback;
                if (purchaseCallback3 != null) {
                    purchaseCallback3.onComplete(nPFError);
                }
            }
        };
        if (C1056a.f1788a.mo1065s().m1335j()) {
            C1056a.f1788a.mo1054h().m1546a(str, purchaseCallback2);
        } else {
            C1056a.f1788a.mo1052f().m1536a(activity, str, purchaseCallback2);
        }
    }

    public static void updateOwnerships(Context context, final OwnershipsCallback ownershipsCallback) {
        OwnershipsCallback ownershipsCallback2 = new OwnershipsCallback() { // from class: com.nintendo.npf.sdk.subscription.SubscriptionPurchase.3
            @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.OwnershipsCallback
            public void onComplete(int i, long j, NPFError nPFError) {
                OwnershipsCallback ownershipsCallback3 = ownershipsCallback;
                if (ownershipsCallback3 != null) {
                    ownershipsCallback3.onComplete(i, j, nPFError);
                }
            }
        };
        if (C1056a.f1788a.mo1065s().m1335j()) {
            C1056a.f1788a.mo1054h().m1543a(ownershipsCallback2);
        } else {
            C1056a.f1788a.mo1052f().m1537a(context, ownershipsCallback2);
        }
    }

    public static void updatePurchases(Context context, final PurchasesCallback purchasesCallback) {
        PurchasesCallback purchasesCallback2 = new PurchasesCallback() { // from class: com.nintendo.npf.sdk.subscription.SubscriptionPurchase.2
            @Override // com.nintendo.npf.sdk.subscription.SubscriptionPurchase.PurchasesCallback
            public void onComplete(List<SubscriptionPurchase> list, NPFError nPFError) {
                PurchasesCallback purchasesCallback3 = purchasesCallback;
                if (purchasesCallback3 != null) {
                    purchasesCallback3.onComplete(list, nPFError);
                }
            }
        };
        if (C1056a.f1788a.mo1065s().m1335j()) {
            C1056a.f1788a.mo1054h().m1547b(purchasesCallback2);
        } else {
            C1056a.f1788a.mo1052f().m1538a(context, purchasesCallback2);
        }
    }

    public long getEndsAt() {
        return this.endsAt;
    }

    public Boolean getInFreeTrialPeriod() {
        return Boolean.valueOf(this.inFreeTrialPeriod);
    }

    public String getProductId() {
        return this.productId;
    }

    public long getRevokedAt() {
        return this.revokedAt;
    }

    public long getStartsAt() {
        return this.startsAt;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    public void setEndsAt(long j) {
        this.endsAt = j;
    }

    public void setInFreeTrialPeriod(Boolean bool) {
        this.inFreeTrialPeriod = bool.booleanValue();
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public void setRevokedAt(long j) {
        this.revokedAt = j;
    }

    public void setStartsAt(long j) {
        this.startsAt = j;
    }

    public void setSubscriptionId(String str) {
        this.subscriptionId = str;
    }

    public JSONObject toJSON() {
        return C1056a.f1788a.mo1052f().m1533a(this);
    }
}
