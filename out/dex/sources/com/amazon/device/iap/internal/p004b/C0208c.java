package com.amazon.device.iap.internal.p004b;

import android.content.Context;
import android.content.Intent;
import com.amazon.device.iap.internal.InterfaceC0233c;
import com.amazon.device.iap.internal.p004b.p005a.C0202d;
import com.amazon.device.iap.internal.p004b.p006b.C0207d;
import com.amazon.device.iap.internal.p004b.p007c.C0212d;
import com.amazon.device.iap.internal.p004b.p008d.C0214a;
import com.amazon.device.iap.internal.p004b.p009e.C0219a;
import com.amazon.device.iap.internal.p004b.p011g.C0229b;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.RequestId;
import java.util.Set;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.b.c */
/* JADX INFO: compiled from: KiwiRequestHandler.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public final class C0208c implements InterfaceC0233c {

    /* JADX INFO: renamed from: a */
    private static final String f171a = "c";

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo317a(Context context, Intent intent) {
        C0246e.m412a(f171a, "handleResponse");
        String stringExtra = intent.getStringExtra("response_type");
        if (stringExtra == null) {
            C0246e.m412a(f171a, "Invalid response type: null");
            return;
        }
        C0246e.m412a(f171a, "Found response type: " + stringExtra);
        if ("purchase_response".equals(stringExtra)) {
            new C0202d(RequestId.fromString(intent.getStringExtra("requestId"))).m343e();
        }
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo318a(RequestId requestId) {
        C0246e.m412a(f171a, "sendGetUserData");
        new C0219a(requestId).m343e();
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo319a(RequestId requestId, String str) {
        C0246e.m412a(f171a, "sendPurchaseRequest");
        new C0207d(requestId, str).m343e();
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo320a(RequestId requestId, String str, FulfillmentResult fulfillmentResult) {
        C0246e.m412a(f171a, "sendNotifyFulfillment");
        new C0229b(requestId, str, fulfillmentResult).m343e();
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo321a(RequestId requestId, Set<String> set) {
        C0246e.m412a(f171a, "sendGetProductDataRequest");
        new C0212d(requestId, set).m343e();
    }

    @Override // com.amazon.device.iap.internal.InterfaceC0233c
    /* JADX INFO: renamed from: a */
    public void mo322a(RequestId requestId, boolean z) {
        C0246e.m412a(f171a, "sendGetPurchaseUpdates");
        new C0214a(requestId, z).m343e();
    }
}
