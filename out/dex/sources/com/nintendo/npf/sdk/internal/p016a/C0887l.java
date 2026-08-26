package com.nintendo.npf.sdk.internal.p016a;

import android.os.Bundle;
import com.android.billingclient.util.BillingHelper;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyTransaction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.l */
/* JADX INFO: compiled from: VirtualCurrencyBundleUnprocessedPurchaseChecker.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
class C0887l implements AbstractC0880e.a, InterfaceC0883h.a, C0918a.a {

    /* JADX INFO: renamed from: c */
    private static final String f1116c = "l";

    /* JADX INFO: renamed from: d */
    private VirtualCurrencyBundle.UnprocessedPurchaseCallback f1119d;

    /* JADX INFO: renamed from: a */
    ArrayList<VirtualCurrencyTransaction> f1117a = new ArrayList<>();

    /* JADX INFO: renamed from: b */
    HashMap<String, String> f1118b = new HashMap<>();

    /* JADX INFO: renamed from: g */
    private final InterfaceC0875a f1122g = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: e */
    private List<String> f1120e = new ArrayList();

    /* JADX INFO: renamed from: f */
    private InterfaceC0883h f1121f = null;

    public C0887l(VirtualCurrencyBundle.UnprocessedPurchaseCallback unprocessedPurchaseCallback) {
        this.f1119d = unprocessedPurchaseCallback;
    }

    /* JADX INFO: renamed from: a */
    private void m1155a(List<VirtualCurrencyTransaction> list, NPFError nPFError) {
        VirtualCurrencyBundle.UnprocessedPurchaseCallback unprocessedPurchaseCallback = this.f1119d;
        if (unprocessedPurchaseCallback != null) {
            unprocessedPurchaseCallback.onComplete(list, nPFError);
        }
        if (this.f1121f != null) {
            this.f1122g.mo1063q().mo1133d();
            this.f1121f = null;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1156a() {
        this.f1122g.mo1063q().mo1130a(this);
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h.a
    /* JADX INFO: renamed from: a */
    public void mo1138a(Bundle bundle, NPFError nPFError) {
        String string;
        if (nPFError != null) {
            C0954d.m1388a("purchase_error", "checkUnprocessedPurchase#getPurchases#Error", nPFError);
            m1155a((List<VirtualCurrencyTransaction>) null, nPFError);
            return;
        }
        try {
            ArrayList<String> stringArrayList = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
            ArrayList<String> stringArrayList2 = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_PURCHASE_DATA_LIST);
            HashSet hashSet = new HashSet();
            for (int i = 0; i < stringArrayList2.size(); i++) {
                String str = stringArrayList.get(i);
                if (!this.f1120e.contains(str)) {
                    JSONObject jSONObject = new JSONObject(stringArrayList2.get(i));
                    if (jSONObject.has("orderId")) {
                        string = jSONObject.getString("orderId");
                    } else {
                        String str2 = stringArrayList2.get(i);
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        messageDigest.update(str2.getBytes());
                        byte[] bArrDigest = messageDigest.digest();
                        StringBuilder sb = new StringBuilder();
                        for (byte b : bArrDigest) {
                            String hexString = Integer.toHexString(b & 255);
                            if (hexString.length() == 1) {
                                sb.append('0');
                            }
                            sb.append(hexString);
                        }
                        string = sb.toString();
                    }
                    hashSet.add(string);
                    this.f1118b.put(string, str);
                }
            }
            if (hashSet.size() == 0) {
                m1155a(this.f1117a, (NPFError) null);
                return;
            }
            C0905c.m1186g().m1208a(this.f1122g.mo1048b().m1665a(), AbstractC0880e.m1122a(), hashSet, this);
        } catch (NoSuchAlgorithmException e) {
            C0955e.m1394b(f1116c, "onCompleteGetPurchase", e);
        } catch (JSONException e2) {
            m1155a((List<VirtualCurrencyTransaction>) null, C1025o.m1658a(e2));
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e.a
    /* JADX INFO: renamed from: a */
    public void mo1137a(InterfaceC0883h interfaceC0883h, int i) {
        if (this.f1122g.mo1063q().mo1131b(i)) {
            this.f1121f = interfaceC0883h;
            this.f1121f.mo1086a(this);
        } else {
            this.f1122g.mo1063q().mo1133d();
            NPFError nPFErrorMo1129a = this.f1122g.mo1063q().mo1129a(i);
            C0954d.m1388a("purchase_error", "checkUnprocessedPurchase#bindInAppBillingService#Error", nPFErrorMo1129a);
            m1155a((List<VirtualCurrencyTransaction>) null, nPFErrorMo1129a);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1157a(List<String> list) {
        this.f1120e = list;
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.a
    /* JADX INFO: renamed from: a */
    public void mo1145a(JSONArray jSONArray, NPFError nPFError) {
        if (nPFError != null) {
            if (nPFError.getErrorCode() == 404) {
                m1155a(this.f1117a, (NPFError) null);
                return;
            } else {
                m1155a((List<VirtualCurrencyTransaction>) null, nPFError);
                return;
            }
        }
        try {
            for (String str : this.f1118b.keySet()) {
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= jSONArray.length()) {
                        z = true;
                        break;
                    } else if (str.equals(jSONArray.getJSONObject(i).getJSONObject("extras").getString("orderId"))) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    this.f1117a.add(VirtualCurrencyTransaction.internalCreate(str, this.f1118b.get(str), VirtualCurrencyTransaction.State.PURCHASED));
                }
            }
            m1155a(this.f1117a, (NPFError) null);
        } catch (JSONException e) {
            m1155a((List<VirtualCurrencyTransaction>) null, C1025o.m1658a(e));
        }
    }
}
