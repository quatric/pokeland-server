package com.metaps.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.android.billingclient.api.BillingClient;
import com.metaps.common.C0847a;
import com.metaps.common.C0856j;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.metaps.analytics.w */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0834w {

    /* JADX INFO: renamed from: a */
    private static final String f821a = "INAPP_PURCHASE_DATA";

    /* JADX INFO: renamed from: b */
    private static final String f822b = "ITEM_ID_LIST";

    /* JADX INFO: renamed from: c */
    private static final String f823c = "RESPONSE_CODE";

    /* JADX INFO: renamed from: d */
    private static final String f824d = "DETAILS_LIST";

    /* JADX INFO: renamed from: e */
    private static final String f825e = "com.android.vending.billing.IInAppBillingService";

    /* JADX INFO: renamed from: f */
    private static final String f826f = "com.android.vending.billing.IInAppBillingService$Stub";

    /* JADX INFO: renamed from: g */
    private static final String f827g = "asInterface";

    /* JADX INFO: renamed from: h */
    private static final String f828h = "getSkuDetails";

    /* JADX INFO: renamed from: i */
    private static final HashMap<String, Method> f829i = new HashMap<>();

    /* JADX INFO: renamed from: j */
    private static final HashMap<String, Class<?>> f830j = new HashMap<>();

    /* JADX INFO: renamed from: k */
    private static boolean f831k = false;

    /* JADX INFO: renamed from: l */
    private static Object f832l;

    /* JADX INFO: renamed from: a */
    public static void m879a(final Context context, int i, Intent intent) {
        final String stringExtra;
        if (f831k && intent != null && i == -1 && (stringExtra = intent.getStringExtra(f821a)) != null) {
            ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.metaps.analytics.w.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    C0847a.m903a(C0834w.class.toString(), "In-app billing service connected.");
                    Object unused = C0834w.f832l = C0834w.m881b(context, iBinder);
                    try {
                        try {
                            JSONObject jSONObject = new JSONObject(stringExtra);
                            String string = jSONObject.getString("productId");
                            boolean zHas = jSONObject.has("autoRenewing");
                            String strM882b = C0834w.m882b(context, string, zHas);
                            if (strM882b.equals("")) {
                                C0847a.m911c("Purchase detail cannot be retrieved. productId: " + string + " isSubscription: " + zHas);
                                return;
                            }
                            String string2 = jSONObject.getString("purchaseTime");
                            String string3 = jSONObject.getString("purchaseState");
                            JSONObject jSONObject2 = new JSONObject(strM882b);
                            String string4 = jSONObject2.getString("type");
                            String string5 = jSONObject2.getString(C0856j.f955a);
                            String string6 = jSONObject2.getString("description");
                            double d = jSONObject2.getDouble("price_amount_micros") / 1000000.0d;
                            String string7 = jSONObject2.getString("price_currency_code");
                            Analytics.trackPurchase(string, d, string7);
                            C0847a.m908b("Purchase data tracked. productId:" + string + " price:" + d + " currency:" + string7 + " purchaseTime:" + string2 + " purchaseState:" + string3 + " productType:" + string4 + " title:" + string5 + " description:" + string6);
                        } catch (JSONException e) {
                            C0847a.m911c("Failed to parse purchase json data: " + e.getMessage());
                        }
                    } finally {
                        context.unbindService(this);
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                    C0847a.m903a(C0834w.class.toString(), "In-app billing service disconnected.");
                    Object unused = C0834w.f832l = null;
                }
            };
            Intent intent2 = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent2.setPackage("com.android.vending");
            context.bindService(intent2, serviceConnection, 1);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m880a(boolean z) {
        f831k = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static Object m881b(Context context, IBinder iBinder) {
        StringBuilder sb;
        String message;
        try {
            Method declaredMethod = f829i.get(f827g);
            if (declaredMethod == null) {
                declaredMethod = context.getClassLoader().loadClass(f826f).getDeclaredMethod(f827g, IBinder.class);
                f829i.put(f827g, declaredMethod);
            }
            return declaredMethod.invoke(null, iBinder);
        } catch (ClassNotFoundException e) {
            sb = new StringBuilder();
            sb.append("[IAB Service] A class from 'com.android.vending.billing' is not available ");
            message = e.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (IllegalAccessException e2) {
            sb = new StringBuilder();
            sb.append("[IAB Service] Illegal access to a method from 'com.android.vending.billing'");
            message = e2.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (NoSuchMethodException e3) {
            sb = new StringBuilder();
            sb.append("[IAB Service] A method from 'com.android.vending.billing' is not available ");
            message = e3.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        } catch (InvocationTargetException e4) {
            sb = new StringBuilder();
            sb.append("[IAB Service] Invocation error to a method from 'com.android.vending.billing' is not available ");
            message = e4.getMessage();
            sb.append(message);
            C0847a.m911c(sb.toString());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static String m882b(Context context, String str, boolean z) {
        StringBuilder sb;
        String message;
        if (f832l != null && str != "") {
            try {
                Method declaredMethod = f829i.get(f828h);
                Class<?> clsLoadClass = f830j.get(f825e);
                if (declaredMethod == null || clsLoadClass == null) {
                    clsLoadClass = context.getClassLoader().loadClass(f825e);
                    declaredMethod = clsLoadClass.getDeclaredMethod(f828h, Integer.TYPE, String.class, String.class, Bundle.class);
                    f829i.put(f828h, declaredMethod);
                    f830j.put(f825e, clsLoadClass);
                }
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(str);
                Object[] objArr = new Object[4];
                objArr[0] = 3;
                objArr[1] = context.getPackageName();
                objArr[2] = z ? BillingClient.SkuType.SUBS : BillingClient.SkuType.INAPP;
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(f822b, arrayList);
                objArr[3] = bundle;
                Bundle bundle2 = (Bundle) declaredMethod.invoke(clsLoadClass.cast(f832l), objArr);
                if (bundle2.getInt("RESPONSE_CODE") == 0) {
                    ArrayList<String> stringArrayList = bundle2.getStringArrayList("DETAILS_LIST");
                    return stringArrayList.size() < 1 ? "" : stringArrayList.get(0);
                }
            } catch (ClassNotFoundException e) {
                sb = new StringBuilder();
                sb.append("[IAB Service] A class from 'com.android.vending.billing' is not available ");
                message = e.getMessage();
                sb.append(message);
                C0847a.m911c(sb.toString());
            } catch (IllegalAccessException e2) {
                sb = new StringBuilder();
                sb.append("[IAB Service] Invocation error to a method from 'com.android.vending.billing' is not available ");
                message = e2.getMessage();
                sb.append(message);
                C0847a.m911c(sb.toString());
            } catch (NoSuchMethodException e3) {
                sb = new StringBuilder();
                sb.append("[IAB Service] A method from 'com.android.vending.billing' is not available ");
                message = e3.getMessage();
                sb.append(message);
                C0847a.m911c(sb.toString());
            } catch (InvocationTargetException e4) {
                sb = new StringBuilder();
                sb.append("[IAB Service] Illegal access to a method from 'com.android.vending.billing'");
                message = e4.getMessage();
                sb.append(message);
                C0847a.m911c(sb.toString());
            }
        }
        return "";
    }
}
