package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.PointerIconCompat;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.util.List;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d */
/* JADX INFO: compiled from: GoogleBillingManager.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0879d implements PurchasesUpdatedListener {

    /* JADX INFO: renamed from: a */
    private BillingClient f1046a;

    /* JADX INFO: renamed from: b */
    private boolean f1047b;

    /* JADX INFO: renamed from: c */
    private int f1048c = -1;

    /* JADX INFO: renamed from: d */
    private a f1049d;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d$a */
    /* JADX INFO: compiled from: GoogleBillingManager.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1116a(List<Purchase> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d$b */
    /* JADX INFO: compiled from: GoogleBillingManager.java */
    public interface b {
        /* JADX INFO: renamed from: a */
        void mo1117a();
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d$c */
    /* JADX INFO: compiled from: GoogleBillingManager.java */
    public interface c {
        /* JADX INFO: renamed from: a */
        void mo1118a(NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d$d */
    /* JADX INFO: compiled from: GoogleBillingManager.java */
    private enum d {
        NETWORK_ERROR(0),
        BILLING_NOT_SUPPORTED(1000),
        PURCHASE_FAILED(PointerIconCompat.TYPE_WAIT),
        DEVELOPER_ERROR(PointerIconCompat.TYPE_CROSSHAIR),
        OWNER_ERROR(PointerIconCompat.TYPE_TEXT),
        PRODUCT_NOT_AVAILABLE(PointerIconCompat.TYPE_VERTICAL_TEXT),
        INTERNAL_ERROR(PointerIconCompat.TYPE_ALIAS),
        SERVICE_DISCONNECTED(InputDeviceCompat.SOURCE_GAMEPAD);


        /* JADX INFO: renamed from: a */
        private int f1079a;

        d(int i) {
            this.f1079a = i;
        }

        /* JADX INFO: renamed from: a */
        public int m1119a() {
            return this.f1079a;
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d$e */
    /* JADX INFO: compiled from: GoogleBillingManager.java */
    public interface e {
        /* JADX INFO: renamed from: a */
        void mo1120a(List<Purchase> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.d$f */
    /* JADX INFO: compiled from: GoogleBillingManager.java */
    public interface f {
        /* JADX INFO: renamed from: a */
        void mo1121a(List<SkuDetails> list, NPFError nPFError);
    }

    /* JADX INFO: renamed from: a */
    private void m1098a(final Runnable runnable) {
        this.f1046a.startConnection(new BillingClientStateListener() { // from class: com.nintendo.npf.sdk.internal.a.d.3
            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingServiceDisconnected() {
                C0955e.m1391a("GoogleBillingManager", "Service is disconnected");
                C0879d.this.f1047b = false;
                C0879d.this.f1048c = -1;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }

            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingSetupFinished(int i) {
                C0955e.m1391a("GoogleBillingManager", "Setup finished. Response code: " + i);
                C0879d.this.f1047b = i == 0;
                C0879d.this.f1048c = i;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static NPFError m1101b(int i) {
        switch (i) {
            case -2:
                C0955e.m1396d("GoogleBillingManager", "Subscription is not supported");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.BILLING_NOT_SUPPORTED.m1119a(), "Subscription is not supported");
            case -1:
                C0955e.m1396d("GoogleBillingManager", "Service is disconnected or not logged in to Google account");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.SERVICE_DISCONNECTED.m1119a(), "Service is disconnected or not logged in to Google account");
            case 0:
                return null;
            case 1:
                C0955e.m1396d("GoogleBillingManager", "User canceled billing process");
                return new C1025o(NPFError.ErrorType.USER_CANCEL, d.PURCHASE_FAILED.m1119a(), "User canceled billing process");
            case 2:
                C0955e.m1396d("GoogleBillingManager", "Billing response can't be handled for network error");
                return new C1025o(NPFError.ErrorType.NETWORK_ERROR, d.NETWORK_ERROR.m1119a(), "Billing response can't be handled for network error");
            case 3:
                C0955e.m1396d("GoogleBillingManager", "Billing API version 3 is not supported");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.BILLING_NOT_SUPPORTED.m1119a(), "Billing API version 3 is not supported");
            case 4:
                C0955e.m1396d("GoogleBillingManager", "Requested product is not available for purchase");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.PRODUCT_NOT_AVAILABLE.m1119a(), "Requested product is not available for purchase");
            case 5:
                C0955e.m1396d("GoogleBillingManager", "Invalid arguments provided to the API");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.DEVELOPER_ERROR.m1119a(), "Invalid arguments provided to the API");
            case 6:
                C0955e.m1396d("GoogleBillingManager", "Fatal error during the API action");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.INTERNAL_ERROR.m1119a(), "Fatal error during the API action");
            case 7:
                C0955e.m1396d("GoogleBillingManager", "Failure to purchase since item is already owned");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.OWNER_ERROR.m1119a(), "Failure to purchase since item is already owned");
            case 8:
                C0955e.m1396d("GoogleBillingManager", "Failure to consume since item is not owned");
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.OWNER_ERROR.m1119a(), "Failure to consume since item is not owned");
            default:
                String str = "Unknown error from IAB: " + new Integer(i).toString();
                C0955e.m1396d("GoogleBillingManager", str);
                return new C1025o(NPFError.ErrorType.NPF_ERROR, d.INTERNAL_ERROR.m1119a(), str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1102b() {
        BillingClient billingClient = this.f1046a;
        if (billingClient == null || !billingClient.isReady()) {
            return;
        }
        C0955e.m1391a("GoogleBillingManager", "Ending service connection.");
        this.f1046a.endConnection();
        C0955e.m1391a("GoogleBillingManager", "Destroying the Billing client.");
        this.f1046a = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1103b(final Activity activity, final String str, a aVar) {
        this.f1049d = aVar;
        m1107b(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.2
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.f1046a.launchBillingFlow(activity, BillingFlowParams.newBuilder().setSku(str).setType(BillingClient.SkuType.SUBS).build());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1104b(Context context, final b bVar) {
        C0955e.m1391a("GoogleBillingManager", "Creating Billing client.");
        this.f1046a = BillingClient.newBuilder(context.getApplicationContext()).setListener(this).build();
        C0955e.m1391a("GoogleBillingManager", "Starting service connection.");
        m1098a(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.4
            @Override // java.lang.Runnable
            public void run() {
                b bVar2 = bVar;
                if (bVar2 != null) {
                    bVar2.mo1117a();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1105b(c cVar) {
        this.f1048c = this.f1046a.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS);
        cVar.mo1118a(m1101b(this.f1048c));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1106b(final e eVar) {
        this.f1046a.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, new PurchaseHistoryResponseListener() { // from class: com.nintendo.npf.sdk.internal.a.d.10
            @Override // com.android.billingclient.api.PurchaseHistoryResponseListener
            public void onPurchaseHistoryResponse(int i, List<Purchase> list) {
                eVar.mo1120a(list, C0879d.m1101b(i));
            }
        });
    }

    /* JADX INFO: renamed from: b */
    private void m1107b(Runnable runnable) {
        if (this.f1047b) {
            runnable.run();
        } else {
            m1098a(runnable);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1109a() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.5
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.m1102b();
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1110a(final Activity activity, final String str, final a aVar) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.11
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.m1103b(activity, str, aVar);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1111a(final Context context, final b bVar) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.1
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.m1104b(context, bVar);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1112a(final c cVar) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.6
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.m1105b(cVar);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1113a(final e eVar) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.9
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.m1106b(eVar);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1114a(final List<String> list, final f fVar) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.7
            @Override // java.lang.Runnable
            public void run() {
                C0879d.this.m1115b(list, fVar);
            }
        });
    }

    /* JADX INFO: renamed from: b */
    public void m1115b(final List<String> list, final f fVar) {
        m1107b(new Runnable() { // from class: com.nintendo.npf.sdk.internal.a.d.8
            @Override // java.lang.Runnable
            public void run() {
                if (C0879d.this.f1048c != 0) {
                    fVar.mo1121a(null, C0879d.m1101b(C0879d.this.f1048c));
                    return;
                }
                SkuDetailsParams.Builder builderNewBuilder = SkuDetailsParams.newBuilder();
                builderNewBuilder.setSkusList(list).setType(BillingClient.SkuType.SUBS);
                C0879d.this.f1046a.querySkuDetailsAsync(builderNewBuilder.build(), new SkuDetailsResponseListener() { // from class: com.nintendo.npf.sdk.internal.a.d.8.1
                    @Override // com.android.billingclient.api.SkuDetailsResponseListener
                    public void onSkuDetailsResponse(int i, List<SkuDetails> list2) {
                        fVar.mo1121a(list2, C0879d.m1101b(i));
                    }
                });
            }
        });
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(int i, List<Purchase> list) {
        a aVar = this.f1049d;
        if (aVar != null) {
            aVar.mo1116a(list, m1101b(i));
        }
    }
}
