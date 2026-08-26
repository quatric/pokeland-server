package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.user.Mii;
import com.nintendo.npf.sdk.user.OtherUser;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.i */
/* JADX INFO: compiled from: OtherUserMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0937i extends AbstractC0931c<OtherUser> {

    /* JADX INFO: renamed from: a */
    private final C0934f f1219a = new C0934f();

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.i$a */
    /* JADX INFO: compiled from: OtherUserMapper.java */
    private static class a extends OtherUser {
        private a(String str, String str2, String str3, Mii mii) {
            super(str, str2, str3, mii);
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0072  */
    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public OtherUser mo1260b(JSONObject jSONObject) throws JSONException {
        String str;
        Mii mii;
        Mii miiMo1260b = null;
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("id");
        String string2 = (!m1262a(jSONObject, "nickname") || jSONObject.getString("nickname").length() <= 0) ? null : jSONObject.getString("nickname");
        JSONObject jSONObject2 = jSONObject.getJSONObject("links");
        if (m1262a(jSONObject2, "nintendoAccount")) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject("nintendoAccount");
            if (!jSONObject3.has("userinfo") || jSONObject3.isNull("userinfo")) {
                str = null;
                mii = null;
            } else {
                JSONObject jSONObject4 = jSONObject3.getJSONObject("userinfo");
                String string3 = (!m1262a(jSONObject4, "nickname") || jSONObject4.getString("nickname").length() <= 0) ? null : jSONObject4.getString("nickname");
                if (m1262a(jSONObject4, "mii")) {
                    miiMo1260b = this.f1219a.mo1260b(jSONObject4.getJSONObject("mii"));
                }
                mii = miiMo1260b;
                str = string3;
            }
        } else {
            str = null;
            mii = null;
        }
        return new a(string, string2, str, mii);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(OtherUser otherUser) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
