package com.nintendo.npf.sdk.internal.p021c;

import android.util.Base64;
import com.nintendo.npf.sdk.user.Mii;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.f */
/* JADX INFO: compiled from: MiiMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0934f extends AbstractC0931c<Mii> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.f$a */
    /* JADX INFO: compiled from: MiiMapper.java */
    private static class a extends Mii {
        private a(String str, String str2, byte[] bArr, byte[] bArr2, String str3, String str4, String str5) {
            super(str, str2, bArr, bArr2, str3, str4, str5);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Mii mo1260b(JSONObject jSONObject) throws JSONException {
        byte[] bArrDecode;
        byte[] bArrDecode2 = null;
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("imageUriTemplate");
        String string2 = jSONObject.getString("id");
        String string3 = jSONObject.getString("imageOrigin");
        String string4 = jSONObject.getString("etag");
        String string5 = m1262a(jSONObject, "favoriteColor") ? jSONObject.getString("favoriteColor") : null;
        if (m1262a(jSONObject, "coreData")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("coreData");
            bArrDecode = Base64.decode(jSONObject2.getString(jSONObject2.keys().next()), 0);
        } else {
            bArrDecode = null;
        }
        if (m1262a(jSONObject, "storeData")) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("storeData");
            bArrDecode2 = Base64.decode(jSONObject3.getString(jSONObject3.keys().next()), 0);
        }
        return new a(string, string2, bArrDecode, bArrDecode2, string3, string4, string5);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(Mii mii) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
