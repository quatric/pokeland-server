package com.nintendo.npf.sdk.internal.impl.cpp;

import android.app.Activity;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.NPFSDK;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class NintendoAccountEventHandler implements NPFSDK.NPFErrorCallback, NintendoAccount.AuthorizationCallback {

    /* JADX INFO: renamed from: a */
    private long f1520a;

    /* JADX INFO: renamed from: b */
    private long f1521b;

    public NintendoAccountEventHandler() {
        this.f1520a = -1L;
        this.f1521b = -1L;
    }

    public NintendoAccountEventHandler(long j, long j2) {
        this.f1520a = -1L;
        this.f1521b = -1L;
        this.f1520a = j;
        this.f1521b = j2;
    }

    public static void authorizeByNintendoAccount(long j, long j2, Activity activity, byte[] bArr) {
        NPFSDK.authorizeByNintendoAccount(activity, parseScope(new String(bArr)), null, new NintendoAccountEventHandler(j, j2));
    }

    public static void authorizeByNintendoAccount2(long j, long j2, Activity activity, byte[] bArr) {
        NPFSDK.authorizeByNintendoAccount2(activity, parseScope(new String(bArr)), null, new NintendoAccountEventHandler(j, j2));
    }

    private static native void onAuthorizedByNintendoAccountCallback(long j, long j2, String str, String str2);

    private static native void onOpenMiiStudioCallback(long j, long j2, String str);

    public static void openMiiStudio(long j, long j2, Activity activity) {
        NintendoAccount.openMiiStudio(activity, new NintendoAccountEventHandler(j, j2));
    }

    public static List<String> parseScope(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void retryPendingAuthorizationByNintendoAccount2(long j, long j2, Activity activity) {
        NPFSDK.retryPendingAuthorizationByNintendoAccount2(new NintendoAccountEventHandler(j, j2));
    }

    @Override // com.nintendo.npf.sdk.NPFSDK.NPFErrorCallback
    public void onComplete(NPFError nPFError) {
        String string = null;
        if (nPFError != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        onOpenMiiStudioCallback(this.f1520a, this.f1521b, string);
    }

    @Override // com.nintendo.npf.sdk.user.NintendoAccount.AuthorizationCallback
    public void onComplete(NintendoAccount nintendoAccount, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (nintendoAccount != null) {
            try {
                string = NativeBridgeUtil.toJsonFromNintendoAccount(nintendoAccount).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onAuthorizedByNintendoAccountCallback(this.f1520a, this.f1521b, str2, string2);
            }
        } else {
            string = null;
        }
        if (nPFError != null) {
            try {
                string2 = NativeBridgeUtil.toJsonFromNPFError(nPFError).toString();
            } catch (JSONException e2) {
                str = string;
                e = e2;
                e.printStackTrace();
                str2 = str;
            }
        }
        str2 = string;
        onAuthorizedByNintendoAccountCallback(this.f1520a, this.f1521b, str2, string2);
    }
}
