package com.amazon.device.iap.internal.p004b;

import android.content.Context;
import android.os.Handler;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.internal.C0239d;
import com.amazon.device.iap.internal.util.C0243b;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.RequestId;
import com.amazon.device.iap.model.UserDataResponse;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.e */
/* JADX INFO: compiled from: KiwiRequest.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0218e {

    /* JADX INFO: renamed from: a */
    private static final String f182a = "e";

    /* JADX INFO: renamed from: b */
    private final RequestId f183b;

    /* JADX INFO: renamed from: c */
    private final C0230h f184c = new C0230h();

    /* JADX INFO: renamed from: d */
    private AbstractC0232i f185d = null;

    public C0218e(RequestId requestId) {
        this.f183b = requestId;
    }

    /* JADX INFO: renamed from: a */
    public void mo329a() {
    }

    /* JADX INFO: renamed from: a */
    protected void m338a(AbstractC0232i abstractC0232i) {
        this.f185d = abstractC0232i;
    }

    /* JADX INFO: renamed from: a */
    protected void m339a(Object obj) {
        m340a(obj, null);
    }

    /* JADX INFO: renamed from: a */
    protected void m340a(final Object obj, final AbstractC0232i abstractC0232i) {
        C0245d.m408a(obj, "response");
        Context contextM390b = C0239d.m381d().m390b();
        final PurchasingListener purchasingListenerM383a = C0239d.m381d().m383a();
        if (contextM390b != null && purchasingListenerM383a != null) {
            new Handler(contextM390b.getMainLooper()).post(new Runnable() { // from class: com.amazon.device.iap.internal.b.e.1
                @Override // java.lang.Runnable
                public void run() {
                    C0218e.this.m342d().m349a("notifyListenerResult", Boolean.FALSE);
                    try {
                        if (obj instanceof ProductDataResponse) {
                            purchasingListenerM383a.onProductDataResponse((ProductDataResponse) obj);
                        } else if (obj instanceof UserDataResponse) {
                            purchasingListenerM383a.onUserDataResponse((UserDataResponse) obj);
                        } else if (obj instanceof PurchaseUpdatesResponse) {
                            PurchaseUpdatesResponse purchaseUpdatesResponse = (PurchaseUpdatesResponse) obj;
                            purchasingListenerM383a.onPurchaseUpdatesResponse(purchaseUpdatesResponse);
                            Object objM347a = C0218e.this.m342d().m347a("newCursor");
                            if (objM347a != null && (objM347a instanceof String)) {
                                C0243b.m406a(purchaseUpdatesResponse.getUserData().getUserId(), objM347a.toString());
                            }
                        } else if (obj instanceof PurchaseResponse) {
                            purchasingListenerM383a.onPurchaseResponse((PurchaseResponse) obj);
                        } else {
                            C0246e.m414b(C0218e.f182a, "Unknown response type:" + obj.getClass().getName());
                        }
                        C0218e.this.m342d().m349a("notifyListenerResult", Boolean.TRUE);
                    } catch (Throwable th) {
                        C0246e.m414b(C0218e.f182a, "Error in sendResponse: " + th);
                    }
                    AbstractC0232i abstractC0232i2 = abstractC0232i;
                    if (abstractC0232i2 != null) {
                        abstractC0232i2.m352a(true);
                        abstractC0232i.mo345a_();
                    }
                }
            });
            return;
        }
        C0246e.m412a(f182a, "PurchasingListener is not set. Dropping response: " + obj);
    }

    /* JADX INFO: renamed from: b */
    public void mo330b() {
    }

    /* JADX INFO: renamed from: c */
    public RequestId m341c() {
        return this.f183b;
    }

    /* JADX INFO: renamed from: d */
    public C0230h m342d() {
        return this.f184c;
    }

    /* JADX INFO: renamed from: e */
    public void m343e() {
        AbstractC0232i abstractC0232i = this.f185d;
        if (abstractC0232i != null) {
            abstractC0232i.mo345a_();
        } else {
            mo329a();
        }
    }
}
