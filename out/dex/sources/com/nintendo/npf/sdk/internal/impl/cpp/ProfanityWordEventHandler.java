package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.audit.ProfanityWord;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class ProfanityWordEventHandler implements ProfanityWord.CheckProfanityWordCallback {

    /* JADX INFO: renamed from: a */
    private long f1527a;

    /* JADX INFO: renamed from: b */
    private long f1528b;

    public ProfanityWordEventHandler() {
        this.f1527a = -1L;
        this.f1528b = -1L;
    }

    public ProfanityWordEventHandler(long j, long j2) {
        this.f1527a = -1L;
        this.f1528b = -1L;
        this.f1527a = j;
        this.f1528b = j2;
    }

    public static void checkProfanityWord(long j, long j2, byte[] bArr) {
        ProfanityWord.checkProfanityWord(parseWordList(new String(bArr)), new ProfanityWordEventHandler(j, j2));
    }

    private static native void onRetrieveCallback(long j, long j2, String str, String str2);

    public static List<ProfanityWord> parseWordList(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("language");
                String string2 = jSONObject.getString("text");
                ProfanityWord.ProfanityDictionaryType profanityDictionaryType = null;
                if (jSONObject.has("dictionaryType")) {
                    String string3 = jSONObject.getString("dictionaryType");
                    if (string3.equals("nickname")) {
                        profanityDictionaryType = ProfanityWord.ProfanityDictionaryType.NICKNAME;
                    } else if (string3.equals("common")) {
                        profanityDictionaryType = ProfanityWord.ProfanityDictionaryType.COMMON;
                    }
                }
                arrayList.add(new ProfanityWord(string, string2, profanityDictionaryType));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override // com.nintendo.npf.sdk.audit.ProfanityWord.CheckProfanityWordCallback
    public void onComplete(List<ProfanityWord> list, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (list != null) {
            try {
                string = NativeBridgeUtil.toJsonFromWordList(list).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onRetrieveCallback(this.f1527a, this.f1528b, str2, string2);
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
        onRetrieveCallback(this.f1527a, this.f1528b, str2, string2);
    }
}
