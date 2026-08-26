package com.nintendo.npf.sdk.internal.p016a;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.billingclient.api.BillingClient;
import com.android.vending.billing.IInAppBillingService;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.AbstractC0952b;
import com.nintendo.npf.sdk.internal.p023e.C0955e;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.g */
/* JADX INFO: compiled from: IABUtilGoogle.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ServiceConnectionC0882g extends AbstractC0880e implements ServiceConnection {

    /* JADX INFO: renamed from: c */
    private static final String f1086c = "g";

    /* JADX INFO: renamed from: d */
    private final AbstractC0952b<InterfaceC0875a> f1087d = InterfaceC0875a.a.m1070a();

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: a */
    public NPFError mo1129a(int i) {
        if (i == 0) {
            return null;
        }
        if (i == 1) {
            C1025o c1025o = new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled billing process");
            C0955e.m1396d(f1086c, "User canceled billing process");
            return c1025o;
        }
        if (i == 2) {
            C1025o c1025o2 = new C1025o(NPFError.ErrorType.NETWORK_ERROR, 0, "Billing response can't be handled for network error");
            C0955e.m1396d(f1086c, "Billing response can't be handled for network error");
            return c1025o2;
        }
        if (i == 3) {
            C1025o c1025o3 = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_METHOD_NOT_ALLOWED, "Billing API version 3 is not supported");
            C0955e.m1396d(f1086c, "Billing API version 3 is not supported");
            return c1025o3;
        }
        if (i == 4) {
            C1025o c1025o4 = new C1025o(NPFError.ErrorType.NPF_ERROR, 402, "Requested product is not available for purchase");
            C0955e.m1396d(f1086c, "Requested product is not available for purchase");
            return c1025o4;
        }
        if (i == 5) {
            C1025o c1025o5 = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "Invalid arguments provided to the API");
            C0955e.m1396d(f1086c, "Invalid arguments provided to the API");
            return c1025o5;
        }
        if (i == 6) {
            C1025o c1025o6 = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, "Fatal error during the API action");
            C0955e.m1396d(f1086c, "Fatal error during the API action");
            return c1025o6;
        }
        if (i == 7) {
            C1025o c1025o7 = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_CONFLICT, "Failure to purchase since item is already owned");
            C0955e.m1396d(f1086c, "Failure to purchase since item is already owned");
            return c1025o7;
        }
        if (i == 8) {
            C1025o c1025o8 = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_CONFLICT, "Failure to consume since item is not owned");
            C0955e.m1396d(f1086c, "Failure to consume since item is not owned");
            return c1025o8;
        }
        String str = "Unknown error from IAB: " + new Integer(i).toString();
        C1025o c1025o9 = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, str);
        C0955e.m1396d(f1086c, str);
        return c1025o9;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: a */
    public void mo1130a(AbstractC0880e.a aVar) {
        this.f1083b = aVar;
        if (this.f1082a != null) {
            aVar.mo1137a(this.f1082a, mo1134e());
            return;
        }
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        this.f1087d.m1386c().mo1047a().bindService(intent, this, 1);
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: b */
    public boolean mo1131b(int i) {
        return i == 0;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: c */
    public boolean mo1132c(int i) {
        return i == 3;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: d */
    public void mo1133d() {
        if (this.f1082a != null) {
            this.f1087d.m1386c().mo1047a().unbindService(this);
            this.f1082a = null;
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: e */
    public int mo1134e() {
        return 0;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: f */
    public int mo1135f() {
        return 6;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: g */
    public int mo1136g() {
        return 1;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        C0955e.m1391a(f1086c, "ComponentName name : " + componentName);
        try {
            String packageName = this.f1087d.m1386c().mo1047a().getPackageName();
            IInAppBillingService iInAppBillingServiceAsInterface = IInAppBillingService.Stub.asInterface(iBinder);
            int iIsBillingSupported = iInAppBillingServiceAsInterface.isBillingSupported(3, packageName, BillingClient.SkuType.INAPP);
            this.f1082a = new C0877b(iInAppBillingServiceAsInterface);
            if (this.f1083b != null) {
                this.f1083b.mo1137a(this.f1082a, iIsBillingSupported);
                this.f1083b = null;
            }
        } catch (RemoteException unused) {
            if (this.f1083b != null) {
                this.f1083b.mo1137a(null, mo1135f());
                this.f1083b = null;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        C0955e.m1393b(f1086c, "onServiceDisconnected : " + componentName);
    }
}
