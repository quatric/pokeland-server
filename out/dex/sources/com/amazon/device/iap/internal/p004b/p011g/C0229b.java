package com.amazon.device.iap.internal.p004b.p011g;

import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.p004b.p010f.C0225b;
import com.amazon.device.iap.internal.p013c.C0234a;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.RequestId;
import java.util.HashSet;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.g.b */
/* JADX INFO: compiled from: NotifyFulfillmentRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0229b extends C0218e {

    /* JADX INFO: renamed from: a */
    private final String f197a;

    /* JADX INFO: renamed from: b */
    private final FulfillmentResult f198b;

    public C0229b(RequestId requestId, String str, FulfillmentResult fulfillmentResult) {
        super(requestId);
        HashSet hashSet = new HashSet();
        hashSet.add(str);
        this.f197a = str;
        this.f198b = fulfillmentResult;
        m338a((AbstractC0232i) new C0228a(this, hashSet, fulfillmentResult.toString()));
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: a */
    public void mo329a() {
    }

    @Override // com.amazon.device.iap.internal.p004b.C0218e
    /* JADX INFO: renamed from: b */
    public void mo330b() {
        String strM369c;
        if ((FulfillmentResult.FULFILLED == this.f198b || FulfillmentResult.UNAVAILABLE == this.f198b) && (strM369c = C0234a.m359a().m369c(this.f197a)) != null) {
            new C0225b(this, strM369c).mo345a_();
            C0234a.m359a().m366a(this.f197a);
        }
    }
}
