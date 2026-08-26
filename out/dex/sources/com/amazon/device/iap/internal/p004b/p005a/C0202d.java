package com.amazon.device.iap.internal.p004b.p005a;

import com.amazon.device.iap.internal.model.PurchaseResponseBuilder;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.p004b.p010f.C0225b;
import com.amazon.device.iap.internal.p004b.p010f.C0226c;
import com.amazon.device.iap.model.ProductType;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.RequestId;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.a.d */
/* JADX INFO: compiled from: PurchaseResponseRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0202d extends C0218e {
    public C0202d(RequestId requestId) {
        super(requestId);
        C0199a c0199a = new C0199a(this);
        c0199a.m356b(new C0200b(this));
        m338a((AbstractC0232i) c0199a);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: a */
    public void mo329a() {
        PurchaseResponse purchaseResponse = (PurchaseResponse) m342d().m346a();
        if (purchaseResponse == null) {
            return;
        }
        Receipt receipt = purchaseResponse.getReceipt();
        boolean z = receipt != null;
        AbstractC0232i c0226c = new C0226c(this, z);
        if (z && (ProductType.ENTITLED == receipt.getProductType() || ProductType.SUBSCRIPTION == receipt.getProductType())) {
            c0226c.m356b(new C0225b(this, m341c().toString()));
        }
        m340a(purchaseResponse, c0226c);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: b */
    public void mo330b() {
        PurchaseResponse purchaseResponseBuild = (PurchaseResponse) m342d().m346a();
        if (purchaseResponseBuild == null) {
            purchaseResponseBuild = new PurchaseResponseBuilder().setRequestId(m341c()).setRequestStatus(PurchaseResponse.RequestStatus.FAILED).build();
        }
        m340a(purchaseResponseBuild, new C0226c(this, false));
    }
}
