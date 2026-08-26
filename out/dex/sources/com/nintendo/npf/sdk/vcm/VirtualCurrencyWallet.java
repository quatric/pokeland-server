package com.nintendo.npf.sdk.vcm;

import android.support.annotation.RestrictTo;
import com.nintendo.npf.sdk.NPFError;
import com.nintendo.npf.sdk.internal.InterfaceC0875a;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class VirtualCurrencyWallet {
    private int freeBalance;
    private Map<String, Integer> paidBalance;
    private int totalBalance;
    private String virtualCurrencyName;

    public interface RetrievingCallback {
        void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError);
    }

    /* JADX INFO: renamed from: com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet$a */
    private static class C1079a {

        /* JADX INFO: renamed from: a */
        static final InterfaceC0875a f1835a = InterfaceC0875a.a.m1072b();
    }

    protected VirtualCurrencyWallet(String str, int i, int i2, Map<String, Integer> map) {
        this.virtualCurrencyName = str;
        this.totalBalance = i;
        this.freeBalance = i2;
        this.paidBalance = map;
    }

    public static void getAll(final RetrievingCallback retrievingCallback) {
        C1079a.f1835a.mo1061o().m1562a(new RetrievingCallback() { // from class: com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.1
            @Override // com.nintendo.npf.sdk.vcm.VirtualCurrencyWallet.RetrievingCallback
            public void onComplete(Map<String, VirtualCurrencyWallet> map, NPFError nPFError) {
                RetrievingCallback retrievingCallback2 = retrievingCallback;
                if (retrievingCallback2 != null) {
                    retrievingCallback2.onComplete(map, nPFError);
                }
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static VirtualCurrencyWallet internalCreate(String str, int i, int i2, Map<String, Integer> map) {
        return new VirtualCurrencyWallet(str, i, i2, map);
    }

    public int getFreeBalance() {
        return this.freeBalance;
    }

    public Map<String, Integer> getPaidBalance() {
        return this.paidBalance;
    }

    public int getTotalBalance() {
        return this.totalBalance;
    }

    public String getVirtualCurrencyName() {
        return this.virtualCurrencyName;
    }
}
