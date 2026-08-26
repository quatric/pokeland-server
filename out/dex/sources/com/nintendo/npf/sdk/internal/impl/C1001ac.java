package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p016a.C0884i;
import com.nintendo.npf.sdk.internal.p016a.C0885j;
import com.nintendo.npf.sdk.internal.p016a.C0888m;
import com.nintendo.npf.sdk.internal.p016a.C0890o;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.ArrayList;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.ac */
/* JADX INFO: compiled from: VirtualCurrencyBundleImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1001ac {

    /* JADX INFO: renamed from: a */
    private static final String f1478a = "ac";

    /* JADX INFO: renamed from: b */
    private final InterfaceC0875a f1479b = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1548a(Activity activity, VirtualCurrencyBundle virtualCurrencyBundle, @NonNull VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1478a, "purchase is called");
        m1549a(activity, virtualCurrencyBundle, null, retrievingCallback);
    }

    /* JADX INFO: renamed from: a */
    public void m1549a(Activity activity, VirtualCurrencyBundle virtualCurrencyBundle, String str, @NonNull VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1478a, "purchaseProductInfo is called");
        BaaSUser baaSUserM1665a = this.f1479b.mo1048b().m1665a();
        if (!this.f1479b.mo1050d().m1633b(baaSUserM1665a)) {
            retrievingCallback.onComplete(null, C1025o.m1656a());
            return;
        }
        if (str == null || (Pattern.matches("^[a-zA-Z0-9_\\.]+$", str) && str.length() <= 255)) {
            new C0884i(activity, virtualCurrencyBundle, str, baaSUserM1665a, retrievingCallback).m1142a();
            return;
        }
        C1025o c1025o = new C1025o(NPFError.ErrorType.PROCESS_CANCEL, 0, "argument error");
        retrievingCallback.onComplete(null, c1025o);
        this.f1479b.mo1049c().m1522c().onVirtualCurrencyPurchaseProcessError(c1025o);
    }

    /* JADX INFO: renamed from: a */
    public void m1550a(@NonNull VirtualCurrencyBundle.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1478a, "getAll is called");
        BaaSUser baaSUserM1665a = this.f1479b.mo1048b().m1665a();
        if (!this.f1479b.mo1050d().m1633b(baaSUserM1665a)) {
            retrievingCallback.onComplete(null, C1025o.m1656a());
        } else {
            C0905c.m1186g().m1210b(baaSUserM1665a, AbstractC0880e.m1122a(), new C0885j(retrievingCallback));
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1551a(@NonNull VirtualCurrencyBundle.UnprocessedPurchaseCallback unprocessedPurchaseCallback) {
        C0955e.m1393b(f1478a, "checkUnprocessedPurchase is called");
        if (!this.f1479b.mo1050d().m1633b(this.f1479b.mo1048b().m1665a())) {
            unprocessedPurchaseCallback.onComplete(null, C1025o.m1656a());
        } else if (this.f1479b.mo1065s().m1335j()) {
            unprocessedPurchaseCallback.onComplete(new ArrayList(), null);
        } else {
            new C1022l(new C0888m(unprocessedPurchaseCallback)).m1650a();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1552a(@NonNull VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1478a, "recoverPurchased is called");
        if (this.f1479b.mo1050d().m1633b(this.f1479b.mo1048b().m1665a())) {
            new C1022l(new C0890o(retrievingCallback)).m1650a();
        } else {
            retrievingCallback.onComplete(null, C1025o.m1656a());
        }
    }
}
