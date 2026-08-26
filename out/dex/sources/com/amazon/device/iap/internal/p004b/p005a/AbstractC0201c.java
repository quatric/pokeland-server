package com.amazon.device.iap.internal.p004b.p005a;

import com.amazon.device.iap.internal.model.PurchaseResponseBuilder;
import com.amazon.device.iap.internal.model.UserDataBuilder;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.model.PurchaseResponse;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.a.c */
/* JADX INFO: compiled from: PurchaseResponseCommandBase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractC0201c extends AbstractC0232i {
    AbstractC0201c(C0218e c0218e, String str) {
        super(c0218e, "purchase_response", str);
    }

    /* JADX INFO: renamed from: a */
    protected void m328a(String str, String str2, String str3, PurchaseResponse.RequestStatus requestStatus) {
        C0218e c0218eB = m355b();
        c0218eB.m342d().m348a(new PurchaseResponseBuilder().setRequestId(c0218eB.m341c()).setRequestStatus(requestStatus).setUserData(new UserDataBuilder().setUserId(str).setMarketplace(str2).build()).setReceipt(null).build());
    }
}
