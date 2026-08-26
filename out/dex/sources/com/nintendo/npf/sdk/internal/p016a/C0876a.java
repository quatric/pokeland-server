package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.Product;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.UserDataResponse;
import com.android.billingclient.util.BillingHelper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.a */
/* JADX INFO: compiled from: BillingMarketServiceAmazonImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0876a implements PurchasingListener, InterfaceC0883h {

    /* JADX INFO: renamed from: a */
    private static final String f1023a = "a";

    /* JADX INFO: renamed from: c */
    private List<String> f1025c;

    /* JADX INFO: renamed from: o */
    private String f1037o;

    /* JADX INFO: renamed from: p */
    private final InterfaceC0875a f1038p = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: b */
    private InterfaceC0883h.b f1024b = null;

    /* JADX INFO: renamed from: d */
    private InterfaceC0883h.c f1026d = null;

    /* JADX INFO: renamed from: e */
    private Activity f1027e = null;

    /* JADX INFO: renamed from: f */
    private BigDecimal f1028f = null;

    /* JADX INFO: renamed from: g */
    private String f1029g = null;

    /* JADX INFO: renamed from: i */
    private String f1031i = null;

    /* JADX INFO: renamed from: j */
    private String f1032j = null;

    /* JADX INFO: renamed from: h */
    private int f1030h = -1;

    /* JADX INFO: renamed from: k */
    private InterfaceC0883h.a f1033k = null;

    /* JADX INFO: renamed from: l */
    private ArrayList<String> f1034l = null;

    /* JADX INFO: renamed from: m */
    private ArrayList<String> f1035m = null;

    /* JADX INFO: renamed from: n */
    private ArrayList<String> f1036n = null;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.a$1, reason: invalid class name */
    /* JADX INFO: compiled from: BillingMarketServiceAmazonImpl.java */
    static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f1039a;

        /* JADX INFO: renamed from: b */
        static final /* synthetic */ int[] f1040b;

        /* JADX INFO: renamed from: c */
        static final /* synthetic */ int[] f1041c;

        /* JADX INFO: renamed from: d */
        static final /* synthetic */ int[] f1042d = new int[PurchaseUpdatesResponse.RequestStatus.values().length];

        static {
            try {
                f1042d[PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1042d[PurchaseUpdatesResponse.RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1042d[PurchaseUpdatesResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            f1041c = new int[PurchaseResponse.RequestStatus.values().length];
            try {
                f1041c[PurchaseResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1041c[PurchaseResponse.RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1041c[PurchaseResponse.RequestStatus.INVALID_SKU.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1041c[PurchaseResponse.RequestStatus.ALREADY_PURCHASED.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f1041c[PurchaseResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            f1040b = new int[ProductDataResponse.RequestStatus.values().length];
            try {
                f1040b[ProductDataResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1040b[ProductDataResponse.RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f1040b[ProductDataResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            f1039a = new int[UserDataResponse.RequestStatus.values().length];
            try {
                f1039a[UserDataResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f1039a[UserDataResponse.RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f1039a[UserDataResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public C0876a() {
        PurchasingService.registerListener(this.f1038p.mo1047a(), this);
    }

    /* JADX INFO: renamed from: a */
    private int m1075a(ProductDataResponse.RequestStatus requestStatus) {
        int i = AnonymousClass1.f1040b[requestStatus.ordinal()];
        if (i != 1) {
            return (i == 2 || i != 3) ? 1 : 2;
        }
        return 0;
    }

    /* JADX INFO: renamed from: a */
    private int m1076a(PurchaseResponse.RequestStatus requestStatus) {
        int i = AnonymousClass1.f1041c[requestStatus.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            if (i == 3) {
                return 3;
            }
            if (i == 4) {
                return 4;
            }
            if (i == 5) {
                return 2;
            }
        }
        return 1;
    }

    /* JADX INFO: renamed from: a */
    private int m1077a(PurchaseUpdatesResponse.RequestStatus requestStatus) {
        int i = AnonymousClass1.f1042d[requestStatus.ordinal()];
        if (i != 1) {
            return (i == 2 || i != 3) ? 1 : 2;
        }
        return 0;
    }

    /* JADX INFO: renamed from: a */
    private int m1078a(UserDataResponse.RequestStatus requestStatus) {
        int i = AnonymousClass1.f1039a[requestStatus.ordinal()];
        if (i != 1) {
            return (i == 2 || i != 3) ? 1 : 2;
        }
        return 0;
    }

    /* JADX INFO: renamed from: a */
    private String m1079a(String str, String str2, Receipt receipt) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("productId", str);
            jSONObject.put("amazonUserId", str2);
            jSONObject.put("orderId", receipt.getReceiptId());
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1080a() {
        this.f1033k = null;
        this.f1035m = null;
        this.f1036n = null;
        this.f1034l = null;
    }

    /* JADX INFO: renamed from: a */
    private void m1081a(int i) {
        this.f1027e = null;
        this.f1028f = null;
        this.f1029g = null;
        this.f1031i = null;
        this.f1032j = null;
        this.f1030h = -1;
        this.f1026d = null;
    }

    /* JADX INFO: renamed from: a */
    private boolean m1082a(String str, BigDecimal bigDecimal, String str2, String str3, String str4) {
        SharedPreferences.Editor editorEdit = this.f1038p.mo1047a().getSharedPreferences("amazonTransactionData", 0).edit();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sku", str);
            jSONObject.put(FirebaseAnalytics.Param.PRICE, bigDecimal);
            jSONObject.put("priceCode", str2);
            jSONObject.put("customAttribute", str3 != null ? str3 : JSONObject.NULL);
            jSONObject.put("purchaseProductInfo", str4 != null ? str4 : JSONObject.NULL);
            editorEdit.putString(str, jSONObject.toString());
            editorEdit.apply();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: renamed from: b */
    private JSONObject m1083b(String str) {
        SharedPreferences sharedPreferences = this.f1038p.mo1047a().getSharedPreferences("amazonTransactionData", 0);
        if (!sharedPreferences.contains(str)) {
            return null;
        }
        try {
            return new JSONObject(sharedPreferences.getString(str, null));
        } catch (JSONException unused) {
            return null;
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public int mo1084a(String str) {
        PurchasingService.notifyFulfillment(str, FulfillmentResult.FULFILLED);
        return 0;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public void mo1085a(Activity activity, String str, BigDecimal bigDecimal, String str2, int i, String str3, String str4, InterfaceC0883h.c cVar) {
        this.f1027e = activity;
        this.f1028f = bigDecimal;
        this.f1029g = str2;
        this.f1031i = str3;
        this.f1032j = str4;
        this.f1030h = i;
        this.f1026d = cVar;
        PurchasingService.purchase(str);
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public void mo1086a(InterfaceC0883h.a aVar) {
        this.f1033k = aVar;
        this.f1034l = new ArrayList<>();
        this.f1035m = new ArrayList<>();
        this.f1036n = new ArrayList<>();
        PurchasingService.getPurchaseUpdates(false);
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public void mo1087a(@NonNull List<String> list, InterfaceC0883h.b bVar) {
        if (list == null || list.size() == 0) {
            if (bVar != null) {
                bVar.mo1139a(null, new C1025o(NPFError.ErrorType.NPF_ERROR, 500, "argument error"));
            }
        } else {
            this.f1024b = bVar;
            this.f1025c = list;
            PurchasingService.getUserData();
        }
    }

    @Override // com.amazon.device.iap.PurchasingListener
    public void onProductDataResponse(ProductDataResponse productDataResponse) {
        if (!this.f1038p.mo1063q().mo1131b(m1075a(productDataResponse.getRequestStatus()))) {
            if (this.f1024b != null) {
                this.f1024b.mo1139a(null, this.f1038p.mo1063q().mo1129a(this.f1038p.mo1063q().mo1135f()));
                this.f1024b = null;
                return;
            }
            return;
        }
        for (String str : productDataResponse.getUnavailableSkus()) {
            C0955e.m1393b(f1023a, "Unavailable SKU: " + str);
        }
        HashMap<String, JSONObject> map = new HashMap<>();
        Map<String, Product> productData = productDataResponse.getProductData();
        Iterator<String> it = productData.keySet().iterator();
        while (it.hasNext()) {
            Product product = productData.get(it.next());
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("productId", product.getSku());
                jSONObject.put(C0856j.f955a, product.getTitle());
                jSONObject.put("description", product.getDescription());
                jSONObject.put("display_price", product.getPrice());
                jSONObject.put("price_currency_code", "XXX");
                jSONObject.put("price_amount_micros", String.valueOf(0));
                map.put(product.getSku(), jSONObject);
            } catch (JSONException e) {
                if (this.f1024b != null) {
                    this.f1024b.mo1139a(null, C1025o.m1658a(e));
                    this.f1024b = null;
                    return;
                }
                return;
            }
        }
        InterfaceC0883h.b bVar = this.f1024b;
        if (bVar != null) {
            bVar.mo1139a(map, null);
            this.f1024b = null;
        }
    }

    @Override // com.amazon.device.iap.PurchasingListener
    public void onPurchaseResponse(PurchaseResponse purchaseResponse) {
        C0955e.m1391a(f1023a, "onPurchaseResponse in");
        int iM1076a = m1076a(purchaseResponse.getRequestStatus());
        if (!this.f1038p.mo1063q().mo1131b(iM1076a)) {
            InterfaceC0883h.c cVar = this.f1026d;
            if (cVar != null) {
                cVar.mo1140a(iM1076a, null);
            }
            m1081a(iM1076a);
            return;
        }
        if (!m1082a(purchaseResponse.getReceipt().getSku(), this.f1028f, this.f1029g, this.f1031i, this.f1032j)) {
            InterfaceC0883h.c cVar2 = this.f1026d;
            if (cVar2 != null) {
                cVar2.mo1140a(this.f1038p.mo1063q().mo1135f(), null);
            }
            m1081a(iM1076a);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BillingHelper.RESPONSE_CODE, this.f1038p.mo1063q().mo1134e());
        Receipt receipt = purchaseResponse.getReceipt();
        intent.putExtra("INAPP_PURCHASE_DATA", m1079a(purchaseResponse.getReceipt().getSku(), purchaseResponse.getUserData().getUserId(), purchaseResponse.getReceipt()));
        intent.putExtra("INAPP_DATA_SIGNATURE", AbstractC0880e.m1123a(receipt.getSku(), this.f1029g, this.f1028f));
        InterfaceC0883h.c cVar3 = this.f1026d;
        if (cVar3 != null) {
            cVar3.mo1140a(iM1076a, intent);
        }
        m1081a(iM1076a);
    }

    @Override // com.amazon.device.iap.PurchasingListener
    public void onPurchaseUpdatesResponse(PurchaseUpdatesResponse purchaseUpdatesResponse) {
        BigDecimal bigDecimal;
        int iM1077a = m1077a(purchaseUpdatesResponse.getRequestStatus());
        if (!this.f1038p.mo1063q().mo1131b(iM1077a)) {
            if (this.f1033k != null) {
                this.f1033k.mo1138a(null, this.f1038p.mo1063q().mo1129a(iM1077a));
            }
            m1080a();
            return;
        }
        for (Receipt receipt : purchaseUpdatesResponse.getReceipts()) {
            String sku = receipt.getSku();
            JSONObject jSONObjectM1083b = m1083b(sku);
            String str = "XXX";
            if (jSONObjectM1083b != null) {
                try {
                    String string = jSONObjectM1083b.getString("priceCode");
                    bigDecimal = new BigDecimal(jSONObjectM1083b.getString(FirebaseAnalytics.Param.PRICE));
                    if (!jSONObjectM1083b.isNull("customAttribute")) {
                        jSONObjectM1083b.getString("customAttribute");
                    }
                    if (!jSONObjectM1083b.isNull("purchaseProductInfo")) {
                        jSONObjectM1083b.getString("purchaseProductInfo");
                    }
                    str = string;
                } catch (JSONException unused) {
                    bigDecimal = new BigDecimal(0);
                }
            } else {
                bigDecimal = new BigDecimal(0);
            }
            this.f1034l.add(sku);
            this.f1035m.add(m1079a(receipt.getSku(), purchaseUpdatesResponse.getUserData().getUserId(), receipt));
            this.f1036n.add(AbstractC0880e.m1123a(receipt.getSku(), str, bigDecimal));
        }
        if (purchaseUpdatesResponse.hasMore()) {
            PurchasingService.getPurchaseUpdates(false);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(BillingHelper.RESPONSE_CODE, this.f1038p.mo1063q().mo1134e());
        bundle.putStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST, this.f1034l);
        bundle.putStringArrayList(BillingHelper.RESPONSE_INAPP_PURCHASE_DATA_LIST, this.f1035m);
        bundle.putStringArrayList(BillingHelper.RESPONSE_INAPP_SIGNATURE_LIST, this.f1036n);
        InterfaceC0883h.a aVar = this.f1033k;
        if (aVar != null) {
            aVar.mo1138a(bundle, null);
        }
        m1080a();
    }

    @Override // com.amazon.device.iap.PurchasingListener
    public void onUserDataResponse(UserDataResponse userDataResponse) {
        userDataResponse.getRequestStatus();
        if (!this.f1038p.mo1063q().mo1131b(m1078a(userDataResponse.getRequestStatus()))) {
            if (this.f1024b != null) {
                this.f1024b.mo1139a(null, this.f1038p.mo1063q().mo1129a(this.f1038p.mo1063q().mo1135f()));
                this.f1024b = null;
                return;
            }
            return;
        }
        this.f1037o = userDataResponse.getUserData().getMarketplace();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < this.f1025c.size(); i++) {
            C0955e.m1391a(f1023a, "SKU: " + this.f1025c.get(i));
            hashSet.add(this.f1025c.get(i));
        }
        PurchasingService.getProductData(hashSet);
    }
}
