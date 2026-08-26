package com.amazon.device.iap.internal.p003a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.internal.C0239d;
import com.amazon.device.iap.internal.InterfaceC0233c;
import com.amazon.device.iap.internal.model.ProductBuilder;
import com.amazon.device.iap.internal.model.ProductDataResponseBuilder;
import com.amazon.device.iap.internal.model.PurchaseResponseBuilder;
import com.amazon.device.iap.internal.model.PurchaseUpdatesResponseBuilder;
import com.amazon.device.iap.internal.model.ReceiptBuilder;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.model.UserDataResponseBuilder;
import com.amazon.device.iap.internal.util.C0243b;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.Product;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.ProductType;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserData;
import com.amazon.device.iap.model.UserDataResponse;
import com.deploygate.service.DeployGateEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.metaps.common.C0856j;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.a.c */
/* JADX INFO: compiled from: SandboxRequestHandler.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0195c implements InterfaceC0233c {

    /* JADX INFO: renamed from: a */
    private static final String f153a = "c";

    /* JADX INFO: renamed from: a */
    private Intent m303a(String str) {
        Intent intent = new Intent(str);
        intent.setComponent(new ComponentName("com.amazon.sdktestclient", "com.amazon.sdktestclient.command.CommandBroker"));
        return intent;
    }

    /* JADX INFO: renamed from: a */
    private Product m304a(String str, JSONObject jSONObject) throws JSONException {
        ProductType productTypeValueOf = ProductType.valueOf(jSONObject.optString("itemType"));
        JSONObject jSONObject2 = jSONObject.getJSONObject("priceJson");
        Currency currency = Currency.getInstance(jSONObject2.optString(FirebaseAnalytics.Param.CURRENCY));
        String str2 = currency.getSymbol() + jSONObject2.optString("value");
        String strOptString = jSONObject.optString(C0856j.f955a);
        String strOptString2 = jSONObject.optString("description");
        return new ProductBuilder().setSku(str).setProductType(productTypeValueOf).setDescription(strOptString2).setPrice(str2).setSmallIconUrl(jSONObject.optString("smallIconUrl")).setTitle(strOptString).setCoinsRewardAmount(jSONObject.optInt("coinsRewardAmount", 0)).build();
    }

    /* JADX INFO: renamed from: a */
    private Receipt m305a(JSONObject jSONObject) throws ParseException {
        String strOptString = jSONObject.optString("receiptId");
        String strOptString2 = jSONObject.optString("sku");
        ProductType productTypeValueOf = ProductType.valueOf(jSONObject.optString("itemType"));
        Date date = C0194b.f152a.parse(jSONObject.optString("purchaseDate"));
        String strOptString3 = jSONObject.optString("cancelDate");
        return new ReceiptBuilder().setReceiptId(strOptString).setSku(strOptString2).setProductType(productTypeValueOf).setPurchaseDate(date).setCancelDate((strOptString3 == null || strOptString3.length() == 0) ? null : C0194b.f152a.parse(strOptString3)).build();
    }

    /* JADX INFO: renamed from: a */
    private void m307a(Intent intent) throws JSONException {
        PurchaseUpdatesResponse purchaseUpdatesResponseM310b = m310b(intent);
        if (purchaseUpdatesResponseM310b.getRequestStatus() == PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL) {
            String strOptString = new JSONObject(intent.getStringExtra("purchaseUpdatesOutput")).optString("offset");
            Log.i(f153a, "Offset for PurchaseUpdatesResponse:" + strOptString);
            C0243b.m406a(purchaseUpdatesResponseM310b.getUserData().getUserId(), strOptString);
        }
        m323a(purchaseUpdatesResponseM310b);
    }

    /* JADX INFO: renamed from: a */
    private void m308a(String str, String str2, boolean z) {
        try {
            Context contextM390b = C0239d.m381d().m390b();
            String strM405a = C0243b.m405a(str2);
            Log.i(f153a, "send PurchaseUpdates with user id:" + str2 + ";reset flag:" + z + ", local cursor:" + strM405a + ", parsed from old requestId:" + str);
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", str.toString());
            if (z) {
                strM405a = null;
            }
            jSONObject.put("offset", strM405a);
            jSONObject.put(DeployGateEvent.EXTRA_SDK_VERSION, PurchasingService.SDK_VERSION);
            jSONObject.put("packageName", contextM390b.getPackageName());
            bundle.putString("purchaseUpdatesInput", jSONObject.toString());
            Intent intentM303a = m303a("com.amazon.testclient.iap.purchaseUpdates");
            intentM303a.addFlags(268435456);
            intentM303a.putExtras(bundle);
            contextM390b.startService(intentM303a);
        } catch (JSONException unused) {
            C0246e.m414b(f153a, "Error in sendPurchaseUpdatesRequest.");
        }
    }

    /* JADX INFO: renamed from: a */
    private void m309a(String str, boolean z, boolean z2) {
        try {
            Context contextM390b = C0239d.m381d().m390b();
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", str);
            jSONObject.put("packageName", contextM390b.getPackageName());
            jSONObject.put(DeployGateEvent.EXTRA_SDK_VERSION, PurchasingService.SDK_VERSION);
            jSONObject.put("isPurchaseUpdates", z);
            jSONObject.put("reset", z2);
            bundle.putString("userInput", jSONObject.toString());
            Intent intentM303a = m303a("com.amazon.testclient.iap.appUserId");
            intentM303a.addFlags(268435456);
            intentM303a.putExtras(bundle);
            contextM390b.startService(intentM303a);
        } catch (JSONException unused) {
            C0246e.m414b(f153a, "Error in sendGetUserDataRequest.");
        }
    }

    /* JADX INFO: renamed from: b */
    private PurchaseUpdatesResponse m310b(Intent intent) {
        Exception e;
        RequestId requestIdFromString;
        UserData userDataBuild;
        boolean zOptBoolean;
        PurchaseUpdatesResponse.RequestStatus requestStatusValueOf = PurchaseUpdatesResponse.RequestStatus.FAILED;
        ArrayList arrayList = null;
        try {
            JSONObject jSONObject = new JSONObject(intent.getStringExtra("purchaseUpdatesOutput"));
            requestIdFromString = RequestId.fromString(jSONObject.optString("requestId"));
            try {
                requestStatusValueOf = PurchaseUpdatesResponse.RequestStatus.valueOf(jSONObject.optString(NotificationCompat.CATEGORY_STATUS));
                zOptBoolean = jSONObject.optBoolean("isMore");
                try {
                    userDataBuild = new UserDataBuilder().setUserId(jSONObject.optString("userId")).setMarketplace(jSONObject.optString("marketplace")).build();
                    try {
                        if (requestStatusValueOf == PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL) {
                            ArrayList arrayList2 = new ArrayList();
                            try {
                                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("receipts");
                                if (jSONArrayOptJSONArray != null) {
                                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                                        try {
                                            arrayList2.add(m305a(jSONObjectOptJSONObject));
                                        } catch (Exception unused) {
                                            Log.e(f153a, "Failed to parse receipt from json:" + jSONObjectOptJSONObject);
                                        }
                                    }
                                }
                                arrayList = arrayList2;
                            } catch (Exception e2) {
                                e = e2;
                                arrayList = arrayList2;
                                Log.e(f153a, "Error parsing purchase updates output", e);
                                return new PurchaseUpdatesResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusValueOf).setUserData(userDataBuild).setReceipts(arrayList).setHasMore(zOptBoolean).build();
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                } catch (Exception e4) {
                    e = e4;
                    userDataBuild = null;
                }
            } catch (Exception e5) {
                userDataBuild = null;
                e = e5;
                zOptBoolean = false;
                Log.e(f153a, "Error parsing purchase updates output", e);
                return new PurchaseUpdatesResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusValueOf).setUserData(userDataBuild).setReceipts(arrayList).setHasMore(zOptBoolean).build();
            }
        } catch (Exception e6) {
            e = e6;
            requestIdFromString = null;
            userDataBuild = null;
        }
        return new PurchaseUpdatesResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusValueOf).setUserData(userDataBuild).setReceipts(arrayList).setHasMore(zOptBoolean).build();
    }

    /* JADX INFO: renamed from: c */
    private void m311c(Intent intent) {
        m323a(m312d(intent));
    }

    /* JADX INFO: renamed from: d */
    private ProductDataResponse m312d(Intent intent) {
        LinkedHashSet linkedHashSet;
        HashMap map;
        Exception e;
        RequestId requestIdFromString;
        ProductDataResponse.RequestStatus requestStatusValueOf = ProductDataResponse.RequestStatus.FAILED;
        LinkedHashSet linkedHashSet2 = null;
        try {
            JSONObject jSONObject = new JSONObject(intent.getStringExtra("itemDataOutput"));
            requestIdFromString = RequestId.fromString(jSONObject.optString("requestId"));
            try {
                requestStatusValueOf = ProductDataResponse.RequestStatus.valueOf(jSONObject.optString(NotificationCompat.CATEGORY_STATUS));
                if (requestStatusValueOf != ProductDataResponse.RequestStatus.FAILED) {
                    linkedHashSet = new LinkedHashSet();
                    try {
                        map = new HashMap();
                        try {
                            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("unavailableSkus");
                            if (jSONArrayOptJSONArray != null) {
                                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                                    linkedHashSet.add(jSONArrayOptJSONArray.getString(i));
                                }
                            }
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("items");
                            if (jSONObjectOptJSONObject != null) {
                                Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                                while (itKeys.hasNext()) {
                                    String next = itKeys.next();
                                    map.put(next, m304a(next, jSONObjectOptJSONObject.optJSONObject(next)));
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            Log.e(f153a, "Error parsing item data output", e);
                        }
                    } catch (Exception e3) {
                        e = e3;
                        map = null;
                        e = e;
                        Log.e(f153a, "Error parsing item data output", e);
                        linkedHashSet2 = linkedHashSet;
                        return new ProductDataResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusValueOf).setProductData(map).setUnavailableSkus(linkedHashSet2).build();
                    }
                    linkedHashSet2 = linkedHashSet;
                } else {
                    map = null;
                }
            } catch (Exception e4) {
                e = e4;
                linkedHashSet = null;
                map = null;
            }
        } catch (Exception e5) {
            linkedHashSet = null;
            map = null;
            e = e5;
            requestIdFromString = null;
        }
        return new ProductDataResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusValueOf).setProductData(map).setUnavailableSkus(linkedHashSet2).build();
    }

    /* JADX INFO: renamed from: e */
    private void m313e(Intent intent) {
        JSONObject jSONObject;
        UserDataResponse userDataResponseM314f = m314f(intent);
        RequestId requestId = userDataResponseM314f.getRequestId();
        String stringExtra = intent.getStringExtra("userInput");
        try {
            jSONObject = new JSONObject(stringExtra);
        } catch (JSONException e) {
            Log.e(f153a, "Unable to parse request data: " + stringExtra, e);
            jSONObject = null;
        }
        if (requestId == null || jSONObject == null) {
            m323a(userDataResponseM314f);
            return;
        }
        if (!jSONObject.optBoolean("isPurchaseUpdates", false)) {
            m323a(userDataResponseM314f);
            return;
        }
        if (userDataResponseM314f.getUserData() == null || C0245d.m411a(userDataResponseM314f.getUserData().getUserId())) {
            Log.e(f153a, "No Userid found in userDataResponse" + userDataResponseM314f);
            m323a(new PurchaseUpdatesResponseBuilder().setRequestId(requestId).setRequestStatus(PurchaseUpdatesResponse.RequestStatus.FAILED).setUserData(userDataResponseM314f.getUserData()).setReceipts(new ArrayList()).setHasMore(false).build());
            return;
        }
        Log.i(f153a, "sendGetPurchaseUpdates with user id" + userDataResponseM314f.getUserData().getUserId());
        m308a(requestId.toString(), userDataResponseM314f.getUserData().getUserId(), jSONObject.optBoolean("reset", true));
    }

    /* JADX INFO: renamed from: f */
    private UserDataResponse m314f(Intent intent) {
        RequestId requestIdFromString;
        UserDataResponse.RequestStatus requestStatusValueOf = UserDataResponse.RequestStatus.FAILED;
        UserData userDataBuild = null;
        try {
            JSONObject jSONObject = new JSONObject(intent.getStringExtra("userOutput"));
            requestIdFromString = RequestId.fromString(jSONObject.optString("requestId"));
            try {
                requestStatusValueOf = UserDataResponse.RequestStatus.valueOf(jSONObject.optString(NotificationCompat.CATEGORY_STATUS));
                if (requestStatusValueOf == UserDataResponse.RequestStatus.SUCCESSFUL) {
                    userDataBuild = new UserDataBuilder().setUserId(jSONObject.optString("userId")).setMarketplace(jSONObject.optString("marketplace")).build();
                }
            } catch (Exception e) {
                e = e;
                Log.e(f153a, "Error parsing userid output", e);
            }
        } catch (Exception e2) {
            e = e2;
            requestIdFromString = null;
        }
        return new UserDataResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusValueOf).setUserData(userDataBuild).build();
    }

    /* JADX INFO: renamed from: g */
    private void m315g(Intent intent) {
        m323a(m316h(intent));
    }

    /* JADX INFO: renamed from: h */
    private PurchaseResponse m316h(Intent intent) {
        RequestId requestIdFromString;
        UserData userDataBuild;
        PurchaseResponse.RequestStatus requestStatusSafeValueOf = PurchaseResponse.RequestStatus.FAILED;
        Receipt receiptM305a = null;
        try {
            JSONObject jSONObject = new JSONObject(intent.getStringExtra("purchaseOutput"));
            requestIdFromString = RequestId.fromString(jSONObject.optString("requestId"));
            try {
                userDataBuild = new UserDataBuilder().setUserId(jSONObject.optString("userId")).setMarketplace(jSONObject.optString("marketplace")).build();
                try {
                    requestStatusSafeValueOf = PurchaseResponse.RequestStatus.safeValueOf(jSONObject.optString("purchaseStatus"));
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("receipt");
                    if (jSONObjectOptJSONObject != null) {
                        receiptM305a = m305a(jSONObjectOptJSONObject);
                    }
                } catch (Exception e) {
                    e = e;
                    Log.e(f153a, "Error parsing purchase output", e);
                }
            } catch (Exception e2) {
                e = e2;
                userDataBuild = null;
            }
        } catch (Exception e3) {
            e = e3;
            requestIdFromString = null;
            userDataBuild = null;
        }
        return new PurchaseResponseBuilder().setRequestId(requestIdFromString).setRequestStatus(requestStatusSafeValueOf).setUserData(userDataBuild).setReceipt(receiptM305a).build();
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo317a(Context context, Intent intent) {
        C0246e.m412a(f153a, "handleResponse");
        intent.setComponent(new ComponentName("com.amazon.sdktestclient", "com.amazon.sdktestclient.command.CommandBroker"));
        try {
            String string = intent.getExtras().getString("responseType");
            if (string.equalsIgnoreCase("com.amazon.testclient.iap.purchase")) {
                m315g(intent);
            } else if (string.equalsIgnoreCase("com.amazon.testclient.iap.appUserId")) {
                m313e(intent);
            } else if (string.equalsIgnoreCase("com.amazon.testclient.iap.itemData")) {
                m311c(intent);
            } else if (string.equalsIgnoreCase("com.amazon.testclient.iap.purchaseUpdates")) {
                m307a(intent);
            }
        } catch (Exception e) {
            Log.e(f153a, "Error handling response.", e);
        }
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo318a(RequestId requestId) {
        C0246e.m412a(f153a, "sendGetUserDataRequest");
        m309a(requestId.toString(), false, false);
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo319a(RequestId requestId, String str) {
        C0246e.m412a(f153a, "sendPurchaseRequest");
        try {
            Context contextM390b = C0239d.m381d().m390b();
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sku", str);
            jSONObject.put("requestId", requestId.toString());
            jSONObject.put("packageName", contextM390b.getPackageName());
            jSONObject.put(DeployGateEvent.EXTRA_SDK_VERSION, PurchasingService.SDK_VERSION);
            bundle.putString("purchaseInput", jSONObject.toString());
            Intent intentM303a = m303a("com.amazon.testclient.iap.purchase");
            intentM303a.addFlags(268435456);
            intentM303a.putExtras(bundle);
            contextM390b.startService(intentM303a);
        } catch (JSONException unused) {
            C0246e.m414b(f153a, "Error in sendPurchaseRequest.");
        }
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo320a(RequestId requestId, String str, FulfillmentResult fulfillmentResult) {
        C0246e.m412a(f153a, "sendNotifyPurchaseFulfilled");
        try {
            Context contextM390b = C0239d.m381d().m390b();
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", requestId.toString());
            jSONObject.put("packageName", contextM390b.getPackageName());
            jSONObject.put("receiptId", str);
            jSONObject.put("fulfillmentResult", fulfillmentResult);
            jSONObject.put(DeployGateEvent.EXTRA_SDK_VERSION, PurchasingService.SDK_VERSION);
            bundle.putString("purchaseFulfilledInput", jSONObject.toString());
            Intent intentM303a = m303a("com.amazon.testclient.iap.purchaseFulfilled");
            intentM303a.addFlags(268435456);
            intentM303a.putExtras(bundle);
            contextM390b.startService(intentM303a);
        } catch (JSONException unused) {
            C0246e.m414b(f153a, "Error in sendNotifyPurchaseFulfilled.");
        }
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo321a(RequestId requestId, Set<String> set) {
        C0246e.m412a(f153a, "sendItemDataRequest");
        try {
            Context contextM390b = C0239d.m381d().m390b();
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray((Collection) set);
            jSONObject.put("requestId", requestId.toString());
            jSONObject.put("packageName", contextM390b.getPackageName());
            jSONObject.put("skus", jSONArray);
            jSONObject.put(DeployGateEvent.EXTRA_SDK_VERSION, PurchasingService.SDK_VERSION);
            bundle.putString("itemDataInput", jSONObject.toString());
            Intent intentM303a = m303a("com.amazon.testclient.iap.itemData");
            intentM303a.addFlags(268435456);
            intentM303a.putExtras(bundle);
            contextM390b.startService(intentM303a);
        } catch (JSONException unused) {
            C0246e.m414b(f153a, "Error in sendItemDataRequest.");
        }
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo322a(RequestId requestId, boolean z) {
        if (requestId == null) {
            requestId = new RequestId();
        }
        C0246e.m412a(f153a, "sendPurchaseUpdatesRequest/sendGetUserData first:" + requestId);
        m309a(requestId.toString(), true, z);
    }

    /* JADX INFO: renamed from: a */
    protected void m323a(final Object obj) {
        C0245d.m408a(obj, "response");
        Context contextM390b = C0239d.m381d().m390b();
        final PurchasingListener purchasingListenerM383a = C0239d.m381d().m383a();
        if (contextM390b != null && purchasingListenerM383a != null) {
            new Handler(contextM390b.getMainLooper()).post(new Runnable() { // from class: com.amazon.device.iap.internal.a.c.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (obj instanceof ProductDataResponse) {
                            purchasingListenerM383a.onProductDataResponse((ProductDataResponse) obj);
                        } else if (obj instanceof UserDataResponse) {
                            purchasingListenerM383a.onUserDataResponse((UserDataResponse) obj);
                        } else if (obj instanceof PurchaseUpdatesResponse) {
                            purchasingListenerM383a.onPurchaseUpdatesResponse((PurchaseUpdatesResponse) obj);
                        } else if (obj instanceof PurchaseResponse) {
                            purchasingListenerM383a.onPurchaseResponse((PurchaseResponse) obj);
                        } else {
                            C0246e.m414b(C0195c.f153a, "Unknown response type:" + obj.getClass().getName());
                        }
                    } catch (Exception e) {
                        C0246e.m414b(C0195c.f153a, "Error in sendResponse: " + e);
                    }
                }
            });
            return;
        }
        C0246e.m412a(f153a, "PurchasingListener is not set. Dropping response: " + obj);
    }
}
