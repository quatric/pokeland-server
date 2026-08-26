package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0922e;
import com.nintendo.npf.sdk.internal.p022d.C0949d;
import com.nintendo.npf.sdk.internal.p023e.C0953c;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.p */
/* JADX INFO: compiled from: NPFSDKImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1026p {

    /* JADX INFO: renamed from: a */
    private static final String f1640a = "p";

    /* JADX INFO: renamed from: b */
    private BaaSUser f1641b = new BaaSUser();

    /* JADX INFO: renamed from: c */
    private NintendoAccount f1642c = new NintendoAccount();

    /* JADX INFO: renamed from: d */
    private final InterfaceC0875a f1643d = InterfaceC0875a.a.m1072b();

    public C1026p(Application application) {
        C0955e.m1393b(f1640a, "NPFSDK.onCreate() is called");
        application.registerActivityLifecycleCallbacks(this.f1643d.mo1049c());
        C0955e.m1391a(f1640a, "NPFSDK version : " + m1679f());
    }

    /* JADX INFO: renamed from: b */
    private NintendoAccount.AuthorizationCallback m1664b(@NonNull final NintendoAccount.AuthorizationCallback authorizationCallback) {
        return new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.p.2
            @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
            public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
                if (nintendoAccount != null && nintendoAccount.getNintendoAccountId().equals(C1026p.this.f1643d.mo1064r().m1364e())) {
                    C1026p.this.f1643d.mo1051e().m1721b(nintendoAccount);
                }
                authorizationCallback.onComplete(nintendoAccount, nPFError);
            }
        };
    }

    /* JADX INFO: renamed from: a */
    public BaaSUser m1665a() {
        return this.f1641b;
    }

    /* JADX INFO: renamed from: a */
    public void m1666a(int i) {
        this.f1643d.mo1065s().m1319a(i);
    }

    /* JADX INFO: renamed from: a */
    public void m1667a(Activity activity, List<String> list, @NonNull final NintendoAccount.AuthorizationCallback authorizationCallback) {
        C0955e.m1393b(f1640a, "authorizeByNintendoAccount is called");
        if (this.f1643d.mo1050d().m1633b(this.f1643d.mo1048b().m1665a())) {
            this.f1643d.mo1051e().m1714a(C0949d.c.AUTHORIZE_BY, activity, list, this.f1643d.mo1064r().m1363d(), new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.p.1
                @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
                public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
                    if (nintendoAccount != null && nintendoAccount.getNintendoAccountId().equals(C1026p.this.f1643d.mo1064r().m1364e())) {
                        C1026p.this.f1643d.mo1051e().m1721b(nintendoAccount);
                    }
                    authorizationCallback.onComplete(nintendoAccount, nPFError);
                }
            });
        } else {
            authorizationCallback.onComplete(null, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1668a(@NonNull NPFSDK.EventHandler eventHandler) {
        this.f1643d.mo1049c().m1515a(eventHandler);
        this.f1643d.mo1049c().m1521b(C0953c.f1282a);
    }

    /* JADX INFO: renamed from: a */
    public void m1669a(@NonNull BaaSUser.AuthorizationCallback authorizationCallback) {
        C0955e.m1393b(f1640a, "retryBaaSAuth is called");
        this.f1643d.mo1049c().m1517a(authorizationCallback);
    }

    /* JADX INFO: renamed from: a */
    public void m1670a(@NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        C0955e.m1393b(f1640a, "retryPendingAuthorizationByNintendoAccount2 is called");
        this.f1643d.mo1051e().m1715a(C0949d.c.AUTHORIZE_BY_2, m1664b(authorizationCallback));
    }

    /* JADX INFO: renamed from: a */
    public void m1671a(String str) {
        if (this.f1643d.mo1065s().m1347v().equals(str)) {
            return;
        }
        this.f1643d.mo1065s().m1325b(str);
        C1017g.m1615a((String) null, (String) null, new C1017g.a() { // from class: com.nintendo.npf.sdk.internal.impl.p.3
            @Override // com.nintendo.npf.sdk.internal.impl.C1017g.a
            /* JADX INFO: renamed from: a */
            public void mo1237a(BaaSUser baaSUser, String str2, NPFError nPFError) {
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public void m1672a(String str, String str2, @NonNull BaaSUser.AuthorizationCallback authorizationCallback) {
        C0955e.m1393b(f1640a, "retryBaaSAuth is called");
        this.f1643d.mo1048b().m1681h();
        this.f1643d.mo1064r().m1358a(str, str2);
        this.f1643d.mo1049c().m1517a(authorizationCallback);
    }

    /* JADX INFO: renamed from: b */
    public NintendoAccount m1673b() {
        return this.f1642c;
    }

    /* JADX INFO: renamed from: b */
    public void m1674b(int i) {
        this.f1643d.mo1065s().m1324b(i);
    }

    /* JADX INFO: renamed from: b */
    public void m1675b(Activity activity, List<String> list, @NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        C0955e.m1393b(f1640a, "authorizeByNintendoAccount2 is called");
        if (this.f1643d.mo1050d().m1633b(this.f1643d.mo1048b().m1665a())) {
            this.f1643d.mo1051e().m1714a(C0949d.c.AUTHORIZE_BY_2, activity, list, this.f1643d.mo1064r().m1363d(), m1664b(authorizationCallback));
        } else {
            authorizationCallback.onComplete(null, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: c */
    public String m1676c() {
        return "https://" + this.f1643d.mo1065s().m1330e() + "/term_chooser/faq";
    }

    /* JADX INFO: renamed from: d */
    public int m1677d() {
        return this.f1643d.mo1065s().m1342q();
    }

    /* JADX INFO: renamed from: e */
    public int m1678e() {
        return this.f1643d.mo1065s().m1343r();
    }

    /* JADX INFO: renamed from: f */
    public String m1679f() {
        return this.f1643d.mo1065s().m1340o();
    }

    /* JADX INFO: renamed from: g */
    public boolean m1680g() {
        return this.f1643d.mo1065s().m1312C();
    }

    /* JADX INFO: renamed from: h */
    public void m1681h() {
        this.f1643d.mo1064r().m1358a(null, null);
        this.f1643d.mo1050d().m1634c(this.f1641b);
        this.f1643d.mo1051e().m1718a(this.f1642c, true);
        this.f1643d.mo1067u().m1743a();
        this.f1643d.mo1049c().m1523d();
    }

    /* JADX INFO: renamed from: i */
    public String m1682i() {
        return this.f1643d.mo1065s().m1347v();
    }

    /* JADX INFO: renamed from: j */
    public String m1683j() {
        return AbstractC0880e.m1122a();
    }

    /* JADX INFO: renamed from: k */
    public void m1684k() {
        C0922e.m1243a();
    }

    /* JADX INFO: renamed from: l */
    public long m1685l() {
        return C0922e.m1247c();
    }

    /* JADX INFO: renamed from: m */
    public long m1686m() {
        return C0922e.m1248d();
    }

    /* JADX INFO: renamed from: n */
    public String m1687n() {
        try {
            return this.f1643d.mo1065s().m1316G().toString(2);
        } catch (JSONException unused) {
            throw new IllegalStateException("Capabilities is invalid JSON");
        }
    }
}
