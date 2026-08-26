package com.amazon.device.iap.internal.p004b.p010f;

import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.venezia.command.SuccessResult;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.f.a */
/* JADX INFO: compiled from: ResponseReceivedCommandBase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractC0224a extends AbstractC0232i {
    AbstractC0224a(C0218e c0218e, String str) {
        super(c0218e, "response_received", str);
        m357b(false);
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws Exception {
        return true;
    }
}
