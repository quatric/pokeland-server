package com.nintendo.npf.sdk.internal.impl;

import android.text.TextUtils;
import com.google.api.client.http.HttpStatusCodes;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.p023e.C0951a;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.impl.o */
/* JADX INFO: compiled from: NPFErrorImpl.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C1025o extends NPFError {
    public C1025o(NPFError.ErrorType errorType, int i, String str) {
        this.errorType = errorType;
        this.errorCode = i;
        this.errorMessage = str;
    }

    /* JADX INFO: renamed from: a */
    public static NPFError m1656a() {
        return new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_UNAUTHORIZED, "User is not logged in");
    }

    /* JADX INFO: renamed from: a */
    public static NPFError m1657a(int i, String str) {
        if (i == 0) {
            return new C1025o(NPFError.ErrorType.NETWORK_ERROR, i, str);
        }
        NPFError.ErrorType errorType = NPFError.ErrorType.NPF_ERROR;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (C0951a.m1385a(jSONObject, "code") && C0951a.m1385a(jSONObject, "message")) {
                    i = jSONObject.getInt("code");
                    str = jSONObject.getString("message");
                }
            } catch (JSONException unused) {
            }
        }
        return new C1025o(errorType, i, str);
    }

    /* JADX INFO: renamed from: a */
    public static NPFError m1658a(JSONException jSONException) {
        return new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_UNPROCESSABLE_ENTITY, jSONException.getMessage());
    }

    /* JADX INFO: renamed from: b */
    public static NPFError m1659b() {
        return new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_FORBIDDEN, "Browser is not available.");
    }

    /* JADX INFO: renamed from: c */
    public static NPFError m1660c() {
        return new C1025o(NPFError.ErrorType.NPF_ERROR, 4001, "NintendoAccount is not authorized.");
    }

    /* JADX INFO: renamed from: d */
    public static NPFError m1661d() {
        return new C1025o(NPFError.ErrorType.NPF_ERROR, HttpStatusCodes.STATUS_CODE_BAD_REQUEST, "Invalid parameters");
    }

    /* JADX INFO: renamed from: a */
    public void m1662a(NPFError.ErrorType errorType) {
        this.errorType = errorType;
    }
}
