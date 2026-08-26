package com.amazon.device.iap.internal.p004b.p011g;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.model.EnumC0241a;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.venezia.command.SuccessResult;
import java.util.Set;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.g.a */
/* JADX INFO: compiled from: PurchaseFulfilledCommandV2.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0228a extends AbstractC0232i {

    /* JADX INFO: renamed from: a */
    protected final Set<String> f195a;

    /* JADX INFO: renamed from: b */
    protected final String f196b;

    public C0228a(C0218e c0218e, Set<String> set, String str) {
        super(c0218e, "purchase_fulfilled", "2.0");
        this.f195a = set;
        this.f196b = str;
        m357b(false);
        m354a("receiptIds", this.f195a);
        m354a("fulfillmentStatus", this.f196b);
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws RemoteException, KiwiException {
        return true;
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a_ */
    public void mo345a_() {
        Object objM347a = m355b().m342d().m347a("notifyListenerResult");
        if (objM347a != null && Boolean.FALSE.equals(objM347a)) {
            m354a("fulfillmentStatus", EnumC0241a.DELIVERY_ATTEMPTED.toString());
        }
        super.mo345a_();
    }
}
