package com.amazon.device.iap.internal.p004b.p008d;

import com.amazon.device.iap.internal.model.PurchaseUpdatesResponseBuilder;
import com.amazon.device.iap.internal.model.ReceiptBuilder;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.p004b.C0198a;
import com.amazon.device.iap.internal.p004b.C0213d;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.p013c.C0234a;
import com.amazon.device.iap.internal.p013c.C0236c;
import com.amazon.device.iap.internal.util.C0242a;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.ProductType;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.venezia.command.SuccessResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.d.d */
/* JADX INFO: compiled from: PurchaseUpdatesCommandV1.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0217d extends AbstractC0215b {

    /* JADX INFO: renamed from: b */
    private static final String f180b = "d";

    /* JADX INFO: renamed from: c */
    private static final Date f181c = new Date(0);

    public C0217d(C0218e c0218e, boolean z) {
        super(c0218e, "1.0", z);
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws Exception {
        Map data = successResult.getData();
        C0246e.m412a(f180b, "data: " + data);
        String str = (String) data.get("userId");
        String str2 = (String) data.get("marketplace");
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = new JSONArray((String) data.get("receipts"));
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                Receipt receiptM398a = C0242a.m398a(jSONArray.getJSONObject(i), str, null);
                arrayList.add(receiptM398a);
                if (ProductType.ENTITLED == receiptM398a.getProductType()) {
                    C0236c.m373a().m375a(str, receiptM398a.getReceiptId(), receiptM398a.getSku());
                }
            } catch (C0198a e) {
                C0246e.m414b(f180b, "fail to parse receipt, requestId:" + e.m325a());
            } catch (C0213d e2) {
                C0246e.m414b(f180b, "fail to verify receipt, requestId:" + e2.m335a());
            } catch (Throwable th) {
                C0246e.m414b(f180b, "fail to verify receipt, requestId:" + th.getMessage());
            }
        }
        JSONArray jSONArray2 = new JSONArray((String) data.get("revocations"));
        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
            try {
                String string = jSONArray2.getString(i2);
                arrayList.add(new ReceiptBuilder().setSku(string).setProductType(ProductType.ENTITLED).setPurchaseDate(null).setCancelDate(f181c).setReceiptId(C0236c.m373a().m374a(str, string)).build());
            } catch (JSONException unused) {
                C0246e.m414b(f180b, "fail to parse JSON[" + i2 + "] in \"" + jSONArray2 + "\"");
            }
        }
        String str3 = (String) data.get("cursor");
        boolean zEqualsIgnoreCase = "true".equalsIgnoreCase((String) data.get("hasMore"));
        C0218e c0218eB = m355b();
        PurchaseUpdatesResponse purchaseUpdatesResponseBuild = new PurchaseUpdatesResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL).setUserData(new UserDataBuilder().setUserId(str).setMarketplace(str2).build()).setReceipts(arrayList).setHasMore(zEqualsIgnoreCase).build();
        purchaseUpdatesResponseBuild.getReceipts().addAll(C0234a.m359a().m368b(purchaseUpdatesResponseBuild.getUserData().getUserId()));
        c0218eB.m342d().m348a(purchaseUpdatesResponseBuild);
        c0218eB.m342d().m349a("newCursor", str3);
        return true;
    }
}
