package com.nintendo.npf.sdk.internal.impl;

import android.support.annotation.NonNull;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import com.nintendo.npf.sdk.internal.p017b.p019b.C0918a;
import com.nintendo.npf.sdk.internal.p017b.p020c.C0927e;
import com.nintendo.npf.sdk.internal.p021c.C0935g;
import com.nintendo.npf.sdk.internal.p023e.C0955e;
import com.nintendo.npf.sdk.mynintendo.MissionStatus;
import com.nintendo.npf.sdk.mynintendo.PointProgramService;
import com.nintendo.npf.sdk.user.BaaSUser;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.n */
/* JADX INFO: compiled from: MissionStatusImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1024n {

    /* JADX INFO: renamed from: a */
    private static final String f1633a = "n";

    /* JADX INFO: renamed from: b */
    private final C0935g f1634b = new C0935g();

    /* JADX INFO: renamed from: c */
    private final InterfaceC0875a f1635c = InterfaceC0875a.a.m1072b();

    /* JADX INFO: renamed from: a */
    public void m1654a(@NonNull final MissionStatus.RetrievingCallback retrievingCallback) {
        C0955e.m1393b(f1633a, "getAll is called");
        BaaSUser baaSUserM1665a = this.f1635c.mo1048b().m1665a();
        if (!this.f1635c.mo1050d().m1633b(baaSUserM1665a)) {
            retrievingCallback.onComplete(null, C1025o.m1656a());
            return;
        }
        NintendoAccount nintendoAccount = baaSUserM1665a.getNintendoAccount();
        if (nintendoAccount == null) {
            retrievingCallback.onComplete(null, new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "Current BaaS User doesn't link with Nintendo Account."));
        } else if (nintendoAccount.getCountry() == null) {
            retrievingCallback.onComplete(null, new C1025o(NPFError.ErrorType.INVALID_NA_TOKEN, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "country code of Nintendo Account is unauthorized."));
        } else {
            C0927e.m1255b().mo1249a(nintendoAccount, PointProgramService.getDebugCurrentTimestamp(), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.n.1
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null) {
                        if (nPFError.getErrorCode() == 403) {
                            ((C1025o) nPFError).m1662a(NPFError.ErrorType.INVALID_NA_TOKEN);
                        }
                        retrievingCallback.onComplete(null, nPFError);
                    } else {
                        try {
                            retrievingCallback.onComplete(C1024n.this.f1634b.m1265c(jSONObject), null);
                        } catch (JSONException e) {
                            retrievingCallback.onComplete(null, C1025o.m1658a(e));
                        }
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1655a(Map<String, Long> map, @NonNull final MissionStatus.ReceivingGiftsCallback receivingGiftsCallback) {
        C0955e.m1393b(f1633a, "receiveAvailableGifts is called");
        if (!this.f1635c.mo1050d().m1633b(this.f1635c.mo1048b().m1665a())) {
            receivingGiftsCallback.onComplete(C1025o.m1656a());
            return;
        }
        NintendoAccount nintendoAccount = this.f1635c.mo1048b().m1665a().getNintendoAccount();
        if (nintendoAccount == null) {
            receivingGiftsCallback.onComplete(new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "Current BaaS User doesn't link with Nintendo Account."));
            return;
        }
        if (nintendoAccount.getCountry() == null) {
            receivingGiftsCallback.onComplete(new C1025o(NPFError.ErrorType.INVALID_NA_TOKEN, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "country code of Nintendo Account is unauthorized."));
        } else if (map == null || map.keySet().size() == 0) {
            receivingGiftsCallback.onComplete(new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_NOT_FOUND, "This mission doesn't have available gifts"));
        } else {
            C0927e.m1255b().mo1250a(nintendoAccount, map.keySet(), PointProgramService.getDebugCurrentTimestamp(), new C0918a.b() { // from class: com.nintendo.npf.sdk.internal.impl.n.2
                @Override // com.nintendo.npf.sdk.internal.p017b.p019b.C0918a.b
                /* JADX INFO: renamed from: a */
                public void mo1143a(JSONObject jSONObject, NPFError nPFError) {
                    if (nPFError != null && nPFError.getErrorCode() == 403) {
                        ((C1025o) nPFError).m1662a(NPFError.ErrorType.INVALID_NA_TOKEN);
                    }
                    receivingGiftsCallback.onComplete(nPFError);
                }
            });
        }
    }
}
