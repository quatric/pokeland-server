package com.amazon.device.iap.internal.p004b.p008d;

import com.amazon.device.iap.internal.model.PurchaseUpdatesResponseBuilder;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.p004b.C0198a;
import com.amazon.device.iap.internal.p004b.C0213d;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0242a;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.venezia.command.SuccessResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.d.c */
/* JADX INFO: compiled from: PurchaseUpdatesCommandV2.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0216c extends AbstractC0215b {

    /* JADX INFO: renamed from: b */
    private static final String f179b = "c";

    public C0216c(C0218e c0218e, boolean z) {
        super(c0218e, "2.0", z);
    }

    /* JADX INFO: renamed from: a */
    private List<Receipt> m336a(String str, String str2, String str3) throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = new JSONArray(str2);
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(C0242a.m398a(jSONArray.getJSONObject(i), str, str3));
            } catch (C0198a e) {
                C0246e.m414b(f179b, "fail to parse receipt, requestId:" + e.m325a());
            } catch (C0213d e2) {
                C0246e.m414b(f179b, "fail to verify receipt, requestId:" + e2.m335a());
            } catch (Throwable th) {
                C0246e.m414b(f179b, "fail to verify receipt, requestId:" + th.getMessage());
            }
        }
        return arrayList;
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws Exception {
        Map data = successResult.getData();
        C0246e.m412a(f179b, "data: " + data);
        String str = (String) data.get("userId");
        String str2 = (String) data.get("marketplace");
        List<Receipt> listM336a = m336a(str, (String) data.get("receipts"), (String) data.get("requestId"));
        String str3 = (String) data.get("cursor");
        boolean zBooleanValue = Boolean.valueOf((String) data.get("hasMore")).booleanValue();
        C0218e c0218eB = m355b();
        PurchaseUpdatesResponse purchaseUpdatesResponseBuild = new PurchaseUpdatesResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL).setUserData(new UserDataBuilder().setUserId(str).setMarketplace(str2).build()).setReceipts(listM336a).setHasMore(zBooleanValue).build();
        c0218eB.m342d().m349a("newCursor", str3);
        c0218eB.m342d().m348a(purchaseUpdatesResponseBuild);
        return true;
    }
}
