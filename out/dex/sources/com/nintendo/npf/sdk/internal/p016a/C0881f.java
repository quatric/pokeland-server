package com.nintendo.npf.sdk.internal.p016a;

import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p023e.C0955e;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.f */
/* JADX INFO: compiled from: IABUtilAmazon.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0881f extends AbstractC0880e {

    /* JADX INFO: renamed from: c */
    private static final String f1085c = "f";

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: a */
    public NPFError mo1129a(int i) {
        C1025o c1025o;
        String str = null;
        if (i == 0) {
            c1025o = null;
        } else if (i == 1) {
            str = "User canceled billing process";
            c1025o = new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled billing process");
            C0955e.m1396d(f1085c, "User canceled billing process");
        } else if (i == 2) {
            str = "Amazon IAP API is not supported";
            c1025o = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_METHOD_NOT_ALLOWED, "Amazon IAP API is not supported");
        } else if (i == 3) {
            str = "Failure to purchase invalid sku";
            c1025o = new C1025o(NPFError.ErrorType.NPF_ERROR, 402, "Failure to purchase invalid sku");
        } else if (i != 4) {
            str = "Unknown error from Amazon IAP: " + i;
            c1025o = new C1025o(NPFError.ErrorType.NPF_ERROR, 500, str);
        } else {
            str = "Failure to purchase since item is already purchased";
            c1025o = new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_CONFLICT, "Failure to purchase since item is already purchased");
        }
        if (str != null) {
            C0955e.m1391a(f1085c, str);
        }
        return c1025o;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: a */
    public void mo1130a(AbstractC0880e.a aVar) {
        this.f1083b = aVar;
        if (this.f1082a != null) {
            aVar.mo1137a(this.f1082a, mo1134e());
            return;
        }
        this.f1082a = new C0876a();
        if (aVar != null) {
            this.f1083b.mo1137a(this.f1082a, mo1134e());
            this.f1083b = null;
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: b */
    public boolean mo1131b(int i) {
        return i == 0;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: c */
    public boolean mo1132c(int i) {
        return i == 1;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: d */
    public void mo1133d() {
        if (this.f1082a != null) {
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
        return 1;
    }

    @Override // com.nintendo.npf.sdk.internal.p016a.AbstractC0880e
    /* JADX INFO: renamed from: g */
    public int mo1136g() {
        return 1;
    }
}
