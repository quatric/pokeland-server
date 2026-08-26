package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.audit.ProfanityWord;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.j */
/* JADX INFO: compiled from: ProfanityWordMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0938j extends AbstractC0931c<ProfanityWord> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.j$a */
    /* JADX INFO: compiled from: ProfanityWordMapper.java */
    private static class a extends ProfanityWord {
        private a(String str, String str2, ProfanityWord.ProfanityDictionaryType profanityDictionaryType, ProfanityWord.ProfanityCheckStatus profanityCheckStatus) {
            super(str, str2, profanityDictionaryType, profanityCheckStatus);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public ProfanityWord mo1260b(JSONObject jSONObject) throws JSONException {
        ProfanityWord.ProfanityDictionaryType profanityDictionaryType = null;
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("language");
        String string2 = jSONObject.getString("text");
        if (jSONObject.has("dictionaryType")) {
            String string3 = jSONObject.getString("dictionaryType");
            if (string3.equals("nickname")) {
                profanityDictionaryType = ProfanityWord.ProfanityDictionaryType.NICKNAME;
            } else if (string3.equals("common")) {
                profanityDictionaryType = ProfanityWord.ProfanityDictionaryType.COMMON;
            }
        }
        return new a(string, string2, profanityDictionaryType, jSONObject.getBoolean("valid") ? ProfanityWord.ProfanityCheckStatus.VALID : ProfanityWord.ProfanityCheckStatus.INVALID);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(ProfanityWord profanityWord) {
        if (profanityWord == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("language", profanityWord.getLanguage());
            jSONObject.put("text", profanityWord.getText());
            if (profanityWord.getDictionaryType() == ProfanityWord.ProfanityDictionaryType.NICKNAME) {
                jSONObject.put("dictionaryType", "nickname");
            } else if (profanityWord.getDictionaryType() == ProfanityWord.ProfanityDictionaryType.COMMON) {
                jSONObject.put("dictionaryType", "common");
            }
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }
}
