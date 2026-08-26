package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p016a.AbstractC0880e;
import com.nintendo.npf.sdk.internal.p017b.p018a.C0905c;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0921d;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p022d.C0948c;
import com.nintendo.npf.sdk.internal.p022d.C0949d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.internal.p023e.C0956f;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.g */
/* JADX INFO: compiled from: BaaSAuth.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1017g {

    /* JADX INFO: renamed from: a */
    private static final String f1573a = "g";

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.g$3, reason: invalid class name */
    /* JADX INFO: compiled from: BaaSAuth.java */
    static /* synthetic */ class AnonymousClass3 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f1583a = new int[C0949d.c.values().length];

        static {
            try {
                f1583a[C0949d.c.AUTHORIZE_BY_2.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1583a[C0949d.c.SWITCH_BY_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.g$a */
    /* JADX INFO: compiled from: BaaSAuth.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1237a(BaaSUser baaSUser, String str, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.g$b */
    /* JADX INFO: compiled from: BaaSAuth.java */
    public static class b {

        /* JADX INFO: renamed from: a */
        private static boolean f1584a;

        /* JADX INFO: renamed from: b */
        private static boolean f1585b;

        /* JADX INFO: renamed from: c */
        private static C0949d.c f1586c;

        /* JADX INFO: renamed from: a */
        private static void m1620a() {
            f1585b = false;
            int i = AnonymousClass3.f1583a[f1586c.ordinal()];
            if (i == 1) {
                c.f1587a.mo1049c().m1522c().onPendingAuthorizationByNintendoAccount2();
            } else {
                if (i != 2) {
                    return;
                }
                c.f1587a.mo1049c().m1522c().onPendingSwitchByNintendoAccount2();
            }
        }

        /* JADX INFO: renamed from: a */
        public static void m1621a(@NonNull C0949d.c cVar) {
            f1585b = true;
            f1586c = cVar;
            Log.d(C1017g.f1573a, "notifyPendingSessionWhenBaaSAuthUpdated: isBaaSAuthUpdated: " + f1584a);
            if (f1584a) {
                m1620a();
            }
        }

        /* JADX INFO: renamed from: a */
        public static void m1622a(BaaSUser baaSUser) {
            f1584a = true;
            c.f1587a.mo1049c().m1522c().onBaaSAuthUpdate(baaSUser);
            Log.d(C1017g.f1573a, "notifyBaaSAuthUpdated: hasPendingSession: " + f1585b);
            if (f1585b) {
                m1620a();
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.g$c */
    /* JADX INFO: compiled from: BaaSAuth.java */
    private static class c {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1587a = InterfaceC0875a.a.m1072b();
    }

    /* JADX INFO: renamed from: a */
    private static JSONObject m1612a(String str) {
        try {
            C0947b c0947bMo1065s = c.f1587a.mo1065s();
            JSONObject jSONObjectM1351z = c0947bMo1065s.m1351z();
            String str2 = c0947bMo1065s.m1337l() + ":" + c0947bMo1065s.m1338m();
            String strM1397a = C0956f.m1397a(str2.getBytes(), 600, 8, "HmacSHA1");
            C0955e.m1391a(f1573a, "Key : " + str2);
            C0955e.m1391a(f1573a, "Secret : " + strM1397a);
            jSONObjectM1351z.put("assertion", C0921d.m1242a(c0947bMo1065s.m1317a(), strM1397a, str2));
            C0948c c0948cMo1064r = c.f1587a.mo1064r();
            if (c0948cMo1064r.m1356a() != null && !c0948cMo1064r.m1356a().isEmpty() && c0948cMo1064r.m1359b() != null && !c0948cMo1064r.m1359b().isEmpty()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", c0948cMo1064r.m1356a());
                jSONObject.put("password", c0948cMo1064r.m1359b());
                jSONObjectM1351z.put("deviceAccount", jSONObject);
            }
            JSONObject jSONObject2 = new JSONObject();
            if (str != null) {
                jSONObject2.put("idp", "nintendoAccount");
                jSONObject2.put("idToken", str);
                jSONObjectM1351z.put("idpAccount", jSONObject2);
                jSONObjectM1351z.put("previousUserId", InterfaceC0875a.a.m1072b().mo1048b().m1665a().getUserId());
            }
            return jSONObjectM1351z;
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1615a(final String str, final String str2, @NonNull final a aVar) {
        C0955e.m1393b(f1573a, "executeBaaSAuth is called");
        JSONObject jSONObjectM1612a = m1612a(str);
        C0918a.b bVar = new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.g.1
            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
            /* JADX INFO: renamed from: a */
            public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                if (nPFError != null) {
                    C1017g.m1617b(str, nPFError, aVar);
                    return;
                }
                try {
                    C1017g.m1619b(jSONObject, str, str2, aVar);
                } catch (JSONException e) {
                    C1017g.m1617b(str, C1025o.m1658a(e), aVar);
                }
            }
        };
        if (str != null) {
            C0905c.m1182c().mo1194b(jSONObjectM1612a, bVar);
        } else {
            c.f1587a.mo1049c().m1522c().onBaaSAuthStart();
            C0905c.m1182c().mo1193a(jSONObjectM1612a, bVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static void m1617b(String str, NPFError nPFError, a aVar) {
        if (str == null) {
            c.f1587a.mo1049c().m1522c().onBaaSAuthError(nPFError);
        }
        aVar.mo1237a(null, null, nPFError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static void m1618b(String str, BaaSUser baaSUser, String str2, a aVar) {
        if (str == null) {
            b.m1622a(baaSUser);
        }
        aVar.mo1237a(baaSUser, str2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static void m1619b(JSONObject jSONObject, final String str, String str2, final a aVar) throws JSONException {
        if (jSONObject.has("error") && !jSONObject.isNull("error")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("error");
            NPFError.ErrorType errorType = NPFError.ErrorType.NPF_ERROR;
            String string = jSONObject2.getJSONObject("errorMessage").toString();
            int i = jSONObject2.getInt("errorCode");
            if (i == -1) {
                errorType = NPFError.ErrorType.PROCESS_CANCEL;
            }
            m1617b(str, new C1025o(errorType, i, string), aVar);
            return;
        }
        C1026p c1026pMo1048b = InterfaceC0875a.a.m1072b().mo1048b();
        final JSONObject jSONObject3 = jSONObject.getJSONObject("user");
        String string2 = jSONObject3.getString("id");
        if (c1026pMo1048b.m1665a().getUserId() != null && !c1026pMo1048b.m1665a().getUserId().isEmpty() && !string2.equals(c1026pMo1048b.m1665a().getUserId()) && str == null) {
            C0955e.m1395c(f1573a, "Cancel user update for old response data");
            BaaSUser baaSUserM1665a = c.f1587a.mo1048b().m1665a();
            b.m1622a(baaSUserM1665a);
            aVar.mo1237a(baaSUserM1665a, c.f1587a.mo1065s().m1310A(), null);
            return;
        }
        String string3 = (!jSONObject.has("sessionId") || jSONObject.isNull("sessionId")) ? null : jSONObject.getString("sessionId");
        C0948c c0948cMo1064r = c.f1587a.mo1064r();
        if (jSONObject.has("createdDeviceAccount") && !jSONObject.isNull("createdDeviceAccount")) {
            JSONObject jSONObject4 = jSONObject.getJSONObject("createdDeviceAccount");
            c0948cMo1064r.m1358a(jSONObject4.getString("id"), jSONObject4.getString("password"));
        }
        String string4 = jSONObject.getString("accessToken");
        String string5 = jSONObject.getString("idToken");
        int i2 = jSONObject.getInt("expiresIn");
        C0947b c0947bMo1065s = c.f1587a.mo1065s();
        if (c0947bMo1065s.m1312C() && c0947bMo1065s.m1313D() != null) {
            AbstractC0880e.m1126a(c0947bMo1065s.m1313D());
        } else if (jSONObject.has("market") && !jSONObject.isNull("market")) {
            AbstractC0880e.m1126a(jSONObject.getString("market"));
        }
        if (jSONObject.has("capability")) {
            c0947bMo1065s.m1322a(jSONObject.getJSONObject("capability"));
        }
        long timeInMillis = ((long) (i2 * 1000)) + Calendar.getInstance().getTimeInMillis();
        final BaaSUser baaSUserM1665a2 = c.f1587a.mo1048b().m1665a();
        c.f1587a.mo1050d().m1630a(baaSUserM1665a2, c0948cMo1064r.m1356a(), string4, string5, timeInMillis);
        JSONObject jSONObject5 = jSONObject3.getJSONObject("links");
        if (!jSONObject5.has("nintendoAccount")) {
            c.f1587a.mo1050d().m1631a(baaSUserM1665a2, jSONObject3, (NintendoAccount) null);
            m1618b(str, baaSUserM1665a2, string3, aVar);
        } else {
            final String string6 = jSONObject5.getJSONObject("nintendoAccount").getString("id");
            c0948cMo1064r.m1360b(string6);
            final String str3 = string3;
            c.f1587a.mo1051e().m1720a(true, str2 != null ? str2 : c0948cMo1064r.m1362c(), new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.g.2
                @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
                public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
                    try {
                        if (nPFError != null) {
                            c.f1587a.mo1050d().m1631a(baaSUserM1665a2, jSONObject3, nintendoAccount);
                            if (str == null) {
                                c.f1587a.mo1049c().m1522c().onBaaSAuthUpdate(baaSUserM1665a2);
                                c.f1587a.mo1049c().m1522c().onNintendoAccountAuthError(nPFError);
                            }
                            aVar.mo1237a(baaSUserM1665a2, str3, nPFError);
                            return;
                        }
                        if (nintendoAccount.getNintendoAccountId().equals(string6)) {
                            c.f1587a.mo1050d().m1631a(baaSUserM1665a2, jSONObject3, nintendoAccount);
                            C1017g.m1618b(str, baaSUserM1665a2, str3, aVar);
                        } else {
                            C1017g.m1617b(str, new C1025o(NPFError.ErrorType.MISMATCHED_NA_USER, HttpStatusCodes.STATUS_CODE_CONFLICT, "Linked Nintendo Account is different from session token's Nintendo Account."), aVar);
                        }
                    } catch (JSONException e) {
                        C1017g.m1617b(str, C1025o.m1658a(e), aVar);
                    }
                }
            });
        }
    }
}
