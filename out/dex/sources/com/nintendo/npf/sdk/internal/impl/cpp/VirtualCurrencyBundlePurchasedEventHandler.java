package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyBundlePurchasedEventHandler implements VirtualCurrencyWallet.RetrievingCallback {
    public static native void onRetrieveCallback(String str, String str2);

    @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
    public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
        String str;
        String string;
        String string2 = null;
        if (map != null) {
            try {
                string = NativeBridgeUtil.toJsonFromVCWallets(map).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                string = str;
                onRetrieveCallback(string, string2);
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
                string = str;
            }
        }
        onRetrieveCallback(string, string2);
    }
}
