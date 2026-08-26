package com.amazon.device.iap.internal;

import android.content.Context;
import android.content.Intent;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.internal.util.C0245d;
import com.amazon.device.iap.internal.util.C0246e;
import com.amazon.device.iap.model.FulfillmentResult;
import com.amazon.device.iap.model.RequestId;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.amazon.device.iap.internal.d */
/* JADX INFO: compiled from: PurchasingManager.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0239d {

    /* JADX INFO: renamed from: a */
    private static String f228a = "d";

    /* JADX INFO: renamed from: b */
    private static String f229b = "sku";

    /* JADX INFO: renamed from: c */
    private static C0239d f230c = new C0239d();

    /* JADX INFO: renamed from: d */
    private final InterfaceC0233c f231d = C0240e.m394b();

    /* JADX INFO: renamed from: e */
    private Context f232e;

    /* JADX INFO: renamed from: f */
    private PurchasingListener f233f;

    private C0239d() {
    }

    /* JADX INFO: renamed from: d */
    public static C0239d m381d() {
        return f230c;
    }

    /* JADX INFO: renamed from: e */
    private void m382e() {
        if (this.f233f == null) {
            throw new IllegalStateException("You must register a PurchasingListener before invoking this operation");
        }
    }

    /* JADX INFO: renamed from: a */
    public PurchasingListener m383a() {
        return this.f233f;
    }

    /* JADX INFO: renamed from: a */
    public RequestId m384a(String str) {
        C0245d.m408a((Object) str, f229b);
        m382e();
        RequestId requestId = new RequestId();
        this.f231d.mo319a(requestId, str);
        return requestId;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX INFO: renamed from: a */
    public RequestId m385a(Set<String> set) {
        C0245d.m408a((Object) set, "skus");
        C0245d.m410a((Collection<? extends Object>) set, "skus");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().trim().length() == 0) {
                throw new IllegalArgumentException("Empty SKU values are not allowed");
            }
        }
        if (set.size() <= 100) {
            m382e();
            RequestId requestId = new RequestId();
            this.f231d.mo321a(requestId, new LinkedHashSet(set));
            return requestId;
        }
        throw new IllegalArgumentException(set.size() + " SKUs were provided, but no more than 100 SKUs are allowed");
    }

    /* JADX INFO: renamed from: a */
    public RequestId m386a(boolean z) {
        m382e();
        RequestId requestId = new RequestId();
        this.f231d.mo322a(requestId, z);
        return requestId;
    }

    /* JADX INFO: renamed from: a */
    public void m387a(Context context, Intent intent) {
        try {
            this.f231d.mo317a(context, intent);
        } catch (Exception e) {
            C0246e.m414b(f228a, "Error in onReceive: " + e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m388a(Context context, PurchasingListener purchasingListener) {
        C0246e.m412a(f228a, "PurchasingListener registered: " + purchasingListener);
        C0246e.m412a(f228a, "PurchasingListener Context: " + context);
        if (purchasingListener == null || context == null) {
            throw new IllegalArgumentException("Neither PurchasingListener or its Context can be null");
        }
        this.f232e = context.getApplicationContext();
        this.f233f = purchasingListener;
    }

    /* JADX INFO: renamed from: a */
    public void m389a(String str, FulfillmentResult fulfillmentResult) {
        if (C0245d.m411a(str)) {
            throw new IllegalArgumentException("Empty receiptId is not allowed");
        }
        C0245d.m408a(fulfillmentResult, "fulfillmentResult");
        m382e();
        this.f231d.mo320a(new RequestId(), str, fulfillmentResult);
    }

    /* JADX INFO: renamed from: b */
    public Context m390b() {
        return this.f232e;
    }

    /* JADX INFO: renamed from: c */
    public RequestId m391c() {
        m382e();
        RequestId requestId = new RequestId();
        this.f231d.mo318a(requestId);
        return requestId;
    }
}
