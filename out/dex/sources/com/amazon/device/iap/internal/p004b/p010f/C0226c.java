package com.amazon.device.iap.internal.p004b.p010f;

import com.amazon.device.iap.internal.p004b.C0218e;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.f.c */
/* JADX INFO: compiled from: ResponseReceivedCommandV2.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0226c extends AbstractC0224a {
    public C0226c(C0218e c0218e, boolean z) {
        super(c0218e, "2.0");
        m354a("receiptDelivered", Boolean.valueOf(z));
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a_ */
    public void mo345a_() {
        Object objM347a = m355b().m342d().m347a("notifyListenerResult");
        if (objM347a == null || !Boolean.TRUE.equals(objM347a)) {
            m354a("notifyListenerSucceeded", false);
        } else {
            m354a("notifyListenerSucceeded", true);
        }
        super.mo345a_();
    }
}
