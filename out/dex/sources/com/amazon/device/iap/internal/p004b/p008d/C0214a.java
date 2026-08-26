package com.amazon.device.iap.internal.p004b.p008d;

import com.amazon.device.iap.internal.model.EnumC0241a;
import com.amazon.device.iap.internal.model.PurchaseUpdatesResponseBuilder;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.p004b.p009e.C0221c;
import com.amazon.device.iap.internal.p004b.p009e.C0222d;
import com.amazon.device.iap.internal.p004b.p011g.C0228a;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.RequestId;
import java.util.HashSet;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.d.a */
/* JADX INFO: compiled from: GetPurchaseUpdatesRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0214a extends C0218e {
    public C0214a(RequestId requestId, boolean z) {
        super(requestId);
        C0221c c0221c = new C0221c(this);
        c0221c.m353a(new C0216c(this, z));
        C0222d c0222d = new C0222d(this);
        c0222d.m353a(new C0217d(this, z));
        c0221c.m356b(c0222d);
        m338a((AbstractC0232i) c0221c);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: a */
    public void mo329a() {
        AbstractC0232i c0228a;
        PurchaseUpdatesResponse purchaseUpdatesResponse = (PurchaseUpdatesResponse) m342d().m346a();
        if (purchaseUpdatesResponse.getReceipts() == null || purchaseUpdatesResponse.getReceipts().size() <= 0) {
            c0228a = null;
        } else {
            HashSet hashSet = new HashSet();
            for (Receipt receipt : purchaseUpdatesResponse.getReceipts()) {
                if (!C0245d.m411a(receipt.getReceiptId())) {
                    hashSet.add(receipt.getReceiptId());
                }
            }
            c0228a = new C0228a(this, hashSet, EnumC0241a.DELIVERED.toString());
        }
        m340a(purchaseUpdatesResponse, c0228a);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: b */
    public void mo330b() {
        Object objM346a = m342d().m346a();
        m339a((objM346a == null || !(objM346a instanceof PurchaseUpdatesResponse)) ? new PurchaseUpdatesResponseBuilder().setRequestId(m341c()).setRequestStatus(PurchaseUpdatesResponse.RequestStatus.FAILED).build() : (PurchaseUpdatesResponse) objM346a);
    }
}
