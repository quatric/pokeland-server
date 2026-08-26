package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.util.BillingHelper;
import com.android.vending.billing.IInAppBillingService;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.b */
/* JADX INFO: compiled from: BillingMarketServiceGoogleImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0877b implements InterfaceC0883h {

    /* JADX INFO: renamed from: a */
    private static final String f1043a = "b";

    /* JADX INFO: renamed from: b */
    private IInAppBillingService f1044b;

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1045c = InterfaceC0875a.a.m1072b();

    C0877b(IInAppBillingService iInAppBillingService) {
        this.f1044b = iInAppBillingService;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public int mo1084a(String str) {
        try {
            return this.f1044b.consumePurchase(3, this.f1045c.mo1047a().getPackageName(), str);
        } catch (RemoteException unused) {
            return this.f1045c.mo1063q().mo1135f();
        }
    }

    @VisibleForTesting
    /* JADX INFO: renamed from: a */
    ArrayList<String> m1088a(InterfaceC0883h.b bVar, String str, Bundle bundle) {
        NPFError nPFErrorMo1129a;
        try {
            Bundle skuDetails = this.f1044b.getSkuDetails(3, str, BillingClient.SkuType.INAPP, bundle);
            if (skuDetails == null) {
                nPFErrorMo1129a = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, "billingService.getSkuDetails() returns null");
                C0955e.m1396d(f1043a, nPFErrorMo1129a.getErrorMessage());
            } else {
                nPFErrorMo1129a = this.f1045c.mo1063q().mo1129a(skuDetails.getInt(BillingHelper.RESPONSE_CODE));
            }
            if (nPFErrorMo1129a != null) {
                if (bVar != null) {
                    bVar.mo1139a(null, nPFErrorMo1129a);
                }
                return null;
            }
            ArrayList<String> stringArrayList = skuDetails.getStringArrayList(BillingHelper.RESPONSE_GET_SKU_DETAILS_LIST);
            if (stringArrayList != null) {
                return stringArrayList;
            }
            C1025o c1025o = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, "not found DETAILS_LIST");
            C0955e.m1396d(f1043a, c1025o.getErrorMessage());
            if (bVar != null) {
                bVar.mo1139a(null, c1025o);
            }
            return null;
        } catch (RemoteException unused) {
            C1025o c1025o2 = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, "RemoteException has happened");
            C0955e.m1396d(f1043a, c1025o2.getErrorMessage());
            if (bVar != null) {
                bVar.mo1139a(null, c1025o2);
            }
            return null;
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public void mo1085a(Activity activity, String str, BigDecimal bigDecimal, String str2, int i, String str3, String str4, InterfaceC0883h.c cVar) {
        try {
            Bundle buyIntent = this.f1044b.getBuyIntent(3, activity.getPackageName(), str, BillingClient.SkuType.INAPP, null);
            int i2 = buyIntent.getInt(BillingHelper.RESPONSE_CODE);
            if (!this.f1045c.mo1063q().mo1131b(i2)) {
                if (cVar != null) {
                    cVar.mo1140a(i2, null);
                    return;
                }
                return;
            }
            PendingIntent pendingIntent = (PendingIntent) buyIntent.getParcelable(BillingHelper.RESPONSE_BUY_INTENT);
            if (pendingIntent == null) {
                if (cVar != null) {
                    cVar.mo1140a(this.f1045c.mo1063q().mo1135f(), null);
                }
            } else {
                activity.startIntentSenderForResult(pendingIntent.getIntentSender(), i, new Intent(), 0, 0, 0);
                if (cVar != null) {
                    cVar.mo1140a(this.f1045c.mo1063q().mo1134e(), null);
                }
            }
        } catch (IntentSender.SendIntentException | RemoteException unused) {
            if (cVar != null) {
                cVar.mo1140a(this.f1045c.mo1063q().mo1135f(), null);
            }
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public void mo1086a(InterfaceC0883h.a aVar) {
        Bundle purchases;
        if (aVar != null) {
            C1025o c1025o = null;
            try {
                purchases = this.f1044b.getPurchases(3, this.f1045c.mo1047a().getPackageName(), BillingClient.SkuType.INAPP, null);
            } catch (RemoteException e) {
                C1025o c1025o2 = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, e.getLocalizedMessage());
                purchases = null;
                c1025o = c1025o2;
            }
            aVar.mo1138a(purchases, c1025o);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.InterfaceC0883h
    /* JADX INFO: renamed from: a */
    public void mo1087a(@NonNull List<String> list, InterfaceC0883h.b bVar) {
        String packageName = this.f1045c.mo1047a().getPackageName();
        HashMap<String, JSONObject> map = new HashMap<>();
        if (list.isEmpty()) {
            if (bVar != null) {
                bVar.mo1139a(map, null);
                return;
            }
            return;
        }
        int size = list.size();
        for (int i = 0; i <= (size - 1) / 20; i++) {
            int i2 = i * 20;
            int i3 = i2 + 20;
            if (i3 > size) {
                i3 = size;
            }
            ArrayList<String> arrayList = new ArrayList<>(list.subList(i2, i3));
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList);
            ArrayList<String> arrayListM1088a = m1088a(bVar, packageName, bundle);
            if (arrayListM1088a == null) {
                return;
            }
            Iterator<String> it = arrayListM1088a.iterator();
            while (it.hasNext()) {
                try {
                    JSONObject jSONObject = new JSONObject(it.next());
                    jSONObject.put("display_price", AbstractC0880e.m1124a(jSONObject.getString("price_currency_code"), new BigDecimal(jSONObject.getString("price_amount_micros")).movePointLeft(6)));
                    map.put(jSONObject.getString("productId"), jSONObject);
                } catch (JSONException e) {
                    NPFError nPFErrorM1658a = C1025o.m1658a(e);
                    if (bVar != null) {
                        bVar.mo1139a(null, nPFErrorM1658a);
                        return;
                    }
                    return;
                }
            }
        }
        if (bVar != null) {
            bVar.mo1139a(map, null);
        }
    }
}
