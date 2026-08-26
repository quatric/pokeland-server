package com.nintendo.npf.sdk.internal.impl.cpp;

import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.impl.NativeBridgeUtil;
import com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyWalletEventHandler implements VirtualCurrencyWallet.RetrievingCallback {

    /* JADX INFO: renamed from: a */
    private long f1547a;

    /* JADX INFO: renamed from: b */
    private long f1548b;

    public VirtualCurrencyWalletEventHandler() {
        this.f1547a = -1L;
        this.f1548b = -1L;
    }

    public VirtualCurrencyWalletEventHandler(long j, long j2) {
        this.f1547a = -1L;
        this.f1548b = -1L;
        this.f1547a = j;
        this.f1548b = j2;
    }

    public static void getAll(long j, long j2) {
        VirtualCurrencyWallet.getAll(new VirtualCurrencyWalletEventHandler(j, j2));
    }

    public static native void onRetrieveCallback(long j, long j2, String str, String str2);

    @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
    public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
        String str;
        String string;
        String str2;
        String string2 = null;
        if (map != null) {
            try {
                string = NativeBridgeUtil.toJsonFromVCWallets(map).toString();
            } catch (JSONException e) {
                e = e;
                str = null;
                e.printStackTrace();
                str2 = str;
                onRetrieveCallback(this.f1547a, this.f1548b, str2, string2);
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
        onRetrieveCallback(this.f1547a, this.f1548b, str2, string2);
    }
}
