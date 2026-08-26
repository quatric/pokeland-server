package com.amazon.device.iap.internal.util;

import com.amazon.android.Kiwi;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.internal.model.ReceiptBuilder;
import com.amazon.device.iap.internal.p004b.C0198a;
import com.amazon.device.iap.internal.p004b.C0213d;
import com.amazon.device.iap.model.ProductType;
import com.amazon.device.iap.model.Receipt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.util.a */
/* JADX INFO: compiled from: ReceiptHelper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0242a {

    /* JADX INFO: renamed from: a */
    private static final String f243a = "a";

    /* JADX INFO: renamed from: com.amazon.device.iap.internal.util.a$1, reason: invalid class name */
    /* JADX INFO: compiled from: ReceiptHelper.java */
    static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f244a = new int[EnumC0244c.values().length];

        static {
            try {
                f244a[EnumC0244c.V1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f244a[EnumC0244c.LEGACY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f244a[EnumC0244c.V2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static Receipt m397a(JSONObject jSONObject) throws JSONException {
        String strOptString = jSONObject.optString("token");
        String string = jSONObject.getString("sku");
        ProductType productTypeValueOf = ProductType.valueOf(jSONObject.getString("itemType").toUpperCase());
        String strOptString2 = jSONObject.optString("startDate");
        Date dateM402b = m399a(strOptString2) ? null : m402b(strOptString2);
        String strOptString3 = jSONObject.optString("endDate");
        return new ReceiptBuilder().setReceiptId(strOptString).setSku(string).setProductType(productTypeValueOf).setPurchaseDate(dateM402b).setCancelDate(m399a(strOptString3) ? null : m402b(strOptString3)).build();
    }

    /* JADX INFO: renamed from: a */
    public static Receipt m398a(JSONObject jSONObject, String str, String str2) throws C0213d, C0198a, IllegalArgumentException {
        int i = AnonymousClass1.f244a[m400b(jSONObject).ordinal()];
        if (i != 1) {
            return i != 2 ? m404d(jSONObject, str, str2) : m401b(jSONObject, str, str2);
        }
        return m403c(jSONObject, str, str2);
    }

    /* JADX INFO: renamed from: a */
    protected static boolean m399a(String str) {
        return str == null || str.trim().length() == 0;
    }

    /* JADX INFO: renamed from: b */
    private static EnumC0244c m400b(JSONObject jSONObject) {
        String strOptString = jSONObject.optString("DeviceId");
        if (C0245d.m411a(jSONObject.optString("receiptId"))) {
            return C0245d.m411a(strOptString) ? EnumC0244c.LEGACY : EnumC0244c.V1;
        }
        return EnumC0244c.V2;
    }

    /* JADX INFO: renamed from: b */
    private static Receipt m401b(JSONObject jSONObject, String str, String str2) throws C0213d, C0198a {
        String strOptString = jSONObject.optString("signature");
        if (C0245d.m411a(strOptString)) {
            C0246e.m414b(f243a, "a signature was not found in the receipt for request ID " + str2);
            MetricsHelper.submitReceiptVerificationFailureMetrics(str2, "NO Signature found", strOptString);
            throw new C0213d(str2, null, strOptString);
        }
        try {
            Receipt receiptM397a = m397a(jSONObject);
            String str3 = str + "-" + receiptM397a.getReceiptId();
            boolean zIsSignedByKiwi = Kiwi.isSignedByKiwi(str3, strOptString);
            C0246e.m412a(f243a, "stringToVerify/legacy:\n" + str3 + "\nsignature:\n" + strOptString);
            if (zIsSignedByKiwi) {
                return receiptM397a;
            }
            MetricsHelper.submitReceiptVerificationFailureMetrics(str2, str3, strOptString);
            throw new C0213d(str2, str3, strOptString);
        } catch (JSONException e) {
            throw new C0198a(str2, jSONObject.toString(), e);
        }
    }

    /* JADX INFO: renamed from: b */
    protected static Date m402b(String str) throws JSONException {
        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(str);
            if (0 == date.getTime()) {
                return null;
            }
            return date;
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
    }

    /* JADX INFO: renamed from: c */
    private static Receipt m403c(JSONObject jSONObject, String str, String str2) throws C0213d, C0198a {
        String strOptString = jSONObject.optString("DeviceId");
        String strOptString2 = jSONObject.optString("signature");
        if (C0245d.m411a(strOptString2)) {
            C0246e.m414b(f243a, "a signature was not found in the receipt for request ID " + str2);
            MetricsHelper.submitReceiptVerificationFailureMetrics(str2, "NO Signature found", strOptString2);
            throw new C0213d(str2, null, strOptString2);
        }
        try {
            Receipt receiptM397a = m397a(jSONObject);
            Object[] objArr = new Object[9];
            objArr[0] = PurchasingService.SDK_VERSION;
            objArr[1] = str;
            objArr[2] = strOptString;
            objArr[3] = receiptM397a.getProductType();
            objArr[4] = receiptM397a.getSku();
            objArr[5] = receiptM397a.getReceiptId();
            objArr[6] = str2;
            objArr[7] = ProductType.SUBSCRIPTION == receiptM397a.getProductType() ? receiptM397a.getPurchaseDate() : null;
            objArr[8] = ProductType.SUBSCRIPTION == receiptM397a.getProductType() ? receiptM397a.getCancelDate() : null;
            String str3 = String.format("%s|%s|%s|%s|%s|%s|%s|%tQ|%tQ", objArr);
            C0246e.m412a(f243a, "stringToVerify/v1:\n" + str3 + "\nsignature:\n" + strOptString2);
            if (Kiwi.isSignedByKiwi(str3, strOptString2)) {
                return receiptM397a;
            }
            MetricsHelper.submitReceiptVerificationFailureMetrics(str2, str3, strOptString2);
            throw new C0213d(str2, str3, strOptString2);
        } catch (JSONException e) {
            throw new C0198a(str2, jSONObject.toString(), e);
        }
    }

    /* JADX INFO: renamed from: d */
    private static Receipt m404d(JSONObject jSONObject, String str, String str2) throws C0213d, C0198a {
        String strOptString = jSONObject.optString("DeviceId");
        String strOptString2 = jSONObject.optString("signature");
        Date dateM402b = null;
        if (C0245d.m411a(strOptString2)) {
            C0246e.m414b(f243a, "a signature was not found in the receipt for request ID " + str2);
            MetricsHelper.submitReceiptVerificationFailureMetrics(str2, "NO Signature found", strOptString2);
            throw new C0213d(str2, null, strOptString2);
        }
        try {
            String string = jSONObject.getString("receiptId");
            String string2 = jSONObject.getString("sku");
            ProductType productTypeValueOf = ProductType.valueOf(jSONObject.getString("itemType").toUpperCase());
            String strOptString3 = jSONObject.optString("purchaseDate");
            Date dateM402b2 = m399a(strOptString3) ? null : m402b(strOptString3);
            String strOptString4 = jSONObject.optString("cancelDate");
            if (!m399a(strOptString4)) {
                dateM402b = m402b(strOptString4);
            }
            Receipt receiptBuild = new ReceiptBuilder().setReceiptId(string).setSku(string2).setProductType(productTypeValueOf).setPurchaseDate(dateM402b2).setCancelDate(dateM402b).build();
            String str3 = String.format("%s|%s|%s|%s|%s|%tQ|%tQ", str, strOptString, receiptBuild.getProductType(), receiptBuild.getSku(), receiptBuild.getReceiptId(), receiptBuild.getPurchaseDate(), receiptBuild.getCancelDate());
            C0246e.m412a(f243a, "stringToVerify/v2:\n" + str3 + "\nsignature:\n" + strOptString2);
            if (Kiwi.isSignedByKiwi(str3, strOptString2)) {
                return receiptBuild;
            }
            MetricsHelper.submitReceiptVerificationFailureMetrics(str2, str3, strOptString2);
            throw new C0213d(str2, str3, strOptString2);
        } catch (JSONException e) {
            throw new C0198a(str2, jSONObject.toString(), e);
        }
    }
}
