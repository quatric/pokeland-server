package com.amazon.device.iap.internal.p004b.p006b;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.p013c.C0235b;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.b.b */
/* JADX INFO: compiled from: PurchaseItemCommandV1.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0205b extends AbstractC0204a {
    public C0205b(C0218e c0218e, String str) {
        super(c0218e, "1.0", str);
    }

    protected void preExecution() throws KiwiException {
        super.preExecution();
        C0235b.m370a().m372b(m358c());
    }
}
