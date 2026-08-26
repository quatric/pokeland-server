package com.nintendo.npf.sdk.internal.impl.cpp;

import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.impl.C1025o;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class BaaSUserLinkEventHandler implements BaaSUser.LinkNintendoAccountCallback {

    /* JADX INFO: renamed from: a */
    private long f1503a;

    /* JADX INFO: renamed from: b */
    private long f1504b;

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.cpp.BaaSUserLinkEventHandler$a */
    private static class C1006a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1505a = InterfaceC0875a.a.m1072b();
    }

    public BaaSUserLinkEventHandler() {
        this.f1503a = -1L;
        this.f1504b = -1L;
    }

    public BaaSUserLinkEventHandler(long j, long j2) {
        this.f1503a = -1L;
        this.f1504b = -1L;
        this.f1503a = j;
        this.f1504b = j2;
    }

    public static void linkNintendoAccount(long j, long j2, byte[] bArr) {
        try {
            NintendoAccount nintendoAccountM1673b = C1006a.f1505a.mo1048b().m1673b();
            if (!nintendoAccountM1673b.getNintendoAccountId().equals(new String(bArr))) {
                onLinkNintendoAccountCallback(j, j2, null, NativeBridgeUtil.toJsonFromNPFError(new C1025o(NPFError.ErrorType.INVALID_NA_TOKEN, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "Please use correct re-authorization information")).toString());
            }
            C1006a.f1505a.mo1050d().m1629a(C1006a.f1505a.mo1048b().m1665a(), nintendoAccountM1673b, new BaaSUserLinkEventHandler(j, j2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static native void onLinkNintendoAccountCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.user.BaaSUser.LinkNintendoAccountCallback
    public void onComplete(NPFError nPFError) {
        String str;
        String str2;
        String string;
        String string2 = null;
        try {
            if (nPFError != null) {
                string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
                string = null;
            } else {
                string = NativeBridgeUtil.toJsonFromNintendoAccount(C1006a.f1505a.mo1048b().m1665a().getNintendoAccount()).toString();
            }
            str = string;
            str2 = string2;
        } catch (JSONException e) {
            e.printStackTrace();
            str = string2;
            str2 = str;
        }
        onLinkNintendoAccountCallback(this.f1503a, this.f1504b, str, str2);
    }
}
