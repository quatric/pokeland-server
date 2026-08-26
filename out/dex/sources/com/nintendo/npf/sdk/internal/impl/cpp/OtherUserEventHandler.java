package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.user.OtherUser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class OtherUserEventHandler implements OtherUser.RetrievingCallback {

    /* JADX INFO: renamed from: a */
    private long f1522a;

    /* JADX INFO: renamed from: b */
    private long f1523b;

    public OtherUserEventHandler() {
        this.f1522a = -1L;
        this.f1523b = -1L;
    }

    public OtherUserEventHandler(long j, long j2) {
        this.f1522a = -1L;
        this.f1523b = -1L;
        this.f1522a = j;
        this.f1523b = j2;
    }

    public static void getAsList(long j, long j2, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OtherUser.getAsList(arrayList, new OtherUserEventHandler(j, j2));
    }

    private static native void onRetrieveCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.user.OtherUser.RetrievingCallback
    public void onComplete(List<OtherUser> list, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (list != null) {
            try {
                string = NativeBridgeUtil.toJsonFromOtherUsers(list).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onRetrieveCallback(this.f1522a, this.f1523b, str2, string2);
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
        onRetrieveCallback(this.f1522a, this.f1523b, str2, string2);
    }
}
