package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p021c.C0930b;
import com.nintendo.npf.sdk.internal.p022d.C0949d;
import com.nintendo.npf.sdk.internal.p023e.AbstractC0952b;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.Gender;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.h */
/* JADX INFO: compiled from: BaasUserImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1018h {

    /* JADX INFO: renamed from: a */
    private static final String f1588a = "h";

    /* JADX INFO: renamed from: b */
    private final AbstractC0952b<InterfaceC0875a> f1589b = InterfaceC0875a.a.m1070a();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.h$a */
    /* JADX INFO: compiled from: BaasUserImpl.java */
    private static class a {

        /* JADX INFO: renamed from: a */
        private static final C0930b f1607a = new C0930b();
    }

    /* JADX INFO: renamed from: b */
    private NintendoAccount.AuthorizationCallback m1624b(@NonNull BaaSUser baaSUser, @NonNull final BaaSUser.SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        final String str = baaSUser.userId;
        return new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.h.3
            @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
            public void onComplete(final NintendoAccount nintendoAccount, NPFError nPFError) {
                if (nPFError != null) {
                    switchByNintendoAccountCallback.onComplete(null, null, null, nPFError);
                } else {
                    C1017g.m1615a(nintendoAccount.idToken, nintendoAccount.sessionToken, new C1017g.a() { // from class: com.nintendo.npf.sdk.internal.impl.h.3.1
                        @Override // com.nintendo.npf.sdk.internal.impl.C1017g.a
                        /* JADX INFO: renamed from: a */
                        public void mo1237a(BaaSUser baaSUser2, String str2, NPFError nPFError2) {
                            if (nPFError2 != null) {
                                switchByNintendoAccountCallback.onComplete(null, null, nintendoAccount, nPFError2);
                                return;
                            }
                            ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1065s().m1327c(str2);
                            ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1051e().m1721b(nintendoAccount);
                            ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1067u().m1743a();
                            switchByNintendoAccountCallback.onComplete(str, baaSUser2.getUserId(), nintendoAccount, null);
                        }
                    });
                }
            }
        };
    }

    /* JADX INFO: renamed from: a */
    public long m1625a(@NonNull BaaSUser baaSUser) {
        return baaSUser.expiresTime;
    }

    /* JADX INFO: renamed from: a */
    public void m1626a(@NonNull BaaSUser baaSUser, Activity activity, List<String> list, @NonNull final BaaSUser.SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        C0955e.m1393b(f1588a, "switchByNintendoAccount is called");
        if (!this.f1589b.m1386c().mo1050d().m1633b(baaSUser)) {
            switchByNintendoAccountCallback.onComplete(null, null, null, C1025o.m1656a());
        } else {
            final String str = baaSUser.userId;
            this.f1589b.m1386c().mo1051e().m1714a(C0949d.c.SWITCH_BY, activity, list, null, new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.h.2
                @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
                public void onComplete(final NintendoAccount nintendoAccount, NPFError nPFError) {
                    if (nPFError != null) {
                        switchByNintendoAccountCallback.onComplete(null, null, null, nPFError);
                    } else {
                        C1017g.m1615a(nintendoAccount.idToken, nintendoAccount.sessionToken, new C1017g.a() { // from class: com.nintendo.npf.sdk.internal.impl.h.2.1
                            @Override // com.nintendo.npf.sdk.internal.impl.C1017g.a
                            /* JADX INFO: renamed from: a */
                            public void mo1237a(BaaSUser baaSUser2, String str2, NPFError nPFError2) {
                                if (nPFError2 != null) {
                                    switchByNintendoAccountCallback.onComplete(null, null, nintendoAccount, nPFError2);
                                    return;
                                }
                                ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1065s().m1327c(str2);
                                ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1051e().m1721b(nintendoAccount);
                                ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1067u().m1743a();
                                switchByNintendoAccountCallback.onComplete(str, baaSUser2.getUserId(), nintendoAccount, null);
                            }
                        });
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1627a(@NonNull final BaaSUser baaSUser, @NonNull final BaaSUser.SaveCallback saveCallback) {
        C0955e.m1393b(f1588a, "save is called");
        if (m1633b(baaSUser)) {
            C0905c.m1182c().mo1190a(baaSUser, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.h.4
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        saveCallback.onComplete(nPFError);
                        return;
                    }
                    try {
                        C1018h.this.m1631a(baaSUser, jSONObject, baaSUser.nintendoAccount);
                        saveCallback.onComplete(null);
                    } catch (JSONException e) {
                        saveCallback.onComplete(C1025o.m1658a(e));
                    }
                }
            });
        } else {
            saveCallback.onComplete(C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1628a(@NonNull BaaSUser baaSUser, @NonNull BaaSUser.SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        C0955e.m1393b(f1588a, "retryPendingSwitchByNintendoAccount2 is called");
        if (this.f1589b.m1386c().mo1050d().m1633b(baaSUser)) {
            this.f1589b.m1386c().mo1051e().m1715a(C0949d.c.SWITCH_BY_2, m1624b(baaSUser, switchByNintendoAccountCallback));
        } else {
            switchByNintendoAccountCallback.onComplete(null, null, null, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1629a(@NonNull final BaaSUser baaSUser, final NintendoAccount nintendoAccount, @NonNull final BaaSUser.LinkNintendoAccountCallback linkNintendoAccountCallback) {
        C0955e.m1393b(f1588a, "linkNintendoAccount is called");
        if (!m1633b(baaSUser)) {
            linkNintendoAccountCallback.onComplete(C1025o.m1656a());
            return;
        }
        if (baaSUser.getNintendoAccount() != null) {
            linkNintendoAccountCallback.onComplete(new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "Already linked with Nintendo Account"));
        } else if (nintendoAccount == null || TextUtils.isEmpty(nintendoAccount.idToken)) {
            linkNintendoAccountCallback.onComplete(new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "nintendoAccount parameter is invalid"));
        } else {
            C0905c.m1182c().mo1191a(baaSUser, nintendoAccount.idToken, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.h.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        if (nPFError.getErrorCode() == 409) {
                            ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1064r().m1357a(null);
                            ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1064r().m1361b(null, null);
                        }
                        linkNintendoAccountCallback.onComplete(nPFError);
                        return;
                    }
                    try {
                        C1018h.this.m1631a(baaSUser, jSONObject, nintendoAccount);
                        ((InterfaceC0875a) C1018h.this.f1589b.m1386c()).mo1051e().m1721b(nintendoAccount);
                        linkNintendoAccountCallback.onComplete(null);
                    } catch (JSONException e) {
                        linkNintendoAccountCallback.onComplete(C1025o.m1658a(e));
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1630a(@NonNull BaaSUser baaSUser, String str, String str2, String str3, long j) {
        baaSUser.deviceAccount = str;
        baaSUser.accessToken = str2;
        baaSUser.idToken = str3;
        baaSUser.expiresTime = j;
    }

    /* JADX INFO: renamed from: a */
    public void m1631a(@NonNull BaaSUser baaSUser, JSONObject jSONObject, NintendoAccount nintendoAccount) throws JSONException {
        BaaSUser baaSUserM1261a = a.f1607a.mo1260b(jSONObject);
        if (baaSUserM1261a != null) {
            baaSUser.userId = baaSUserM1261a.userId;
            baaSUser.nickname = baaSUserM1261a.nickname;
            baaSUser.country = baaSUserM1261a.country;
            baaSUser.gender = baaSUserM1261a.gender;
            baaSUser.birthdayDay = baaSUserM1261a.birthdayDay;
            baaSUser.birthdayMonth = baaSUserM1261a.birthdayMonth;
            baaSUser.birthdayYear = baaSUserM1261a.birthdayYear;
            baaSUser.personalAnalytics = baaSUserM1261a.personalAnalytics;
            baaSUser.personalNotification = baaSUserM1261a.personalNotification;
            baaSUser.personalAnalyticsUpdatedAt = baaSUserM1261a.personalAnalyticsUpdatedAt;
            baaSUser.personalNotificationUpdatedAt = baaSUserM1261a.personalNotificationUpdatedAt;
            baaSUser.inquiryStatus = baaSUserM1261a.inquiryStatus;
            baaSUser.createdAt = baaSUserM1261a.createdAt;
        }
        if (this.f1589b.m1386c().mo1048b().m1680g()) {
            baaSUser.devicePassword = this.f1589b.m1386c().mo1064r().m1359b();
        }
        baaSUser.nintendoAccount = nintendoAccount;
    }

    /* JADX INFO: renamed from: b */
    public void m1632b(@NonNull BaaSUser baaSUser, Activity activity, List<String> list, @NonNull BaaSUser.SwitchByNintendoAccountCallback switchByNintendoAccountCallback) {
        C0955e.m1393b(f1588a, "switchByNintendoAccount2 is called");
        if (this.f1589b.m1386c().mo1050d().m1633b(baaSUser)) {
            this.f1589b.m1386c().mo1051e().m1714a(C0949d.c.SWITCH_BY_2, activity, list, null, m1624b(baaSUser, switchByNintendoAccountCallback));
        } else {
            switchByNintendoAccountCallback.onComplete(null, null, null, C1025o.m1656a());
        }
    }

    /* JADX INFO: renamed from: b */
    public boolean m1633b(@NonNull BaaSUser baaSUser) {
        return !TextUtils.isEmpty(baaSUser.getUserId());
    }

    /* JADX INFO: renamed from: c */
    public void m1634c(@NonNull BaaSUser baaSUser) {
        baaSUser.userId = null;
        baaSUser.idToken = null;
        baaSUser.accessToken = null;
        baaSUser.deviceAccount = null;
        baaSUser.devicePassword = null;
        baaSUser.nickname = null;
        baaSUser.country = null;
        baaSUser.gender = Gender.UNKNOWN;
        baaSUser.birthdayYear = 0;
        baaSUser.birthdayMonth = 0;
        baaSUser.birthdayDay = 0;
        baaSUser.inquiryStatus = null;
        baaSUser.nintendoAccount = null;
        baaSUser.createdAt = 0L;
        baaSUser.personalAnalytics = false;
        baaSUser.personalNotification = false;
        baaSUser.personalAnalyticsUpdatedAt = 0L;
        baaSUser.personalNotificationUpdatedAt = 0L;
    }
}
