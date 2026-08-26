package com.amazon.device.iap.internal.p004b.p012h;

import android.os.RemoteException;
import com.amazon.android.framework.exception.KiwiException;
import com.amazon.device.iap.internal.p004b.AbstractC0232i;
import com.amazon.device.iap.internal.p004b.C0218e;
import com.amazon.venezia.command.SuccessResult;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.h.a */
/* JADX INFO: compiled from: SubmitMetricCommand.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0231a extends AbstractC0232i {
    public C0231a(C0218e c0218e, String str, String str2) {
        super(c0218e, "submit_metric", "1.0");
        m354a("metricName", str);
        m354a("metricAttributes", str2);
        m357b(false);
    }

    @Override // com.amazon.device.iap.internal.p004b.AbstractC0232i
    /* JADX INFO: renamed from: a */
    protected boolean mo326a(SuccessResult successResult) throws RemoteException, KiwiException {
        return true;
    }
}
