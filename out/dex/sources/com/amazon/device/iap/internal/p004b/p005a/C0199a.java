package com.amazon.device.iap.internal.p004b.p005a;

import com.amazon.device.iap.internal.model.PurchaseResponseBuilder;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0242a;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.venezia.command.SuccessResult;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.a.a */
/* JADX INFO: compiled from: PurchaseResponseCommandV2.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0199a extends AbstractC0201c {

    /* JADX INFO: renamed from: a */
    private static final String f160a = "a";

    public C0199a(C0218e c0218e) {
        super(c0218e, "2.0");
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws Exception {
        Map data = successResult.getData();
        C0246e.m412a(f160a, "data: " + data);
        String str = (String) getCommandData().get("requestId");
        String str2 = (String) data.get("userId");
        String str3 = (String) data.get("marketplace");
        String str4 = (String) data.get("receipt");
        if (C0245d.m411a(str4)) {
            m328a(str2, str3, str, PurchaseResponse.RequestStatus.FAILED);
            return false;
        }
        Receipt receiptM398a = null;
        JSONObject jSONObject = new JSONObject(str4);
        PurchaseResponse.RequestStatus requestStatusSafeValueOf = PurchaseResponse.RequestStatus.safeValueOf(jSONObject.getString("orderStatus"));
        if (requestStatusSafeValueOf == PurchaseResponse.RequestStatus.SUCCESSFUL) {
            try {
                receiptM398a = C0242a.m398a(jSONObject, str2, str);
            } catch (Throwable unused) {
                m328a(str2, str3, str, PurchaseResponse.RequestStatus.FAILED);
                return false;
            }
        }
        C0218e c0218eB = m355b();
        c0218eB.m342d().m348a(new PurchaseResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(requestStatusSafeValueOf).setUserData(new UserDataBuilder().setUserId(str2).setMarketplace(str3).build()).setReceipt(receiptM398a).build());
        return true;
    }
}
