package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import com.android.billingclient.util.BillingHelper;
import com.google.api.client.http.HttpStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.app.PurchaseActivity;
import com.nintendo.npf.sdk.internal.impl.C0998a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.k */
/* JADX INFO: compiled from: VirtualCurrencyBundlePaymentManager.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C0886k implements AbstractC0880e.a, InterfaceC0883h.a, C0918a.b, C0998a.a {

    /* JADX INFO: renamed from: a */
    private static final String f1103a = "k";

    /* JADX INFO: renamed from: b */
    private static C0886k f1104b;

    /* JADX INFO: renamed from: c */
    private Activity f1105c;

    /* JADX INFO: renamed from: d */
    private VirtualCurrencyBundle f1106d;

    /* JADX INFO: renamed from: e */
    private VirtualCurrencyWallet.RetrievingCallback f1107e;

    /* JADX INFO: renamed from: j */
    private Map<String, VirtualCurrencyWallet> f1112j;

    /* JADX INFO: renamed from: k */
    private List<String> f1113k;

    /* JADX INFO: renamed from: l */
    private List<String> f1114l;

    /* JADX INFO: renamed from: f */
    private boolean f1108f = false;

    /* JADX INFO: renamed from: g */
    private boolean f1109g = false;

    /* JADX INFO: renamed from: h */
    private boolean f1110h = true;

    /* JADX INFO: renamed from: i */
    private String f1111i = null;

    /* JADX INFO: renamed from: m */
    private final InterfaceC0875a f1115m = InterfaceC0875a.a.m1072b();

    private C0886k() {
    }

    /* JADX INFO: renamed from: a */
    private static Pair<List<String>, Map<String, VirtualCurrencyWallet>> m1146a(JSONObject jSONObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = jSONObject.getJSONArray("wallets");
        JSONArray jSONArray2 = jSONObject.getJSONArray("transactions");
        C0955e.m1391a(f1103a, "transactions size : " + jSONArray2.length());
        for (int i = 0; i < jSONArray2.length(); i++) {
            JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
            if (jSONObject2.getString("type").equalsIgnoreCase("PURCHASE")) {
                String string = jSONObject2.getJSONObject("extras").getString("token");
                C0955e.m1391a(f1103a, "token : " + string);
                arrayList.add(string);
            }
        }
        C0955e.m1391a(f1103a, "purchase token size : " + arrayList.size());
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject3 = jSONArray.getJSONObject(i2);
            String string2 = jSONObject3.getString("virtualCurrencyName");
            JSONObject jSONObject4 = jSONObject3.getJSONObject("balance");
            int i3 = jSONObject4.getInt("total");
            int i4 = jSONObject4.getInt("free");
            JSONArray jSONArray3 = jSONObject4.getJSONArray("paid");
            HashMap map2 = new HashMap();
            for (int i5 = 0; i5 < jSONArray3.length(); i5++) {
                JSONObject jSONObject5 = jSONArray3.getJSONObject(i5);
                map2.put(jSONObject5.getString("code"), Integer.valueOf(jSONObject5.getInt("total")));
            }
            map.put(string2, VirtualCurrencyWallet.internalCreate(string2, i3, i4, map2));
        }
        return new Pair<>(arrayList, map);
    }

    /* JADX INFO: renamed from: a */
    public static C0886k m1147a() {
        if (f1104b == null) {
            f1104b = new C0886k();
        }
        return f1104b;
    }

    @Override // com.nintendo.npf.sdk.internal.impl.C0998a.a
    /* JADX INFO: renamed from: a */
    public void mo1148a(int i, int i2, Intent intent) {
        C0955e.m1393b(f1103a, "VirtualCurrencyBundlePaymentManager#onActivityResult");
        if (i != 8213) {
            return;
        }
        if (!this.f1115m.mo1063q().mo1131b(i2) && i2 != -1) {
            NPFError nPFErrorMo1129a = this.f1115m.mo1063q().mo1129a(i2);
            if (nPFErrorMo1129a != null) {
                C0954d.m1388a("purchase_error", "purchase#bindInAppBillingService#Error", nPFErrorMo1129a);
                m1152a((Map<String, VirtualCurrencyWallet>) null, nPFErrorMo1129a);
                return;
            }
            return;
        }
        if (this.f1115m.mo1065s().m1335j()) {
            JSONArray jSONArray = new JSONArray();
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("sku", this.f1106d.getSKU());
                jSONObject.put(FirebaseAnalytics.Param.PRICE, this.f1106d.getPrice());
                jSONObject.put("priceCode", this.f1106d.getPriceCode());
                jSONObject.put("digest", AbstractC0880e.m1123a(this.f1106d.getSKU(), this.f1106d.getPriceCode(), this.f1106d.getPrice()));
                jSONObject.put("customAttribute", this.f1106d.getCustomAttribute() != null ? this.f1106d.getCustomAttribute() : JSONObject.NULL);
                jSONObject.put("purchaseProductInfo", this.f1111i != null ? this.f1111i : JSONObject.NULL);
                C0955e.m1391a(f1103a, "order: " + jSONObject.toString());
                jSONArray.put(jSONObject);
                m1153a((JSONArray) null, jSONArray);
                return;
            } catch (JSONException e) {
                C0955e.m1394b(f1103a, "Failed making request JSON object", e);
                throw new IllegalArgumentException(e);
            }
        }
        int intExtra = intent.getIntExtra(BillingHelper.RESPONSE_CODE, -1);
        C0955e.m1393b(f1103a, "VirtualCurrencyBundlePaymentManager#onActivityResult#result " + intExtra);
        NPFError nPFErrorMo1129a2 = this.f1115m.mo1063q().mo1129a(intent.getIntExtra(BillingHelper.RESPONSE_CODE, 0));
        if (nPFErrorMo1129a2 != null) {
            C0954d.m1388a("purchase_error", "purchase#purchasing#Error", nPFErrorMo1129a2);
            m1152a((Map<String, VirtualCurrencyWallet>) null, nPFErrorMo1129a2);
            return;
        }
        C0955e.m1393b(f1103a, "VirtualCurrencyBundlePaymentManager#onActivityResult#PASS_RESPONSE_CODE");
        this.f1115m.mo1051e().m1719a(this.f1106d);
        JSONArray jSONArray2 = new JSONArray();
        JSONArray jSONArray3 = new JSONArray();
        try {
            JSONObject jSONObject2 = new JSONObject();
            String stringExtra = intent.getStringExtra("INAPP_PURCHASE_DATA");
            String stringExtra2 = intent.getStringExtra("INAPP_DATA_SIGNATURE");
            if (stringExtra != null && !stringExtra.isEmpty()) {
                if (stringExtra2 != null && !stringExtra2.isEmpty()) {
                    jSONObject2.put("data", stringExtra);
                    jSONObject2.put("signature", stringExtra2);
                    jSONArray2.put(jSONObject2);
                    Object objM1089a = C0878c.m1089a(new JSONObject(stringExtra).getString("productId"), this.f1115m);
                    if (objM1089a != null) {
                        jSONArray3.put(objM1089a);
                    }
                    m1153a(jSONArray2, jSONArray3);
                    return;
                }
                NPFError c1025o = new C1025o(NPFError.ErrorType.NPF_ERROR, 406, "inapp purchase signature is missing");
                C0954d.m1388a("purchase_error", "purchase#purchasing#missing_inapp_purchase_signature", c1025o);
                m1152a((Map<String, VirtualCurrencyWallet>) null, c1025o);
                return;
            }
            NPFError c1025o2 = new C1025o(NPFError.ErrorType.NPF_ERROR, 406, "inapp purchase data is missing");
            C0954d.m1388a("purchase_error", "purchase#purchasing#missing_inapp_purchase_data", c1025o2);
            m1152a((Map<String, VirtualCurrencyWallet>) null, c1025o2);
        } catch (JSONException e2) {
            C0955e.m1394b(f1103a, "Failed making request JSON object", e2);
            throw new IllegalArgumentException(e2);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1149a(Activity activity, VirtualCurrencyBundle virtualCurrencyBundle, String str, VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C0955e.m1391a(f1103a, "Start actual purchase flow");
        if (this.f1108f) {
            C1025o c1025o = new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "purchase is processing.");
            if (retrievingCallback != null) {
                retrievingCallback.onComplete(null, c1025o);
            }
            this.f1115m.mo1049c().m1522c().onVirtualCurrencyPurchaseProcessError(c1025o);
            return;
        }
        this.f1108f = true;
        this.f1107e = retrievingCallback;
        this.f1105c = activity;
        this.f1106d = virtualCurrencyBundle;
        this.f1111i = str;
        this.f1109g = true;
        this.f1115m.mo1049c().m1518a(true);
        if (this.f1115m.mo1065s().m1335j()) {
            m1154b();
        } else {
            this.f1115m.mo1063q().mo1130a(this);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h.a
    /* JADX INFO: renamed from: a */
    public void mo1138a(Bundle bundle, NPFError nPFError) {
        if (nPFError != null) {
            if (this.f1109g) {
                m1154b();
                return;
            } else {
                C0954d.m1388a("purchase_error", "recoverPurchased#getPurchases#Error", nPFError);
                m1152a((Map<String, VirtualCurrencyWallet>) null, nPFError);
                return;
            }
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
        ArrayList arrayList = new ArrayList();
        if (stringArrayList != null) {
            for (String str : stringArrayList) {
                if (!this.f1114l.contains(str)) {
                    arrayList.add(str);
                }
            }
        }
        if (arrayList.size() == 0) {
            if (this.f1109g) {
                m1154b();
                return;
            }
            NPFError c1025o = new C1025o(NPFError.ErrorType.PROCESS_CANCEL, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "There are no purchased items");
            C0954d.m1388a("purchase_error", "recoverPurchased#getPurchases#NotFoundPurchaseItemList", c1025o);
            m1152a((Map<String, VirtualCurrencyWallet>) null, c1025o);
            return;
        }
        if (this.f1109g) {
            NPFError c1025o2 = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_CONFLICT, "The item is already bought");
            C0954d.m1388a("purchase_error", "purchase#purchased#AlreadyBought", c1025o2);
            m1152a((Map<String, VirtualCurrencyWallet>) null, c1025o2);
            return;
        }
        try {
            ArrayList<String> stringArrayList2 = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_PURCHASE_DATA_LIST);
            ArrayList<String> stringArrayList3 = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_SIGNATURE_LIST);
            JSONArray jSONArray = new JSONArray();
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < stringArrayList2.size(); i++) {
                String str2 = stringArrayList.get(i);
                if (!this.f1114l.contains(str2)) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("data", stringArrayList2.get(i));
                    jSONObject.put("signature", stringArrayList3.get(i));
                    jSONArray.put(jSONObject);
                    JSONObject jSONObjectM1089a = C0878c.m1089a(str2, this.f1115m);
                    if (jSONObjectM1089a != null) {
                        JSONObject jSONObject2 = new JSONObject(jSONObjectM1089a.toString());
                        jSONObject2.put("digest", AbstractC0880e.m1123a(jSONObject2.getString("sku"), jSONObject2.getString("priceCode"), new BigDecimal(jSONObject2.getString(FirebaseAnalytics.Param.PRICE))));
                        jSONArray2.put(jSONObject2);
                    }
                }
            }
            C0955e.m1391a(f1103a, "orders : " + jSONArray2.toString());
            m1153a(jSONArray, jSONArray2);
        } catch (JSONException e) {
            m1152a((Map<String, VirtualCurrencyWallet>) null, C1025o.m1658a(e));
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e.a
    /* JADX INFO: renamed from: a */
    public void mo1137a(InterfaceC0883h interfaceC0883h, int i) {
        if (!this.f1115m.mo1063q().mo1131b(i)) {
            this.f1115m.mo1063q().mo1133d();
            NPFError nPFErrorMo1129a = this.f1115m.mo1063q().mo1129a(i);
            Object[] objArr = new Object[1];
            objArr[0] = this.f1110h ? "recoverPurchased" : "purchase";
            C0954d.m1388a("purchase_error", String.format("%s#bindInAppBillingService#Error", objArr), nPFErrorMo1129a);
            m1152a((Map<String, VirtualCurrencyWallet>) null, nPFErrorMo1129a);
            return;
        }
        this.f1115m.mo1063q().mo1133d();
        C0955e.m1391a(f1103a, "recover flag : " + this.f1110h);
        C0955e.m1391a(f1103a, "purchase flag : " + this.f1109g);
        if (this.f1110h) {
            this.f1110h = false;
            interfaceC0883h.mo1086a(this);
            return;
        }
        C0955e.m1391a(f1103a, "purchase token size : " + this.f1113k.size());
        if (!this.f1115m.mo1065s().m1336k()) {
            for (int i2 = 0; i2 < this.f1113k.size(); i2++) {
                C0954d.m1388a("close_receipt_response", String.format("purchase#consumePurchase#result response_code: %d purchaseToken: %s", Integer.valueOf(interfaceC0883h.mo1084a(this.f1113k.get(i2))), this.f1113k.get(i2)), null);
            }
        }
        m1152a(this.f1112j, (NPFError) null);
    }

    /* JADX INFO: renamed from: a */
    public void m1150a(VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        if (this.f1108f) {
            C1025o c1025o = new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "recoverPurchase is processing.");
            if (retrievingCallback != null) {
                retrievingCallback.onComplete(null, c1025o);
                return;
            }
            return;
        }
        this.f1108f = true;
        this.f1107e = retrievingCallback;
        if (this.f1115m.mo1065s().m1335j()) {
            m1152a((Map<String, VirtualCurrencyWallet>) null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "There are no purchased items"));
        } else {
            this.f1115m.mo1063q().mo1130a(this);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1151a(List<String> list) {
        this.f1114l = list;
    }

    /* JADX INFO: renamed from: a */
    public void m1152a(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
        this.f1115m.mo1049c().m1518a(false);
        VirtualCurrencyWallet.RetrievingCallback retrievingCallback = this.f1107e;
        if (retrievingCallback != null) {
            retrievingCallback.onComplete(map, nPFError);
        }
        NPFSDK.EventHandler eventHandlerM1522c = this.f1115m.mo1049c().m1522c();
        if (nPFError != null) {
            eventHandlerM1522c.onVirtualCurrencyPurchaseProcessError(nPFError);
        } else {
            eventHandlerM1522c.onVirtualCurrencyPurchaseProcessSuccess(map);
        }
        this.f1105c = null;
        this.f1109g = false;
        this.f1110h = true;
        this.f1106d = null;
        this.f1107e = null;
        this.f1111i = null;
        this.f1113k = null;
        this.f1112j = null;
        this.f1108f = false;
        this.f1114l = new ArrayList();
    }

    /* JADX INFO: renamed from: a */
    public void m1153a(JSONArray jSONArray, JSONArray jSONArray2) {
        BaaSUser baaSUserM1665a = this.f1115m.mo1048b().m1665a();
        if (!this.f1115m.mo1050d().m1633b(baaSUserM1665a)) {
            m1152a((Map<String, VirtualCurrencyWallet>) null, C1025o.m1656a());
            return;
        }
        C0905c.m1186g().m1209a(baaSUserM1665a, AbstractC0880e.m1122a(), AbstractC0880e.m1125a(jSONArray, jSONArray2), this);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
    /* JADX INFO: renamed from: a */
    public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
        if (nPFError != null) {
            m1152a((Map<String, VirtualCurrencyWallet>) null, nPFError);
            return;
        }
        try {
            Pair<List<String>, Map<String, VirtualCurrencyWallet>> pairM1146a = m1146a(jSONObject);
            this.f1113k = (List) pairM1146a.first;
            this.f1112j = (Map) pairM1146a.second;
            if (this.f1115m.mo1065s().m1335j()) {
                m1152a(this.f1112j, (NPFError) null);
            } else {
                this.f1115m.mo1063q().mo1130a(this);
            }
        } catch (JSONException e) {
            C0955e.m1394b(f1103a, "Failed making request JSON object", e);
            throw new IllegalArgumentException(e);
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1154b() {
        this.f1115m.mo1049c().m1516a(this);
        Intent intent = new Intent(this.f1105c, (Class<?>) PurchaseActivity.class);
        intent.putExtra("requestCode", 8213);
        intent.putExtra("sku", this.f1106d.getSKU());
        intent.putExtra(C0856j.f955a, this.f1106d.getTitle());
        intent.putExtra(FirebaseAnalytics.Param.PRICE, this.f1106d.getPrice().toString());
        intent.putExtra("priceCode", this.f1106d.getPriceCode());
        intent.putExtra("displayPrice", this.f1106d.getDisplayPrice());
        intent.putExtra("amount", this.f1106d.getAmount());
        intent.putExtra("extraAmount", this.f1106d.getExtraAmount());
        intent.putExtra("customAttribute", this.f1106d.getCustomAttribute());
        intent.putExtra("purchaseProductInfo", this.f1111i);
        intent.putExtra("virtualCurrencyName", this.f1106d.getVirtualCurrencyName());
        C0878c.m1090a(this.f1106d.getSKU(), this.f1106d.getPrice(), this.f1106d.getPriceCode(), this.f1106d.getCustomAttribute(), this.f1111i, this.f1115m);
        this.f1105c.startActivityForResult(intent, 8213);
    }
}
