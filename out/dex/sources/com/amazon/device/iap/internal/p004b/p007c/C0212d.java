package com.amazon.device.iap.internal.p004b.p007c;

import com.amazon.device.iap.internal.model.ProductDataResponseBuilder;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.RequestId;
import java.util.Set;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.c.d */
/* JADX INFO: compiled from: GetProductDataRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0212d extends C0218e {
    public C0212d(RequestId requestId, Set<String> set) {
        super(requestId);
        C0209a c0209a = new C0209a(this, set);
        c0209a.m356b(new C0210b(this, set));
        m338a((AbstractC0232i) c0209a);
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: a */
    public void mo329a() {
        m339a((ProductDataResponse) m342d().m346a());
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: b */
    public void mo330b() {
        ProductDataResponse productDataResponseBuild = (ProductDataResponse) m342d().m346a();
        if (productDataResponseBuild == null) {
            productDataResponseBuild = new ProductDataResponseBuilder().setRequestId(m341c()).setRequestStatus(ProductDataResponse.RequestStatus.FAILED).build();
        }
        m339a(productDataResponseBuild);
    }
}
