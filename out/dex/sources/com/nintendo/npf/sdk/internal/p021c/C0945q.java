package com.nintendo.npf.sdk.internal.p021c;

import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.q */
/* JADX INFO: compiled from: VirtualCurrencyWalletMapper.java */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class C0945q extends AbstractC0931c<VirtualCurrencyWallet> {

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.internal.c.q$a */
    /* JADX INFO: compiled from: VirtualCurrencyWalletMapper.java */
    private static class a extends VirtualCurrencyWallet {
        private a(String str, int i, int i2, Map<String, Integer> map) {
            super(str, i, i2, map);
        }
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public VirtualCurrencyWallet mo1260b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("virtualCurrencyName");
        JSONObject jSONObject2 = jSONObject.getJSONObject("balance");
        int i = jSONObject2.getInt("total");
        int i2 = jSONObject2.getInt("free");
        JSONArray jSONArray = jSONObject2.getJSONArray("paid");
        HashMap map = new HashMap();
        for (int i3 = 0; i3 < jSONArray.length(); i3++) {
            JSONObject jSONObject3 = jSONArray.getJSONObject(i3);
            map.put(jSONObject3.getString("code"), Integer.valueOf(jSONObject3.getInt("total")));
        }
        return new a(string, i, i2, map);
    }

    @Override // com.nintendo.npf.sdk.internal.p021c.AbstractC0931c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public JSONObject mo1259a(VirtualCurrencyWallet virtualCurrencyWallet) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
