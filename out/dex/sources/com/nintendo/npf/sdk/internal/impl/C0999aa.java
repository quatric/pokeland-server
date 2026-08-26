package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.android.billingclient.api.Purchase;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.C0879d;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0941m;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.subscription.SubscriptionPurchase;
import com.nintendo.npf.sdk.user.BaaSUser;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa */
/* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0999aa {

    /* JADX INFO: renamed from: a */
    private static final String f1436a = "aa";

    /* JADX INFO: renamed from: b */
    private final C0941m f1437b = new C0941m();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1438c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa$2, reason: invalid class name */
    /* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
    class AnonymousClass2 implements C0879d.b {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ C0879d f1441a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ SubscriptionPurchase.PurchasesCallback f1442b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ BaaSUser f1443c;

        AnonymousClass2(C0879d c0879d, SubscriptionPurchase.PurchasesCallback purchasesCallback, BaaSUser baaSUser) {
            this.f1441a = c0879d;
            this.f1442b = purchasesCallback;
            this.f1443c = baaSUser;
        }

        @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.b
        /* JADX INFO: renamed from: a */
        public void mo1117a() {
            this.f1441a.m1113a(new C0879d.e() { // from class: com.nintendo.npf.sdk.internal.impl.aa.2.1
                @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.e
                /* JADX INFO: renamed from: a */
                public void mo1120a(List<Purchase> list, NPFError nPFError) {
                    AnonymousClass2.this.f1441a.m1109a();
                    if (nPFError == null) {
                        C0905c.m1185f().mo1201a(AnonymousClass2.this.f1443c, AbstractC0880e.m1122a(), C0999aa.this.m1529a(list), new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.aa.2.1.1
                            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                            /* JADX INFO: renamed from: a */
                            public void mo1145a(JSONArray jSONArray, NPFError nPFError2) {
                                if (nPFError2 != null) {
                                    AnonymousClass2.this.f1442b.onComplete(null, nPFError2);
                                    return;
                                }
                                try {
                                    AnonymousClass2.this.f1442b.onComplete(C0999aa.this.f1437b.m1263a(jSONArray), null);
                                } catch (JSONException e) {
                                    AnonymousClass2.this.f1442b.onComplete(null, C1025o.m1658a(e));
                                }
                            }
                        });
                    } else {
                        C0954d.m1387a("updatePurchases/queryPurchaseHistoryAsync", nPFError);
                        AnonymousClass2.this.f1442b.onComplete(null, nPFError);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa$3, reason: invalid class name */
    /* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
    class AnonymousClass3 implements C0879d.b {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ C0879d f1447a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ SubscriptionPurchase.OwnershipsCallback f1448b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ BaaSUser f1449c;

        AnonymousClass3(C0879d c0879d, SubscriptionPurchase.OwnershipsCallback ownershipsCallback, BaaSUser baaSUser) {
            this.f1447a = c0879d;
            this.f1448b = ownershipsCallback;
            this.f1449c = baaSUser;
        }

        @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.b
        /* JADX INFO: renamed from: a */
        public void mo1117a() {
            this.f1447a.m1113a(new C0879d.e() { // from class: com.nintendo.npf.sdk.internal.impl.aa.3.1
                @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.e
                /* JADX INFO: renamed from: a */
                public void mo1120a(List<Purchase> list, NPFError nPFError) {
                    AnonymousClass3.this.f1447a.m1109a();
                    if (nPFError == null) {
                        C0905c.m1185f().mo1204b(AnonymousClass3.this.f1449c, AbstractC0880e.m1122a(), C0999aa.this.m1529a(list), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.aa.3.1.1
                            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                            /* JADX INFO: renamed from: a */
                            public void mo1143a(JSONObject jSONObject, NPFError nPFError2) {
                                if (nPFError2 != null) {
                                    AnonymousClass3.this.f1448b.onComplete(-1, -1L, nPFError2);
                                    return;
                                }
                                try {
                                    List listM1276d = C0999aa.this.f1437b.m1276d(jSONObject);
                                    AnonymousClass3.this.f1448b.onComplete(((Integer) listM1276d.get(0)).intValue(), ((Long) listM1276d.get(1)).longValue(), null);
                                } catch (JSONException e) {
                                    AnonymousClass3.this.f1448b.onComplete(-1, -1L, C1025o.m1658a(e));
                                }
                            }
                        });
                    } else {
                        C0954d.m1387a("updateOwnerships/queryPurchaseHistoryAsync", nPFError);
                        AnonymousClass3.this.f1448b.onComplete(-1, -1L, nPFError);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa$4, reason: invalid class name */
    /* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
    class AnonymousClass4 implements C0879d.b {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ C0879d f1453a;

        /* JADX INFO: renamed from: b */
        final /* synthetic */ SubscriptionPurchase.PurchaseCallback f1454b;

        /* JADX INFO: renamed from: c */
        final /* synthetic */ BaaSUser f1455c;

        /* JADX INFO: renamed from: d */
        final /* synthetic */ String f1456d;

        /* JADX INFO: renamed from: e */
        final /* synthetic */ Activity f1457e;

        /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa$4$1, reason: invalid class name */
        /* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
        class AnonymousClass1 implements C0879d.c {

            /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa$4$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
            class C12851 implements C0879d.e {

                /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.aa$4$1$1$1, reason: invalid class name and collision with other inner class name */
                /* JADX INFO: compiled from: SubscriptionPurchaseImpl.java */
                class C12861 implements C0918a.b {
                    C12861() {
                    }

                    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                    /* JADX INFO: renamed from: a */
                    public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                        if (nPFError != null) {
                            AnonymousClass4.this.f1453a.m1109a();
                            AnonymousClass4.this.f1454b.onComplete(nPFError);
                        } else {
                            C0999aa.this.f1438c.mo1049c().m1518a(true);
                            AnonymousClass4.this.f1453a.m1110a(AnonymousClass4.this.f1457e, AnonymousClass4.this.f1456d, new C0879d.a() { // from class: com.nintendo.npf.sdk.internal.impl.aa.4.1.1.1.1
                                @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.a
                                /* JADX INFO: renamed from: a */
                                public void mo1116a(List<Purchase> list, NPFError nPFError2) {
                                    AnonymousClass4.this.f1453a.m1109a();
                                    C0999aa.this.f1438c.mo1049c().m1518a(false);
                                    if (nPFError2 == null) {
                                        C0905c.m1185f().mo1202a(AnonymousClass4.this.f1455c, AbstractC0880e.m1122a(), C0999aa.this.m1529a(list), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.aa.4.1.1.1.1.1
                                            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                                            /* JADX INFO: renamed from: a */
                                            public void mo1143a(JSONObject jSONObject2, NPFError nPFError3) {
                                                if (nPFError3 != null) {
                                                    AnonymousClass4.this.f1454b.onComplete(nPFError3);
                                                } else {
                                                    AnonymousClass4.this.f1454b.onComplete(null);
                                                }
                                            }
                                        });
                                    } else {
                                        C0954d.m1387a("purchase/initiatePurchaseFlow", nPFError2);
                                        AnonymousClass4.this.f1454b.onComplete(nPFError2);
                                    }
                                }
                            });
                        }
                    }
                }

                C12851() {
                }

                @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.e
                /* JADX INFO: renamed from: a */
                public void mo1120a(List<Purchase> list, NPFError nPFError) {
                    if (nPFError == null) {
                        C0905c.m1185f().mo1200a(AnonymousClass4.this.f1455c, AbstractC0880e.m1122a(), AnonymousClass4.this.f1456d, C0999aa.this.m1529a(list), new C12861());
                        return;
                    }
                    C0954d.m1387a("purchase/queryPurchaseHistoryAsync", nPFError);
                    AnonymousClass4.this.f1453a.m1109a();
                    AnonymousClass4.this.f1454b.onComplete(nPFError);
                }
            }

            AnonymousClass1() {
            }

            @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.c
            /* JADX INFO: renamed from: a */
            public void mo1118a(NPFError nPFError) {
                if (nPFError == null) {
                    AnonymousClass4.this.f1453a.m1113a(new C12851());
                    return;
                }
                C0954d.m1387a("purchase/checkSubscriptionsSupported", nPFError);
                AnonymousClass4.this.f1453a.m1109a();
                AnonymousClass4.this.f1454b.onComplete(nPFError);
            }
        }

        AnonymousClass4(C0879d c0879d, SubscriptionPurchase.PurchaseCallback purchaseCallback, BaaSUser baaSUser, String str, Activity activity) {
            this.f1453a = c0879d;
            this.f1454b = purchaseCallback;
            this.f1455c = baaSUser;
            this.f1456d = str;
            this.f1457e = activity;
        }

        @Override // com.nintendo.npf.sdk.internal.p016a.C0879d.b
        /* JADX INFO: renamed from: a */
        public void mo1117a() {
            this.f1453a.m1112a(new AnonymousClass1());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public JSONObject m1529a(List<Purchase> list) {
        JSONArray jSONArray = new JSONArray();
        for (Purchase purchase : m1531b(list)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("data", purchase.getOriginalJson());
                jSONObject.put("signature", purchase.getSignature());
            } catch (JSONException e) {
                C0955e.m1394b(f1436a, "makeReceipt", e);
            }
            jSONArray.put(jSONObject);
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("orders", jSONArray);
        } catch (JSONException e2) {
            C0955e.m1394b(f1436a, "makeReceipt", e2);
        }
        return jSONObject2;
    }

    /* JADX INFO: renamed from: b */
    private static List<Purchase> m1531b(List<Purchase> list) {
        ArrayList arrayList = new ArrayList();
        for (Purchase purchase : list) {
            if (C1035y.m1774a(purchase.getSku())) {
                arrayList.add(purchase);
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: b */
    private void m1532b(Activity activity, String str) {
        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    /* JADX INFO: renamed from: a */
    public JSONObject m1533a(SubscriptionPurchase subscriptionPurchase) {
        return this.f1437b.mo1259a(subscriptionPurchase);
    }

    /* JADX INFO: renamed from: a */
    public void m1534a(Activity activity) {
        C0955e.m1393b(f1436a, "openLink is called");
        if (activity == null) {
            C0955e.m1395c(f1436a, "Activity is null");
        } else {
            m1532b(activity, "http://play.google.com/store/account/subscriptions");
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1535a(Activity activity, String str) {
        C0955e.m1393b(f1436a, "openDeepLink is called");
        if (activity == null) {
            C0955e.m1395c(f1436a, "Activity is null");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            C0955e.m1395c(f1436a, "Product is null or empty");
            return;
        }
        try {
            m1532b(activity, String.format("%s?package=%s&sku=%s", "http://play.google.com/store/account/subscriptions", URLEncoder.encode(this.f1438c.mo1065s().m1337l(), "UTF-8"), URLEncoder.encode(str, "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            C0955e.m1394b(f1436a, "openDeepLink", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1536a(Activity activity, String str, @NonNull SubscriptionPurchase.PurchaseCallback purchaseCallback) {
        C0955e.m1393b(f1436a, "purchase is called");
        if (activity == null) {
            C0955e.m1395c(f1436a, "Activity is null");
            purchaseCallback.onComplete(C1025o.m1661d());
        } else {
            if (TextUtils.isEmpty(str)) {
                C0955e.m1395c(f1436a, "Product id is null or empty");
                purchaseCallback.onComplete(C1025o.m1661d());
                return;
            }
            BaaSUser baaSUserM1665a = this.f1438c.mo1048b().m1665a();
            if (!this.f1438c.mo1050d().m1633b(baaSUserM1665a)) {
                purchaseCallback.onComplete(C1025o.m1656a());
            } else {
                C0879d c0879d = new C0879d();
                c0879d.m1111a(activity, new AnonymousClass4(c0879d, purchaseCallback, baaSUserM1665a, str, activity));
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1537a(Context context, @NonNull SubscriptionPurchase.OwnershipsCallback ownershipsCallback) {
        C0955e.m1393b(f1436a, "updateOwnerships is called");
        BaaSUser baaSUserM1665a = this.f1438c.mo1048b().m1665a();
        if (!this.f1438c.mo1050d().m1633b(baaSUserM1665a)) {
            ownershipsCallback.onComplete(-1, -1L, C1025o.m1656a());
        } else {
            C0879d c0879d = new C0879d();
            c0879d.m1111a(context, new AnonymousClass3(c0879d, ownershipsCallback, baaSUserM1665a));
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1538a(Context context, @NonNull SubscriptionPurchase.PurchasesCallback purchasesCallback) {
        C0955e.m1393b(f1436a, "updatePurchases is called");
        if (context == null) {
            C0955e.m1395c(f1436a, "Context is null");
            purchasesCallback.onComplete(null, C1025o.m1661d());
            return;
        }
        BaaSUser baaSUserM1665a = this.f1438c.mo1048b().m1665a();
        if (!this.f1438c.mo1050d().m1633b(baaSUserM1665a)) {
            purchasesCallback.onComplete(null, C1025o.m1656a());
        } else {
            C0879d c0879d = new C0879d();
            c0879d.m1111a(context, new AnonymousClass2(c0879d, purchasesCallback, baaSUserM1665a));
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1539a(@NonNull final SubscriptionPurchase.PurchasesCallback purchasesCallback) {
        C0955e.m1393b(f1436a, "getPurchases is called");
        BaaSUser baaSUserM1665a = this.f1438c.mo1048b().m1665a();
        if (this.f1438c.mo1050d().m1633b(baaSUserM1665a)) {
            C0905c.m1185f().mo1203b(baaSUserM1665a, AbstractC0880e.m1122a(), new C0918a.a() { // from class: com.nintendo.npf.sdk.internal.impl.aa.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
                /* JADX INFO: renamed from: a */
                public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
                    if (nPFError != null) {
                        purchasesCallback.onComplete(null, nPFError);
                        return;
                    }
                    try {
                        purchasesCallback.onComplete(C0999aa.this.f1437b.m1263a(jSONArray), null);
                    } catch (JSONException e) {
                        purchasesCallback.onComplete(null, C1025o.m1658a(e));
                    }
                }
            });
        } else {
            purchasesCallback.onComplete(null, C1025o.m1656a());
        }
    }
}
