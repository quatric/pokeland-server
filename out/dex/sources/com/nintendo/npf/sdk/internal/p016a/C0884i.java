package com.nintendo.npf.sdk.internal.p016a;

import android.app.Activity;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1022l;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.a.i */
/* JADX INFO: compiled from: VirtualCurrencyBundleAbilityChecker.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0884i implements C0918a.b {

    /* JADX INFO: renamed from: a */
    private static final String f1088a = C0885j.class.getSimpleName();

    /* JADX INFO: renamed from: b */
    private final Activity f1089b;

    /* JADX INFO: renamed from: c */
    private final VirtualCurrencyBundle f1090c;

    /* JADX INFO: renamed from: d */
    private final VirtualCurrencyWallet.RetrievingCallback f1091d;

    /* JADX INFO: renamed from: e */
    private final String f1092e;

    /* JADX INFO: renamed from: f */
    private final InterfaceC0875a f1093f = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: g */
    private final BaaSUser f1094g;

    public C0884i(Activity activity, VirtualCurrencyBundle virtualCurrencyBundle, String str, BaaSUser baaSUser, VirtualCurrencyWallet.RetrievingCallback retrievingCallback) {
        this.f1089b = activity;
        this.f1090c = virtualCurrencyBundle;
        this.f1094g = baaSUser;
        this.f1091d = retrievingCallback;
        this.f1092e = str;
    }

    /* JADX INFO: renamed from: a */
    private void m1141a(NPFError nPFError) {
        this.f1091d.onComplete(null, nPFError);
        this.f1093f.mo1049c().m1522c().onVirtualCurrencyPurchaseProcessError(nPFError);
    }

    /* JADX INFO: renamed from: a */
    public void m1142a() {
        C0955e.m1393b(f1088a, "execute is called");
        C0905c.m1186g().m1205a(this.f1094g, this);
    }

    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
    /* JADX INFO: renamed from: a */
    public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
        if (nPFError != null) {
            m1141a(nPFError);
            return;
        }
        try {
            if (jSONObject.getBoolean("purchasable")) {
                new C1022l(new C0889n(this.f1089b, this.f1090c, this.f1091d, this.f1092e)).m1650a();
            } else {
                m1141a(new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "The user is not allowed to purchase virtual currency!"));
            }
        } catch (JSONException e) {
            m1141a(C1025o.m1658a(e));
        }
    }
}
