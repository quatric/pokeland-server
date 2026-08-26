package com.amazon.device.iap.internal.p004b.p008d;

import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.device.iap.internal.util.C0243b;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.d.b */
/* JADX INFO: compiled from: PurchaseUpdatesCommandBase.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
abstract class AbstractC0215b extends AbstractC0232i {

    /* JADX INFO: renamed from: a */
    protected final boolean f178a;

    AbstractC0215b(C0218e c0218e, String str, boolean z) {
        super(c0218e, "purchase_updates", str);
        this.f178a = z;
    }

    protected void preExecution() throws KiwiException {
        super.preExecution();
        m354a("cursor", this.f178a ? null : C0243b.m405a((String) m355b().m342d().m347a("userId")));
    }
}
