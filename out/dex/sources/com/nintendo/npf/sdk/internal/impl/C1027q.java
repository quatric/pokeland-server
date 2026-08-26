package com.nintendo.npf.sdk.internal.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import com.google.android.gms.common.Scopes;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.app.MiiStudioActivity;
import com.nintendo.npf.sdk.internal.app.NintendoAccountActivity;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p017b.p020c.C0927e;
import com.nintendo.npf.sdk.internal.p021c.C0936h;
import com.nintendo.npf.sdk.internal.p022d.C0947b;
import com.nintendo.npf.sdk.internal.p022d.C0949d;
import com.nintendo.npf.sdk.internal.p023e.AbstractC0952b;
import com.nintendo.npf.sdk.internal.p023e.C0951a;
import com.nintendo.npf.sdk.internal.p023e.C0954d;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.Gender;
import com.nintendo.npf.sdk.user.NintendoAccount;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyBundle;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.q */
/* JADX INFO: compiled from: NintendoAccountImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1027q {

    /* JADX INFO: renamed from: a */
    private static final String f1649a = "q";

    /* JADX INFO: renamed from: b */
    private transient NintendoAccount.AuthorizationCallback f1650b;

    /* JADX INFO: renamed from: c */
    private transient String f1651c;

    /* JADX INFO: renamed from: d */
    private transient String f1652d;

    /* JADX INFO: renamed from: e */
    private C0949d f1653e;

    /* JADX INFO: renamed from: f */
    private NPFSDK.NPFErrorCallback f1654f;

    /* JADX INFO: renamed from: g */
    private final AbstractC0952b<InterfaceC0875a> f1655g = InterfaceC0875a.a.m1070a();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.q$5, reason: invalid class name */
    /* JADX INFO: compiled from: NintendoAccountImpl.java */
    static /* synthetic */ class AnonymousClass5 {

        /* JADX INFO: renamed from: a */
        static final /* synthetic */ int[] f1666a;

        /* JADX INFO: renamed from: b */
        static final /* synthetic */ int[] f1667b = new int[C0949d.d.values().length];

        static {
            try {
                f1667b[C0949d.d.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1667b[C0949d.d.CALLER_ACTIVITY_IS_DEAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1667b[C0949d.d.PROCESS_RESTARTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            f1666a = new int[C0949d.c.values().length];
            try {
                f1666a[C0949d.c.SWITCH_BY.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1666a[C0949d.c.AUTHORIZE_BY.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1666a[C0949d.c.AUTHORIZE_BY_2.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1666a[C0949d.c.SWITCH_BY_2.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.q$a */
    /* JADX INFO: compiled from: NintendoAccountImpl.java */
    private static class a {

        /* JADX INFO: renamed from: a */
        private static final C0936h f1668a = new C0936h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public NPFError m1689a(JSONObject jSONObject) throws JSONException {
        if (C0951a.m1385a(jSONObject, "termsAgreement")) {
            return new C1025o(NPFError.ErrorType.NA_EULA_UPDATE, -1, "Needs to re-authorize to update EULA");
        }
        if (!C0951a.m1385a(jSONObject, "error")) {
            NintendoAccount nintendoAccountM1673b = this.f1655g.m1386c().mo1048b().m1673b();
            if (nintendoAccountM1673b == null) {
                return null;
            }
            m1697a(nintendoAccountM1673b, jSONObject);
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("error");
        int i = jSONObject2.getInt("errorCode");
        JSONObject jSONObject3 = jSONObject2.getJSONObject("errorMessage");
        NPFError.ErrorType errorType = NPFError.ErrorType.INVALID_NA_TOKEN;
        if (C0951a.m1385a(jSONObject3, "user_status")) {
            String string = jSONObject3.getString("user_status");
            if (string.equals("banned") || string.equals("deleted") || string.equals("suspended") || string.equals("withdrawn")) {
                errorType = NPFError.ErrorType.INVALID_NA_USER;
            }
        }
        return new C1025o(errorType, i, jSONObject3.toString());
    }

    /* JADX INFO: renamed from: a */
    private String m1691a(String str) throws NoSuchAlgorithmException {
        return Base64.encodeToString(MessageDigest.getInstance("SHA-256").digest(str.getBytes()), 27);
    }

    /* JADX INFO: renamed from: a */
    private String m1692a(List<String> list, String str, String str2, String str3) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException {
        C0947b c0947bMo1065s = this.f1655g.m1386c().mo1065s();
        StringBuilder sb = new StringBuilder();
        String str4 = "";
        sb.append("");
        sb.append("state=");
        sb.append(URLEncoder.encode(str, "UTF-8"));
        String str5 = ((sb.toString() + "&redirect_uri=" + URLEncoder.encode("npf" + c0947bMo1065s.m1329d() + "://auth", "UTF-8")) + "&client_id=" + URLEncoder.encode(c0947bMo1065s.m1329d(), "UTF-8")) + "&lang=" + URLEncoder.encode(this.f1655g.m1386c().mo1048b().m1682i(), "UTF-8");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                str4 = str4 + " ";
            }
            str4 = str4 + list.get(i);
        }
        String str6 = (((str5 + "&scope=" + URLEncoder.encode(str4, "UTF-8")) + "&response_type=" + URLEncoder.encode("session_token_code", "UTF-8")) + "&session_token_code_challenge=" + URLEncoder.encode(m1691a(str2), "UTF-8")) + "&session_token_code_challenge_method=S256";
        if (str3 == null || str3.isEmpty()) {
            return str6;
        }
        return str6 + "&prompt=login&id_token_hint=" + str3;
    }

    /* JADX INFO: renamed from: a */
    private void m1693a(Activity activity, List<String> list, String str) {
        try {
            this.f1651c = C0949d.m1365a(50);
            this.f1652d = C0949d.m1365a(50);
            Intent intent = new Intent(activity, (Class<?>) NintendoAccountActivity.class);
            intent.putExtra("requestCode", 342);
            intent.putExtra("queryParameter", m1692a(list, this.f1651c, this.f1652d, str));
            this.f1655g.m1386c().mo1049c().m1518a(true);
            activity.startActivity(intent);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1694a(Activity activity, List<String> list, String str, @NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        if (this.f1650b != null) {
            authorizationCallback.onComplete(null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "Nintendo authorization can't run multiply"));
            return;
        }
        this.f1650b = authorizationCallback;
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            arrayList.addAll(list);
        }
        if (!arrayList.contains(Scopes.OPEN_ID)) {
            arrayList.add(Scopes.OPEN_ID);
        }
        m1693a(activity, arrayList, str);
    }

    /* JADX INFO: renamed from: a */
    private void m1695a(@NonNull C0949d c0949d, Activity activity, List<String> list, String str) {
        try {
            Intent intent = new Intent(activity, (Class<?>) NintendoAccountActivity.class);
            intent.putExtra("requestCode", 343);
            intent.putExtra("queryParameter", m1692a(list, c0949d.f1266b, c0949d.f1267c, str));
            this.f1655g.m1386c().mo1049c().m1518a(true);
            activity.startActivity(intent);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | JSONException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1697a(NintendoAccount nintendoAccount, JSONObject jSONObject) throws JSONException {
        NintendoAccount nintendoAccountM1270a = a.f1668a.mo1260b(jSONObject);
        if (nintendoAccountM1270a != null) {
            nintendoAccount.nintendoAccountId = nintendoAccountM1270a.nintendoAccountId;
            nintendoAccount.type = nintendoAccountM1270a.type;
            nintendoAccount.nickname = nintendoAccountM1270a.nickname;
            nintendoAccount.gender = nintendoAccountM1270a.gender;
            nintendoAccount.language = nintendoAccountM1270a.language;
            nintendoAccount.country = nintendoAccountM1270a.country;
            nintendoAccount.region = nintendoAccountM1270a.region;
            nintendoAccount.timezone = nintendoAccountM1270a.timezone;
            nintendoAccount.birthdayYear = nintendoAccountM1270a.birthdayYear;
            nintendoAccount.birthdayMonth = nintendoAccountM1270a.birthdayMonth;
            nintendoAccount.birthdayDay = nintendoAccountM1270a.birthdayDay;
            nintendoAccount.email = nintendoAccountM1270a.email;
            nintendoAccount.nintendoNetworkId = nintendoAccountM1270a.nintendoNetworkId;
            nintendoAccount.mii = nintendoAccountM1270a.mii;
            nintendoAccount.idToken = nintendoAccountM1270a.idToken;
            nintendoAccount.accessToken = nintendoAccountM1270a.accessToken;
            nintendoAccount.f1812a = nintendoAccountM1270a.f1812a;
        }
        C0955e.m1391a(f1649a, "NA expiresTime: " + nintendoAccount.f1812a);
    }

    /* JADX INFO: renamed from: a */
    private void m1698a(String str, String str2) {
        C0955e.m1391a(f1649a, "sessionTokenCode : " + str);
        C0927e.m1254a().mo1253a(str, str2, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.q.1
            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
            /* JADX INFO: renamed from: a */
            public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                if (nPFError != null) {
                    C1027q.this.m1703b((NintendoAccount) null, nPFError);
                    return;
                }
                try {
                    String string = jSONObject.getString("session_token");
                    NintendoAccount nintendoAccountM1673b = ((InterfaceC0875a) C1027q.this.f1655g.m1386c()).mo1048b().m1673b();
                    nintendoAccountM1673b.sessionToken = string;
                    C1027q.this.m1721b(nintendoAccountM1673b);
                    C1027q.this.m1720a(false, string, new NintendoAccount.AuthorizationCallback() { // from class: com.nintendo.npf.sdk.internal.impl.q.1.1
                        @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
                        public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError2) {
                            C1027q.this.m1703b(nintendoAccount, nPFError2);
                        }
                    });
                } catch (JSONException e) {
                    C1027q.this.m1703b((NintendoAccount) null, C1025o.m1658a(e));
                }
            }
        });
    }

    /* JADX INFO: renamed from: a */
    private void m1699a(String str, String str2, NPFError nPFError) {
        m1706c(null, nPFError);
        C0954d.m1389b("naauth_error", str, new C1025o(nPFError.getErrorType(), nPFError.getErrorCode(), str2));
    }

    /* JADX INFO: renamed from: b */
    private NPFError m1700b(C0949d.b bVar) {
        if (bVar.f1276a == null || bVar.f1276a.isEmpty()) {
            return new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled for authorization");
        }
        String str = bVar.f1277b;
        String str2 = this.f1653e.f1266b;
        if (str == null || str.equals(str2)) {
            return null;
        }
        return new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled for authorization");
    }

    /* JADX INFO: renamed from: b */
    private void m1701b(C0949d.c cVar, Activity activity, List<String> list, String str, @NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        C0949d c0949d = this.f1653e;
        if (c0949d != null && !c0949d.m1374b()) {
            authorizationCallback.onComplete(null, new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "Nintendo authorization can't run multiply"));
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            arrayList.addAll(list);
        }
        if (!arrayList.contains(Scopes.OPEN_ID)) {
            arrayList.add(Scopes.OPEN_ID);
        }
        this.f1653e = new C0949d(cVar);
        this.f1653e.m1368a(activity, authorizationCallback);
        m1695a(this.f1653e, activity, arrayList, str);
    }

    /* JADX INFO: renamed from: b */
    private void m1702b(@NonNull C0949d c0949d) {
        NPFError nPFErrorM1380h = c0949d.m1380h();
        if (nPFErrorM1380h != null) {
            m1699a("NAAuth#OtherError2", nPFErrorM1380h.getErrorMessage(), nPFErrorM1380h);
            return;
        }
        C0949d.b bVarM1379g = c0949d.m1379g();
        NPFError nPFErrorM1700b = m1700b(bVarM1379g);
        if (bVarM1379g.f1276a == null || bVarM1379g.f1276a.isEmpty()) {
            m1699a("NAAuth#EmptySessionTokenCode2", "Session token code is empty", nPFErrorM1700b);
            return;
        }
        String str = bVarM1379g.f1277b;
        String str2 = c0949d.f1266b;
        if (str == null || str.equals(str2)) {
            return;
        }
        m1699a("NAAuth#InvalidState2", "state:" + str + " this.state:" + str2, nPFErrorM1700b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m1703b(NintendoAccount nintendoAccount, NPFError nPFError) {
        if (this.f1653e == null) {
            m1717a(nintendoAccount, nPFError);
        } else {
            m1706c(nintendoAccount, nPFError);
        }
    }

    /* JADX INFO: renamed from: b */
    private void m1704b(final String str, final String str2) {
        new Handler().postDelayed(new Runnable() { // from class: com.nintendo.npf.sdk.internal.impl.q.2
            @Override // java.lang.Runnable
            public void run() {
                C0954d.m1389b("naauth_error", str, new C1025o(NPFError.ErrorType.USER_CANCEL, -1, str2));
                C1027q.this.m1703b((NintendoAccount) null, new C1025o(NPFError.ErrorType.USER_CANCEL, -1, "User canceled for authorization"));
            }
        }, 1000L);
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1705b(NPFError nPFError) {
        return nPFError == null || nPFError.getErrorType() == NPFError.ErrorType.INVALID_NA_TOKEN || nPFError.getErrorType() == NPFError.ErrorType.NA_EULA_UPDATE || nPFError.getErrorType() == NPFError.ErrorType.INVALID_NA_USER;
    }

    /* JADX INFO: renamed from: c */
    private void m1706c(NintendoAccount nintendoAccount, NPFError nPFError) {
        if (this.f1655g.m1386c().mo1049c() != null) {
            this.f1655g.m1386c().mo1049c().m1518a(false);
        }
        this.f1653e.m1372a(nintendoAccount, nPFError);
        this.f1653e = null;
    }

    /* JADX INFO: renamed from: c */
    private boolean m1707c(NintendoAccount nintendoAccount) {
        return (nintendoAccount == null || TextUtils.isEmpty(nintendoAccount.getNintendoAccountId())) ? false : true;
    }

    /* JADX INFO: renamed from: a */
    public long m1708a(NintendoAccount nintendoAccount) {
        return nintendoAccount.f1812a;
    }

    /* JADX INFO: renamed from: a */
    public C0949d m1709a() {
        return this.f1653e;
    }

    /* JADX INFO: renamed from: a */
    public void m1710a(@NonNull Activity activity, @NonNull NPFSDK.NPFErrorCallback nPFErrorCallback) {
        NintendoAccount nintendoAccountM1673b = this.f1655g.m1386c().mo1048b().m1673b();
        if (!m1707c(nintendoAccountM1673b)) {
            NPFError nPFErrorM1660c = C1025o.m1660c();
            C0954d.m1389b("mii_studio_error", "MiiStudio#NintendoAccountNotAuthorized", nPFErrorM1660c);
            nPFErrorCallback.onComplete(nPFErrorM1660c);
        } else {
            if (this.f1654f != null) {
                nPFErrorCallback.onComplete(new C1025o(NPFError.ErrorType.PROCESS_CANCEL, -1, "openMiiStudio can't run multiply"));
                return;
            }
            this.f1654f = nPFErrorCallback;
            this.f1655g.m1386c().mo1049c().m1520b().m1737a();
            Intent intent = new Intent(activity, (Class<?>) MiiStudioActivity.class);
            intent.putExtra("requestCode", 452);
            intent.putExtra("naIdToken", nintendoAccountM1673b.idToken);
            activity.startActivity(intent);
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0055  */
    /* JADX INFO: renamed from: a */
    public void m1711a(Uri uri) {
        String str;
        String str2 = null;
        if (uri != null) {
            try {
                String fragment = uri.getFragment();
                if (fragment == null || fragment.isEmpty()) {
                    str = null;
                } else {
                    String str3 = null;
                    str = null;
                    for (String str4 : fragment.split("&")) {
                        String[] strArrSplit = str4.split("=");
                        String strDecode = URLDecoder.decode(strArrSplit[0], "UTF-8");
                        String strDecode2 = URLDecoder.decode(strArrSplit[1], "UTF-8");
                        if (strDecode.equals("state")) {
                            str3 = strDecode2;
                        } else if (strDecode.equals("session_token_code")) {
                            str = strDecode2;
                        }
                    }
                    str2 = str3;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
        } else {
            str = null;
        }
        C0955e.m1391a(f1649a, "state : " + str2);
        C0955e.m1391a(f1649a, "sessionTokenCode : " + str);
        if (str == null || str.isEmpty()) {
            m1704b("NAAuth#EmptySessionTokenCode", "Session token code is empty");
            return;
        }
        if (str2 != null && !str2.equals(this.f1651c)) {
            m1704b("NAAuth#InvalidState", "state:" + str2 + " this.state:" + this.f1651c);
        }
        m1698a(str, this.f1652d);
    }

    /* JADX INFO: renamed from: a */
    public void m1712a(@NonNull NPFError nPFError) {
        C0949d c0949d = this.f1653e;
        if (c0949d == null) {
            C0954d.m1389b("naauth_error", "NAAuth#SessionHasGone#registerSessionTokenCode2", nPFError);
            return;
        }
        if (!c0949d.m1377e()) {
            C0954d.m1389b("naauth_error", "NAAuth#InvalidSession#registerError2", nPFError);
            return;
        }
        this.f1653e.m1369a(nPFError);
        C0955e.m1393b(f1649a, "NintendoAccountAuthSession#getSystemState(): " + this.f1653e.m1378f());
        int i = AnonymousClass5.f1667b[this.f1653e.m1378f().ordinal()];
        if (i != 2 && i != 3) {
            m1702b(this.f1653e);
        } else {
            this.f1653e.m1367a();
            C1017g.b.m1621a(this.f1653e.f1265a);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1713a(@NonNull C0949d.b bVar) {
        C0949d c0949d = this.f1653e;
        if (c0949d == null) {
            C0954d.m1389b("naauth_error", "NAAuth#SessionHasGone#registerSessionTokenCode2", new C1025o(NPFError.ErrorType.NPF_ERROR, -1, "registerSessionTokenCode2() was canceled."));
            return;
        }
        if (!c0949d.m1377e()) {
            C0954d.m1389b("naauth_error", "NAAuth#InvalidSession#registerSessionTokenCode2", new C1025o(NPFError.ErrorType.NPF_ERROR, -1, "registerSessionTokenCode2() was canceled."));
            return;
        }
        this.f1653e.m1370a(bVar);
        C0955e.m1393b(f1649a, "NintendoAccountAuthSession#getSystemState(): " + this.f1653e.m1378f());
        int i = AnonymousClass5.f1667b[this.f1653e.m1378f().ordinal()];
        if (i == 2 || i == 3) {
            this.f1653e.m1367a();
            C1017g.b.m1621a(this.f1653e.f1265a);
        } else if (m1700b(bVar) == null) {
            m1698a(this.f1653e.m1379g().f1276a, this.f1653e.f1267c);
        } else {
            m1702b(this.f1653e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1714a(C0949d.c cVar, Activity activity, List<String> list, String str, @NonNull NintendoAccount.AuthorizationCallback authorizationCallback) {
        int i = AnonymousClass5.f1666a[cVar.ordinal()];
        if (i == 1 || i == 2) {
            m1694a(activity, list, str, authorizationCallback);
        } else if (i == 3 || i == 4) {
            m1701b(cVar, activity, list, str, authorizationCallback);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1715a(C0949d.c cVar, NintendoAccount.AuthorizationCallback authorizationCallback) {
        C0949d c0949d = this.f1653e;
        if (c0949d == null || !c0949d.m1373a(cVar)) {
            authorizationCallback.onComplete(null, new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_CONFLICT, "Illegal state was detected."));
            return;
        }
        this.f1653e.m1371a(cVar, authorizationCallback);
        if (this.f1653e.m1380h() == null && m1700b(this.f1653e.m1379g()) == null) {
            m1698a(this.f1653e.m1379g().f1276a, this.f1653e.f1267c);
        } else {
            m1702b(this.f1653e);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1716a(C0949d c0949d) {
        if (this.f1653e == null) {
            this.f1653e = c0949d;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1717a(NintendoAccount nintendoAccount, NPFError nPFError) {
        this.f1655g.m1386c().mo1049c().m1518a(false);
        NintendoAccount.AuthorizationCallback authorizationCallback = this.f1650b;
        if (authorizationCallback != null) {
            authorizationCallback.onComplete(nintendoAccount, nPFError);
            this.f1650b = null;
        }
        this.f1652d = null;
        this.f1651c = null;
    }

    /* JADX INFO: renamed from: a */
    public void m1718a(NintendoAccount nintendoAccount, boolean z) {
        nintendoAccount.nintendoAccountId = null;
        nintendoAccount.type = NintendoAccount.Type.UNKNOWN;
        nintendoAccount.nickname = null;
        nintendoAccount.gender = Gender.UNKNOWN;
        nintendoAccount.language = null;
        nintendoAccount.country = null;
        nintendoAccount.region = null;
        nintendoAccount.timezone = null;
        nintendoAccount.birthdayYear = 0;
        nintendoAccount.birthdayMonth = 0;
        nintendoAccount.birthdayDay = 0;
        nintendoAccount.email = null;
        nintendoAccount.nintendoNetworkId = null;
        nintendoAccount.mii = null;
        nintendoAccount.idToken = null;
        nintendoAccount.accessToken = null;
        if (z) {
            nintendoAccount.sessionToken = null;
            this.f1655g.m1386c().mo1064r().m1357a(null);
            this.f1655g.m1386c().mo1064r().m1361b(null, null);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1719a(VirtualCurrencyBundle virtualCurrencyBundle) {
        C0955e.m1393b(f1649a, "Send purchase email!");
        BaaSUser baaSUserM1665a = this.f1655g.m1386c().mo1048b().m1665a();
        if (this.f1655g.m1386c().mo1050d().m1633b(baaSUserM1665a)) {
            NintendoAccount nintendoAccount = baaSUserM1665a.getNintendoAccount();
            if (m1707c(nintendoAccount)) {
                Application applicationMo1047a = this.f1655g.m1386c().mo1047a();
                int identifier = applicationMo1047a.getResources().getIdentifier("app_name", "string", applicationMo1047a.getPackageName());
                C0927e.m1254a().mo1252a(nintendoAccount, identifier != 0 ? applicationMo1047a.getResources().getString(identifier) : "", this.f1655g.m1386c().mo1048b().m1683j(), virtualCurrencyBundle.getTitle(), virtualCurrencyBundle.getDisplayPrice(), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.q.4
                    @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                    /* JADX INFO: renamed from: a */
                    public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: a */
    void m1720a(final boolean z, final String str, final NintendoAccount.AuthorizationCallback authorizationCallback) {
        C0955e.m1393b(f1649a, "executeTokenEndpoint is called");
        C0927e.m1255b().mo1251a(str, new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.q.3
            @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
            /* JADX INFO: renamed from: a */
            public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                if (nPFError != null) {
                    authorizationCallback.onComplete(null, (nPFError.getErrorCode() == 400 && TextUtils.isEmpty(str)) ? new C1025o(NPFError.ErrorType.INVALID_NA_TOKEN, nPFError.getErrorCode(), nPFError.getErrorMessage()) : nPFError);
                    return;
                }
                try {
                    NPFError nPFErrorM1689a = C1027q.this.m1689a(jSONObject);
                    if (nPFErrorM1689a != null) {
                        C1027q c1027q = C1027q.this;
                        c1027q.m1718a(((InterfaceC0875a) c1027q.f1655g.m1386c()).mo1048b().m1673b(), false);
                        authorizationCallback.onComplete(((InterfaceC0875a) C1027q.this.f1655g.m1386c()).mo1048b().m1673b(), nPFErrorM1689a);
                    } else {
                        if (z) {
                            ((InterfaceC0875a) C1027q.this.f1655g.m1386c()).mo1064r().m1361b(((InterfaceC0875a) C1027q.this.f1655g.m1386c()).mo1048b().m1673b().idToken, ((InterfaceC0875a) C1027q.this.f1655g.m1386c()).mo1048b().m1673b().nintendoAccountId);
                        }
                        authorizationCallback.onComplete(((InterfaceC0875a) C1027q.this.f1655g.m1386c()).mo1048b().m1673b(), null);
                    }
                } catch (JSONException e) {
                    authorizationCallback.onComplete(null, C1025o.m1658a(e));
                }
            }
        });
    }

    /* JADX INFO: renamed from: b */
    public void m1721b(NintendoAccount nintendoAccount) {
        this.f1655g.m1386c().mo1064r().m1357a(nintendoAccount.sessionToken);
        this.f1655g.m1386c().mo1064r().m1361b(nintendoAccount.idToken, nintendoAccount.nintendoAccountId);
    }

    /* JADX INFO: renamed from: b */
    public boolean m1722b() {
        C0949d c0949d = this.f1653e;
        return c0949d != null && c0949d.m1379g() == null;
    }

    /* JADX INFO: renamed from: c */
    public NintendoAccount.AuthorizationCallback m1723c() {
        return this.f1650b;
    }

    /* JADX INFO: renamed from: c */
    public void m1724c(NPFError nPFError) {
        if (this.f1654f != null) {
            this.f1655g.m1386c().mo1049c().m1520b().m1738a(3000L);
            NPFSDK.NPFErrorCallback nPFErrorCallback = this.f1654f;
            this.f1654f = null;
            nPFErrorCallback.onComplete(nPFError);
        }
    }
}
