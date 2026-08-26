package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.user.Gender;
import com.nintendo.npf.sdk.user.NintendoAccount;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.h */
/* JADX INFO: compiled from: NintendoAccountMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0936h extends AbstractC0931c<NintendoAccount> {

    /* JADX INFO: renamed from: a */
    private final C0934f f1218a = new C0934f();

    /* JADX WARN: Code duplicated, block: B:52:0x00df  */
    /* JADX WARN: Code duplicated, block: B:58:0x010d  */
    /* JADX WARN: Code duplicated, block: B:70:0x0143  */
    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public NintendoAccount mo1260b(JSONObject jSONObject) throws JSONException {
        String string;
        int i;
        int i2;
        String string2;
        if (jSONObject == null) {
            return null;
        }
        String string3 = jSONObject.getString("accessToken");
        String string4 = m1262a(jSONObject, "idToken") ? jSONObject.getString("idToken") : null;
        long timeInMillis = m1262a(jSONObject, "expiresIn") ? Calendar.getInstance().getTimeInMillis() + ((long) (jSONObject.getInt("expiresIn") * 1000)) : 0L;
        JSONObject jSONObject2 = jSONObject.getJSONObject("user");
        String string5 = m1262a(jSONObject2, "id") ? jSONObject2.getString("id") : null;
        NintendoAccount.Type type = NintendoAccount.Type.UNKNOWN;
        if (m1262a(jSONObject2, "isChild")) {
            type = jSONObject2.getBoolean("isChild") ? NintendoAccount.Type.CHILD : NintendoAccount.Type.GENERAL;
        }
        NintendoAccount.Type type2 = type;
        String string6 = m1262a(jSONObject2, "nickname") ? jSONObject2.getString("nickname") : null;
        Gender gender = Gender.UNKNOWN;
        if (m1262a(jSONObject2, "gender")) {
            String string7 = jSONObject2.getString("gender");
            if (string7.equals("male")) {
                gender = Gender.MALE;
            } else {
                gender = string7.equals("female") ? Gender.FEMALE : Gender.UNKNOWN;
            }
        }
        Gender gender2 = gender;
        String string8 = m1262a(jSONObject2, "language") ? jSONObject2.getString("language") : null;
        String string9 = m1262a(jSONObject2, "country") ? jSONObject2.getString("country") : null;
        String string10 = m1262a(jSONObject2, "region") ? jSONObject2.getString("region") : null;
        if (m1262a(jSONObject2, "timezone")) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject("timezone");
            if (m1262a(jSONObject3, "id")) {
                string = jSONObject3.getString("id");
            } else {
                string = null;
            }
        } else {
            string = null;
        }
        int i3 = 0;
        if (m1262a(jSONObject2, "birthday")) {
            String[] strArrSplit = jSONObject2.getString("birthday").split("-");
            if (strArrSplit.length >= 3) {
                i = Integer.parseInt(strArrSplit[0]);
                i3 = Integer.parseInt(strArrSplit[1]);
                i2 = Integer.parseInt(strArrSplit[2]);
            } else {
                i = 0;
                i2 = 0;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        String string11 = m1262a(jSONObject2, "email") ? jSONObject2.getString("email") : null;
        if (m1262a(jSONObject2, "links")) {
            JSONObject jSONObject4 = jSONObject2.getJSONObject("links");
            if (m1262a(jSONObject4, "nintendoNetwork")) {
                JSONObject jSONObject5 = jSONObject4.getJSONObject("nintendoNetwork");
                if (m1262a(jSONObject5, "id")) {
                    string2 = jSONObject5.getString("id");
                } else {
                    string2 = null;
                }
            } else {
                string2 = null;
            }
        } else {
            string2 = null;
        }
        return new NintendoAccount(string5, type2, string6, gender2, string8, string9, string10, string, i, i3, i2, string11, string2, m1262a(jSONObject2, "mii") ? this.f1218a.mo1260b(jSONObject2.getJSONObject("mii")) : null, string4, string3, timeInMillis);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(NintendoAccount nintendoAccount) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
