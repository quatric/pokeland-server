package com.amazon.device.iap.internal.p004b.p006b;

import com.amazon.device.iap.internal.model.PurchaseResponseBuilder;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.RequestId;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.b.d */
/* JADX INFO: compiled from: PurchaseRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0207d extends C0218e {
    public C0207d(RequestId requestId, String str) {
        super(requestId);
        C0206c c0206c = new C0206c(this, str);
        c0206c.m356b(new C0205b(this, str));
        m338a((AbstractC0232i) c0206c);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: a */
    public void mo329a() {
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: b */
    public void mo330b() {
        PurchaseResponse purchaseResponseBuild = (PurchaseResponse) m342d().m346a();
        if (purchaseResponseBuild == null) {
            purchaseResponseBuild = new PurchaseResponseBuilder().setRequestId(m341c()).setRequestStatus(PurchaseResponse.RequestStatus.FAILED).build();
        }
        m339a(purchaseResponseBuild);
    }
}
